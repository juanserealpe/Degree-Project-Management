package App.Models;

import java.util.ArrayList;
import java.util.List;

public class Professor extends User {

    private List<Role> roles = new ArrayList<>();

    public Professor(String email, String names, String lastNames, int accountId, int program, List<Role> roles) {
        super(email, names, lastNames, accountId, program);
        this.roles = roles;
    }

    public Professor() {

    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Profesor{" + super.toString() + ", roles=" + roles + "}";
    }
}
