package it.polimi.ingsw;

import static it.polimi.ingsw.CommunicationSentences.disconnection;
import static java.lang.Thread.sleep;

public class QuitCommand extends ReplyCommand {

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
            sleep(7000);
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
        gui.printPopUp(disconnection);
        try {
            sleep(7000);
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
