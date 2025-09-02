package App.Services;

import App.Dtos.StudentDTO;
import App.Interfaces.IEncrypt;
import App.Interfaces.IRepository;
import App.Interfaces.IStudentServices;
import App.Interfaces.IValidatorRegisterServices;
import java.util.List;

public class StudentServices implements IStudentServices {

    private final IRepository<StudentDTO> _studentRepository;
    private final IEncrypt _encryptService;
    private final IValidatorRegisterServices _validatorService;

    public StudentServices(IRepository<StudentDTO> _studentRepository, IEncrypt _encryptService, IValidatorRegisterServices _validatorService) {
        this._studentRepository = _studentRepository;
        this._encryptService = _encryptService;
        this._validatorService = _validatorService;
    }

    @Override
    public void registerStudent(StudentDTO prmStudent) throws Exception {
        _validatorService.isValidEmail(prmStudent.getStudent().getEmail());
        _validatorService.isValidTelephone(prmStudent.getStudent().getNumberPhone());
        _validatorService.isValidPassword(prmStudent.getAccount().getPassword());
        String encryptedPassword = _encryptService.Encrypt(prmStudent.getAccount().getPassword());
        prmStudent.getAccount().setPassword(encryptedPassword);
        _studentRepository.toAdd(prmStudent);
    }

    @Override
    public void deleteStudent(String prmEmail) throws Exception {
        _studentRepository.toDeleteByString(prmEmail);
    }

    @Override
    public List<StudentDTO> getAllStudents() throws Exception {
        return _studentRepository.toGetAll();
    }

    @Override
    public StudentDTO getStudentByEmail(String prmEmail) throws Exception {
        return _studentRepository.toGetByString(prmEmail);
    }

}
