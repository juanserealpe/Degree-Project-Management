package App.Interfaces;

import App.Models.Session;

public interface IAuthService {

    public Session isLoginValid(String prmEmail, String prmPassword) throws Exception;
}
