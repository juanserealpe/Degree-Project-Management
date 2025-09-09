package Repositories;

import DataBase.DbConnection;
import Dtos.UserDTO;
import Interfaces.IRepository;
import Models.Role;
import Models.User;

import java.sql.*;
import java.util.List;

public class UserRepository implements IRepository<UserDTO> {

    private final DbConnection dbConnection;

    public UserRepository() {
        this.dbConnection = new DbConnection();
    }


    @Override
    public void toAdd(UserDTO prmItem) {
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
                pstmtUser.setString(1, prmItem.getUser().getEmail());
                pstmtUser.setString(2, prmItem.getUser().getNames());
                pstmtUser.setString(3, prmItem.getUser().getLastNames());
                pstmtUser.setInt(4, generatedAccountId);
                pstmtUser.setInt(5, prmItem.getUser().getProgramId());
                pstmtUser.setString(6, prmItem.getUser().getNumberPhone());
                pstmtUser.executeUpdate();
            }
            try (PreparedStatement pstmtRole = conn.prepareStatement(sqlUserRole)) {
                for (Role role : prmItem.getUser().getRoles()) {
                    pstmtRole.setString(1, prmItem.getUser().getEmail());
                    pstmtRole.setInt(2, role.getIdRole());
                    pstmtRole.addBatch();
                }
                pstmtRole.executeBatch();
            }

            conn.commit();

        } catch (SQLException e) {
            System.out.println("Error al agregar user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<UserDTO> toGetAll() {
        return List.of();
    }

    @Override
    public UserDTO toGetByString(String prmString) {
        return null;
    }

    @Override
    public void toDeleteByString(String prmString) {

    }

    @Override
    public void toUpdate(UserDTO prmItem) {

    }
}
