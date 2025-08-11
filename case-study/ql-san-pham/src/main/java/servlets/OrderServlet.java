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

@WebServlet("/orders/*")
public class OrderServlet extends HttpServlet {
    private OrderDAO orderDAO = new OrderDAO();

    private Integer parseIntSafe(String s) {
        try { return Integer.parseInt(s); } catch (Exception e) { return null; }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }
        User u = (User) session.getAttribute("user");
        List<Order> orders = orderDAO.getOrdersByUser(u.getId());
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/views/order/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getPathInfo();
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }
        User u = (User) session.getAttribute("user");

        if (action == null || action.equals("/place")) {
            boolean ok = orderDAO.placeOrder(u.getId());
            if (ok) {
                resp.sendRedirect(req.getContextPath() + "/orders");
            } else {
                // better to redirect back to cart with error flag
                resp.sendRedirect(req.getContextPath() + "/cart?error=order_failed");
            }
        } else if (action.equals("/cancel")) {
            Integer orderId = parseIntSafe(req.getParameter("orderId"));
            if (orderId == null) { resp.sendRedirect(req.getContextPath() + "/orders"); return; }

            Order o = orderDAO.findById(orderId);
            if (o == null || o.getUserId() != u.getId()) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            orderDAO.cancelOrder(orderId);
            resp.sendRedirect(req.getContextPath() + "/orders");
        } else {
            resp.sendError(404);
        }
    }
}
