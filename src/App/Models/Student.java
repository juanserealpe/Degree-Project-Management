package App.Models;

public class Student extends User {

    public Student(String email, String names, String lastNames, int accountId, int programId, String numberPhone) {
        super(email, names, lastNames, accountId, programId, numberPhone);
    }

    public Student(String email, String names, String lastNames, int accountId, int programId) {
        super(email, names, lastNames, accountId, programId);
    }

    @Override
    public String toString() {
        return "Estudiante{" + super.toString() + "}";
    }
}
