package models;

import entities.Product;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductModel extends BaseModel {

    public List<Product> getAllProducts() throws SQLException, ClassNotFoundException {
        String sql = "SELECT p.id, p.name, p.price, p.quantity, p.color, c.name AS category_name\n" +
                " FROM Product p\n" +
                "JOIN category c ON p.c_id = c.id\n" +
                "GROUP BY p.id;";

        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        List<Product> products = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            double price = rs.getDouble("price");
            int quantity = rs.getInt("quantity");
            String color = rs.getString("color");
            String categoryName = rs.getString("category_name");
            Product product = new Product(id, name, price, quantity, color, categoryName);
            products.add(product);
        }
        return products;
    }

    public void createProduct(Product product, int categoryId) throws SQLException {
        String sql = "INSERT INTO Product(name, price, quantity, color, c_id) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, product.getName());
        statement.setDouble(2, product.getPrice());
        statement.setInt(3, product.getQuantity());
        statement.setString(4, product.getColor());
        statement.setInt(5, categoryId);
        statement.executeUpdate();
        statement.close();
    }

    public Product findById(int id) throws SQLException {
        String sql = "SELECT p.id, p.name, p.price, p.quantity, p.color, c.name AS category_name " +
                "FROM Product p JOIN category c ON p.c_id = c.id WHERE p.id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        Product product = null;
        if (rs.next()) {
            product = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"),
                    rs.getString("color"),
                    rs.getString("category_name")
            );
        }
        rs.close();
        statement.close();
        return product;
    }

    public void updateProduct(Product product, int categoryId) throws SQLException {
        String sql = "UPDATE Product SET name = ?, price = ?, quantity = ?, color = ?, c_id = ? WHERE id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, product.getName());
        statement.setDouble(2, product.getPrice());
        statement.setInt(3, product.getQuantity());
        statement.setString(4, product.getColor());
        statement.setInt(5, categoryId);
        statement.setInt(6, product.getId());
        statement.executeUpdate();
        statement.close();
    }

    public void deleteProduct(int id) throws SQLException {
        String sql = "DELETE FROM Product WHERE id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
        statement.close();
    }
}
