package it.polimi.ingsw;

import static it.polimi.ingsw.CommunicationSentences.ANSI_RESET;
import static it.polimi.ingsw.CommunicationSentences.printParticularSentence;

public class SentenceBottomRequestCommand extends ReplyCommand {

    /**
     * Represents a reply with a sentence to print.
     */
    private String playerColor;
    private String playerName;
    private String specification;

    public SentenceBottomRequestCommand(String playerColor, String playerName, String specification) {
        this.playerColor = playerColor;
        this.playerName = playerName;
        this.specification = specification;
    }

    public String getSpecification() {
        return specification;
    }

    /**
     * Prints a phrase determined by information in command in bottom of interface.
     * @param graphicInterface is interface.
     */

    @Override
    public void execute(GraphicInterface graphicInterface) {
        if(graphicInterface!=null) {
            String sentence = printParticularSentence(specification);
            graphicInterface.printBottom(playerName + sentence);
        }
    }

    /**
     * Print a sentence determined by information in command.
     * @param field is the interface.
     */

    @Override
    public void execute(Field field) {
        String sentence =  printParticularSentence(specification);
        field.printSentence(playerColor + playerName + ANSI_RESET + sentence);
    }

}

