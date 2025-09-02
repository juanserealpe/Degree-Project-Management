package App.Repositories;

import App.DataBase.DbConnection;
import App.Dtos.StudentDTO;
import App.Interfaces.IRepository;
import App.Models.Account;
import App.Models.Student;
import java.sql.*;
import java.util.List;

public class StudentRepository implements IRepository<StudentDTO> {

    private final DbConnection dbConnection;

    public StudentRepository() {
        this.dbConnection = new DbConnection();
    }

    @Override
    public void toAdd(StudentDTO prmStudentDTO) {
        String sqlAccount = "INSERT INTO Account (password) VALUES (?)";
        String sqlUser = "INSERT INTO User (email, names, last_names, id_account, id_program, number_phone) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlUserRole = "INSERT INTO User_Role (email, id_role) VALUES (?, 1)";

        try (Connection conn = dbConnection.toConnect()) {
            conn.setAutoCommit(false);

            int generatedAccountId;

            try (PreparedStatement pstmtAcc = conn.prepareStatement(sqlAccount, Statement.RETURN_GENERATED_KEYS)) {
                pstmtAcc.setString(1, prmStudentDTO.getAccount().getPassword());
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
                pstmtUser.setString(1, prmStudentDTO.getStudent().getEmail());
                pstmtUser.setString(2, prmStudentDTO.getStudent().getNames());
                pstmtUser.setString(3, prmStudentDTO.getStudent().getLastNames());
                pstmtUser.setInt(4, generatedAccountId);
                pstmtUser.setInt(5, prmStudentDTO.getStudent().getProgramId());
                pstmtUser.setString(6, prmStudentDTO.getStudent().getNumberPhone());
                pstmtUser.executeUpdate();
            }

            try (PreparedStatement pstmtRole = conn.prepareStatement(sqlUserRole)) {
                pstmtRole.setString(1, prmStudentDTO.getStudent().getEmail());
                pstmtRole.executeUpdate();
            }

            conn.commit();
            System.out.println(">> Estudiante agregado correctamente: " + prmStudentDTO.getStudent().getEmail());

        } catch (SQLException e) {
            System.out.println("Error al agregar estudiante: " + e.getMessage());
            try {
                dbConnection.toConnect().rollback();
            } catch (SQLException ex) {
                System.out.println("Error al hacer rollback: " + ex.getMessage());
            }
        }
    }

    @Override
    public List<StudentDTO> toGetAll() {
        String sql = """
        SELECT u.email, u.names, u.last_names, u.id_account, u.id_program, u.number_phone, a.password
        FROM User u
        JOIN User_Role ur ON u.email = ur.email
        JOIN Account a ON u.id_account = a.id_account
        WHERE ur.id_role = 1
        """;

        List<StudentDTO> students = new java.util.ArrayList<>();

        try (Connection conn = dbConnection.toConnect(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Student s = new Student(
                        rs.getString("email"),
                        rs.getString("names"),
                        rs.getString("last_names"),
                        rs.getInt("id_account"),
                        rs.getInt("id_program"),
                        rs.getString("number_phone") // 
                );

                Account acc = new Account(
                        rs.getInt("id_account"),
                        rs.getString("password")
                );

                StudentDTO dto = new StudentDTO(s, acc);
                students.add(dto);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching students: " + e.getMessage());
        }

        return students;
    }

    @Override
    public void toDeleteByString(String prmString) {
        String sqlGetAccount = "SELECT id_account FROM User WHERE email = ?";
        String sqlDeleteUserRole = "DELETE FROM User_Role WHERE email = ?";
        String sqlDeleteUser = "DELETE FROM User WHERE email = ?";
        String sqlDeleteAccount = "DELETE FROM Account WHERE id_account = ?";

        try (Connection conn = dbConnection.toConnect()) {
            conn.setAutoCommit(false); // ✅ iniciar transacción

            int idAccount = -1;

            // 1. Obtener el id_account
            try (PreparedStatement pstmtGet = conn.prepareStatement(sqlGetAccount)) {
                pstmtGet.setString(1, prmString);
                try (ResultSet rs = pstmtGet.executeQuery()) {
                    if (rs.next()) {
                        idAccount = rs.getInt("id_account");
                    } else {
                        System.out.println("No se encontró estudiante con email: " + prmString);
                        return;
                    }
                }
            }

            // 2. Eliminar primero de User_Role
            try (PreparedStatement pstmtDelUR = conn.prepareStatement(sqlDeleteUserRole)) {
                pstmtDelUR.setString(1, prmString);
                pstmtDelUR.executeUpdate();
            }

            // 3. Eliminar de User
            try (PreparedStatement pstmtDelUser = conn.prepareStatement(sqlDeleteUser)) {
                pstmtDelUser.setString(1, prmString);
                pstmtDelUser.executeUpdate();
            }

            // 4. Eliminar de Account
            try (PreparedStatement pstmtDelAcc = conn.prepareStatement(sqlDeleteAccount)) {
                pstmtDelAcc.setInt(1, idAccount);
                pstmtDelAcc.executeUpdate();
            }

            // 5. Confirmar transacción
            conn.commit();
            System.out.println(">> Estudiante eliminado correctamente: " + prmString);

        } catch (SQLException e) {
            System.out.println("Error al eliminar estudiante: " + e.getMessage());
            try {
                dbConnection.toConnect().rollback();
            } catch (SQLException ex) {
                System.out.println("Error al hacer rollback: " + ex.getMessage());
            }
        }
    }

    @Override
    public StudentDTO toGetByString(String prmString) {
        String sql = """
        SELECT u.email, u.names, u.last_names, u.id_account, u.id_program, u.number_phone, a.password
        FROM User u
        JOIN User_Role ur ON u.email = ur.email
        JOIN Role r ON ur.id_role = r.id_role
        JOIN Account a ON u.id_account = a.id_account
        WHERE LOWER(u.email) = LOWER(?) AND r.id_role = 1
        """;

        try (Connection conn = dbConnection.toConnect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, prmString);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println(">> Estudiante encontrado: " + rs.getString("email"));

                // 1. Student
                Student s = new Student(
                        rs.getString("email"),
                        rs.getString("names"),
                        rs.getString("last_names"),
                        rs.getInt("id_account"),
                        rs.getInt("id_program"),
                        rs.getString("number_phone") // 
                );

                // 2. Account
                Account acc = new Account(
                        rs.getInt("id_account"),
                        rs.getString("password")
                );

                // 3. DTO
                return new StudentDTO(s, acc);
            } else {
                System.out.println(">> No se encontró estudiante con email: " + prmString);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching student: " + e.getMessage());
        }

        return null;
    }

    @Override
    public void toUpdate(StudentDTO prmItem) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
