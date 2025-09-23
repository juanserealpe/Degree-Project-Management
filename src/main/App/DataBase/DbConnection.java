package DataBase;

import Utilities.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {

    /**
     * Devuelve una nueva conexión a la base de datos usando la configuración de config.properties.
     *
     * @return Connection conexión abierta a la base de datos
     * @throws SQLException si no se puede abrir la conexión
     */
    public static Connection getConnection() throws SQLException {
        try (InputStream input = DbConnection.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IOException("No se encontró config.properties en el classpath");
            }

            Properties props = new Properties();
            props.load(input);
            String url = props.getProperty("db.url");

            Connection conn = DriverManager.getConnection(url);
            Logger.info(DbConnection.class,"Conexión establecida con SQLite.");
            return conn;
        } catch (IOException e) {
            Logger.error(DbConnection.class,"Error leyendo config.properties: " + e.getMessage());
            throw new SQLException("No se pudo abrir conexión", e);
        }
    }

    /**
     * Cierra de forma segura una conexión.
     *
     * @param conn conexión a cerrar (puede ser null)
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                    Logger.info(DbConnection.class,"Conexión cerrada correctamente.");
                }
            } catch (SQLException e) {
                Logger.error(DbConnection.class,"Error al cerrar la conexión: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
