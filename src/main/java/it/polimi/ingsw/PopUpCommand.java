package it.polimi.ingsw;

import static it.polimi.ingsw.CommunicationSentences.*;
import static it.polimi.ingsw.FinalCommunication.*;

public class PopUpCommand extends ReplyCommand {
    /**
     * Represents the command that could generate a Pop-up in GUI.
     * In the CLI prints a sentence that represents the event.
     */

    private String playerName;
    private String playerColor;
    private String specification;

    public PopUpCommand(String playerName, String playerColor, String specification) {
        this.playerName = playerName;
        this.playerColor = playerColor;
        this.specification = specification;
    }

    /**
     * Builds the sentence that represents the event and creates a pop-up.
     * @param graphicInterface is where it generates the pop up.
     */

    @Override
    public void execute(GraphicInterface graphicInterface) {
        String sentence;
        switch (specification) {
            case WIN:
                sentence = won;
                break;
            case LOSE:
                System.out.println("Mi sa che ho perso");
                sentence = lose;
                break;
            case LOST:
                sentence = playerName + getLostPhrase();
                break;
            default:
                sentence = "";
                break;
        }

        graphicInterface.printPopUp(sentence);
    }

    /**
     * Builds and prints a sentence that represents the event.
     * @param field permits to print the sentence.
     */

    @Override
    public void execute(Field field) {
        String sentence = printParticularSentence(specification, playerName, playerColor);
        field.printSentence(sentence);
    }
}
