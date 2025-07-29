package org.example.thdangnhaphtloichao;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        PrintWriter write = response.getWriter();
        write.println("<html>");
        if ("admin".equals(username) && "admin".equals(password)) {
            write.println("<h1>Welcome " + username + " to website</h1>");
        } else {
            write.println("<h1>Login Error</h1>");
        }
        write.println("</html>");
    }
}