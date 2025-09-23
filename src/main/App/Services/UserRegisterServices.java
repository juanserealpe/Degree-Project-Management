package Services;

import Dtos.UserRegisterDTO;
import Interfaces.*;
import Models.Account;
import Models.User;

/**
 * Servicio para la gestión del registro de usuarios.
 *
 * Esta clase implementa la interfaz IUserRegisterServices y se encarga de:
 *  - Validar los datos de un usuario.
 *  - Normalizar los datos (como nombres y apellidos).
 *  - Encriptar la contraseña.
 *  - Registrar el usuario en el repositorio correspondiente.
 *
 * Depende de servicios de validación, encriptación y normalización de datos.
 *
 * @author juanserealpe
 */
public class UserRegisterServices implements IUserRegisterServices {

    private final IRepository<UserRegisterDTO> _userRepository;
    private final IEncrypt _encryptService;
    private final IValidatorRegisterServices _validatorService;
    private final IDataNormalizerServices _dataNormalizeService;

    /**
     * Constructor del servicio de registro de usuarios.
     *
     * @param _UserRepository Repositorio donde se almacenarán los usuarios.
     * @param _encryptService Servicio de encriptación de contraseñas.
     * @param _validatorService Servicio de validación de datos de usuario.
     * @param _dataNormalizeService Servicio de normalización de datos de usuario.
     */
    public UserRegisterServices(IRepository<UserRegisterDTO> _UserRepository,
                                IEncrypt _encryptService,
                                IValidatorRegisterServices _validatorService,
                                IDataNormalizerServices _dataNormalizeService) {
        this._userRepository = _UserRepository;
        this._encryptService = _encryptService;
        this._validatorService = _validatorService;
        this._dataNormalizeService = _dataNormalizeService;
    }

    /**
     * Registra un nuevo usuario.
     *
     * Este método primero valida y normaliza los datos del usuario,
     * encripta la contraseña y luego lo almacena en el repositorio.
     *
     * @param prmUser DTO con la información del usuario a registrar.
     * @throws Exception Si ocurre algún error durante la validación o almacenamiento.
     */
    @Override
    public void registerUser(UserRegisterDTO prmUser) throws Exception {
        _userRepository.add(validateUser(prmUser));
    }

    /**
     * Valida, normaliza y encripta la información de un usuario.
     *
     * - Valida nombres, apellidos, teléfono, email, roles y contraseña.
     * - Normaliza los nombres y apellidos.
     * - Encripta la contraseña.
     *
     * @param prmUser DTO con la información del usuario a validar.
     * @return DTO actualizado con datos normalizados y contraseña encriptada.
     * @throws Exception Si alguna validación falla.
     */
    @Override
    public UserRegisterDTO validateUser(UserRegisterDTO prmUser) throws Exception {
        User user = prmUser.getUser();
        Account account = prmUser.getAccount();
        String password = prmUser.getPassword();

        // Validaciones
        _validatorService.isValidNames(user.getName());
        _validatorService.isValidLastNames(user.getLastName());
        _validatorService.isValidRole(account.getRoles());
        _validatorService.isValidTelephone(user.getPhoneNumber());
        _validatorService.isValidEmail(account.getEmail());
        _validatorService.isValidPassword(password);

        // Normalización
        String normalizedName = _dataNormalizeService.normalizeString(user.getName());
        String normalizedLastName = _dataNormalizeService.normalizeString(user.getLastName());

        user.setName(normalizedName);
        user.setLastName(normalizedLastName);

        // Cifrado de contraseña
        String encryptedPassword = _encryptService.Encrypt(password);

        // Retornar un nuevo DTO actualizado
        return new UserRegisterDTO(encryptedPassword, user, account);
    }
}
