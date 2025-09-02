package App.Repositories;

import App.DataBase.DbConnection;
import App.Interfaces.IRoleRepository;
import App.Models.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleRepository implements IRoleRepository {

    private final DbConnection dbConnection;

    public RoleRepository() {
        this.dbConnection = new DbConnection();
    }

    public List<Role> getByEmail(String prmEmail) {
        String sql = "SELECT r.id_role, r.name "
                + "FROM Role r "
                + "INNER JOIN User_Role ur ON r.id_role = ur.id_role "
                + "WHERE ur.email = ?";

        List<Role> roles = new ArrayList<>();

        try (Connection conn = dbConnection.toConnect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, prmEmail);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Role role = new Role(
                            rs.getInt("id_role"),
                            rs.getString("name")
                    );
                    roles.add(role);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener roles: " + e.getMessage());
        }

        return roles;
    }

}
