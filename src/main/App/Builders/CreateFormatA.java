package Builders;

import Enums.EnumState;
import Enums.EnumTypeProcess;
import Models.FormatA;
import Models.Process;

import java.time.Instant;
import java.util.Date;

public class CreateFormatA extends ProcessCreator {

    @Override
    public Process createProcess() {
        return new FormatA(Date.from(Instant.now()), EnumState.ESPERA, EnumTypeProcess.FORMAT_A);
    }

}
