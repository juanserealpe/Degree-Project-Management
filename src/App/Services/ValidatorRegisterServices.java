package App.Services;

import App.Exceptions.RegisterUserFailerException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import App.Interfaces.IValidatorRegisterServices;

public class ValidatorRegisterServices implements IValidatorRegisterServices {

    public static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{6,}$";
    public static final String PHONE_NUMBER_PATTERN = "^\\d{10}$";
    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@unicauca\\.edu\\.co$";

    public ValidatorRegisterServices() {
    }

    @Override
    public void isValidPassword(String password) throws RegisterUserFailerException {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            throw RegisterUserFailerException.invalidPassword();
        }
    }

    @Override
    public void isValidEmail(String email) throws RegisterUserFailerException {
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
    }

    @Override
    public void isValidLastNames(String prmLastNames) throws RegisterUserFailerException {
        if (prmLastNames.length() > 30) {
            throw RegisterUserFailerException.exceedsCharactersLastNames();
        }
    }

}
