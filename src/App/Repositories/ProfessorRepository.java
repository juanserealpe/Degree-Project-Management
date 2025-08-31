package App.Repositories;

import App.DataBase.DbConnection;
import App.Models.Account;
import App.Models.Professor;
import App.Models.Role;
import java.sql.*;
import java.util.*;

public class ProfessorRepository {

    private final DbConnection dbConnection;

    public ProfessorRepository() {
        this.dbConnection = new DbConnection();
    }

    public void addProfessor(Professor prmProfessor, Account prmAccount) {
        String sqlAccount = "INSERT INTO Account (password) VALUES (?)";
        String sqlUser = "INSERT INTO User (email, names, last_names, id_account, id_program) VALUES (?, ?, ?, ?, ?)";
        String sqlUserRole = "INSERT INTO User_Role (email, id_role) VALUES (?, ?)";

        try (Connection conn = dbConnection.Connect()) {
            conn.setAutoCommit(false);

            int generatedAccountId;

            try (PreparedStatement pstmtAcc = conn.prepareStatement(sqlAccount, Statement.RETURN_GENERATED_KEYS)) {
                pstmtAcc.setString(1, prmAccount.getPassword());
                pstmtAcc.executeUpdate();

                try (ResultSet rs = pstmtAcc.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedAccountId = rs.getInt(1);
                    } else {
                        conn.rollback();
                        throw new SQLException("No se pudo obtener id_account generado.");
                    }
                }
            }

            try (PreparedStatement pstmtUser = conn.prepareStatement(sqlUser)) {
                pstmtUser.setString(1, prmProfessor.getEmail());
                pstmtUser.setString(2, prmProfessor.getNames());
                pstmtUser.setString(3, prmProfessor.getLastNames());
                pstmtUser.setInt(4, generatedAccountId);
                pstmtUser.setInt(5, prmProfessor.getProgramId());
                pstmtUser.executeUpdate();
            }

            try (PreparedStatement pstmtRole = conn.prepareStatement(sqlUserRole)) {
                for (Role role : prmProfessor.getRoles()) {
                    pstmtRole.setString(1, prmProfessor.getEmail());
                    pstmtRole.setInt(2, role.getIdRole());
                    pstmtRole.addBatch();
                }
                pstmtRole.executeBatch();
            }

            conn.commit();
            System.out.println(">> Profesor agregado correctamente: " + prmProfessor.getEmail());

        } catch (SQLException e) {
            System.out.println("Error al agregar profesor: " + e.getMessage());
            try {
                dbConnection.Connect().rollback();
            } catch (SQLException ex) {
                System.out.println("Error al hacer rollback: " + ex.getMessage());
            }
        }
    }

    public List<Professor> getAllProfessors() {
        List<Professor> professors = new ArrayList<>();

        String sql = "SELECT u.email, u.names, u.last_names, u.id_program, "
                + "r.id_role, r.name AS role_name, a.id_account, a.password "
                + "FROM User u "
                + "JOIN Account a ON u.id_account = a.id_account "
                + "LEFT JOIN User_Role ur ON u.email = ur.email "
                + "LEFT JOIN Role r ON ur.id_role = r.id_role";

        try (Connection conn = dbConnection.Connect(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            Map<String, Professor> mapProf = new HashMap<>();

            while (rs.next()) {
                String email = rs.getString("email");
                Professor prof = mapProf.get(email);

                if (prof == null) {
                    prof = new Professor();
                    prof.setEmail(email);
                    prof.setNames(rs.getString("names"));
                    prof.setLastNames(rs.getString("last_names"));
                    prof.setProgramId(rs.getInt("id_program"));

                    Account acc = new Account();
                    acc.setIdAccount(rs.getInt("id_account"));
                    acc.setPassword(rs.getString("password"));
                    prof.setAccountId(acc.getIdAccount());

                    mapProf.put(email, prof);
                }

                int roleId = rs.getInt("id_role");
                if (roleId != 0) {
                    Role role = new Role();
                    role.setIdRole(roleId);
                    role.setName(rs.getString("role_name"));
                    prof.getRoles().add(role);
                }
            }

            professors.addAll(mapProf.values());

        } catch (SQLException e) {
            System.out.println("Error al obtener profesores: " + e.getMessage());
        }

        return professors;
    }

    public Professor getProfessorByEmail(String email) {
        Professor prof = null;

        String sql = "SELECT u.email, u.names, u.last_names, u.id_program, "
                + "r.id_role, r.name AS role_name, a.id_account, a.password "
                + "FROM User u "
                + "JOIN Account a ON u.id_account = a.id_account "
                + "LEFT JOIN User_Role ur ON u.email = ur.email "
                + "LEFT JOIN Role r ON ur.id_role = r.id_role "
                + "WHERE u.email = ?";

        try (Connection conn = dbConnection.Connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    if (prof == null) {
                        prof = new Professor();
                        prof.setEmail(rs.getString("email"));
                        prof.setNames(rs.getString("names"));
                        prof.setLastNames(rs.getString("last_names"));
                        prof.setProgramId(rs.getInt("id_program"));

                        Account acc = new Account();
                        acc.setIdAccount(rs.getInt("id_account"));
                        acc.setPassword(rs.getString("password"));
                        prof.setAccountId(acc.getIdAccount());
                    }

                    int roleId = rs.getInt("id_role");
                    if (roleId != 0) {
                        Role role = new Role();
                        role.setIdRole(roleId);
                        role.setName(rs.getString("role_name"));
                        prof.getRoles().add(role);
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener profesor: " + e.getMessage());
        }

        return prof;
    }

    public void deleteProfessor(String email) {
        String sqlUserRole = "DELETE FROM User_Role WHERE email = ?";
        String sqlUser = "DELETE FROM User WHERE email = ?";
        String sqlAccount = "DELETE FROM Account WHERE id_account = ?";

        try (Connection conn = dbConnection.Connect()) {
            conn.setAutoCommit(false);

            int accountId = -1;

            try (PreparedStatement pstmtFind = conn.prepareStatement("SELECT id_account FROM User WHERE email = ?")) {
                pstmtFind.setString(1, email);
                try (ResultSet rs = pstmtFind.executeQuery()) {
                    if (rs.next()) {
                        accountId = rs.getInt("id_account");
                    } else {
                        System.out.println("No existe profesor con email: " + email);
                        return;
                    }
                }
            }

            try (PreparedStatement pstmtRole = conn.prepareStatement(sqlUserRole)) {
                pstmtRole.setString(1, email);
                pstmtRole.executeUpdate();
            }

            try (PreparedStatement pstmtUser = conn.prepareStatement(sqlUser)) {
                pstmtUser.setString(1, email);
                pstmtUser.executeUpdate();
            }

            try (PreparedStatement pstmtAcc = conn.prepareStatement(sqlAccount)) {
                pstmtAcc.setInt(1, accountId);
                pstmtAcc.executeUpdate();
            }

            conn.commit();
            System.out.println(">> Profesor eliminado correctamente: " + email);

        } catch (SQLException e) {
            System.out.println("Error al eliminar profesor: " + e.getMessage());
            try {
                dbConnection.Connect().rollback();
            } catch (SQLException ex) {
                System.out.println("Error al hacer rollback: " + ex.getMessage());
            }
        }
    }
}
