package Services;

import Dtos.UserRegisterDTO;
import Exceptions.LoginFailedException;
import Interfaces.IAuthService;
import Interfaces.IEncrypt;
import Interfaces.IRepository;
import Models.Account;
import Models.Session;
import Utilities.Logger;

/**
 * Servicio de autenticación de usuarios.
 *
 * Esta clase se encarga de validar credenciales de login mediante
 * la verificación de correo electrónico y contraseña.
 * Utiliza un repositorio para obtener los datos del usuario y un
 * servicio de cifrado para comparar la contraseña ingresada con la almacenada.
 *
 * @author juanseralpe
 */
public class AuthService implements IAuthService {

    private final IEncrypt _encryptService;               // Servicio de cifrado
    private final IRepository<UserRegisterDTO> _userRepository; // Repositorio de usuarios
    private final CookieService _cookieService;
    /**
     * Constructor que recibe los servicios necesarios para autenticación.
     *
     * @param _encryptService Servicio de cifrado de contraseñas.
     * @param _userRepository Repositorio de usuarios para consultar información.
     */
    public AuthService(IEncrypt _encryptService, IRepository<UserRegisterDTO> _userRepository, CookieService _cookieService) {
        this._encryptService = _encryptService;
        this._userRepository = _userRepository;
        this._cookieService = _cookieService;
    }

    /**
     * Valida si las credenciales de login son correctas.
     *
     * @param prmEmail    Correo electrónico del usuario.
     * @param prmPassword Contraseña ingresada.
     * @return DTO del usuario sin la contraseña si las credenciales son válidas; null en caso contrario.
     * @throws Exception Si ocurre algún error durante la consulta o validación.
     */
    @Override
    public void isLoginValid(String prmEmail, String prmPassword) throws Exception {
        // Obtener el usuario por email
        UserRegisterDTO result = _userRepository.getByString(prmEmail);

        if (result == null) {
            Logger.warn(AuthService.class, "isLoginValid: cuenta no encontrada para " + prmEmail);
            throw LoginFailedException.invalidCredentials();
        }

        // Comparar contraseña ingresada con la almacenada
        boolean isPasswordEqual = _encryptService.Check(prmPassword, result.getPassword());

        if (!isPasswordEqual) {
            Logger.warn(AuthService.class, "isLoginValid: contraseña incorrecta para " + prmEmail);
            throw LoginFailedException.invalidCredentials();
            _cookieService.setCookie(result.getAccount().getIdAccount());
        }

        Logger.info(AuthService.class, "isLoginValid: login válido para " + prmEmail);

        // Limpiar la contraseña antes de continuar
        result.setPassword(null);

        // Iniciar sesión
        Session.setRoles(result.getAccount().getRoles());
        Session.setEmail(result.getAccount().getEmail());
    }

}
