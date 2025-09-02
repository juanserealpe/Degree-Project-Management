package App.Models;

import java.util.ArrayList;
import java.util.List;

public class User {

    private List<Role> roles = new ArrayList<>();
    private String email;
    private String names;
    private String lastNames;
    private int accountId;
    private int programId;
    private String numberPhone;

    public User(String email, String names, String lastNames, int accountId, int programId, List<Role> listRole, String numberPhone) {
        this.email = email;
        this.names = names;
        this.lastNames = lastNames;
        this.accountId = accountId;
        this.programId = programId;
        this.roles = listRole;
        this.numberPhone = numberPhone;
    }

    public User(String email, String names, String lastNames, int accountId, int programId, String numberPhone) {
        this.email = email;
        this.names = names;
        this.lastNames = lastNames;
        this.accountId = accountId;
        this.programId = programId;
        this.numberPhone = numberPhone;
    }

    public User(String email, String names, String lastNames, int accountId, int programId) {
        this.email = email;
        this.names = names;
        this.lastNames = lastNames;
        this.accountId = accountId;
        this.programId = programId;
    }

    public User() {
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    @Override
    public String toString() {
        return "User{" + "roles=" + roles + ", email=" + email + ", names=" + names + ", lastNames=" + lastNames + ", accountId=" + accountId + ", programId=" + programId + ", numberPhone=" + numberPhone + '}';
    }

}
