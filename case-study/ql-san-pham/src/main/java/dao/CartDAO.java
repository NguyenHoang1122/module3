package dao;

import entities.CartItem;
import config.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    public CartItem findByUserAndProduct(int userId, int productId) {
        String sql = "SELECT * FROM cart WHERE user_id = ? AND product_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    CartItem c = new CartItem();
                    c.setId(rs.getInt("id"));
                    c.setUserId(userId);
                    c.setProductId(productId);
                    c.setQuantity(rs.getInt("quantity"));
                    return c;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public CartItem findById(int cartId) {
        String sql = "SELECT * FROM cart WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    CartItem c = new CartItem();
                    c.setId(rs.getInt("id"));
                    c.setUserId(rs.getInt("user_id"));
                    c.setProductId(rs.getInt("product_id"));
                    c.setQuantity(rs.getInt("quantity"));
                    return c;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean addOrIncrease(int userId, int productId, int quantity) {
        CartItem exists = findByUserAndProduct(userId, productId);
        if (exists != null) {
            return updateQuantity(exists.getId(), exists.getQuantity() + quantity);
        } else {
            String sql = "INSERT INTO cart (user_id, product_id, quantity) VALUES (?, ?, ?)";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, userId);
                ps.setInt(2, productId);
                ps.setInt(3, quantity);
                int a = ps.executeUpdate();
                return a > 0;
            } catch (SQLException e) { e.printStackTrace(); }
        }
        return false;
    }

    public boolean updateQuantity(int cartId, int newQty) {
        if (newQty <= 0) return delete(cartId);
        String sql = "UPDATE cart SET quantity = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newQty);
            ps.setInt(2, cartId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean delete(int cartId) {
        String sql = "DELETE FROM cart WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public List<CartItem> getCartByUser(int userId) {
        List<CartItem> list = new ArrayList<>();
        String sql = "SELECT * FROM cart WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CartItem c = new CartItem();
                    c.setId(rs.getInt("id"));
                    c.setUserId(userId);
                    c.setProductId(rs.getInt("product_id"));
                    c.setQuantity(rs.getInt("quantity"));
                    list.add(c);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean clearCart(int userId) {
        String sql = "DELETE FROM cart WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}
