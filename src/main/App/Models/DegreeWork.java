package Models;

import Enums.EnumModality;
import Enums.EnumState;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DegreeWork {
    private int idDegreeWork;
    private List<Integer> studentIds;
    private int directorId;
    private int codirectorId;
    private List<Process> processes;
    private EnumModality modality;
    private EnumState state;
    private Date date;
    public DegreeWork() {
        processes = new ArrayList<Process>();
    }
    public DegreeWork(List<Integer> studentIds, int directorId, int codirectorId,List<Process> processes, EnumModality modality,EnumState state, Date date) {
        this.studentIds = studentIds;
        this.directorId = directorId;
        this.codirectorId = codirectorId;
        this.processes = processes;
        this.modality = modality;
        this.state = state;
        this.date = date;
    }
    public DegreeWork(int idDegreeWork,List<Integer> studentIds, int directorId, int codirectorId,List<Process> processes, EnumModality modality,Date date) {
        this.studentIds = studentIds;
        this.idDegreeWork = idDegreeWork;
        this.directorId = directorId;
        this.codirectorId = codirectorId;
        this.processes = processes;
        this.modality = modality;
        this.date = date;
    }
    public List<Integer> getStudentIds() { return studentIds; }
    public void setStudentIds(List<Integer> studentIds) { this.studentIds = studentIds; }

    public int getDirectorId() { return directorId; }
    public void setDirectorId(int directorId) { this.directorId = directorId; }

    public int getCodirectorId() { return codirectorId; }
    public void setCodirectorId(int codirectorId) { this.codirectorId = codirectorId; }

    public List<Process> getProcesses() { return processes; }
    public void setProcesses(List<Process> processes) { this.processes = processes; }

    public EnumModality getModality() { return modality; }
    public void setModality(EnumModality modality) { this.modality = modality; }

    public EnumState getState() { return state; }
    public void setState(EnumState state) { this.state = state; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public int getCountStudents(){
        if(studentIds == null) return 0;
        else return studentIds.size();
    }
    public int getCountProcess(){
        if(processes == null) return 0;
        else return processes.size();
    }
    public int getIdDegreeWork() {
        return idDegreeWork;
    }

    public void setIdDegreeWork(int idDegreeWork) {
        this.idDegreeWork = idDegreeWork;
    }
}
