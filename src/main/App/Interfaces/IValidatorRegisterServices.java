package Interfaces;

import Enums.EnumRole;
import Exceptions.RegisterUserFailerException;


import java.util.List;

public interface IValidatorRegisterServices {

    public void isValidPassword(String password) throws RegisterUserFailerException;

    public void isValidEmail(String email) throws RegisterUserFailerException;

    public void isValidTelephone(String telephone) throws RegisterUserFailerException;

    public void isValidNames(String prmNames) throws RegisterUserFailerException;

    public void isValidLastNames(String prmLastNames) throws RegisterUserFailerException;

    public void validateConfirmPassword(String mainPassword, String repPassword) throws RegisterUserFailerException;
    public void isValidRole(List<EnumRole> prmListRoles) throws RegisterUserFailerException;
}
