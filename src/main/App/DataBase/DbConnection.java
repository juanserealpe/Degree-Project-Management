package DataBase;

import Utilities.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try (InputStream input = DbConnection.class.getClassLoader().getResourceAsStream("config.properties")) {
                if (input == null) {
                    throw new IOException("No se encontró config.properties en el classpath");
                }

                Properties props = new Properties();
                props.load(input);
                String url = props.getProperty("db.url");

                connection = DriverManager.getConnection(url);
                Logger.info("Conexión establecida con SQLite.");
            } catch (IOException e) {
                Logger.error("Error leyendo config.properties: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                Logger.info("Conexión cerrada correctamente.");
            } catch (SQLException e) {
                Logger.error("Error al cerrar la conexión: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
