package App.Services;

import App.Exceptions.LoginFailedException;
import App.Interfaces.IAccountRepository;
import App.Interfaces.IAuthService;
import App.Interfaces.IEncrypt;
import App.Interfaces.IRepository;
import App.Interfaces.IRoleRepository;
import App.Models.Account;
import App.Models.Role;
import App.Models.Session;
import App.Models.User;
import java.util.List;

public class AuthService implements IAuthService {

    private final IAccountRepository _accountRepository;
    private final IRoleRepository _roleRepository;
    private final IEncrypt _encryptService;
    private final IRepository<User> _userRepository;

    public AuthService(IAccountRepository _accountRepository, IRoleRepository _roleRepository, IEncrypt _encryptService, IRepository<User> _userRepository) {
        this._accountRepository = _accountRepository;
        this._roleRepository = _roleRepository;
        this._encryptService = _encryptService;
        this._userRepository = _userRepository;
    }

    @Override
    public Session isLoginValid(String prmEmail, String prmPassword) throws Exception {
        User user = _userRepository.toGetByString(prmEmail);
        if (user == null) {
            throw LoginFailedException.invalidCredentials();
        }
        Account resultAccount = _accountRepository.toGetById(user.getAccountId());
        if (resultAccount == null) {
            throw LoginFailedException.invalidCredentials();
        }
        if (!_encryptService.Check(prmPassword, resultAccount.getPassword())) {
            throw LoginFailedException.invalidCredentials();
        }

        List<Role> roles = _roleRepository.getByEmail(prmEmail);
        Session session = new Session();
        session.setEmail(prmEmail);
        session.setRoles(roles);

        System.out.println(">> Sesión iniciada para: " + prmEmail);
        System.out.println(">> Roles: " + roles.toString());
        return session;
    }

}
