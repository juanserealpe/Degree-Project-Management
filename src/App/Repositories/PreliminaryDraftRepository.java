package App.Repositories;

import App.DataBase.DataBase;
import App.Entities.PreliminaryDraft;
import java.sql.*;

public class PreliminaryDraftRepository {
    private DataBase _DataBase;

    public PreliminaryDraftRepository(DataBase db) {
        this._DataBase = db;
    }

    public boolean AddPreliminaryDraft(PreliminaryDraft prmItem, String prmEmailStudent, String prmEmailProfessor) {

        String insertSql = "INSERT INTO Anteproyecto (status, date, name, email_student, email_professor) " +
                           "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = _DataBase.Connect()) {

            try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
                stmt.setString(1, prmItem.getStatus());
                stmt.setString(2, prmItem.getDate().toString()); 
                stmt.setString(3, prmItem.getName());
                stmt.setString(4, prmEmailStudent);
                stmt.setString(5, prmEmailProfessor);

                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}