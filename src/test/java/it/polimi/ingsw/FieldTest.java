package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

class FieldTest {

    @Test
    void CLITest1() throws InterruptedException {
        Field field = new Field();
        field.viewSetup(1,3, Color.ANSI_RED.escape());
        sleep(1000);
        field.viewMove("A", 1,3,2,3, Color.ANSI_RED.escape());
        sleep(1000);
        field.viewSetup(2,2, Color.ANSI_WHITE.escape());
        sleep(1000);
        field.viewSetup(2,1, Color.ANSI_CYAN.escape());
        sleep(1000);
        field.viewSetup(3,2, Color.ANSI_PURPLE.escape());
        sleep(1000);
        field.viewBuild(3,2,1,0);
        sleep(1000);
        field.viewBuild(0,0,2,1);
        sleep(1000);
        field.viewRemoveWorker(2,1);
        field.plot();
    }
}