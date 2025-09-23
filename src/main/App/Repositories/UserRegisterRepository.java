package Repositories;

import DataBase.DbConnection;
import Dtos.UserRegisterDTO;
import Interfaces.IRepository;
import Utilities.BaseRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserRegisterRepository extends BaseRepository implements IRepository<UserRegisterDTO> {
    @Override
    public void add(UserRegisterDTO entity) {
        String insertUserSQL = "INSERT INTO User (name, lastName, phone) VALUES (?, ?, ?)";
        String insertAccountSQL = "INSERT INTO Account (idAccount, email, password, idProgram) VALUES (?, ?, ?, ?)";
        String insertAccountRoleSQL = "INSERT INTO Account_Role (idAccount, idRole) VALUES (?, ?)";

        try (Connection conn = DbConnection.getConnection()) {
            conn.setAutoCommit(false); // Inicia transacción
            this.connBd = conn; // 🔹 aseguramos que tu helper use la misma conexión

            // 1️⃣ Insertar User y obtener el ID
            int userId = makeInsertWithGeneratedKey(insertUserSQL,
                    new Object[]{entity.getUser().getName(), entity.getUser().getLastName(), entity.getUser().getPhoneNumber()});

            if (userId == -1) {
                throw new SQLException("No se pudo insertar el usuario");
            }
            entity.getAccount().setIdAccount(userId);

            // 2️⃣ Insertar Account
            boolean accountInserted = makeInsert(insertAccountSQL,
                    new Object[]{entity.getAccount().getIdAccount(), entity.getAccount().getEmail(), entity.getPassword(), entity.getAccount().getProgram().ordinal()});
            if (!accountInserted) {
                throw new SQLException("No se pudo insertar la cuenta");
            }

            // 3️⃣ Insertar Roles
            for (var role : entity.getAccount().getRoles()) {
                boolean roleInserted = makeInsert(insertAccountRoleSQL,
                        new Object[]{entity.getAccount().getIdAccount(), role.getId()});
                if (!roleInserted) {
                    throw new SQLException("No se pudo insertar el rol: " + role);
                }
            }

            conn.commit();
            System.out.println("✅ Usuario registrado correctamente con cuenta y roles.");

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connBd != null) connBd.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


    @Override
    public void update(UserRegisterDTO entity) {

    }

    @Override
    public void delete(UserRegisterDTO entity) {

    }

    @Override
    public UserRegisterDTO getById(int id) {
        return null;
    }

    @Override
    public List<UserRegisterDTO> getAll() {
        return List.of();
    }
}
