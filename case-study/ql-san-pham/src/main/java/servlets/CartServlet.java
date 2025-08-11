package servlets;

import dao.CartDAO;
import dao.ProductDAO;
import entities.CartItem;
import entities.Product;
import entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cart/*")
public class CartServlet extends HttpServlet {
    private CartDAO cartDAO = new CartDAO();
    private ProductDAO productDAO = new ProductDAO();

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
        List<CartItem> cart = cartDAO.getCartByUser(u.getId());

        // Build richer objects for JSP: cartItems with product + subtotal
        List<Object> cartItems = new ArrayList<>();
        double total = 0;
        for (CartItem c : cart) {
            Product p = productDAO.findById(c.getProductId());
            double sub = (p != null) ? p.getPrice() * c.getQuantity() : 0.0;
            total += sub;
            // use a simple map-like object via attributes (could create a DTO)
            java.util.Map<String, Object> map = new java.util.HashMap<>();
            map.put("id", c.getId());
            map.put("product", p);
            map.put("quantity", c.getQuantity());
            map.put("subtotal", sub);
            cartItems.add(map);
        }
        req.setAttribute("cartItems", cartItems);
        req.setAttribute("total", total);
        req.getRequestDispatcher("/views/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getPathInfo();
        if (action == null) action = "/add";
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }
        User u = (User) session.getAttribute("user");

        if (action.equals("/add")) {
            Integer pid = parseIntSafe(req.getParameter("productId"));
            Integer qty = parseIntSafe(req.getParameter("quantity"));
            if (pid == null) { resp.sendRedirect(req.getContextPath() + "/products"); return; }
            if (qty == null || qty <= 0) qty = 1;
            Product p = productDAO.findById(pid);
            if (p == null) { resp.sendRedirect(req.getContextPath() + "/products"); return; }
            if (p.getStock() < qty) {
                // pass an error message and redirect to detail (via query param)
                resp.sendRedirect(req.getContextPath() + "/products/detail?id=" + pid + "&error=out_of_stock");
                return;
            }
            cartDAO.addOrIncrease(u.getId(), pid, qty);
            resp.sendRedirect(req.getContextPath() + "/cart");
        } else if (action.equals("/update")) {
            Integer cartId = parseIntSafe(req.getParameter("cartId"));
            Integer qty = parseIntSafe(req.getParameter("quantity"));
            if (cartId == null || qty == null) { resp.sendRedirect(req.getContextPath() + "/cart"); return; }
            // ownership check
            entities.CartItem item = cartDAO.findById(cartId);
            if (item == null || item.getUserId() != u.getId()) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            // check stock
            Product p = productDAO.findById(item.getProductId());
            if (p == null || p.getStock() < qty) {
                resp.sendRedirect(req.getContextPath() + "/cart?error=out_of_stock");
                return;
            }
            cartDAO.updateQuantity(cartId, qty);
            resp.sendRedirect(req.getContextPath() + "/cart");
        } else if (action.equals("/remove")) {
            Integer cartId = parseIntSafe(req.getParameter("cartId"));
            if (cartId == null) { resp.sendRedirect(req.getContextPath() + "/cart"); return; }
            CartItem item = cartDAO.findById(cartId);
            if (item == null || item.getUserId() != u.getId()) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            cartDAO.delete(cartId);
            resp.sendRedirect(req.getContextPath() + "/cart");
        } else if (action.equals("/clear")) {
            cartDAO.clearCart(u.getId());
            resp.sendRedirect(req.getContextPath() + "/cart");
        } else {
            resp.sendError(404);
        }
    }
}
