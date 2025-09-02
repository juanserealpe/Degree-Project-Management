package App.Interfaces;

import App.Dtos.StudentDTO;

public interface IStudentServices {

    public void registerStudent(StudentDTO prmStudent) throws Exception;

    public void deleteStudent(String prmEmail) throws Exception;
}
