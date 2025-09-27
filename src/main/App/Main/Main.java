package Main;

import DataBase.DbConnection;
import javafx.application.Application;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Application.launch(App.class, args);
        DbConnection.closeConnection(DbConnection.getConnection());
    }
}