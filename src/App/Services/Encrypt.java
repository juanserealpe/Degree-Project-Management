package App.Services;

import org.mindrot.jbcrypt.BCrypt;

public class Encrypt {

    private static Encrypt Instance;

    public static Encrypt GetInstance() {
        if (Instance == null) {
            Instance = new Encrypt();
        }
        return Instance;
    }

    public String Encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean Check(String password, String hashpassword) {
        return BCrypt.checkpw(password, hashpassword);
    }

}
