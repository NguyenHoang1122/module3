package models;

import entities.Group;
import entities.Student;
import entities.Subject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentModel extends BaseModel{
    public List<Student> getAllStudents() throws SQLException {
        String sql = " SELECT s.StudentId, s.StudentName, s.Gender, s.Email, s.Phone, g.Id AS group_id, g.GroupName AS group_name, \n" +
                "        GROUP_CONCAT(sub.`name` SEPARATOR ', ') AS subject_names\n" +
                "        FROM students s \n" +
                "        JOIN `groups` g ON s.group_id = g.id \n" +
                "        LEFT JOIN subject_student subs ON s.StudentId = subs.Student_id \n" +
                "        LEFT JOIN subjects sub ON subs.subject_id = sub.id\n" +
                "        GROUP BY s.StudentId;";
            PreparedStatement statement = conn.prepareStatement(sql);

        ResultSet resultSet = statement.executeQuery();

        List<Student> list = new ArrayList<>();

        while (resultSet.next()) {
            int id = resultSet.getInt("StudentId");
            String name = resultSet.getString("StudentName");
            int gender = resultSet.getInt("Gender");
            String email = resultSet.getString("Email");
            String phone = resultSet.getString("Phone");
            Student student = new Student(id, name, gender, email, phone);

            int groupId = resultSet.getInt("Group_id");
            String groupName = resultSet.getString("group_name");
            Group group = new Group(groupId, groupName);
            student.setGroup(group);

            String subjectNames = resultSet.getString("subject_names");
            Subject subject = new Subject(subjectNames);
            student.setSubject(subject);

            list.add(student);
        }
        return list;
    }

    public void deleteById(int id) throws  SQLException {
        String sqlDeleteSubjectStudent = "DELETE FROM subject_student WHERE Student_id = ?";
        PreparedStatement ps1 = conn.prepareStatement(sqlDeleteSubjectStudent);
        ps1.setInt(1, id);
        ps1.executeUpdate();

        String sqlDeleteStudent = "DELETE FROM students WHERE StudentId = ?";
        PreparedStatement ps2 = conn.prepareStatement(sqlDeleteStudent);
        ps2.setInt(1, id);
        ps2.executeUpdate();
    }

    public void editStudent(Student newStudent, int studentId, String groupId) throws SQLException {
        String sql = "UPDATE students SET StudentName = ?, Gender =?, Email = ?, Phone = ?, Group_id = ? WHERE StudentId = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, newStudent.getName());
        preparedStatement.setInt(2, newStudent.getGender());
        preparedStatement.setString(3, newStudent.getEmail());
        preparedStatement.setString(4, newStudent.getPhone());
        preparedStatement.setString(5, groupId);
        preparedStatement.setInt(6, studentId);
        preparedStatement.execute();
    }

    public Student findById(int id) throws SQLException {
        String sql = "SELECT students.*, `groups`.GroupName as 'group_name' FROM students\n" +
                "JOIN `groups` ON students.Group_id = `groups`.Id WHERE students.StudentId = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        String sqlSubject = "SELECT s.id, s.name FROM subject_student ss JOIN subjects s ON ss.subject_id = s.id WHERE ss.student_id = ?";
        PreparedStatement psSubjects = conn.prepareStatement(sqlSubject);
        psSubjects.setInt(1, id);
        ResultSet rsSubjects = psSubjects.executeQuery();

        List<Subject> subjects = new ArrayList<>();


        Student s = null;
        while (resultSet.next()) {
            int studentId = resultSet.getInt("StudentId");
            String name = resultSet.getString("StudentName");
            int gender = resultSet.getInt("Gender");
            String email = resultSet.getString("Email");
            String phone = resultSet.getString("Phone");
            int groupId = resultSet.getInt("Group_id");
            String groupName = resultSet.getString("group_name");

            Group group = new Group(groupId, groupName);

            s = new Student(studentId, name, gender, email, phone);
            s.setGroup(group);
        }
        while (rsSubjects.next()) {
            int subId = rsSubjects.getInt("id");
            String subName = rsSubjects.getString("name");
            subjects.add(new Subject(subId, subName));
        }
        s.setSubjects(subjects);
        return s;
    }

    public void createStudent(Student newStudent, int groupId) throws SQLException {
        String sql = "INSERT INTO students(StudentName, Gender, Email, Phone, Group_id) VALUE (?,?,?,?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, newStudent.getName());
        preparedStatement.setInt(2, newStudent.getGender());
        preparedStatement.setString(3, newStudent.getEmail());
        preparedStatement.setString(4, newStudent.getPhone());
        preparedStatement.setInt(5, groupId);
        preparedStatement.execute();
    }

    public void updateStudentSubjects(int studentId, String[] subjectIds) throws SQLException {
        // Xóa
        String deleteSql = "DELETE FROM subject_student WHERE student_id = ?";
        PreparedStatement psDelete = conn.prepareStatement(deleteSql);
        psDelete.setInt(1, studentId);
        psDelete.executeUpdate();

        // Thêm lại
        String insertSql = "INSERT INTO subject_student(student_id, subject_id) VALUES (?, ?)";
        PreparedStatement psInsert = conn.prepareStatement(insertSql);
        if (subjectIds != null) {
            for (String subjectIdStr : subjectIds) {
                int subjectId = Integer.parseInt(subjectIdStr);
                psInsert.setInt(1, studentId);
                psInsert.setInt(2, subjectId);
                psInsert.addBatch();
            }
            psInsert.executeBatch();
        }
    }

}
