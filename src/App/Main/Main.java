package App.Main;

import App.Dtos.DirectorDTO;
import App.Dtos.StudentDTO;
import App.Interfaces.IAccountRepository;
import App.Interfaces.IDataNormalizerServices;
import App.Interfaces.IEncrypt;
import App.Interfaces.IRepository;
import App.Interfaces.IRoleRepository;
import App.Interfaces.IValidatorRegisterServices;
import App.Models.User;
import App.Repositories.AccountRepository;
import App.Repositories.CoordinatorRepository;
import App.Repositories.DirectorRepository;
import App.Repositories.RoleRepository;
import App.Repositories.StudentRepository;
import App.Repositories.UserRepository;
import App.Services.AuthService;
import App.Services.DataNormalizerServices;
import App.Services.DirectorServices;
import App.Services.EncryptService;
import App.Services.StudentServices;
import App.Services.ValidatorRegisterServices;

public class Main {

    public static void main(String[] args) throws Exception {

        CoordinatorRepository c1 = new CoordinatorRepository();
        c1.GetFormatA();
        IAccountRepository _authRepo = new AccountRepository();
        IRoleRepository _roleRepo = new RoleRepository();
        IEncrypt _encryptService1 = new EncryptService();
        IRepository<User> _userRepo = new UserRepository();
        AuthService authService = new AuthService(_authRepo, _roleRepo, _encryptService1, _userRepo);

        IRepository<StudentDTO> _studentRepo = new StudentRepository();
        IEncrypt _encryptService2 = new EncryptService();
        IValidatorRegisterServices _validatorService1 = new ValidatorRegisterServices();
        IDataNormalizerServices dataService1 = new DataNormalizerServices();
        StudentServices studentService = new StudentServices(_studentRepo, _encryptService2, _validatorService1, dataService1);

        IRepository<DirectorDTO> _directorRepo = new DirectorRepository();
        IEncrypt _encryptService3 = new EncryptService();
        IValidatorRegisterServices _validatorService2 = new ValidatorRegisterServices();
        DirectorServices directorService = new DirectorServices(_directorRepo, _encryptService3, _validatorService2, dataService1);
        /*
        Student newStudent = new Student("juanvaronaov1@unicauca.edu.co", "JuAn", "VaROnA", 0, 1, "3015056565");
        Account newAccount = new Account("juanse1A?");
        StudentDTO studentD = new StudentDTO(newStudent, newAccount);
        studentService.registerStudent(studentD);

        
        StudentDTO resultStudent = studentService.getStudentByEmail("juanvaronaov1@unicauca.edu.co");
        System.out.println(resultStudent.toString());
        
        List<StudentDTO> listStudents = studentService.getAllStudents();
        System.out.println(listStudents.toString());
        
        studentService.deleteStudent("juanvaronaov1@unicauca.edu.co");
         */

 /*
        System.out.println("\n>>> TEST: Agregar director <<<");
        Account acc = new Account();
        acc.setPassword("dir123");

        Director dir = new Director();
        dir.setEmail("newDirector@university.edu");
        dir.setNames("new");
        dir.setLastNames("new");
        dir.setProgramId(2);
        dir.setNumberPhone("30150545445");

        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setIdRole(3);
        roles.add(role);

        Role role2 = new Role();
        role2.setIdRole(2);
        roles.add(role2);

        dir.setRoles(roles);

        DirectorDTO dto = new DirectorDTO(dir, acc);
        directorService.registerDirector(dto);

        //authService.isLoginValid("newDirector@university.edu", "dir123");
         */
    }
}
