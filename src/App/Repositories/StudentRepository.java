package App.Repositories;

import App.DataBase.DbConnection;
import App.Models.Account;
import App.Models.Student;
import java.sql.*;
import java.util.List;

public class StudentRepository {

    private final DbConnection dbConnection;

    public StudentRepository() {
        this.dbConnection = new DbConnection();
    }

    public void addStudent(Student prmStudent, Account prmAccount) {
        String sqlAccount = "INSERT INTO Account (password) VALUES (?)";
        String sqlUser = "INSERT INTO User (email, names, last_names, id_account, id_program) VALUES (?, ?, ?, ?, ?)";
        String sqlUserRole = "INSERT INTO User_Role (email, id_role) VALUES (?, 1)";

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
                pstmtUser.setString(1, prmStudent.getEmail());
                pstmtUser.setString(2, prmStudent.getNames());
                pstmtUser.setString(3, prmStudent.getLastNames());
                pstmtUser.setInt(4, generatedAccountId);
                pstmtUser.setInt(5, prmStudent.getProgramId());
                pstmtUser.executeUpdate();
            }

            try (PreparedStatement pstmtRole = conn.prepareStatement(sqlUserRole)) {
                pstmtRole.setString(1, prmStudent.getEmail());
                pstmtRole.executeUpdate();
            }

            conn.commit();
            System.out.println(">> Estudiante agregado correctamente: " + prmStudent.getEmail());

        } catch (SQLException e) {
            System.out.println("Error al agregar estudiante: " + e.getMessage());
            try {
                dbConnection.Connect().rollback();
            } catch (SQLException ex) {
                System.out.println("Error al hacer rollback: " + ex.getMessage());
            }
        }
    }

    public List<Student> getAllStudents() {
        String sql = """
        SELECT u.email, u.names, u.last_names, u.id_account, u.id_program
        FROM User u
        JOIN User_Role ur ON u.email = ur.email
        WHERE ur.id_role = 1
        """;

        List<Student> students = new java.util.ArrayList<>();

        try (Connection conn = dbConnection.Connect(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Student s = new Student(
                        rs.getString("email"),
                        rs.getString("names"),
                        rs.getString("last_names"),
                        rs.getInt("id_account"),
                        rs.getInt("id_program")
                );
                students.add(s);
            }

            System.out.println(">> Total estudiantes encontrados: " + students.size());

        } catch (SQLException e) {
            System.out.println("Error fetching students: " + e.getMessage());
        }

        return students;
    }

    public void deleteStudent(String email) {
        String sqlGetAccount = "SELECT id_account FROM User WHERE email = ?";
        String sqlDeleteUser = "DELETE FROM User WHERE email = ?";
        String sqlDeleteAccount = "DELETE FROM Account WHERE id_account = ?";

        try (Connection conn = dbConnection.Connect()) {
            conn.setAutoCommit(false); // ✅ iniciar transacción

            int idAccount = -1;

            try (PreparedStatement pstmtGet = conn.prepareStatement(sqlGetAccount)) {
                pstmtGet.setString(1, email);
                try (ResultSet rs = pstmtGet.executeQuery()) {
                    if (rs.next()) {
                        idAccount = rs.getInt("id_account");
                    } else {
                        System.out.println("No se encontró estudiante con email: " + email);
                        return;
                    }
                }
            }

            try (PreparedStatement pstmtDelUser = conn.prepareStatement(sqlDeleteUser)) {
                pstmtDelUser.setString(1, email);
                pstmtDelUser.executeUpdate();
            }

            try (PreparedStatement pstmtDelAcc = conn.prepareStatement(sqlDeleteAccount)) {
                pstmtDelAcc.setInt(1, idAccount);
                pstmtDelAcc.executeUpdate();
            }

            conn.commit();
            System.out.println(">> Estudiante eliminado correctamente: " + email);

        } catch (SQLException e) {
            System.out.println("Error al eliminar estudiante: " + e.getMessage());
            try {
                dbConnection.Connect().rollback();
            } catch (SQLException ex) {
                System.out.println("Error al hacer rollback: " + ex.getMessage());
            }
        }
    }

    public Student getStudentByEmail(String email) {
        String sql = """
        SELECT u.email, u.names, u.last_names, u.id_account, u.id_program
        FROM User u
        JOIN User_Role ur ON u.email = ur.email
        JOIN Role r ON ur.id_role = r.id_role
        WHERE LOWER(u.email) = LOWER(?) AND r.id_role = 1
        """;

        try (Connection conn = dbConnection.Connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println(">> Estudiante encontrado: " + rs.getString("email"));
                return new Student(
                        rs.getString("email"),
                        rs.getString("names"),
                        rs.getString("last_names"),
                        rs.getInt("id_account"),
                        rs.getInt("id_program")
                );
            } else {
                System.out.println(">> No se encontró estudiante con email: " + email);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching student: " + e.getMessage());
        }

        return null;
    }
}
