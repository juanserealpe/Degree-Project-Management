package App.Services;

import App.DataBase.DataBase;
import App.Repositories.ProfessorRepository;
import App.Session.Session;
import App.entities.Professor;
import App.entities.Student;
import App.repositories.StudentRepository;

public class UserService {

    private DataBase db;

    public UserService(DataBase db) {
        this.db = db;
    }

    public UserService() {

    }

    public void RegisterUser(String name, String lastName, String PhoneNumber, String program, String email, String password) throws Exception {
        ValidatorService instance = ValidatorService.GetInstance();
        instance.isValidEmail(email);
        instance.isValidTelephone(PhoneNumber);
        instance.isValidPassword(password);
        password = Encrypt.GetInstance().Encrypt(password);
        Student student = new Student(program, name, lastName, email, password, PhoneNumber);
        StudentRepository studentrepo = new StudentRepository(db);
        studentrepo.Add(student);
    }

    public void LoginUser(String email, String password) throws Exception {
        StudentRepository studentRepo = new StudentRepository(db);
        ProfessorRepository professorRepo = new ProfessorRepository(db);

        // Intentar como estudiante
        Student resultStudent = studentRepo.Retrieve(email);
        if (resultStudent != null) {
            System.out.println("Estudiante encontrado: " + resultStudent.getEmail());
            if (Encrypt.GetInstance().Check(password, resultStudent.getPassword())) {
                System.out.println("Login como ROL: Estudiante");
                Session.startSession(resultStudent.getEmail(), "Student");
                return;
            }
        }

        // Intentar como profesor
        Professor resultProfessor = professorRepo.Retrieve(email);
        if (resultProfessor != null) {
            System.out.println("Profesor encontrado: " + resultProfessor.getEmail());
            if (Encrypt.GetInstance().Check(password, resultProfessor.getPassword())) {
                Session.startSession(resultProfessor.getEmail(), "Professor");
                System.out.println("Login como ROL: Profesor");
                return;
            }
        }

        // Si no se encontró ni validó ninguno
        throw new Exception("Email o contraseña incorrectos");
    }

}
