package Main;

import DataBase.DbConnection;
import Enums.EnumProgram;
import Enums.EnumRole;
import Models.Account;
import Models.User;
import Repositories.AccountRepository;
import javafx.application.Application;

import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
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
    }
}