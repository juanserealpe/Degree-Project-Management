package Dtos;

import Enums.EnumProgram;
import Enums.EnumRole;
import Models.Account;
import Models.User;

import java.util.List;

public class UserRegisterDTO {

    private String Password;
    private User User;
    private Account Account;

    public UserRegisterDTO(Account Account, User User, String Password) {
        this.User = User;
        this.Account = Account;
        this.Password = Password;
    }

    public UserRegisterDTO() {}

    public String getPassword() { return Password; }
    public void setPassword(String password) { this.Password = password; }

    public User getUser() { return User; }
    public void setUser(User user) { this.User = user; }

    public Account getAccount() { return Account; }
    public void setAccount(Account account) { this.Account = account; }
}
