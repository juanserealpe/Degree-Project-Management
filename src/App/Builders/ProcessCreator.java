package App.Builders;

import App.Models.EnumState;
import App.Models.Process;

import java.time.Instant;
import java.util.Date;

public abstract class ProcessCreator {

    public abstract Process createProcess();

    public void initializeProcess(){
        Process process = createProcess();
        process.setDate(Date.from(Instant.now()));
        process.setEnumState(EnumState.ESPERA);
    }
}