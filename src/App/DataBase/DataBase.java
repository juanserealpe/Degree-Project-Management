package App.DataBase;

import App.interfaces.IDataBaseConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase implements IDataBaseConnection{
    private final String URL = "jdbc:sqlite:C:\\Users\\juans\\Desktop\\Repositorio\\Degree-Project-Management\\database.db";


    @Override
    public Connection Connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Error al conectar con SQLite: " + e.getMessage());
        }
        return conn;
    }
    @Override
    public void Disconnect(Connection conn) {
    if (conn != null) {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}

    
}
