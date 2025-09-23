package Models;

import Enums.EnumProgram;
import Enums.EnumRole;
import java.util.List;

public class Account {
    private int IdAccount;
    private EnumProgram Program;
    private String Email;
    private User User;
    private List<EnumRole> Roles;

    public Account(int idAccount, EnumProgram program, String email, String password, User user,  List<EnumRole> roles) {
        this.IdAccount = idAccount;
        this.Program = program;
        this.Email = email;
        this.User = user;
        this.Roles = roles;
    }

    public Account() {

    }

    public int getIdAccount() { return IdAccount; }
    public void setIdAccount(int idAccount) { this.IdAccount = idAccount; }

    public EnumProgram getProgram() { return Program; }
    public void setProgram(EnumProgram program) { this.Program = program; }

    public String getEmail() { return Email; }
    public void setEmail(String email) { this.Email = email; }

    public User getUser() { return User; }
    public void setUser(User user) { this.User = user; }

    public List<EnumRole> getRoles() { return Roles; }
    public void setRoles(List<EnumRole> roles) { this.Roles = roles; }

    @Override
    public String toString() {
        return "Account{" +
                "IdAccount=" + IdAccount +
                ", Program=" + Program +
                ", Email='" + Email + '\'' +
                ", User=" + (User != null ? User.toString() : "null") +
                ", Roles=" + (Roles != null ? Roles.toString() : "[]") +
                '}';
    }

}
