package Services;

import Dtos.DirectorDTO;
import Dtos.StudentDTO;
import Dtos.UserDTO;
import Interfaces.*;
import Models.Account;
import Models.User;
import Repositories.*;

public class ServiceFactory {

    private static final IEncrypt encryptService = new EncryptService();
    private static final IValidatorRegisterServices validatorService = new ValidatorRegisterServices();
    private static final IDataNormalizerServices dataService = new DataNormalizerServices();


    // ========================
    // AuthService
    // ========================
    public static IAuthService getAuthService() {
        IRepository<UserDTO> userRepository = new UserRepository();
        IAccountRepository accountRepository = new AccountRepository();
        IRoleRepository roleRepository = new RoleRepository();
        return new AuthService(accountRepository,roleRepository,encryptService, userRepository);
    }
    // ========================
    // UserServices
    // ========================
    public static UserServices getUserService() {
        IRepository<UserDTO> userRepository = new UserRepository();
        return new UserServices(userRepository, encryptService, validatorService, dataService);
    }
    // ========================
    // StudentServices
    // ========================
    public static StudentServices getStudentService() {
        IRepository<StudentDTO> studentRepository = new StudentRepository();
        return new StudentServices(studentRepository, encryptService, validatorService, dataService);
    }

    // ========================
    // DirectorServices
    // ========================
    public static DirectorServices getDirectorService() {
        IRepository<DirectorDTO> directorRepository = new DirectorRepository();
        return new DirectorServices(directorRepository, encryptService, validatorService, dataService);
    }
    // ========================
    // DirectorServices
    // ========================
    public static  ValidatorRegisterServices getValidatorService(){
        IValidatorRegisterServices validatorService = new ValidatorRegisterServices();
        return new ValidatorRegisterServices();
    }
}