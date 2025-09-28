package Repositories;

import DataBase.DbConnection;
import Dtos.UserRegisterDTO;
import Enums.EnumProgram;
import Enums.EnumRole;
import Interfaces.IRepository;
import Models.Account;
import Models.User;
import Utilities.BaseRepository;
import Utilities.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para la gestión de credenciales de usuarios.
 *
 * Esta clase se encarga de realizar operaciones CRUD sobre usuarios y cuentas,
 * incluyendo la inserción de roles asociados a la cuenta. Se extiende de {@link BaseRepository}
 * para aprovechar métodos genéricos de acceso a base de datos y aplica transacciones
 * para garantizar la consistencia de los datos.
 *
 * @author juanserealpe
 */
public class CredentialRepository extends BaseRepository implements IRepository<UserRegisterDTO> {

    /**
     * Constructor que recibe la conexión a la base de datos.
     *
     * @param connection Conexión a la base de datos.
     */
    public CredentialRepository(Connection connection) {
        super(connection);
    }

    /**
     * Agrega un nuevo usuario junto con su cuenta y roles asociados.
     *
     * @param entity DTO con la información del usuario, cuenta y roles.
     */
    @Override
    public void add(UserRegisterDTO entity) {

        String insertUserSQL = "INSERT INTO User (name, lastName, phone) VALUES (?, ?, ?)";
        String insertAccountSQL = "INSERT INTO Account (idAccount, email, password, idProgram) VALUES (?, ?, ?, ?)";
        String insertAccountRoleSQL = "INSERT INTO Account_Role (idAccount, idRole) VALUES (?, ?)";

        try (Connection conn = DbConnection.getConnection()) {
            conn.setAutoCommit(false);

            // Insertar usuario y obtener el ID generado
            int userId = makeInsertWithGeneratedKey(insertUserSQL,
                    new Object[]{entity.getUser().getName(), entity.getUser().getLastName(), entity.getUser().getPhoneNumber()});
            if (userId == -1) throw new SQLException("No se pudo insertar el usuario");
            entity.getAccount().setIdAccount(userId);

            // Insertar cuenta asociada
            boolean accountInserted = makeInsert(insertAccountSQL,
                    new Object[]{entity.getAccount().getIdAccount(), entity.getAccount().getEmail(), entity.getPassword(), entity.getAccount().getProgram().ordinal()});
            if (!accountInserted) throw new SQLException("No se pudo insertar la cuenta");

            // Insertar roles asociados a la cuenta
            for (var role : entity.getAccount().getRoles()) {
                boolean roleInserted = makeInsert(insertAccountRoleSQL,
                        new Object[]{entity.getAccount().getIdAccount(), role.getId()});
                if (!roleInserted) {
                    throw new SQLException("No se pudo insertar el rol: " + role);
                }
            }

            // Confirmar transacción
            conn.commit();
            Logger.info(CredentialRepository.class,"Usuario : " + entity.toString() + " registrado correctamente en la base de datos.");

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
        // Implementar actualización de usuario y cuenta
    }

    @Override
    public void delete(UserRegisterDTO entity) {
        // Implementar eliminación de usuario y cuenta
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param idAccount ID de la cuenta.
     * @return DTO del usuario o null si no se encuentra.
     */
    @Override
    public UserRegisterDTO getById(int idAccount) {
        String sql = """
        SELECT 
            a.email, a.password, a.idProgram,
            u.name, u.lastName, u.phone
        FROM Account a
        JOIN User u ON a.idAccount = u.idUser
        WHERE a.idAccount = ?
    """;

        try (PreparedStatement stmt = DbConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, idAccount);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // User
                    User user = new User();
                    user.setName(rs.getString("name"));
                    user.setLastName(rs.getString("lastName"));
                    user.setPhoneNumber(rs.getString("phone"));

                    // Account
                    Account account = new Account();
                    account.setIdAccount(idAccount);
                    account.setEmail(rs.getString("email"));
                    account.setProgram(EnumProgram.fromId(rs.getInt("idProgram")));
                    account.setUser(user);

                    // roles
                    account.setRoles(getRolesByAccountId(idAccount));

                    // Construir DTO
                    UserRegisterDTO dto = new UserRegisterDTO();
                    dto.setPassword(null);
                    dto.setUser(user);
                    dto.setAccount(account);

                    return dto;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al recuperar usuario por email", e);
        }
    }

    /**
     * Obtiene un usuario por su correo electrónico.
     *
     * @param email Correo electrónico del usuario.
     * @return DTO del usuario con cuenta y roles; null si no se encuentra.
     */
    @Override
    public UserRegisterDTO getByString(String email) {
        String sql = """
        SELECT 
            a.idAccount, a.email, a.password, a.idProgram,
            u.name, u.lastName, u.phone
        FROM Account a
        JOIN User u ON a.idAccount = u.idUser
        WHERE a.email = ?
    """;

        try (PreparedStatement stmt = DbConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // User
                    User user = new User();
                    user.setName(rs.getString("name"));
                    user.setLastName(rs.getString("lastName"));
                    user.setPhoneNumber(rs.getString("phone"));

                    // Account
                    Account account = new Account();
                    int idAccount = rs.getInt("idAccount");
                    account.setIdAccount(idAccount);
                    account.setEmail(rs.getString("email"));
                    account.setProgram(EnumProgram.fromId(rs.getInt("idProgram")));
                    account.setUser(user);

                    // roles
                    account.setRoles(getRolesByAccountId(idAccount));

                    // Construir DTO
                    UserRegisterDTO dto = new UserRegisterDTO();
                    dto.setPassword(rs.getString("password"));
                    dto.setUser(user);
                    dto.setAccount(account);

                    return dto;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al recuperar usuario por email", e);
        }
    }
    private List<EnumRole> getRolesByAccountId(int idAccount) {
        String sql = "SELECT idRole FROM Account_Role WHERE idAccount = ?";
        List<EnumRole> roles = new ArrayList<>();

        try (PreparedStatement stmt = DbConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, idAccount);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idRole = rs.getInt("idRole");
                    roles.add(EnumRole.fromId(idRole));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al recuperar roles del usuario", e);
        }

        return roles;
    }


