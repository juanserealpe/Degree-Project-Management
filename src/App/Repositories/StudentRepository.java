package App.repositories;

import App.DataBase.DataBase;
import App.entities.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    private DataBase _DataBase = new DataBase();

    public StudentRepository(DataBase db) {
        this._DataBase = db;
    }

    public List<Student> List() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT name, last_name, email, password, phone, program FROM Estudiante";

        try (Connection conn = _DataBase.Connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Student s = new Student(
                        rs.getString("program"),
                        rs.getString("phone"),
                        rs.getString("name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("password")
                );
                students.add(s);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar estudiantes: " + e.getMessage());
        }

        return students;
    }

    public boolean Add(Student prmItem) {
        String sql = "INSERT INTO Estudiante(name, last_name, email, password, phone, program) VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection conn = _DataBase.Connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, prmItem.getName());
            pstmt.setString(2, prmItem.getLastName());
            pstmt.setString(3, prmItem.getEmail());
            pstmt.setString(4, prmItem.getPassword());
            pstmt.setString(5, prmItem.getPhone());
            pstmt.setString(6, prmItem.getPrograma());

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al agregar estudiante: " + e.getMessage());
            return false;
        }
    }

    public boolean Delete(Student prmItem) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Student Retrieve(String prmEmail) {
        String sql = "SELECT name, last_name, email, password, phone, program FROM Estudiante WHERE email = ?";
        Student s = null;

        try (Connection conn = _DataBase.Connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, prmEmail);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                s = new Student(
                        rs.getString("program"),
                        rs.getString("name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al recuperar estudiante: " + e.getMessage());
        }

        return s;
    }

}
