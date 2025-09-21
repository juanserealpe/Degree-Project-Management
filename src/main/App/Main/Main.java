package Main;

import DataBase.DbConnection;
<<<<<<< HEAD
import Enums.EnumProgram;
import Enums.EnumRole;
import Models.Account;
import Models.User;
import Repositories.AccountRepository;
import javafx.application.Application;
=======
import Repositories.TestRepository;
>>>>>>> alex_branch

import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
<<<<<<< HEAD
        //Application.launch(App.class, args);
        User newUser = new User("Juan", "Pérez", "3001234567");

        Account newAccount = new Account(
                0,
                EnumProgram.ING_SISTEMAS,
                "juan.perez@example.com",
                "123456",
                newUser,
                List.of(EnumRole.UNDERGRADUATE_STUDENT) // roles
        );

        AccountRepository accountRepo = new AccountRepository();
        accountRepo.add(newAccount);

        System.out.println("Cuenta agregada con ID: " + newAccount.getIdAccount());
=======
        DbConnection.getConnection();
        TestRepository test = new TestRepository();
        // Ejemplo de parámetros
        Object[] pInsert = { "Alex", "Ponce", "123456789" };
        Object[] pUpdate = { "321654987", 1 }; // Cambiamos phone del idUser 1
        Object[] pRetrieve = { 1 }; // Recuperamos el usuario con idUser = 1

        // INSERT
        String insertScript = "INSERT INTO User (name, lastName, phone) VALUES (?, ?, ?)";
        test.INSERT(insertScript, pInsert);

        // UPDATE
        String updateScript = "UPDATE User SET phone = ? WHERE idUser = ?";
        test.UPDATE(updateScript, pUpdate);

        // RETRIEVE
        String retrieveScript = "SELECT * FROM User WHERE idUser = ?";
        test.RETRIEVE(retrieveScript, pRetrieve);

        // También podemos probar un retrieve que no exista
        Object[] pRetrieveNoExist = { 999 };
        test.RETRIEVE(retrieveScript, pRetrieveNoExist);

        // Y un insert con null
        Object[] pInsertNull = { "Maria", "Lopez", null };
        test.INSERT(insertScript, pInsertNull);
        DbConnection.closeConnection();
>>>>>>> alex_branch
    }
}