    /*@Override
    public UserRegisterDTO getByString(String email) {
        if (email == null || email.isEmpty()) return null;

        try {
            // Consultar cuenta
            String accountSql = "SELECT idAccount, email, idProgram, password FROM Account WHERE email = ?";
            boolean accountFound = makeRetrieve(accountSql, new Object[]{email});
            if (!accountFound) return null;

            Map<String, Object> accountRow = getOperationResult().getPayload().get(0);
            int idAccount = (int) accountRow.get("idAccount");
            String emailResult = (String) accountRow.get("email");
            int programId = (int) accountRow.get("idProgram");
            String passwordResult = (String) accountRow.get("password");

            EnumProgram programResult =
                    Arrays.stream(EnumProgram.values())
                            .filter(p -> p.getId() == programId)
                            .findFirst()
                            .orElse(null);
            Account account = new Account();
            account.setIdAccount(idAccount);
            account.setEmail(emailResult);
            account.setProgram(programResult);

            // Consultar roles asociados
            String rolesSql = "SELECT r.idRole FROM Account_Role ar JOIN Role r ON ar.idRole = r.idRole WHERE ar.idAccount = ?";
            boolean rolesFound = makeRetrieve(rolesSql, new Object[]{idAccount});
            if (rolesFound) {
                List<EnumRole> roles = getOperationResult().getPayload().stream()
                        .map(row -> EnumRole.values()[(int) row.get("idRole") - 1]) // Ajuste según enum
                        .toList();
                account.setRoles(roles);
            }

            return new UserRegisterDTO(passwordResult, null, account);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/

    /**
     * Obtiene todos los usuarios registrados.
     *
     * @return Lista vacía (pendiente implementación completa).
     */
    @Override
    public List<UserRegisterDTO> getAll() {
        return List.of();
    }
}
