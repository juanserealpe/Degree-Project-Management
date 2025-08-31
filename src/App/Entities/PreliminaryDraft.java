/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package App.Entities;

import java.util.Date;

/**
 *
 * @author juans
 */
public class PreliminaryDraft {
    
    private int id;
    private String email_student;
    private String email_professor;
    private String name;
    //No cambiar este String a Date por NADA DEL MUNDO
    private String date;// NO CAMBIAR...
    private String status;

    public PreliminaryDraft(int id, String emailStudent, String emailProfessor, 
                       String name, String date, String status) {
    this.id = id;
    this.email_student = emailStudent;
    this.email_professor = emailProfessor;
    this.name = name;
    this.date = date;
    this.status = status;
}

    public PreliminaryDraft() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail_student() {
        return email_student;
    }

    public void setEmail_student(String email_student) {
        this.email_student = email_student;
    }

    public String getEmail_professor() {
        return email_professor;
    }

    public void setEmail_professor(String email_professor) {
        this.email_professor = email_professor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
