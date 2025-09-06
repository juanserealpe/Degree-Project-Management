package Services;

import Exceptions.RegisterUserFailerException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Interfaces.IValidatorRegisterServices;

public class ValidatorRegisterServices implements IValidatorRegisterServices {

    public static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{6,}$";
    public static final String PHONE_NUMBER_PATTERN = "^\\d{10}$";
    public static final String EMAIL_PATTERN = "^[a-zA-Z]+@unicauca\\.edu\\.co$"; // Solo letras en identificador

    public ValidatorRegisterServices() {
    }

    @Override
    public void isValidPassword(String password) throws RegisterUserFailerException {
        if (password.length() < 6) {
            throw RegisterUserFailerException.invalidPassword();
        }
        if (!password.matches(".*[A-Z].*")) {
            throw RegisterUserFailerException.passwordWithoutUppercase();
        }
        if (!password.matches(".*[^A-Za-z0-9].*")) {
            throw RegisterUserFailerException.passwordWithoutSpecialChar();
        }
        if (!password.matches(".*\\d.*")) {
            throw RegisterUserFailerException.invalidPassword();
        }
    }

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
}
