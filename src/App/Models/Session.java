package App.Models;

import java.util.List;

public class Session {

    private String email;
    private List<Role> roles;

    public Session(String email, List<Role> roles) {
        this.email = email;
        this.roles = roles;
    }

    public Session() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    // Método útil: validar si la sesión tiene un rol en específico
    public boolean hasRole(int roleId) {
        if (roles == null) {
            return false;
        }
        return roles.stream().anyMatch(r -> r.getIdRole() == roleId);
    }

    @Override
    public String toString() {
        return "Session{"
                + "email='" + email + '\''
                + ", roles=" + roles
                + '}';
    }
}
