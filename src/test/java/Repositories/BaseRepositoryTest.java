package Repositories;

import DataBase.DbConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseRepositoryTest {
    protected Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DbConnection.getConnection();
        connection.setAutoCommit(false);
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.rollback();
            connection.close();
        }
    }

    protected boolean existsInTable(String sql, Object... params) throws SQLException {
        try (var stmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            try (var rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}
