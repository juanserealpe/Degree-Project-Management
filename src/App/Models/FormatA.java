package App.Models;

public class FormatA {

    private int idFormato;
    private int idStudent;
    private String emailProfessor1;
    private String emailProfessor2;
    private String title;
    private String currentStatus;
    private int attempts;

    public FormatA(int idFormato, int idStudent, String emailProfessor1, String emailProfessor2, String title, String currentStatus, int attempts) {
        this.idFormato = idFormato;
        this.idStudent = idStudent;
        this.emailProfessor1 = emailProfessor1;
        this.emailProfessor2 = emailProfessor2;
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

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public String getEmailProfessor1() {
        return emailProfessor1;
    }

    public void setEmailProfessor1(String emailProfessor1) {
        this.emailProfessor1 = emailProfessor1;
    }

    public String getEmailProfessor2() {
        return emailProfessor2;
    }

    public void setEmailProfessor2(String emailProfessor2) {
        this.emailProfessor2 = emailProfessor2;
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
