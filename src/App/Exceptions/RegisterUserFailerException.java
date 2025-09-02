package App.Exceptions;

public class RegisterUserFailerException extends AppException {

    public RegisterUserFailerException(String message) {
        super(message);
    }

    public static RegisterUserFailerException invalidPassword() {
        return new RegisterUserFailerException(
                "La contraseña debe tener al menos 6 caracteres, una mayúscula, un número y un carácter especial."
        );
    }

    public static RegisterUserFailerException invalidEmail() {
        return new RegisterUserFailerException(
                "El correo debe tener el formato correcto y pertenecer al dominio @unicauca.edu.co."
        );
    }

    public static RegisterUserFailerException invalidTelephone() {
        return new RegisterUserFailerException(
                "El número de teléfono debe contener exactamente 10 dígitos numéricos."
        );
    }

    public static RegisterUserFailerException exceedsCharactersNames() {
        return new RegisterUserFailerException(
                "El nombre no puede exceder más de 50 caracteres."
        );
    }

    public static RegisterUserFailerException exceedsCharactersLastNames() {
        return new RegisterUserFailerException(
                "los apellidos no pueden exceder más de 30 caracteres."
        );
    }
}
