package App.Models;

public class Account {

    private int idAccount;
    private String password;

    public Account(int idaccount, String password) {
        this.idAccount = idaccount;
        this.password = password;
    }

    public Account(String password) {
        this.idAccount = 0;
        this.password = password;
    }

    public Account() {

    }

    // Getters y Setters
    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Account{idAccount=" + idAccount + ", password='[PROTECTED]'}";
    }
}
