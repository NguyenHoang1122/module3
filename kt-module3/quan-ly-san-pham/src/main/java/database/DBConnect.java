package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/quan_ly_san_pham?useSSL=false";
    private final String username = "root";
    private final String password = "Hoangcuong93";

    public DBConnect() {}

    public Connection getConnect(){
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcURL, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
}