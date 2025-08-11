package servlets;

import dao.OrderDAO;
import entities.Order;
import entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/orders/*")
public class AdminOrderServlet extends HttpServlet {
    private OrderDAO orderDAO = new OrderDAO();

    private boolean isAdmin(HttpSession session) {
        if (session == null) return false;
        User u = (User) session.getAttribute("user");
        return u != null && "admin".equals(u.getRole());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req.getSession(false))) { resp.sendRedirect(req.getContextPath() + "/auth/login"); return; }
        List<Order> orders = orderDAO.getAllOrders();
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/views/admin/order-list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req.getSession(false))) { resp.sendRedirect(req.getContextPath() + "/auth/login"); return; }
        String action = req.getPathInfo();
        if (action == null) action = "/updateStatus";
        if (action.equals("/updateStatus")) {
            try {
                int orderId = Integer.parseInt(req.getParameter("orderId"));
                String status = req.getParameter("status");
                if ("cancelled".equals(status)) {
                    orderDAO.cancelOrder(orderId);
                } else {
                    // optional: support other statuses (completed)
                }
            } catch (Exception ignored) {}
            resp.sendRedirect(req.getContextPath() + "/admin/orders");
        } else {
            resp.sendError(404);
        }
    }
}
