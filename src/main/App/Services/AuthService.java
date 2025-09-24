package Services;

import Dtos.UserRegisterDTO;
import Exceptions.LoginFailedException;
import Interfaces.IAuthService;
import Interfaces.IEncrypt;
import Interfaces.IRepository;
import Models.Account;
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
    public UserRegisterDTO isLoginValid(String prmEmail, String prmPassword) throws Exception {
        // Obtener la cuenta asociada al email
        Account resultAccountByEmail = _userRepository.getByString(prmEmail).getAccount();
        if (resultAccountByEmail == null) LoginFailedException.invalidCredentials();

        // Recuperar DTO completo del usuario
        UserRegisterDTO result = _userRepository.getByString(prmEmail);

        // Comparar contraseña ingresada con la almacenada
        boolean isPasswordEqual = _encryptService.Check(prmPassword, result.getPassword());
        if (isPasswordEqual) {
            Logger.info(AuthService.class, "isLoginValid: login válido para el usuario " + prmEmail);
            result.setPassword(null); // Limpiar contraseña antes de retornar
            _cookieService.setCookie(result.getAccount().getIdAccount());
            return result;
        } else {
            return null;
        }
    }
}
