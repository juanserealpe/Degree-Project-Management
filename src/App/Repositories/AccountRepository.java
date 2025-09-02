package App.Repositories;

import App.DataBase.DbConnection;
import App.Interfaces.IAccountRepository;
import App.Interfaces.IRepository;
import App.Models.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AccountRepository implements IAccountRepository {

    private final DbConnection dbConnection;

    public AccountRepository() {
        this.dbConnection = new DbConnection();
    }

    @Override
    public Account toGetById(int accountId) {
        String sql = "SELECT id_account, password FROM Account WHERE id_account = ?";
        Account account = null;

        try (Connection conn = dbConnection.toConnect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, accountId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    account = new Account(
                            rs.getInt("id_account"),
                            rs.getString("password") // hash de la contraseña
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener cuenta: " + e.getMessage());
        }

        return account;
    }
}
