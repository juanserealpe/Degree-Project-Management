package App.Services;

import App.interfaces.IValidatorService;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ValidatorService implements IValidatorService {

    public static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{6,}$";
    public static final String PHONE_NUMBER_PATTERN = "^\\d{10}$";
    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@unicauca\\.edu\\.co$";
    private static ValidatorService Instance;

    public static ValidatorService GetInstance() {
        if (Instance == null) {
            Instance = new ValidatorService();
        }
        return Instance;
    }

    @Override
    public void isValidPassword(String password) throws Exception {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            throw new Exception("Contraseña no valida");
        }
    }

    @Override
    public void isValidEmail(String email) throws Exception {

        System.out.println(email + "<----");
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new Exception("Correo inválido, recuerde finalizar con @unicauca.edu.co");
        }
    }

    @Override
    public void isValidTelephone(String telephone) throws Exception {
        if("".equals(telephone.trim())) return;
        
        Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(telephone);
        if (!matcher.matches()) {
            throw new Exception("Telefono no valido");
        }
    }
}
