package Main;

import DataBase.DbConnection;
import Repositories.TestRepository;
import javafx.application.Application;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) throws Exception {
        //Application.launch(App.class, args);
        //Application.launch(App.class, args);
        TestRepository test = new TestRepository();
        test.INSERT("INSERT INTO USER", null);
    }
}