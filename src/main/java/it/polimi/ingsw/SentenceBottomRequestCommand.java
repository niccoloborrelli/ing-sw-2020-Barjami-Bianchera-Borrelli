package it.polimi.ingsw;

import static it.polimi.ingsw.CommunicationSentences.printParticularSentence;

public class SentenceBottomRequestCommand extends ReplyCommand {

    //private static final String SET_UP = "Choose positions for your worker.";
    private String playerColor;
    private String playerName;
    private String specification;

    public SentenceBottomRequestCommand(String playerColor, String playerName, String specification) {
        this.playerColor = playerColor;
        this.playerName = playerName;
        this.specification = specification;
    }


    @Override
    public void execute(GraphicInterface graphicInterface) {
        String sentence = printParticularSentence(specification, playerName, playerColor);
        graphicInterface.printBottom(sentence);
    }


    @Override
    public void execute(Field field) {
        String sentence =  printParticularSentence(specification, playerName, playerColor);
        field.printSentence(sentence);
    }

}

