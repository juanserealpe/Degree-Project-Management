package App.Interfaces;

import App.Models.Session;

public interface IAuthService {

    public void isLoginValid(String prmEmail, String prmPassword) throws Exception;
}
