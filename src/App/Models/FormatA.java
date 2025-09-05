package App.Models;

public class FormatA {

    private int idFormato;
    private Student student;
    private Director director;
    private Director coDirector;
    private String title;
    private String currentStatus;
    private int attempts;

    public FormatA(int idFormato, Student student, Director director, Director coDirector, String title, String currentStatus, int attempts) {
        this.idFormato = idFormato;
        this.student = student;
        this.director = director;
        this.coDirector = coDirector;
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

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director_n1) {
        this.director = director_n1;
    }

    public Director getCoDirector() {
        return coDirector;
    }

    public void setCoDirector(Director director_n2) {
        this.coDirector = director_n2;
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
