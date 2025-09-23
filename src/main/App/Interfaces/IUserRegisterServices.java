package Interfaces;

import Dtos.UserRegisterDTO;

import java.util.List;

public interface IUserRegisterServices {
    public void registerUser(UserRegisterDTO prmUser) throws Exception;
    public UserRegisterDTO validateUser(UserRegisterDTO prmUser) throws Exception;
}
