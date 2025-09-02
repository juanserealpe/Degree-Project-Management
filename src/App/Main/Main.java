package App.Main;

import App.Dtos.DirectorDTO;
import App.Dtos.StudentDTO;
import App.Models.Account;
import App.Models.Director;
import App.Models.Role;
import App.Models.Student;
import App.Repositories.DirectorRepository;
import App.Repositories.StudentRepository;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // =============================
        //      TEST STUDENT REPO
        // =============================
        //Instancias necesarias:
        StudentRepository studentRepo = new StudentRepository();
        DirectorRepository directorRepo = new DirectorRepository();
        // =============================
        // TEST: Obtener todos los Estudiantes
        // =============================
        /*
        List<StudentDTO> listStudents = studentRepo.toGetAll();
        for (StudentDTO dto : listStudents) {
            Student s = dto.getStudent();
            Account a = dto.getAccount();

            System.out.println(
                    "Email: " + s.getEmail()
                    + " | Nombre: " + s.getNames() + " " + s.getLastNames()
                    + " | Programa: " + s.getProgramId()
                    + " | Cuenta ID: " + a.getIdAccount()
                    + " | Password: " + a.getPassword()
            );
        }
         */
        // =============================
        // TEST: Buscar Estudiante por Email
        // =============================
        /*
        System.out.println("\n>>> TEST: Buscar estudiante por email <<<");
        StudentDTO sFound = studentRepo.toGetByString("student1@university.edu");

        if (sFound != null) {
            Student s = sFound.getStudent();
            Account a = sFound.getAccount();

            System.out.println(
                    "Estudiante encontrado -> "
                    + s.getEmail() + " | " + s.getNames() + " " + s.getLastNames()
                    + " | Programa: " + s.getProgramId()
                    + " | Cuenta ID: " + a.getIdAccount()
                    + " | Password: " + a.getPassword()
            );
        } else {
            System.out.println("Estudiante NO encontrado.");
        }
        
        
         */
        // =============================
        // TEST: Agregar estudiante
        // =============================
        /*
        System.out.println("\n>>> TEST: Agregar estudiante <<<");
        Student newStudent = new Student(
                "newstudent@university.edu",
                "Carlos",
                "Ramírez",
                0, // id_account lo genera la BD
                1 // id_program (ejemplo: Ingeniería de Sistemas)
        );

        Account newAccount = new Account("pass123");
        StudentDTO newStudentDTO = new StudentDTO(newStudent, newAccount);
        studentRepo.toAdd(newStudentDTO);
        newStudentDTO = null;

        newStudentDTO = studentRepo.toGetByString("newstudent@university.edu");
        if (newStudentDTO == null) {
            System.out.println("El estudiante no se encentró, TEST FALLIDO");
        } else {
            System.out.println("Se encontró registro, TEST PASADO");
            newStudentDTO.toString();
        }
         */
        // ==============================
        // TEST: Eliminar estudiante
        // ==============================
        /*
        StudentDTO newStudentDTO = new StudentDTO();
        System.out.println("\n>>> TEST: Eliminar estudiante <<<");

        studentRepo.toDeleteByString("newstudent@university.edu");

        newStudentDTO = null;
        newStudentDTO = studentRepo.toGetByString("newstudent@university.edu");
        if (newStudentDTO == null) {
            System.out.println("El estudiante no se encentró, TEST PASADO");
        } else {
            System.out.println("Se encontró registro, TEST FALLIDO");
        }
         */

        // =============================
        //      TEST DIRECTOR REPO
        // =============================
        // =============================
        // TEST: Obtener todos los Directores
        // =============================
        /*
        System.out.println("\n>>> TEST: Obtener todos los directores <<<");
        List<DirectorDTO> listDirectors = directorRepo.toGetAll();
        for (DirectorDTO d : listDirectors) {
            System.out.println(
                    "Email: " + d.getDirector().getEmail()
                    + " | Nombre: " + d.getDirector().getNames() + " " + d.getDirector().getLastNames()
                    + " | Programa: " + d.getDirector().getProgramId()
                    + "Rol: " + d.getDirector().getRoles().toString()
            );
        }
         */
        // =============================
        // TEST: Buscar Director por Email
        // =============================
        /*
        System.out.println("\n>>> TEST: Buscar director por email <<<");
        DirectorDTO dFound = directorRepo.toGetByString("director@university.edu");

        if (dFound != null) {
            System.out.println(
                    "Director encontrado -> "
                    + dFound.getDirector().getEmail() + " | " + dFound.getDirector().getNames() + " " + dFound.getDirector().getLastNames()
                    + " | Programa: " + dFound.getDirector().getProgramId()
            );
        } else {
            System.out.println("Director NO encontrado.");
        }
         */
        // =============================
        // TEST: Agregar Director
        // =============================
        /*
        System.out.println("\n>>> TEST: Agregar director <<<");
        Account acc = new Account();
        acc.setPassword("dir123");

        Director dir = new Director();
        dir.setEmail("newDirector@university.edu");
        dir.setNames("new");
        dir.setLastNames("new");
        dir.setProgramId(2);

        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setIdRole(3);
        roles.add(role);
        
        Role role2 = new Role();
        role2.setIdRole(2);
        roles.add(role2);
        
        dir.setRoles(roles);

        DirectorDTO dto = new DirectorDTO(dir, acc);
        directorRepo.toAdd(dto);
         */
        // =============================
        // TEST: Eliminar Director
        // =============================
        /*
        System.out.println("\n>>> TEST: Eliminar director <<<");
        directorRepo.toDeleteByString("newdirector@university.edu");

        DirectorDTO dCheckDel = directorRepo.toGetByString("newdirector@university.edu");
        if (dCheckDel == null) {
            System.out.println("El director no se encontró, TEST PASADO");
        } else {
            System.out.println("Se encontró registro: "
                    + dCheckDel.getDirector().getEmail()
                    + ", TEST FALLIDO");
        }
         */
    }
}
