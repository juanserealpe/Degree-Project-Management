package App.Interfaces;

public interface IValidatorRegisterServices {

    public void isValidPassword(String password) throws Exception;

    public void isValidEmail(String email) throws Exception;

    public void isValidTelephone(String telephone) throws Exception;
}
