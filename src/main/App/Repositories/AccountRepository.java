package Repositories;

import DataBase.DbConnection;
import Interfaces.IRepository;
import Models.Account;

import java.sql.*;
import java.util.List;

public class AccountRepository implements IRepository<Account> {


    @Override
    public void add(Account entity) {
        String insertUserSQL = "INSERT INTO User (name, lastName, phone) VALUES (?, ?, ?)";
        String insertAccountSQL = "INSERT INTO Account (idAccount, email, password, idProgram) VALUES (?, ?, ?, ?)";
        String insertAccountRoleSQL = "INSERT INTO Account_Role (idAccount, idRole) VALUES (?, ?)";

        try (Connection conn = DbConnection.getConnection()) {
            conn.setAutoCommit(false); // Transacción

            // 1️⃣ Insertar User
            try (PreparedStatement psUser = conn.prepareStatement(insertUserSQL, Statement.RETURN_GENERATED_KEYS)) {
                psUser.setString(1, entity.getUser().getName());
                psUser.setString(2, entity.getUser().getLastName());
                psUser.setString(3, entity.getUser().getPhoneNumber());

                int affectedRows = psUser.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("No se pudo insertar el usuario");
                }

                try (ResultSet generatedKeys = psUser.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);
                        entity.setIdAccount(userId);
                    } else {
                        throw new SQLException("No se pudo obtener el ID del usuario insertado");
                    }
                }
            }

            // 2️⃣ Insertar Account
            try (PreparedStatement psAccount = conn.prepareStatement(insertAccountSQL)) {
                psAccount.setInt(1, entity.getIdAccount());
                psAccount.setString(2, entity.getEmail());
                psAccount.setString(3, "hashed_password"); // contraseña hasheada
                psAccount.setInt(4, entity.getProgram().ordinal()); // ordinal del enum
                psAccount.executeUpdate();
            }

            // 3️⃣ Insertar roles en Account_Role
            try (PreparedStatement psRole = conn.prepareStatement(insertAccountRoleSQL)) {
                for (var role : entity.getRoles()) {
                    psRole.setInt(1, entity.getIdAccount());
                    psRole.setInt(2, role.getId()); // suponiendo que EnumRole tiene método getId() que coincide con Role.idRole
                    psRole.addBatch();
                }
                psRole.executeBatch();
            }

            conn.commit();
            System.out.println("Cuenta y roles agregados correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                DbConnection.getConnection().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


    @Override
    public void update(Account entity) {

    }

    @Override
    public void delete(Account entity) {

    }

    @Override
    public Account getById(int id) {
        return null;
    }

    @Override
    public List<Account> getAll() {
        return List.of();
    }
}
