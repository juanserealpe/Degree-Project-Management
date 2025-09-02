package App.Interfaces;

import App.Dtos.StudentDTO;
import java.util.List;

public interface IStudentServices {

    public void registerStudent(StudentDTO prmStudent) throws Exception;

    public void deleteStudent(String prmEmail) throws Exception;

    public List<StudentDTO> getAllStudents() throws Exception;

    public StudentDTO getStudentByEmail(String prmEmail) throws Exception;

    public StudentDTO validateStudent(StudentDTO prmStudent) throws Exception;
}
