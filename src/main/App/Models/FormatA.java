package Models;

import Enums.EnumState;
import Enums.EnumTypeProcess;
import java.util.Date;

public class FormatA extends Process {
    private String Tittle;
    private String URL;
    private String Observation;
    private int Attempts;

    public FormatA(Date date, EnumState state, EnumTypeProcess typeProcess) {
        super(date, state, typeProcess);
    }

    public FormatA(Date date, EnumState state, EnumTypeProcess typeProcess, String tittle, String URL, String observation, int attempts) {
        super(date, state, typeProcess);
        Tittle = tittle;
        this.URL = URL;
        Observation = observation;
        Attempts = attempts;
    }

    public String getTittle() { return Tittle; }
    public void setTittle(String tittle) { this.Tittle = tittle; }

    public String getURL() { return URL; }
    public void setURL(String URL) { this.URL = URL; }

    public String getObservation() { return Observation; }
    public void setObservation(String observation) { this.Observation = observation; }

    public int getAttempts() { return Attempts; }
    public void setAttempts(int attempts) { this.Attempts = attempts; }
}
