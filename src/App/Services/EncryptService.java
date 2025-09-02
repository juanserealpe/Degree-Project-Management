package App.Services;

import App.Interfaces.IEncrypt;
import org.mindrot.jbcrypt.BCrypt;

public class EncryptService implements IEncrypt {

    @Override
    public String Encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public boolean Check(String password, String hashpassword) {
        return BCrypt.checkpw(password, hashpassword);
    }
}
