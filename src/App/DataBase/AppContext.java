/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package App.DataBase;

import App.Services.UserService;
import App.controllers.AccountController;

/**
 *
 * @author juans
 */
public class AppContext {
    public static DataBase db;
    public static UserService userService;
    public static AccountController accountController;

    public static void init() {
        db = new DataBase();
        userService = new UserService(db);
        accountController = new AccountController(userService);
    }
}

