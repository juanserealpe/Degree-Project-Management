package App.DataBase;

import App.Interfaces.IDbConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection implements IDbConnection {

    private final String URL = "jdbc:sqlite:C:\\Users\\juans\\Desktop\\Sw2\\Degree-Project-Management\\database2.db";

    @Override
    public Connection toConnect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar con SQLite: " + e.getMessage());
            e.printStackTrace(); // para más detalles del error
        }
        return conn;
    }

    @Override
    public void toDisconect(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
