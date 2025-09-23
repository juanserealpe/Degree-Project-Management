package App.Models;

import java.util.Date;

public abstract class Process {
    private Date date;
    private EnumState enumState;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public EnumState getEnumState() {
        return enumState;
    }

    public void setEnumState(EnumState enumState) {
        this.enumState = enumState;
    }
}

