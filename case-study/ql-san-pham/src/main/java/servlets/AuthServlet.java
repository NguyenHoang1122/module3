package servlets;

import dao.UserDAO;
import entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/auth/*")
public class AuthServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = (req.getPathInfo() == null) ? "" : req.getPathInfo();
        switch (action) {
            case "":
            case "/":
            case "/login":
                showLogin(req, resp);
                break;
            case "/register":
                showRegister(req, resp);
                break;
            case "/logout":
                logout(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = (req.getPathInfo() == null) ? "/login" : req.getPathInfo();
        switch (action) {
            case "/login":
                processLogin(req, resp);
                break;
            case "/register":
                processRegister(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    private void showRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
    }

    private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) session.invalidate();
        resp.sendRedirect(req.getContextPath() + "/auth/login");
    }

    private void processLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User u = userDAO.authenticate(email, password);
        if (u != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", u);

            if ("admin".equals(u.getRole())) {
                resp.sendRedirect(req.getContextPath() + "/admin/products");
            } else {
                resp.sendRedirect(req.getContextPath() + "/products");
            }
        } else {
            req.setAttribute("error", "Sai email hoặc mật khẩu");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        }
    }

    private void processRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String address = req.getParameter("address");

        if (userDAO.isEmailOrPhoneExists(email, phone)) {
            req.setAttribute("error", "Email hoặc số điện thoại đã tồn tại");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }

        User u = new User();
        u.setName(name);
        u.setPassword(password);
        u.setPhone(phone);
        u.setEmail(email);
        u.setAddress(address);
        u.setRole("user");

        boolean ok = userDAO.createUser(u);
        if (ok) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
        } else {
            req.setAttribute("error", "Đăng ký thất bại, thử lại");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
        }
    }
}
