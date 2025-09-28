package Models;

import Enums.EnumRole;
import Utilities.Logger;

import java.util.List;

public class Session {
    private static int IdAccount;
    private static String email;
    private static List<EnumRole> roles;

    public Session() {}

    public static String getEmail() {return email;}
    public static int getIdAccount() {return IdAccount;}

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
    public static void setIdAccount(int idAccount) {
        Logger.info(Session.class, "setIdAccount: " + idAccount);
        Session.IdAccount = idAccount;
    }

    @Override
    public String toString() {
        return "Session{"
                + "idaccount=" + getIdAccount()
                + "email='" + email + '\''
                + ", roles=" + roles
                + '}';
    }
}
