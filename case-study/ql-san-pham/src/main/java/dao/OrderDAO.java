package dao;

import entities.CartItem;
import entities.Order;
import entities.Product;
import config.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private CartDAO cartDAO = new CartDAO();
    private ProductDAO productDAO = new ProductDAO();

    public boolean placeOrder(int userId) {
        List<CartItem> cart = cartDAO.getCartByUser(userId);
        if (cart.isEmpty()) return false;

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            double total = 0.0;
            // 1) Validate stock & compute total using same connection
            for (CartItem c : cart) {
                Product p = productDAO.findById(conn, c.getProductId());
                if (p == null) { conn.rollback(); return false; }
                if (p.getStock() < c.getQuantity()) { conn.rollback(); return false; }
                total += p.getPrice() * c.getQuantity();
            }

            String sqlOrder = "INSERT INTO orders (user_id, total_amount, status) VALUES (?, ?, 'pending')";
            try (PreparedStatement ps = conn.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, userId);
                ps.setDouble(2, total);
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int orderId = rs.getInt(1);

                        String sqlDetail = "INSERT INTO order_details (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement psd = conn.prepareStatement(sqlDetail)) {
                            for (CartItem c : cart) {
                                Product p = productDAO.findById(conn, c.getProductId()); // reuse conn
                                psd.setInt(1, orderId);
                                psd.setInt(2, c.getProductId());
                                psd.setInt(3, c.getQuantity());
                                psd.setDouble(4, p.getPrice());
                                psd.addBatch();

                                // update stock
                                String sqlUpdateStock = "UPDATE products SET stock = stock - ? WHERE id = ?";
                                try (PreparedStatement psu = conn.prepareStatement(sqlUpdateStock)) {
                                    psu.setInt(1, c.getQuantity());
                                    psu.setInt(2, c.getProductId());
                                    psu.executeUpdate();
                                }
                            }
                            psd.executeBatch();
                        }
                        // clear cart
                        String sqlClear = "DELETE FROM cart WHERE user_id = ?";
                        try (PreparedStatement psc = conn.prepareStatement(sqlClear)) {
                            psc.setInt(1, userId);
                            psc.executeUpdate();
                        }
                        conn.commit();
                        return true;
                    } else {
                        conn.rollback();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
        } finally {
            try { if (conn != null) { conn.setAutoCommit(true); conn.close(); } } catch (SQLException e) { e.printStackTrace(); }
        }
        return false;
    }

    public List<Order> getOrdersByUser(int userId) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order o = new Order();
                    o.setId(rs.getInt("id"));
                    o.setUserId(rs.getInt("user_id"));
                    o.setTotalAmount(rs.getDouble("total_amount"));
                    o.setStatus(rs.getString("status"));
                    o.setCreatedAt(rs.getTimestamp("created_at"));
                    list.add(o);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("id"));
                o.setUserId(rs.getInt("user_id"));
                o.setTotalAmount(rs.getDouble("total_amount"));
                o.setStatus(rs.getString("status"));
                o.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(o);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean cancelOrder(int orderId) {
        String sql = "UPDATE orders SET status = 'cancelled' WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public Order findById(int orderId) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Order o = new Order();
                    o.setId(rs.getInt("id"));
                    o.setUserId(rs.getInt("user_id"));
                    o.setTotalAmount(rs.getDouble("total_amount"));
                    o.setStatus(rs.getString("status"));
                    o.setCreatedAt(rs.getTimestamp("created_at"));
                    return o;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}
