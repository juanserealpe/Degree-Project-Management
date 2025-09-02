package App.Services;

import App.Dtos.StudentDTO;
import App.Interfaces.IEncrypt;
import App.Interfaces.IRepository;
import App.Interfaces.IStudentServices;

public class StudentServices implements IStudentServices {

    private final IRepository<StudentDTO> _studentRepository;
    private final IEncrypt _encryptService;

    public StudentServices(IRepository<StudentDTO> _studentRepository, IEncrypt _encryptService) {
        this._studentRepository = _studentRepository;
        this._encryptService = _encryptService;
    }

    @Override
    public void registerStudent(StudentDTO prmStudent) throws Exception {
        String encryptedPassword = _encryptService.Encrypt(prmStudent.getAccount().getPassword());
        prmStudent.getAccount().setPassword(encryptedPassword);
        _studentRepository.toAdd(prmStudent);
    }

    @Override
    public void deleteStudent(String prmEmail) throws Exception {
        _studentRepository.toDeleteByString(prmEmail);
    }

}
