package Services;

import Dtos.DirectorDTO;
import Dtos.StudentDTO;
import Dtos.UserDTO;
import Interfaces.*;
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
       return null;
    }
    // ========================
    // UserServices
    // ========================
    public static UserServices getUserService() {
        IRepository<UserDTO> userRepo = new UserRepository();
        return new UserServices(userRepo, encryptService, validatorService, dataService);
    }
    // ========================
    // StudentServices
    // ========================
    public static StudentServices getStudentService() {
        IRepository<StudentDTO> studentRepo = new StudentRepository();
        return new StudentServices(studentRepo, encryptService, validatorService, dataService);
    }

    // ========================
    // DirectorServices
    // ========================
    public static DirectorServices getDirectorService() {
        IRepository<DirectorDTO> directorRepo = new DirectorRepository();
        return new DirectorServices(directorRepo, encryptService, validatorService, dataService);
    }
    // ========================
    // DirectorServices
    // ========================
    public static  ValidatorRegisterServices getValidatorService(){
        IValidatorRegisterServices validatorService = new ValidatorRegisterServices();
        return new ValidatorRegisterServices();
    }
}