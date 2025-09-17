package Models;

import Enums.EnumState;
import Enums.EnumTypeProcess;
import java.util.Date;

public abstract class Process {
    private Date Date;
    private EnumState State;
    private EnumTypeProcess Process;

    public Process(Date date, EnumState state, EnumTypeProcess typeProcess) {
        this.Date = date;
        this.State = state;
        this.Process = typeProcess;
    }

    public Date getDate() { return Date; }
    public void setDate(Date date) { this.Date = date; }

    public EnumState getState() { return State; }
    public void setState(EnumState state) { this.State = state; }

    public EnumTypeProcess getProcess() { return Process; }
    public void setProcess(EnumTypeProcess process) { this.Process = process; }
}
