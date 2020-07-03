package it.polimi.ingsw;

import static it.polimi.ingsw.CommunicationSentences.*;
import static java.lang.Thread.sleep;

public class QuitCommand extends ReplyCommand {

    private static final int timeToSleep=5000;
    /**
     * Represents a command who invoke a quit.
     */


    /**
     * Closes the window.
     * @param app is the interface.
     */

    public void execute(App app){
        app.setOnExitStage();
        try {
            sleep(timeToSleep);
        } catch (InterruptedException ignored) {
        }
        app.quitApplication();
    }

    /**
     * Adverts client and closes the interface.
     * @param gui is the interface.
     */

    @Override
    public void execute(GraphicInterface gui) {
        gui.printPopUp(disconnectionGui);
        try {
            sleep(timeToSleep);
        } catch (InterruptedException ignored) {
        }
        gui.quitApplication();
    }

    /**
     * Adverts client of quit.
     * @param field
     */
    @Override
    public void execute(Field field) {
        field.printSentence(disconnection);
    }
}
