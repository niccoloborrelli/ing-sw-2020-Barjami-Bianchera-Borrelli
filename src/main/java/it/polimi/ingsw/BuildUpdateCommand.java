package it.polimi.ingsw;

import java.util.List;

import static it.polimi.ingsw.CommunicationSentences.updateField;
import static it.polimi.ingsw.FinalCommunication.BUILD;

public class BuildUpdateCommand extends ReplyCommand {

    private int row;
    private int column;
    private int level;
    private int dome;
    private String playerName;
    private String playerColor;

    public BuildUpdateCommand(List<Integer> parameters, String playerName, String playerColor) {
        this.row = parameters.get(0);
        this.column = parameters.get(1);
        this.level = parameters.get(2);
        this.dome = parameters.get(3);
        this.playerName = playerName;
        this.playerColor = playerColor;
    }


    @Override
    public void execute(GraphicInterface gui) {
        gui.build(row, column, level, dome);
    }


    @Override
    public void execute(Field field){
        String sentence = updateField(row, column, playerName, playerColor, BUILD);
        field.printSentence(sentence);
        field.viewBuild(row, column, level, dome);
    }
}
