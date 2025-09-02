package App.Repositories;

import App.DataBase.DbConnection;
import App.Interfaces.IRepository;
import App.Models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserRepository implements IRepository<User> {

    private final DbConnection dbConnection;

    public UserRepository() {
        this.dbConnection = new DbConnection();
    }

    public User toGetByString(String email) {
        String sql = "SELECT email, names, last_names, id_program, id_account FROM User WHERE email = ?";
        User user = null;

        try (Connection conn = dbConnection.toConnect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setEmail(rs.getString("email"));
                    user.setNames(rs.getString("names"));
                    user.setLastNames(rs.getString("last_names"));
                    user.setProgramId(rs.getInt("id_program"));
                    user.setAccountId(rs.getInt("id_account"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener usuario por email: " + e.getMessage());
        }

        return user;
    }

    @Override
    public void toAdd(User prmItem) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<User> toGetAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void toDeleteByString(String prmString) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void toUpdate(User prmItem) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
