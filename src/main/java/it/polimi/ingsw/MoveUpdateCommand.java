package it.polimi.ingsw;

import java.util.List;

import static it.polimi.ingsw.CommunicationSentences.updateField;
import static it.polimi.ingsw.FinalCommunication.MOVE;

public class MoveUpdateCommand extends ReplyCommand {

    private int oldRow;
    private int oldColumn;
    private int newRow;
    private int newColumn;
    private String workerValue;
    private String playerName;
    private String playerColor;

    public MoveUpdateCommand(List<Integer> integerList, String workerValue, String playerName, String playerColor) {
        this.oldRow = integerList.get(0);
        this.oldColumn = integerList.get(1);
        this.newRow = integerList.get(2);
        this.newColumn = integerList.get(3);
        this.workerValue = workerValue;
        this.playerName = playerName;
        this.playerColor = playerColor;
    }


    @Override
    public void execute(GraphicInterface graphicInterface) {
       // graphicInterface.move(oldRow, oldColumn, newRow, newColumn, workerValue);
    }



    @Override
    public void execute(Field field){
        String sentence = updateField(newRow, newColumn, playerName, playerColor, MOVE);
        field.printSentence(sentence);
        field.viewMove(workerValue, oldRow, oldColumn, newRow, newColumn, playerColor);

    }
}
