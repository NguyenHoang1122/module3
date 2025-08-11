package dao;

import config.DBConnection;
import entities.User;
import util.PasswordUtil;

import java.sql.*;

public class UserDAO {

    public User findByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setId(rs.getInt("id"));
                    u.setName(rs.getString("name"));
                    u.setEmail(rs.getString("email"));
                    u.setPassword(rs.getString("password"));
                    u.setPhone(rs.getString("phone"));
                    u.setAddress(rs.getString("address"));
                    u.setRole(rs.getString("role"));
                    return u;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User authenticate(String email, String password) {
        User u = findByEmail(email);
        if (u != null) {
            if (PasswordUtil.verify(password, u.getPassword())) {
                u.setPassword(null);
                return u;
            }
        }
        return null;
    }

    public boolean isEmailOrPhoneExists(String email, String phone) {
        String sql = "SELECT COUNT(*) FROM Users WHERE email = ? OR phone = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, phone);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean createUser(User u) {
        String sql = "INSERT INTO Users (name, password, phone, email, address, role) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getName());
            ps.setString(2, PasswordUtil.hash(u.getPassword())); // hash before store
            ps.setString(3, u.getPhone());
            ps.setString(4, u.getEmail());
            ps.setString(5, u.getAddress());
            ps.setString(6, u.getRole() == null ? "user" : u.getRole());
            int a = ps.executeUpdate();
            if (a > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) u.setId(rs.getInt(1));
                }
                u.setPassword(null);
                return true;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}
