package it.polimi.ingsw;

import static it.polimi.ingsw.CommunicationSentences.disconnection;

public class QuitCommand extends ReplyCommand {


    @Override
    public void execute(GraphicInterface gui) {
        gui.printPopUp(disconnection);
    }


    @Override
    public void execute(Field field) {
        field.printSentence(disconnection);
    }
}
