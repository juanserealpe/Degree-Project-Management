package Services;

import Interfaces.IEncrypt;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Servicio de encriptación de contraseñas.
 *
 * Esta clase implementa la interfaz IEncrypt y proporciona métodos
 * para encriptar contraseñas usando el algoritmo BCrypt y para
 * verificar contraseñas contra su hash.
 */
public class EncryptService implements IEncrypt {

    /**
     * Encripta una contraseña utilizando BCrypt.
     *
     * @param password Contraseña en texto plano.
     * @return Contraseña encriptada (hash).
     */
    @Override
    public String Encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Verifica si una contraseña coincide con un hash previamente generado.
     *
     * @param password Contraseña en texto plano.
     * @param hashpassword Hash de la contraseña.
     * @return true si coinciden, false en caso contrario.
     */
    @Override
    public boolean Check(String password, String hashpassword) {
        return BCrypt.checkpw(password, hashpassword);
    }
}
