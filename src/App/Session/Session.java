package App.Session;

public class Session {
    private static Session instancia;
    private String email;
    private String rol;

    // Constructor privado para patrón singleton

    public Session(String email, String rol) {
        this.email = email;
        this.rol = rol;
    }
    

    // Iniciar la sesión
    public static void startSession(String prmEmail, String prmRol) {
        instancia = new Session(prmEmail, prmRol);
    }

    // Obtener la sesión actual
    public static Session getActual() {
        return instancia;
    }

    // Cerrar sesión
    public static void closeSession() {
        instancia = null;
    }

    // Obtener el email de la sesión
    public String getEmail() {
        return email;
    }
    public String getRol() {
        return rol;
    }
}
