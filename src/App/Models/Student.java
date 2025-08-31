package App.Models;

public class Student extends User {

    public Student(String email, String names, String lastNames, int accountId, int program) {
        super(email, names, lastNames, accountId, program);
    }

    @Override
    public String toString() {
        return "Estudiante{" + super.toString() + "}";
    }
}
