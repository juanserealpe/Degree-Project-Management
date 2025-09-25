package Interfaces;


import Dtos.UserRegisterDTO;

public interface IAuthService {

    public void isLoginValid(String prmEmail, String prmPassword) throws Exception;
}
