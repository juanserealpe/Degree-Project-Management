package App.Models;

public class FormatA {

    private int idFormato;
    private Student student;
    private Director director_n1;
    private Director director_n2;
    private String title;
    private String currentStatus;
    private int attempts;

    public FormatA(int idFormato, Student student, Director director_n1, Director director_n2, String title, String currentStatus, int attempts) {
        this.idFormato = idFormato;
        this.student = student;
        this.director_n1 = director_n1;
        this.director_n2 = director_n2;
        this.title = title;
        this.currentStatus = currentStatus;
        this.attempts = attempts;
    }

    public int getIdFormato() {
        return idFormato;
    }

    public void setIdFormato(int idFormato) {
        this.idFormato = idFormato;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Director getDirector_n1() {
        return director_n1;
    }

    public void setDirector_n1(Director director_n1) {
        this.director_n1 = director_n1;
    }

    public Director getDirector_n2() {
        return director_n2;
    }

    public void setDirector_n2(Director director_n2) {
        this.director_n2 = director_n2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

}
