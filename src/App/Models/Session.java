package App.Models;

import java.util.List;

public class Session {

    private static String email;
    private static List<Role> roles;

    private Session() {

    }

    public static void init(String email, List<Role> roles) {
        Session.email = email;
        Session.roles = roles;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        Session.email = email;
    }

    public static List<Role> getRoles() {
        return roles;
    }

    public static void setRoles(List<Role> roles) {
        Session.roles = roles;
    }

    // Método útil: validar si la sesión tiene un rol en específico
    public static boolean hasRole(int roleId) {
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
