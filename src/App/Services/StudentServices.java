package App.Services;

import App.Dtos.StudentDTO;
import App.Exceptions.StudentServiceException;
import App.Interfaces.IDataNormalizerServices;
import App.Interfaces.IEncrypt;
import App.Interfaces.IRepository;
import App.Interfaces.IStudentServices;
import App.Interfaces.IValidatorRegisterServices;
import java.util.List;

public class StudentServices implements IStudentServices {

    private final IRepository<StudentDTO> _studentRepository;
    private final IEncrypt _encryptService;
    private final IValidatorRegisterServices _validatorService;
    private final IDataNormalizerServices _dataNormalizeService;

    public StudentServices(IRepository<StudentDTO> _studentRepository, IEncrypt _encryptService, IValidatorRegisterServices _validatorService, IDataNormalizerServices _dataNormalizeService) {
        this._studentRepository = _studentRepository;
        this._encryptService = _encryptService;
        this._validatorService = _validatorService;
        this._dataNormalizeService = _dataNormalizeService;
    }

    @Override
    public void registerStudent(StudentDTO prmStudent) throws Exception {
        StudentDTO validatedStudentDTO = validateStudent(prmStudent);
        String encryptedPassword = _encryptService.Encrypt(validatedStudentDTO.getAccount().getPassword());
        validatedStudentDTO.getAccount().setPassword(encryptedPassword);
        _studentRepository.toAdd(validatedStudentDTO);
    }

    @Override
    public void deleteStudent(String prmEmail) throws Exception {
        _validatorService.isValidEmail(prmEmail);
        StudentDTO resultStudentToRemove = _studentRepository.toGetByString(prmEmail);
        if (resultStudentToRemove == null) {
            throw StudentServiceException.noFoundStudent();
        }
        _studentRepository.toDeleteByString(prmEmail);
    }

    @Override
    public List<StudentDTO> getAllStudents() throws Exception {
        List<StudentDTO> resultListStudents = _studentRepository.toGetAll();
        if (resultListStudents.size() == 0) {
            throw StudentServiceException.noFoundStudents();
        }
        return resultListStudents;
    }

    @Override
    public StudentDTO getStudentByEmail(String prmEmail) throws Exception {

        _validatorService.isValidEmail(prmEmail);
        StudentDTO resultStudentToRemove = _studentRepository.toGetByString(prmEmail);
        if (resultStudentToRemove == null) {
            throw StudentServiceException.noFoundStudent();
        }
        return _studentRepository.toGetByString(prmEmail);
    }

    @Override
    public StudentDTO validateStudent(StudentDTO prmStudent) throws Exception {
        _validatorService.isValidEmail(prmStudent.getStudent().getEmail());
        _validatorService.isValidTelephone(prmStudent.getStudent().getNumberPhone());
        _validatorService.isValidPassword(prmStudent.getAccount().getPassword());
        String newNames = _dataNormalizeService.normalizeString(prmStudent.getStudent().getNames());
        String newLastNames = _dataNormalizeService.normalizeString(prmStudent.getStudent().getLastNames());

        prmStudent.getStudent().setNames(newNames);
        prmStudent.getStudent().setLastNames(newLastNames);

        return prmStudent;
    }

}
