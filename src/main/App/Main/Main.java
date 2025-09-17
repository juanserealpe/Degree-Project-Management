package Main;

import DataBase.DbConnection;
import javafx.application.Application;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) throws Exception {
        Application.launch(App.class, args);
        //Application.launch(App.class, args);
        try {
            Connection conn = DbConnection.getConnection();
            if (conn != null) {
                System.out.println("Conexión establecida correctamente");
            } else {
                System.out.println("No se pudo establecer la conexión");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbConnection.closeConnection();
        }
    }
}