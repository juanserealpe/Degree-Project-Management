package App.Repositories;

import App.DataBase.DbConnection;
import App.Dtos.DirectorDTO;
import App.Interfaces.IRepository;
import App.Models.Director;
import App.Models.Role;

import java.sql.*;
import java.util.*;

public class DirectorRepository implements IRepository<DirectorDTO> {

    private final DbConnection dbConnection;

    public DirectorRepository() {
        this.dbConnection = new DbConnection();
    }

    @Override
    public void toAdd(DirectorDTO prmItem) {
        String sqlAccount = "INSERT INTO Account (password) VALUES (?)";
        String sqlUser = "INSERT INTO User (email, names, last_names, id_account, id_program, number_phone) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlUserRole = "INSERT INTO User_Role (email, id_role) VALUES (?, ?)";

        try (Connection conn = dbConnection.toConnect()) {
            conn.setAutoCommit(false);

            int generatedAccountId;

            try (PreparedStatement pstmtAcc = conn.prepareStatement(sqlAccount, Statement.RETURN_GENERATED_KEYS)) {
                pstmtAcc.setString(1, prmItem.getAccount().getPassword());
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
                pstmtUser.setString(1, prmItem.getDirector().getEmail());
                pstmtUser.setString(2, prmItem.getDirector().getNames());
                pstmtUser.setString(3, prmItem.getDirector().getLastNames());
                pstmtUser.setInt(4, generatedAccountId);
                pstmtUser.setInt(5, prmItem.getDirector().getProgramId());
                pstmtUser.setString(6, prmItem.getDirector().getNumberPhone());
                pstmtUser.executeUpdate();
            }
            try (PreparedStatement pstmtRole = conn.prepareStatement(sqlUserRole)) {
                for (Role role : prmItem.getDirector().getRoles()) {
                    pstmtRole.setString(1, prmItem.getDirector().getEmail());
                    pstmtRole.setInt(2, role.getIdRole());
                    pstmtRole.addBatch();
                }
                pstmtRole.executeBatch();
            }

            conn.commit();

        } catch (SQLException e) {
            System.out.println("Error al agregar director: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<DirectorDTO> toGetAll() {
        List<DirectorDTO> directors = new ArrayList<>();

        String sql = "SELECT u.email, u.names, u.last_names, u.id_program, u.number_phone, "
                + "r.id_role, r.name AS role_name "
                + "FROM User u "
                + "LEFT JOIN User_Role ur ON u.email = ur.email "
                + "LEFT JOIN Role r ON ur.id_role = r.id_role "
                + "WHERE r.id_role = 3";

        try (Connection conn = dbConnection.toConnect(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            Map<String, DirectorDTO> map = new HashMap<>();

            while (rs.next()) {
                String email = rs.getString("email");
                DirectorDTO dto = map.get(email);

                if (dto == null) {
                    Director dir = new Director();
                    dir.setEmail(email);
                    dir.setNames(rs.getString("names"));
                    dir.setLastNames(rs.getString("last_names"));
                    dir.setProgramId(rs.getInt("id_program"));
                    dir.setNumberPhone(rs.getString("number_phone"));
                    dto = new DirectorDTO(dir, null);
                    map.put(email, dto);
                }

                int roleId = rs.getInt("id_role");
                if (roleId != 0) {
                    Role role = new Role();
                    role.setIdRole(roleId);
                    role.setName(rs.getString("role_name"));
                    dto.getDirector().getRoles().add(role);
                }
            }

            directors.addAll(map.values());

        } catch (SQLException e) {
            System.out.println("Error al obtener directores: " + e.getMessage());
        }

        return directors;
    }

    @Override
    public DirectorDTO toGetByString(String prmString) {
        DirectorDTO dto = null;

        String sql = "SELECT u.email, u.names, u.last_names, u.id_program, u.number_phone, "
                + "r.id_role, r.name AS role_name "
                + "FROM User u "
                + "LEFT JOIN User_Role ur ON u.email = ur.email "
                + "LEFT JOIN Role r ON ur.id_role = r.id_role "
                + "WHERE u.email = ?";

        try (Connection conn = dbConnection.toConnect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, prmString);

            try (ResultSet rs = pstmt.executeQuery()) {
                Director dir = null;

                while (rs.next()) {
                    if (dir == null) {
                        dir = new Director();
                        dir.setEmail(rs.getString("email"));
                        dir.setNames(rs.getString("names"));
                        dir.setLastNames(rs.getString("last_names"));
                        dir.setProgramId(rs.getInt("id_program"));
                        dir.setNumberPhone(rs.getString("number_phone"));

                        dto = new DirectorDTO(dir, null);
                    }

                    int roleId = rs.getInt("id_role");
                    if (roleId != 0) {
                        Role role = new Role();
                        role.setIdRole(roleId);
                        role.setName(rs.getString("role_name"));
                        dir.getRoles().add(role);
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener director: " + e.getMessage());
        }

        return dto;
    }

    @Override
    public void toDeleteByString(String prmString) {
        String sqlUserRole = "DELETE FROM User_Role WHERE email = ?";
        String sqlUser = "DELETE FROM User WHERE email = ?";
        String sqlAccount = "DELETE FROM Account WHERE id_account = ?";

        try (Connection conn = dbConnection.toConnect()) {
            conn.setAutoCommit(false);

            int accountId = -1;

            try (PreparedStatement pstmtFind = conn.prepareStatement("SELECT id_account FROM User WHERE email = ?")) {
                pstmtFind.setString(1, prmString);
                try (ResultSet rs = pstmtFind.executeQuery()) {
                    if (rs.next()) {
                        accountId = rs.getInt("id_account");
                    } else {
                        System.out.println("No existe director con email: " + prmString);
                        return;
                    }
                }
            }

            try (PreparedStatement pstmtRole = conn.prepareStatement(sqlUserRole)) {
                pstmtRole.setString(1, prmString);
                pstmtRole.executeUpdate();
            }

            try (PreparedStatement pstmtUser = conn.prepareStatement(sqlUser)) {
                pstmtUser.setString(1, prmString);
                pstmtUser.executeUpdate();
            }

            try (PreparedStatement pstmtAcc = conn.prepareStatement(sqlAccount)) {
                pstmtAcc.setInt(1, accountId);
                pstmtAcc.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            System.out.println("Error al eliminar director: " + e.getMessage());
        }
    }

    @Override
    public void toUpdate(DirectorDTO prmItem) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
