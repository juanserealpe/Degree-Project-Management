package App.Main;

import App.Models.Account;
import App.Models.Professor;
import App.Models.Role;
import App.Models.Student;
import App.Repositories.ProfessorRepository;
import App.Repositories.StudentRepository;
import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {

        // =============================
        //      TEST STUDENT REPO
        // =============================
        StudentRepository studentRepo = new StudentRepository();

        System.out.println("\n>>> TEST: Agregar estudiante <<<");
        Account acc1 = new Account(0, "pass123");
        Student newStudent = new Student("student10@university.edu", "Carlos", "Ramirez", 0, 1);
        studentRepo.addStudent(newStudent, acc1);

        System.out.println("\n>>> TEST: Obtener todos los estudiantes <<<");
        List<Student> listStudents = studentRepo.getAllStudents();
        for (Student s : listStudents) {
            System.out.println(s.toString());
        }

        System.out.println("\n>>> TEST: Buscar estudiante por email <<<");
        Student sFound = studentRepo.getStudentByEmail("student10@university.edu");
        if (sFound != null) {
            System.out.println("Estudiante encontrado: " + sFound.toString());
        } else {
            System.out.println("Estudiante NO encontrado.");
        }

        System.out.println("\n>>> TEST: Eliminar estudiante <<<");
        studentRepo.deleteStudent("student10@university.edu");

        System.out.println("Verificación post-eliminación:");
        Student deletedStudent = studentRepo.getStudentByEmail("student10@university.edu");
        System.out.println(deletedStudent == null ? "Eliminado correctamente." : "Aún existe!");

        // =============================
        //      TEST PROFESSOR REPO
        // =============================
        ProfessorRepository professorRepo = new ProfessorRepository();

        System.out.println("\n>>> TEST: Agregar profesor con roles <<<");
        Role coordinator = new Role(2, "COORDINATOR");
        Role director = new Role(3, "DIRECTOR");

        List<Role> profRoles = new ArrayList<>();
        profRoles.add(coordinator);
        profRoles.add(director);

        Account acc2 = new Account(0, "profe123");
        Professor newProfessor = new Professor("profesor10@unicauca.edu.co", "Juan", "Lopez", 0, 1, profRoles);
        professorRepo.addProfessor(newProfessor, acc2);

        System.out.println("\n>>> TEST: Obtener todos los profesores <<<");
        List<Professor> listProfessors = professorRepo.getAllProfessors();
        for (Professor p : listProfessors) {
            System.out.println(p.toString());
        }

        System.out.println("\n>>> TEST: Buscar profesor por email <<<");
        Professor pFound = professorRepo.getProfessorByEmail("profesor10@unicauca.edu.co");
        if (pFound != null) {
            System.out.println("Profesor encontrado: " + pFound.toString());
        } else {
            System.out.println("Profesor NO encontrado.");
        }

        System.out.println("\n>>> TEST: Eliminar profesor <<<");
        professorRepo.deleteProfessor("profesor10@unicauca.edu.co");

        System.out.println("Verificación post-eliminación:");
        Professor deletedProfessor = professorRepo.getProfessorByEmail("profesor10@unicauca.edu.co");
        System.out.println(deletedProfessor == null ? "Eliminado correctamente." : "Aún existe!");

        System.out.println("\n>>> TEST FINALIZADOS <<<");
    }
}
