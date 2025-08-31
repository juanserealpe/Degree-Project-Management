package App.Repositories;

import App.DataBase.DataBase;
import App.Entities.PreliminaryDraft;
import App.entities.Professor;
import java.util.ArrayList;
import java.sql.*;
import java.util.List;

public class ProfessorRepository {

    private DataBase _DataBase;

    public ProfessorRepository(DataBase db) {
        this._DataBase = db;
    }

    public ProfessorRepository() {
    }

    public List<PreliminaryDraft> getAllPreliminaryDrafts(String prmEmailProfessor) {
        List<PreliminaryDraft> drafts = new ArrayList<>();
        String sql = "SELECT id, status, date, name, email_student, email_professor "
                + "FROM Anteproyecto "
                + "WHERE email_professor = ?";

        try (Connection conn = _DataBase.Connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, prmEmailProfessor);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                PreliminaryDraft draft = new PreliminaryDraft(
                        rs.getInt("id"),
                        rs.getString("email_student"),
                        rs.getString("email_professor"),
                        rs.getString("name"),
                        rs.getString("date"),
                        rs.getString("status")
                );
                drafts.add(draft);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener anteproyectos: " + e.getMessage());
        }

        return drafts;
    }

    public boolean SetStatusPreliminaryDraft(String prmStatus, int prmPreliminaryId) {
        String sql = "UPDATE Anteproyecto SET status = ? WHERE id = ?";
        try (Connection conn = _DataBase.Connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, prmStatus);
            pstmt.setInt(2, prmPreliminaryId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Estado actualizado exitosamente para anteproyecto ID: " + prmPreliminaryId);
                return true;
            } else {
                System.out.println("No se encontró el anteproyecto con ID: " + prmPreliminaryId);
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar el estado: " + e.getMessage());
            return false;
        }
    }

    public Professor Retrieve(String prmEmail) {
        String sql = "SELECT name, last_name, email, password, phone FROM Docente WHERE email = ?";
        Professor s = null;

        try (Connection conn = _DataBase.Connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, prmEmail);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                s = new Professor(
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

    public boolean Add(Professor prmProfessor) {
        String sql = "INSERT INTO Docente(name, last_name, email, password, phone) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = _DataBase.Connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, prmProfessor.getName());
            pstmt.setString(2, prmProfessor.getLastName());
            pstmt.setString(3, prmProfessor.getEmail());
            pstmt.setString(4, prmProfessor.getPassword());
            pstmt.setString(5, prmProfessor.getPhone());

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al agregar estudiante: " + e.getMessage());
            return false;
        }
    }
}
