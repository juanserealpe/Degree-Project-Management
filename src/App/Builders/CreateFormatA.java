package App.Builders;

import App.Models.FormatA;
import App.Models.Process;

public class CreateFormatA extends ProcessCreator {

    @Override
    public Process createProcess() {
        return new FormatA();
    }

}