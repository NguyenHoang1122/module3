package servlets;

import dao.CategoryDAO;
import dao.ProductDAO;
import entities.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/products/*")
public class ProductServlet extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();

    private Integer parseIntSafe(String s) {
        try { return Integer.parseInt(s); } catch (Exception e) { return null; }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();
        if (action == null || action.equals("/") || action.equals("/list")) {
            listProducts(req, resp);
        } else if (action.equals("/detail")) {
            productDetail(req, resp);
        } else if (action.equals("/search")) {
            search(req, resp);
        } else if (action.equals("/category")) {
            category(req, resp);
        } else {
            resp.sendError(404);
        }
    }

    private void listProducts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> list = productDAO.getAll();
        req.setAttribute("products", list);
        req.setAttribute("categories", categoryDAO.getAll());
        req.getRequestDispatcher("/views/product/list.jsp").forward(req, resp);
    }

    private void productDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sid = req.getParameter("id");
        Integer id = parseIntSafe(sid);
        if (id == null) { resp.sendRedirect(req.getContextPath() + "/products"); return; }
        Product p = productDAO.findById(id);
        if (p == null) { resp.sendRedirect(req.getContextPath() + "/products"); return; }
        req.setAttribute("product", p);
        req.getRequestDispatcher("/views/product/detail.jsp").forward(req, resp);
    }

    private void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String q = req.getParameter("q");
        List<Product> list = productDAO.searchByName(q == null ? "" : q);
        req.setAttribute("products", list);
        req.setAttribute("categories", categoryDAO.getAll());
        req.getRequestDispatcher("/views/product/list.jsp").forward(req, resp);
    }

    private void category(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cid = req.getParameter("cid");
        Integer id = parseIntSafe(cid);
        if (id == null) { resp.sendRedirect(req.getContextPath() + "/products"); return; }
        List<Product> list = productDAO.findByCategory(id);
        req.setAttribute("products", list);
        req.setAttribute("categories", categoryDAO.getAll());
        req.getRequestDispatcher("/views/product/list.jsp").forward(req, resp);
    }
}
