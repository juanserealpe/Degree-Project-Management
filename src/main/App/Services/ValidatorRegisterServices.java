package Services;

import Enums.EnumRole;
import Exceptions.RegisterUserFailerException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Interfaces.IValidatorRegisterServices;

/**
 * Servicio de validación de datos de registro de usuario.
 *
 * Esta clase implementa la interfaz IValidatorRegisterServices y se encarga de validar:
 *  - Contraseña
 *  - Email
 *  - Teléfono
 *  - Nombres y apellidos
 *  - Confirmación de contraseña
 *  - Roles asignados al usuario
 *
 * Lanza excepciones específicas de tipo RegisterUserFailerException si alguna validación falla.
 *
 * @Authors JhersonCastro, ElkinHoyos, juanserealpe
 */
public class ValidatorRegisterServices implements IValidatorRegisterServices {

    /** Patrón para validar números de teléfono (10 dígitos). */
    public static final String PHONE_NUMBER_PATTERN = "^\\d{10}$";

    /** Patrón para validar emails del dominio unicauca.edu.co */
    public static final String EMAIL_PATTERN = "^[a-zA-Z]+@unicauca\\.edu\\.co$";

    public ValidatorRegisterServices() {
    }

    /**
     * Valida la contraseña del usuario.
     *
     * @param password Contraseña a validar.
     * @throws RegisterUserFailerException Si la contraseña no cumple con los requisitos.
     */
    @Override
    public void isValidPassword(String password) throws RegisterUserFailerException {
        if (password.length() < 6) {
            throw RegisterUserFailerException.passwordTooShort();
        }
        if (!password.matches(".*[A-Z].*")) {
            throw RegisterUserFailerException.passwordWithoutUppercase();
        }
        if (!password.matches(".*[^A-Za-z0-9].*")) {
            throw RegisterUserFailerException.passwordWithoutSpecialChar();
        }
        if (!password.matches(".*\\d.*")) {
            throw RegisterUserFailerException.passwordWithoutNumber();
        }
    }

    /**
     * Valida el email del usuario.
     *
     * @param email Email a validar.
     * @throws RegisterUserFailerException Si el email no cumple con las reglas.
     */
    @Override
    public void isValidEmail(String email) throws RegisterUserFailerException {
        if (!email.endsWith("@unicauca.edu.co")) {
            throw RegisterUserFailerException.invalidEmailDomain();
        }
        String identifier = email.split("@")[0];

        if (identifier.matches(".*\\d.*")) {
            throw RegisterUserFailerException.emailWithNumbers();
        }
        if (!identifier.matches("^[a-zA-Z]+$")) {
            throw RegisterUserFailerException.emailWithSpecialChars();
        }

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw RegisterUserFailerException.invalidEmail();
        }
    }

    /**
     * Valida el número de teléfono.
     *
     * @param telephone Teléfono a validar.
     * @throws RegisterUserFailerException Si el teléfono no cumple el patrón.
     */
    @Override
    public void isValidTelephone(String telephone) throws RegisterUserFailerException {
        if ("".equals(telephone.trim())) {
            return; // No obligatorio
        }
        Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(telephone);
        if (!matcher.matches()) {
            throw RegisterUserFailerException.invalidTelephone();
        }
    }

    /**
     * Valida los nombres del usuario.
     *
     * @param prmNames Nombres a validar.
     * @throws RegisterUserFailerException Si hay números, caracteres especiales o longitud excedida.
     */
    @Override
    public void isValidNames(String prmNames) throws RegisterUserFailerException {
        if (prmNames.length() > 50) {
            throw RegisterUserFailerException.exceedsCharactersNames();
        }
        if (prmNames.matches(".*\\d.*")) {
            throw RegisterUserFailerException.numbersInName();
        }
        if (!prmNames.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            throw RegisterUserFailerException.specialCharsInName();
        }
    }

    /**
     * Valida los apellidos del usuario.
     *
     * @param prmLastNames Apellidos a validar.
     * @throws RegisterUserFailerException Si hay números, caracteres especiales o longitud excedida.
     */
    @Override
    public void isValidLastNames(String prmLastNames) throws RegisterUserFailerException {
        if (prmLastNames.length() > 30) {
            throw RegisterUserFailerException.exceedsCharactersLastNames();
        }
        if (prmLastNames.matches(".*\\d.*")) {
            throw RegisterUserFailerException.numbersInLastName();
        }
        if (!prmLastNames.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            throw RegisterUserFailerException.specialCharsInLastName();
        }
    }

    /**
     * Valida que la contraseña y su confirmación sean iguales.
     *
     * @param mainPassword Contraseña principal.
     * @param repPassword Contraseña de confirmación.
     * @throws RegisterUserFailerException Si no coinciden.
     */
    @Override
    public void validateConfirmPassword(String mainPassword, String repPassword) throws RegisterUserFailerException {
        if (!mainPassword.equals(repPassword))
            throw RegisterUserFailerException.invalidConfirmationPassword();
    }

    /**
     * Valida que se haya seleccionado al menos un rol.
     *
     * @param prmListRoles Lista de roles seleccionados.
     * @throws RegisterUserFailerException Si la lista está vacía.
     */
    @Override
    public void isValidRole(List<EnumRole> prmListRoles) throws RegisterUserFailerException {
        if (prmListRoles.isEmpty()) throw RegisterUserFailerException.NoRoleSelected();
    }
}
