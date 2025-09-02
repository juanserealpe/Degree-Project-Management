package App.Interfaces;

import App.Exceptions.RegisterUserFailerException;

public interface IValidatorRegisterServices {

    public void isValidPassword(String password) throws RegisterUserFailerException;

    public void isValidEmail(String email) throws RegisterUserFailerException;

    public void isValidTelephone(String telephone) throws RegisterUserFailerException;

    public void isValidNames(String prmNames) throws RegisterUserFailerException;

    public void isValidLastNames(String prmLastNames) throws RegisterUserFailerException;
}
