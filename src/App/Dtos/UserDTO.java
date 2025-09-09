package Dtos;

import Models.Account;
import Models.Student;
import Models.User;

public class UserDTO {
    private User user;
    private Account account;

    public UserDTO(User user, Account account) {
        this.user = user;
        this.account = account;
    }

    public UserDTO() {

    }

    public User getUser() {
        return user;
    }

    public Account getAccount() {
        return account;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    @Override
    public String toString() {
        return "StudentDTO{" + "student=" + user + ", account=" + account + '}';
    }

}
