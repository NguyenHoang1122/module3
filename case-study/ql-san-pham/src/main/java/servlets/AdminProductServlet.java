package servlets;

import dao.CategoryDAO;
import dao.ProductDAO;
import entities.Product;
import entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/products/*")
public class AdminProductServlet extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();

    private boolean isAdmin(HttpSession session) {
        if (session == null) return false;
        User u = (User) session.getAttribute("user");
        return u != null && "admin".equals(u.getRole());
    }

    private Integer parseIntSafe(String s) {
        try { return Integer.parseInt(s); } catch (Exception e) { return null; }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req.getSession(false))) { resp.sendRedirect(req.getContextPath() + "/auth/login"); return; }
        String action = req.getPathInfo();
        if (action == null || action.equals("/") || action.equals("/list")) {
            List<Product> list = productDAO.getAll();
            req.setAttribute("products", list);
            req.getRequestDispatcher("/views/admin/product-list.jsp").forward(req, resp);
        } else if (action.equals("/create")) {
            req.setAttribute("categories", categoryDAO.getAll());
            req.getRequestDispatcher("/views/admin/product-form.jsp").forward(req, resp);
        } else if (action.equals("/edit")) {
            Integer id = parseIntSafe(req.getParameter("id"));
            if (id == null) { resp.sendRedirect(req.getContextPath() + "/admin/products"); return; }
            Product p = productDAO.findById(id);
            req.setAttribute("product", p);
            req.setAttribute("categories", categoryDAO.getAll());
            req.getRequestDispatcher("/views/admin/product-form.jsp").forward(req, resp);
        } else if (action.equals("/delete")) {

            Integer id = parseIntSafe(req.getParameter("id"));
            if (id != null) productDAO.delete(id);
            resp.sendRedirect(req.getContextPath() + "/admin/products");
        } else {
            resp.sendError(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req.getSession(false))) { resp.sendRedirect(req.getContextPath() + "/auth/login"); return; }
        String action = req.getPathInfo();
        if (action == null) action = "/store";
        if (action.equals("/store")) {
            String name = req.getParameter("name");
            Double price = null;
            try { price = Double.parseDouble(req.getParameter("price")); } catch (Exception ignored) {}
            String description = req.getParameter("description");
            String image = req.getParameter("image");
            Integer stock = null;
            try { stock = Integer.parseInt(req.getParameter("stock")); } catch (Exception ignored) {}
            String cid = req.getParameter("category_id");
            Integer categoryId = (cid == null || cid.isEmpty()) ? null : Integer.parseInt(cid);

            Product p = new Product();
            p.setName(name);
            p.setPrice(price == null ? 0.0 : price);
            p.setDescription(description);
            p.setImage(image);
            p.setStock(stock == null ? 0 : stock);
            p.setCategoryId(categoryId);
            productDAO.create(p);
            resp.sendRedirect(req.getContextPath() + "/admin/products");
        } else if (action.equals("/update")) {
            Integer id = null;
            try { id = Integer.parseInt(req.getParameter("id")); } catch (Exception ignored) {}
            if (id != null) {
                Product p = productDAO.findById(id);
                if (p != null) {
                    p.setName(req.getParameter("name"));
                    try { p.setPrice(Double.parseDouble(req.getParameter("price"))); } catch (Exception ignored) {}
                    p.setDescription(req.getParameter("description"));
                    p.setImage(req.getParameter("image"));
                    try { p.setStock(Integer.parseInt(req.getParameter("stock"))); } catch (Exception ignored) {}
                    String cid = req.getParameter("category_id");
                    p.setCategoryId((cid == null || cid.isEmpty()) ? null : Integer.parseInt(cid));
                    productDAO.update(p);
                }
            }
            resp.sendRedirect(req.getContextPath() + "/admin/products");
        } else {
            resp.sendError(404);
        }
    }
}
