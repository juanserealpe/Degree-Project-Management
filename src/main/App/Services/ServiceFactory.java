package Services;

import Dtos.UserRegisterDTO;
import Interfaces.*;
import Models.Account;
import Repositories.CredentialRepository;

import java.sql.Connection;

/**
 * Fábrica de servicios.
 *
 * Esta clase se encarga de crear instancias de servicios relacionados con
 * autenticación y gestión de usuarios, inyectando las dependencias necesarias
 * como repositorios, servicios de encriptación, validación y normalización de datos.
 *
 * Permite centralizar la creación de servicios y mantener la inyección de dependencias
 * controlada a partir de una conexión a la base de datos.
 */
public class ServiceFactory {

    /** Conexión a la base de datos que se comparte entre los servicios */
    private final Connection connection;

    /** Servicio de encriptación de contraseñas */
    private final IEncrypt encryptService;

    /** Servicio de validación de datos de registro */
    private final IValidatorRegisterServices validatorService;

    /** Servicio de normalización de datos */
    private final IDataNormalizerServices dataService;

    /**
     * Constructor de la fábrica de servicios.
     *
     * @param connection Conexión a la base de datos que se usará para los repositorios.
     */
    public ServiceFactory(Connection connection) {
        this.connection = connection;
        this.encryptService = new EncryptService();
        this.validatorService = new ValidatorRegisterServices();
        this.dataService = new DataNormalizerServices();
    }

    /**
     * Obtiene una instancia de IAuthService.
     *
     * Crea los repositorios necesarios (credenciales de usuario y cuentas)
     * y construye el servicio de autenticación con las dependencias requeridas.
     *
     * @return Instancia de IAuthService lista para usar.
     */
    public IAuthService getAuthService() {
        IRepository<UserRegisterDTO> userRepository = new CredentialRepository(connection);
        return new AuthService(encryptService, userRepository);
    }

    /**
     * Obtiene una instancia de UserRegisterServices.
     *
     * Este servicio permite registrar usuarios, validando y normalizando sus datos,
     * y aplicando encriptación a las contraseñas.
     *
     * @return Instancia de UserRegisterServices lista para usar.
     */
    public UserRegisterServices getUserService() {
        IRepository<UserRegisterDTO> userRepository = new CredentialRepository(connection);
        return new UserRegisterServices(userRepository, encryptService, validatorService, dataService);
    }
}
