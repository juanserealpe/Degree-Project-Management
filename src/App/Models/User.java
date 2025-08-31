package App.Models;

public class User {

    private String email;
    private String names;
    private String lastNames;
    private int accountId;
    private int programId;

    public User(String email, String names, String lastNames, int accountId, int program) {
        this.email = email;
        this.names = names;
        this.lastNames = lastNames;
        this.accountId = accountId;
        this.programId = program;
    }
    public User(){
        
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getLastNames() {
        return lastNames;
    }

    public void setLastNames(String lastNames) {
        this.lastNames = lastNames;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    @Override
    public String toString() {
        return "User{" + "email=" + email + ", names=" + names + ", lastNames=" + lastNames + ", accountId=" + accountId + ", programId=" + programId + '}';
    }

}
