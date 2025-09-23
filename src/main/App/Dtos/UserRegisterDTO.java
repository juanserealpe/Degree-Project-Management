package Dtos;

import Models.Account;
import Models.User;

public class UserRegisterDTO {
    private String password;
    private User user;
    private Account account;

    public UserRegisterDTO(String password, User user, Account account) {
        this.password = password;
        this.user = user;
        this.account = account;
    }

    public String getPassword() {return this.password;}
    public void  setPassword(String password) {this.password = password;}

    public User getUser() {return this.user;}
    public void setUser(User user) {this.user = user;}

    public Account getAccount() {return this.account;}
    public void setAccount(Account account) {this.account = account;}
}
