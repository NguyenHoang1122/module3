package controllers;

import Database.DBConnect;
import entities.Group;
import entities.Student;
import entities.Subject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.GroupModel;
import models.StudentModel;
import models.SubjectModel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "StudentServet", urlPatterns = {"/students/*"})
public class
StudentServlet extends HttpServlet {
    Connection conn = null;
    GroupModel groupModel;
    StudentModel studentModel;
    SubjectModel subjectModel;

    @Override
    public void init() throws ServletException {
        DBConnect dbConnect = new DBConnect();
        conn = dbConnect.getConnect();

        groupModel = new GroupModel();
        studentModel = new StudentModel();
        subjectModel = new SubjectModel();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getPathInfo();
        if (uri == null) {
            uri = "";
        }

        switch (uri) {
            case "/":
            case "":
                showListUserPage(req, resp);
                break;
            case "/delete":
                deleteStudent(req, resp);
                break;
            case "/create":
                showCreateStudentPage(req, resp);
                break;
            case "/edit":
                showEditStudentPage(req, resp);
                break;
            case "/search":
                searchStudent(req, resp);
                break;
            case "/subjects":
                showListSubjectPage(req, resp);
                break;
            case "/delete-subject":
                deleteSubject(req, resp);
                break;
            case "/edit-subject" :
                showEditSubjectPage(req, resp);
                break;
            case "/create-subject":
                showCreateSubjectPage(req, resp);
                break;

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getPathInfo();
        if (uri == null) {
            uri = "";
        }

        switch (uri) {
            case "/store":
                storeStudent(req, resp);
                break;
            case "/edit":
                editStudent(req, resp);
                break;
            case "/edit-subject":
                editSubject(req, resp);
                break;
            case "/create-subject":
                createSubject(req, resp);
                break;
        }
    }

    public void showListUserPage(HttpServletRequest request,
                                 HttpServletResponse response) throws ServletException {
        try {
            List<Student> list = studentModel.getAllStudents();
            request.setAttribute("listStudent", list);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/students/list.jsp");
            requestDispatcher.forward(request, response);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void showListSubjectPage(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            List<Subject> list = subjectModel.getAllSubjects();
            request.setAttribute("listSubject", list);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/subjects/list.jsp");
            requestDispatcher.forward(request, response);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteStudent(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        try {
            studentModel.deleteById(Integer.parseInt(id));
            response.sendRedirect("/students");
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteSubject(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        try {
            subjectModel.deleteSubjectById(Integer.parseInt(id));
            response.sendRedirect("/students/subjects");
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void editSubject(HttpServletRequest request, HttpServletResponse response) {
        try {

            int id = Integer.parseInt(request.getParameter("id"));
            Subject subjectEdit = this.findSubjectById(id);
            if (subjectEdit == null) {
                // cho ve 404
                return;
            }
            String name = request.getParameter("name");
            Subject subject = new Subject(id, name);
            subjectModel.editSubject(subject);
            response.sendRedirect("/students/subjects");
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public  void showEditSubjectPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Subject subjectEdit = this.findSubjectById(id);
            if (subjectEdit == null) {
                // cho ve 404
                return;
            }
            request.setAttribute("subjectEdit", subjectEdit);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/subjects/edit.jsp");
            requestDispatcher.forward(request, response);
        } catch (SQLException | ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Subject findSubjectById(int id) throws SQLException {
         return subjectModel.findById(id);
    }
    public void editStudent(HttpServletRequest request, HttpServletResponse response) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));

            Student studentEdit = this.findStudentById(id);
            if (studentEdit == null) {
                // cho ve 404
                return;
            }

            String name = request.getParameter("name");
            int gender = Integer.parseInt(request.getParameter("gender"));
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String groupId = request.getParameter("group_id");
            String[] subjectIds = request.getParameterValues("subject_ids");
            Student newStudent = new Student(name, gender, email, phone);

            studentModel.editStudent(newStudent, studentEdit.getId(), groupId);
            studentModel.updateStudentSubjects(studentEdit.getId(), subjectIds);

            response.sendRedirect("/students");

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Student findStudentById(int id) throws SQLException {
        return studentModel.findById(id);
    }

    public void showEditStudentPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));


            Student studentEdit = this.findStudentById(id);
            if (studentEdit == null) {
                // cho ve 404
                return;
            }

            List<Group> listGroup = getAllGroup();
            List<Subject> listSubject = subjectModel.getAllSubjects();

            request.setAttribute("studentEdit", studentEdit);
            request.setAttribute("listGroup", listGroup);
            request.setAttribute("listSubject", listSubject);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/students/edit.jsp");
            requestDispatcher.forward(request, response);
        } catch (SQLException | ServletException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void searchStudent(HttpServletRequest request, HttpServletResponse response) {
        try {
            String keyword = request.getParameter("keyword");
            String sql = "SELECT * FROM students WHERE StudentName LIKE ? OR Email LIKE ? ";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, '%' + keyword + "%");
            statement.setString(2, "%" + keyword + "%");

            ResultSet resultSet = statement.executeQuery();

            List<Student> list = new ArrayList<>();

            while (resultSet.next()) {
                int studentId = resultSet.getInt("StudentId");
                String name = resultSet.getString("StudentName");
                int gender = resultSet.getInt("Gender");
                String email = resultSet.getString("Email");
                String phone = resultSet.getString("Phone");

                Student student = new Student(studentId, name, gender, email, phone);
                list.add(student);
            }
            request.setAttribute("listStudent", list);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/students/list.jsp");
            requestDispatcher.forward(request, response);
        } catch (SQLException | IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Group> getAllGroup() {
        try {
            return groupModel.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showCreateStudentPage(HttpServletRequest request, HttpServletResponse response) {
        try {
//            them group
            List<Group> listGroup = getAllGroup();
            System.out.println(listGroup);
            request.setAttribute("listGroup", listGroup);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/students/create.jsp");
            requestDispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void storeStudent(HttpServletRequest request, HttpServletResponse response) {
        try {
            String name = request.getParameter("name");
            int gender = Integer.parseInt(request.getParameter("gender"));
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            int groupId = Integer.parseInt(request.getParameter("group_id"));
            Student newStudent = new Student(name, gender, email, phone);

            studentModel.createStudent(newStudent, groupId);

            response.sendRedirect("/students");
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void showCreateSubjectPage(HttpServletRequest req, HttpServletResponse resp) {
        try {
            RequestDispatcher rd = req.getRequestDispatcher("/views/subjects/create.jsp");
            rd.forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createSubject(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String name = req.getParameter("name");
            Subject newSubject = new Subject(name);
            subjectModel.createSubject(newSubject);
            resp.sendRedirect("/students/subjects");
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public List<Subject> getAllSubjects() {
//        try {
//            String sql = "SELECT * FROM subjects";
//            PreparedStatement statement = conn.prepareStatement(sql);
//            ResultSet resultSet = statement.executeQuery();
//            List<Subject> list = new ArrayList<>();
//            while (resultSet.next()) {
//                int id = resultSet.getInt("id");
//                String name = resultSet.getString("name");
//                Subject s = new Subject(id, name);
//                list.add(s);
//            }
//            return list;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
