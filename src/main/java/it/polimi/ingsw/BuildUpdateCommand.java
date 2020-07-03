package it.polimi.ingsw;

import java.util.List;

import static it.polimi.ingsw.CommunicationSentences.updateField;
import static it.polimi.ingsw.FinalCommunication.BUILD;

public class BuildUpdateCommand extends ReplyCommand {

    private static final int rowPos=0;
    private  static final int columnPos=1;
    private  static final int levelPos=2;
    private  static final int domePos=3;
    /**
     * Represents a update command caused by a build in the game field.
     */

    private int row;
    private int column;
    private int level;
    private int dome;
    private String playerName;
    private String playerColor;

    public BuildUpdateCommand(List<Integer> parameters, String playerName, String playerColor) {
        this.row = parameters.get(rowPos);
        this.column = parameters.get(columnPos);
        this.level = parameters.get(levelPos);
        this.dome = parameters.get(domePos);
        this.playerName = playerName;
        this.playerColor = playerColor;
    }

    /**
     * Updates with a build the graphic interface.
     * @param gui is graphic interface.
     */

    @Override
    public void execute(GraphicInterface gui) {
        gui.build(row, column, level, dome);
    }

    /**
     * Prints a sentence and show the build in the field.
     * @param field is the field.
     */


    @Override
    public void execute(Field field){
        String sentence = updateField(row, column, playerName, playerColor, BUILD);
        field.printSentence(sentence);
        field.viewBuild(row, column, level, dome);
    }
}
