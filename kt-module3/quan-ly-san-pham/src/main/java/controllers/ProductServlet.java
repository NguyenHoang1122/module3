package controllers;

import database.DBConnect;
import entities.Product;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.ProductModel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ProductServlet", urlPatterns = {"/products/*"})
public class ProductServlet extends HttpServlet {
    Connection conn = null;
    ProductModel productModel;

    @Override
    public void init() throws ServletException {
        DBConnect dbConnect = new DBConnect();
        conn = dbConnect.getConnect();

        productModel = new ProductModel();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();
        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "/":
                case "":
                    showListProduct(req, resp);
                    break;
                case "/create":
                    showCreateForm(req, resp);
                    break;
                case "/edit":
                    showEditForm(req, resp);
                    break;
                case "/delete":
                    deleteProduct(req, resp);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "/store":
                    insertProduct(req, resp);
                    break;
                case "/update":
                    updateProduct(req, resp);
                    break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void showListProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Product> List = productModel.getAllProducts();
            req.setAttribute("listProducts", List);
            RequestDispatcher rd = req.getRequestDispatcher("/views/products/list.jsp");
            rd.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showCreateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/products/create.jsp").forward(req, resp);
    }

    private void insertProduct(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String name = req.getParameter("name");
        double price = Double.parseDouble(req.getParameter("price"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        String color = req.getParameter("color");
        int c_id = Integer.parseInt(req.getParameter("c_id"));

        Product newProduct = new Product(name, price, quantity, color);
        productModel.createProduct(newProduct, c_id);
        resp.sendRedirect(req.getContextPath() + "/products");
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Product existingProduct = productModel.findById(id);
        req.setAttribute("product", existingProduct);
        req.getRequestDispatcher("/views/products/edit.jsp").forward(req, resp);
    }

    private void updateProduct(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        double price = Double.parseDouble(req.getParameter("price"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        String color = req.getParameter("color");
        int c_id = Integer.parseInt(req.getParameter("c_id"));

        Product product = new Product(id, name, price, quantity, color);
        productModel.updateProduct(product, c_id);
        resp.sendRedirect(req.getContextPath() + "/products");
    }

    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        productModel.deleteProduct(id);
        resp.sendRedirect(req.getContextPath() + "/products");
    }


}
