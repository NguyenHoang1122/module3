package org.example.demojdbc.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private final String jdbcUrl = "jdbc:mysql://localhost:3306/quanlyhocsinh?useSSl=false";
    private final String username = "root";
    private final String password = "Hoangcuong93";


    public DBConnect() {}

    public Connection getConnect() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("connect success");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return conn;
    }
}