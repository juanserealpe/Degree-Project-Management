package Exceptions;

/**
 * Excepción específica para fallos en el registro de usuarios.
 *
 * Esta clase maneja diferentes escenarios de validación al registrar
 * un usuario, incluyendo errores en contraseña, correo electrónico,
 * teléfono, nombres, apellidos y selección de roles. Proporciona métodos
 * estáticos para crear instancias con mensajes claros y específicos
 * según el tipo de error encontrado.
 *
 * @author juanserealpe
 */


public class RegisterUserFailerException extends AppException {

    public RegisterUserFailerException(String message) {
        super(message);
    }

    // -------- PASSWORD --------
    public static RegisterUserFailerException passwordTooShort() {
        return new RegisterUserFailerException("Debe tener al menos 6 caracteres.");
    }

    public static RegisterUserFailerException passwordWithoutUppercase() {
        return new RegisterUserFailerException("Debe contener al menos una letra mayúscula.");
    }

    public static RegisterUserFailerException passwordWithoutSpecialChar() {
        return new RegisterUserFailerException("Debe contener al menos un carácter especial.");
    }

    public static RegisterUserFailerException passwordWithoutNumber() {
        return new RegisterUserFailerException("Debe contener al menos un número.");
    }

    // -------- EMAIL --------
    public static RegisterUserFailerException invalidEmail() {
        return new RegisterUserFailerException(
                "El correo debe tener el formato correcto y pertenecer al dominio @unicauca.edu.co."
        );
    }

    public static RegisterUserFailerException invalidEmailDomain() {
        return new RegisterUserFailerException(
                "Dominio inválido, debe ser @unicauca.edu.co."
        );
    }

    public static RegisterUserFailerException emailWithNumbers() {
        return new RegisterUserFailerException(
                "No se permiten números en el identificador del correo electrónico."
        );
    }

    public static RegisterUserFailerException emailWithSpecialChars() {
        return new RegisterUserFailerException(
                "No se permiten caracteres especiales en el identificador del correo electrónico."
        );
    }

    // -------- TELEPHONE --------
    public static RegisterUserFailerException invalidTelephone() {
        return new RegisterUserFailerException(
                "El número de teléfono debe contener exactamente 10 dígitos numéricos."
        );
    }

    // -------- NAMES --------
    public static RegisterUserFailerException exceedsCharactersNames() {
        return new RegisterUserFailerException(
                "El nombre no puede exceder más de 50 caracteres."
        );
    }

    public static RegisterUserFailerException numbersInName() {
        return new RegisterUserFailerException(
                "No se permiten números en el nombre."
        );
    }

    public static RegisterUserFailerException specialCharsInName() {
        return new RegisterUserFailerException(
                "No se permiten caracteres especiales en el nombre."
        );
    }

    // -------- LAST NAMES --------
    public static RegisterUserFailerException exceedsCharactersLastNames() {
        return new RegisterUserFailerException(
                "Los apellidos no pueden exceder más de 30 caracteres."
        );
    }

    public static RegisterUserFailerException numbersInLastName() {
        return new RegisterUserFailerException(
                "No se permiten números en los apellidos."
        );
    }

    public static RegisterUserFailerException specialCharsInLastName() {
        return new RegisterUserFailerException(
                "No se permiten caracteres especiales en los apellidos."
        );
    }
    public static RegisterUserFailerException invalidConfirmationPassword() {
        return new RegisterUserFailerException(
                "Las contraseñas no coinciden."
        );
    }
    public static RegisterUserFailerException NoRoleSelected() {
        return new RegisterUserFailerException(
                "Se debe seleccionar almenos 1 rol."
        );
    }
}
