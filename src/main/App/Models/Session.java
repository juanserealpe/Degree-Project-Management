package Models;

import Enums.EnumRole;
import Utilities.Logger;

import java.util.List;

public class Session {

    private static String email;
    private static List<EnumRole> roles;

    private Session() {}

    public static String getEmail() {return email;}

    public static void setEmail(String email) {Session.email = email;}

    public static List<EnumRole> getRoles() {return roles;}
    public static void logOut(){
        Session.roles.clear();
        Session.email = "";
    }
    public static void setRoles(List<EnumRole> roles) {
        Logger.info(Session.class, "setRoles: roles " + roles);
        Session.roles = roles;
    }

    @Override
    public String toString() {
        return "Session{"
                + "email='" + email + '\''
                + ", roles=" + roles
                + '}';
    }
}
