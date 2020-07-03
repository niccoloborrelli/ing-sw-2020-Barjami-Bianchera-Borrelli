package it.polimi.ingsw;

import java.util.List;

import static it.polimi.ingsw.CommunicationSentences.updateField;
import static it.polimi.ingsw.FinalCommunication.MOVE;

public class MoveUpdateCommand extends ReplyCommand {
    /**
     * It's a command representing an update caused by a movement.
     */

    private static final int oldRowPos=0;
    private static final int oldColumnPos=1;
    private static final int newRowPos=2;
    private static final int newColumnPos=3;

    private int oldRow;
    private int oldColumn;
    private int newRow;
    private int newColumn;
    private String workerValue;
    private String playerName;
    private String playerColor;

    public MoveUpdateCommand(List<Integer> integerList, String workerValue, String playerName, String playerColor) {
        this.oldRow = integerList.get(oldRowPos);
        this.oldColumn = integerList.get(oldColumnPos);
        this.newRow = integerList.get(newRowPos);
        this.newColumn = integerList.get(newColumnPos);
        this.workerValue = workerValue;
        this.playerName = playerName;
        this.playerColor = playerColor;
    }


    /**
     * Moves a pawn in a different place.
     * @param graphicInterface is the interface.
     */
    @Override
    public void execute(GraphicInterface graphicInterface) {
        graphicInterface.move(oldRow, oldColumn, newRow, newColumn, workerValue);
    }


    /**
     * Moves a pawn in a different place.
     * @param field is the interface.
     */
    @Override
    public void execute(Field field){
        String sentence = updateField(newRow, newColumn, playerName, playerColor, MOVE);
        field.printSentence(sentence);
        field.viewMove(workerValue, oldRow, oldColumn, newRow, newColumn, playerColor);

    }
}
