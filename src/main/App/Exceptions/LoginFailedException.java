package Exceptions;

/**
 * Excepción específica para fallos de autenticación.
 *
 * Esta clase se lanza cuando un intento de login no es exitoso,
 * por ejemplo, debido a credenciales incorrectas. Proporciona un método
 * estático para crear una instancia con el mensaje estándar de
 * usuario o contraseña incorrectos.
 *
 * @author juanserealpe
 */

public class LoginFailedException extends AppException{

    public LoginFailedException(String message) {
        super(message);
    }

    public static LoginFailedException invalidCredentials() {
        return new LoginFailedException("Usuario o contraseña incorrectos.");
    }
}
