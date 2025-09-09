package Services;

import Dtos.UserDTO;
import Exceptions.LoginFailedException;
import Exceptions.RegisterUserFailerException;
import Interfaces.IAccountRepository;
import Interfaces.IAuthService;
import Interfaces.IEncrypt;
import Interfaces.IRepository;
import Interfaces.IRoleRepository;
import Models.Account;
import Models.Role;
import Models.Session;
import Models.User;
import java.util.List;

public class AuthService implements IAuthService {

    private final IAccountRepository _accountRepository;
    private final IRoleRepository _roleRepository;
    private final IEncrypt _encryptService;
    private final IRepository<UserDTO> _userRepository;

    public AuthService(IAccountRepository _accountRepository, IRoleRepository _roleRepository, IEncrypt _encryptService,
                       IRepository<UserDTO> _userRepository) {
        this._accountRepository = _accountRepository;
        this._roleRepository = _roleRepository;
        this._encryptService = _encryptService;
        this._userRepository = _userRepository;
    }

    @Override
    public void isLoginValid(String prmEmail, String prmPassword) throws Exception {
        System.out.println("[AuthService] Iniciando validación de login...");

        // Validar dominio
        System.out.println("[AuthService] Verificando dominio del email...");
        if (!prmEmail.endsWith("@unicauca.edu.co")) {
            System.out.println("[AuthService] ❌ Email con dominio inválido: " + prmEmail);
            System.out.println();
            throw RegisterUserFailerException.invalidEmailDomain();
        }

        // Buscar usuario
        System.out.println("[AuthService] Buscando usuario en repositorio...");
        UserDTO user = _userRepository.toGetByString(prmEmail);
        if (user == null) {
            System.out.println("[AuthService] ❌ Usuario no encontrado.");
            throw LoginFailedException.invalidCredentials();
        }
        System.out.println("[AuthService] ✅ Usuario encontrado: " + user.getUser().getEmail());

        // Buscar cuenta asociada
        System.out.println("[AuthService] Buscando cuenta asociada...");
        Account resultAccount = _accountRepository.toGetById(user.getUser().getAccountId());
        if (resultAccount == null) {
            System.out.println("[AuthService] ❌ Cuenta no encontrada.");
            throw LoginFailedException.invalidCredentials();
        }
        System.out.println("[AuthService] ✅ Cuenta encontrada: " + resultAccount.getIdAccount());

        // Validar contraseña
        System.out.println("[AuthService] Verificando contraseña...");
        if (!_encryptService.Check(prmPassword, resultAccount.getPassword())) {
            System.out.println("[AuthService] ❌ Contraseña incorrecta.");
            throw LoginFailedException.invalidCredentials();
        }
        System.out.println("[AuthService] ✅ Contraseña correcta.");

        // Obtener roles
        System.out.println("[AuthService] Cargando roles...");
        List<Role> roles = _roleRepository.getByEmail(prmEmail);
        System.out.println("[AuthService] ✅ Roles obtenidos: " + roles);

        // Inicializar sesión
        System.out.println("[AuthService] Inicializando sesión...");
        Session.init(prmEmail, roles);

        System.out.println("[AuthService] ✅ Sesión iniciada para: " + prmEmail);
    }

}
