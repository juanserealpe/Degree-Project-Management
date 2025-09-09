package Interfaces;

import Dtos.StudentDTO;
import Dtos.UserDTO;
import Models.User;

import java.util.List;

public interface IUserServices {
    public void registerUser(UserDTO prmUser) throws Exception;
    public void deleteUser(String prmEmail) throws Exception;
    public List<UserDTO> getAllUsers() throws Exception;
    public UserDTO getUserByEmail(String prmEmail) throws Exception;
    public UserDTO validateUser(UserDTO prmUser) throws Exception;
}
