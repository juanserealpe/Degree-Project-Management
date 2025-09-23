package Interfaces;


import Dtos.UserRegisterDTO;

public interface IAuthService {

    public UserRegisterDTO isLoginValid(String prmEmail, String prmPassword) throws Exception;
}
