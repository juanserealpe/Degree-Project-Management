package App.controllers;

import App.Services.UserService;

public class AccountController {

    public UserService _userService;

    public AccountController(UserService _userService) {
        this._userService = _userService;
    }

    public void isLoginValid(String prmEmail, String prmPassword) throws Exception {
        _userService.LoginUser(prmEmail, prmPassword);
    }

    public void RegisterStudent(String name, String lastName, String PhoneNumber, String program,
            String email, String password) throws Exception {
        _userService.RegisterUser(name, lastName, PhoneNumber, program, email, password);
    }
}
