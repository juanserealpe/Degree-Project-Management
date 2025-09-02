package App.Exceptions;

public class LoginFailedException extends AppException{

    public LoginFailedException(String message) {
        super(message);
    }

    public static LoginFailedException invalidCredentials() {
        return new LoginFailedException("Usuario o contraseña incorrectos.");
    }
}
