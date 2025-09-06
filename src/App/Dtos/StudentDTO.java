package Dtos;

import Models.Account;
import Models.Student;

public class StudentDTO {

    private Student student;
    private Account account;

    public StudentDTO(Student student, Account account) {
        this.student = student;
        this.account = account;
    }

    public StudentDTO() {

    }

    public Student getStudent() {
        return student;
    }

    public Account getAccount() {
        return account;
    }

    @Override
    public String toString() {
        return "StudentDTO{" + "student=" + student + ", account=" + account + '}';
    }

}
