package models;

import entities.Subject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class SubjectModel extends BaseModel {
    public List<Subject> getAllSubjects() throws SQLException {
        String sql = "SELECT * FROM subjects";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Subject> list = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            Subject subject = new Subject(id, name);
            list.add(subject);
        }
        return list;
    }

    public void deleteSubjectById(int id) throws SQLException {

        String sqlDeleteSubjectStudent = "DELETE FROM subject_student WHERE subject_id = ?";
        PreparedStatement ps1 = conn.prepareStatement(sqlDeleteSubjectStudent);
        ps1.setInt(1, id);
        ps1.executeUpdate();

        String sql = "DELETE FROM subjects WHERE id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }


    public void editSubject(Subject subject) throws SQLException {
        String sql = "UPDATE subjects SET name = ? WHERE id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, subject.getName());
        preparedStatement.setInt(2, subject.getId());
        preparedStatement.executeUpdate();
    }

    public Subject findById(int id) throws SQLException {
        String sql = "SELECT * FROM subjects WHERE id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Subject subject = null;
        while (resultSet.next()) {
            int subjectId = resultSet.getInt("id");
            String name = resultSet.getString("name");
            subject = new Subject(subjectId, name);
        }
        return subject;
    }

    public void createSubject(Subject subject) throws SQLException {
        String sql = "INSERT INTO subjects(name) VALUES (?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, subject.getName());
        ps.executeUpdate();
    }
}