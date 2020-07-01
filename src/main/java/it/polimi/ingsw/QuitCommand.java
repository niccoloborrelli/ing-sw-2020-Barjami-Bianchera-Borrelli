package it.polimi.ingsw;

import static it.polimi.ingsw.CommunicationSentences.disconnection;
import static java.lang.Thread.sleep;

public class QuitCommand extends ReplyCommand {

    public void execute(App app){
        System.out.println("Ora mi ammazzo");
        app.setOnExitStage();
        try {
            sleep(7000);
        } catch (InterruptedException ignored) {
        }
        app.quitApplication();
    }

    @Override
    public void execute(GraphicInterface gui) {
        gui.printPopUp(disconnection);
        try {
            sleep(7000);
        } catch (InterruptedException ignored) {
        }
        gui.quitApplication();

    }


    @Override
    public void execute(Field field) {
        field.printSentence(disconnection);
    }
}
