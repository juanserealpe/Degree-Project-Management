package Models;

import Enums.EnumRole;
import Utilities.Logger;

import java.util.ArrayList;
import java.util.List;

public class Session {
    private static Session instance;
    private int id;
    private String email;
    private List<EnumRole> roles;

    public Session() {}
    public static Session getInstance(){
        if(instance == null){
            instance = new Session();
            instance.roles = new ArrayList<>();
            instance.email = "";
            instance.id = -1;
        }
        return instance;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getEmail() {return email;}

    public void setEmail(String email) {instance.email = email;}

    public List<EnumRole> getRoles() {return roles;}
    public void logOut(){
        instance.roles.clear();
        instance.email = "";
    }
    public void setRoles(List<EnumRole> roles) {
        Logger.info(Session.class, "setRoles: roles " + roles);
        instance.roles = roles;
    }

    @Override
    public String toString() {
        return "Session{"
                + "email='" + email + '\''
                + ", roles=" + roles
                + '}';
    }
}
