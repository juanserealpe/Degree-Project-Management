package App.Services;

import App.DataBase.DataBase;
import App.Entities.PreliminaryDraft;
import App.Repositories.ProfessorRepository;
import App.entities.Professor;
import App.entities.Student;
import App.repositories.StudentRepository;
import java.util.List;

public class ProfessorService {

    private final DataBase db;

    public ProfessorService(DataBase db) {
        this.db = db;
    }

    public List<PreliminaryDraft> GetAllRepositories(String email) {
        ProfessorRepository s = new ProfessorRepository(db);
        return s.getAllPreliminaryDrafts(email);

    }

    public void RegisterProfessor(String name, String lastName, String PhoneNumber, String email, String password) throws Exception {
        ValidatorService instance = ValidatorService.GetInstance();
        System.out.println("Email : " + email);
        instance.isValidEmail(email);
        instance.isValidPassword(password);
        //instance.isValidTelephone(password);
        password = Encrypt.GetInstance().Encrypt(password);
        Professor newProfessor = new Professor(name, lastName, email, password, PhoneNumber);
        ProfessorRepository professorRepository = new ProfessorRepository(db);
        professorRepository.Add(newProfessor);
    }
}
