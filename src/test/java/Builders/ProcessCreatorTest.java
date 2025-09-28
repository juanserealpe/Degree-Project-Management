package Builders;

import Enums.EnumState;
import Enums.EnumTypeProcess;
import Models.FormatA;
import Models.Process;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProcessCreatorTest {

    private Process formatA;

    @BeforeEach
    void setUp() {
        ProcessCreator creator = new CreateFormatA();
        formatA = creator.createProcess();
    }

    @Test
    public void testProcessCreatedNotNull() {
        assertNotNull(formatA);
    }

    @Test
    public void testProcessIsFormatA() {
        assertInstanceOf(FormatA.class, formatA);
    }

    @Test
    public void testProcessIsProcess() {
        assertInstanceOf(Process.class, formatA);
    }

    @Test
    public void testProcessDefaultValuesState() {
        assertEquals(EnumState.ESPERA, formatA.getState());
    }

    @Test
    public void testProcessDefaultValuesTypeProcess() {
        assertEquals(EnumTypeProcess.FORMAT_A, formatA.getProcess());
    }

    @Test
    public void testProcessWorksAsFormatA() {
        ((FormatA) formatA).setTittle("test tittle");
        assertEquals("test tittle", ((FormatA) formatA).getTittle());
    }
}
