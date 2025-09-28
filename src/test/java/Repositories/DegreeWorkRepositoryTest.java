package Repositories;

import Enums.EnumModality;
import Enums.EnumState;
import Models.FormatA;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class DegreeWorkRepositoryTest extends BaseRepositoryTest {

    private DegreeWorkRepository repository = new DegreeWorkRepository(connection);
    
    private int insertUser(String name, String lastName, String phone) throws Exception {
        String sql = "INSERT INTO User (name, lastName, phone) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.setString(2, lastName);
            stmt.setString(3, phone);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        throw new IllegalStateException("No se pudo insertar User");
    }

    private int insertAccount(int userId, String email, String password, int programId) throws Exception {
        String sql = "INSERT INTO Account (idAccount, email, password, idProgram) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setInt(4, programId);
            stmt.executeUpdate();
        }
        return userId;
    }

    private int insertDegreeWork(int directorId, int programId, EnumModality modality) throws Exception {
        String sql = "INSERT INTO degreeWork (idDirector, idProgram, modality) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, directorId);
            stmt.setInt(2, programId);
            stmt.setString(3, modality.name());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        throw new IllegalStateException("No se pudo insertar DegreeWork");
    }

    private int insertFormatA(int degreeWorkId, String title, EnumState state, int attempt) throws Exception {
        String sql = "INSERT INTO FormatA (idDegreeWork, title, currentStatus, attempt, date, url, observation) " +
                "VALUES (?, ?, ?, ?, '2025-09-27', 'http://test.com', 'obs')";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, degreeWorkId);
            stmt.setString(2, title);
            stmt.setString(3, state.name());
            stmt.setInt(4, attempt);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        throw new IllegalStateException("No se pudo insertar FormatA");
    }

    @Test
    void testGetFormatAByDegreeWorkId() throws Exception {
        int userId = insertUser("Juan", "Perez", "999888777");
        int accountId = insertAccount(userId, "juan@unicauca.edu.co", "abc", 1);
        int degreeWorkId = insertDegreeWork(accountId, 1, EnumModality.PRACTICA_PROFESIONAL);
        insertFormatA(degreeWorkId, "Titulo Test", EnumState.ESPERA, 1);

        DegreeWorkRepository repo = new DegreeWorkRepository(connection);
        FormatA formatA = repo.getFormatAByDegreeWorkId(degreeWorkId);

        assertNotNull(formatA, "FormatA no debería ser null");
        assertEquals("Titulo Test", formatA.getTittle());
    }

    @Test
    void testEvaluateFormatAByDegreeWorkIdRejectIncreasesAttempt() throws Exception {
        int userId = insertUser("Carlos", "Gomez", "555444333");
        int accountId = insertAccount(userId, "carlos@unicauca.edu.co", "pass", 1);
        int degreeWorkId = insertDegreeWork(accountId, 1, EnumModality.INVESTIGACION);
        insertFormatA(degreeWorkId, "Trabajo X", EnumState.ESPERA, 1);

        DegreeWorkRepository repo = new DegreeWorkRepository(connection);
        boolean updated = repo.evaluateFormatAByDegreeWorkId(degreeWorkId, "Observacion rechazo", EnumState.RECHAZADO);

        assertTrue(updated, "El registro debería haberse actualizado");

        FormatA formatA = repo.getFormatAByDegreeWorkId(degreeWorkId);
        assertEquals(2, formatA.getAttempts(), "El attempt debería haberse incrementado");
    }

    @Test
    void testEvaluateFormatAByDegreeWorkIdApprove() throws Exception {
        int userId = insertUser("Luis", "Hernandez", "000111222");
        int accountId = insertAccount(userId, "luis@unicauca.edu.co", "pass", 1);
        int degreeWorkId = insertDegreeWork(accountId, 1, EnumModality.INVESTIGACION);
        insertFormatA(degreeWorkId, "Trabajo Y", EnumState.ESPERA, 1);

        DegreeWorkRepository repo = new DegreeWorkRepository(connection);
        boolean updated = repo.evaluateFormatAByDegreeWorkId(degreeWorkId, "Observacion aprobacion", EnumState.APROBADO);

        assertTrue(updated, "El registro debería haberse aprobado");

        FormatA formatA = repo.getFormatAByDegreeWorkId(degreeWorkId);
        assertEquals(EnumState.APROBADO, formatA.getState(), "El estado debería ser APROBADO");
    }

    @Test
    void testGetEmailByAccountId() throws Exception {
        int userId = insertUser("Ana", "Martinez", "321321321");
        int accountId = insertAccount(userId, "maria@unicauca.edu.co", "clave", 1);

        DegreeWorkRepository repo = new DegreeWorkRepository(connection);
        String email = repo.getEmailByAccountId(accountId);

        assertEquals("maria@unicauca.edu.co", email);
    }

    @Test
    void testGetUserByAccountId() throws Exception {
        int userId = insertUser("Pedro", "Ramirez", "123456789");
        int accountId = insertAccount(userId, "pedro@unicauca.edu.co", "12345", 1);

        DegreeWorkRepository repo = new DegreeWorkRepository(connection);
        var user = repo.getUserByAccountId(accountId);

        assertNotNull(user, "El User no debería ser null");
        assertEquals("Pedro", user.getName());
    }
}
