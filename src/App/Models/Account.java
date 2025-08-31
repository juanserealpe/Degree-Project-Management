package App.Models;

public class Account {
    private int idAccount;
    private String password;

    public Account(int idAccount, String password) {
        this.idAccount = idAccount;
        this.password = password;
    }
    public Account(){
        
    }

    // Getters y Setters
    public int getIdAccount() { return idAccount; }
    public void setIdAccount(int idAccount) { this.idAccount = idAccount; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "Account{idAccount=" + idAccount + ", password='[PROTECTED]'}";
    }
}

