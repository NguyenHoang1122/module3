package servlets;

import dao.CategoryDAO;
import entities.Category;
import entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/categories/*")
public class AdminCategoryServlet extends HttpServlet {
    private CategoryDAO categoryDAO = new CategoryDAO();

    private boolean isAdmin(HttpSession session) {
        if (session == null) return false;
        User u = (User) session.getAttribute("user");
        return u != null && "admin".equals(u.getRole());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req.getSession(false))) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }
        String action = req.getPathInfo();
        if (action == null || action.equals("/") || action.equals("/list")) {
            List<Category> list = categoryDAO.getAll();
            req.setAttribute("categories", list);
            req.getRequestDispatcher("/views/admin/category-list.jsp").forward(req, resp);
        } else if (action.equals("/create")) {
            req.getRequestDispatcher("/views/admin/category-form.jsp").forward(req, resp);
        } else if (action.equals("/delete")) {
            String sid = req.getParameter("id");
            try {
                int id = Integer.parseInt(sid);
                categoryDAO.delete(id);
            } catch (Exception ex) {
            }
            resp.sendRedirect(req.getContextPath() + "/admin/categories");
        } else {
            resp.sendError(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req.getSession(false))) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }
        String action = req.getPathInfo();
        if (action == null) action = "/store";
        if (action.equals("/store")) {
            String name = req.getParameter("name");
            if (name != null && !name.trim().isEmpty()) {
                Category c = new Category();
                c.setName(name.trim());
                categoryDAO.create(c);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/categories");
        } else if (action.equals("/update")) {
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                String name = req.getParameter("name");
                Category c = new Category();
                c.setId(id); c.setName(name);
                categoryDAO.update(c);
            } catch (Exception ex) { }
            resp.sendRedirect(req.getContextPath() + "/admin/categories");
        } else {
            resp.sendError(404);
        }
    }
}
