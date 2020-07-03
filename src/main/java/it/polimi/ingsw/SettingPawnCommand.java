package it.polimi.ingsw;

import java.util.List;

public class SettingPawnCommand extends ReplyCommand {

    private static final int rowPos=0;
    private static final int columnPost=1;
    /**
     * Represents a reply to a request of a setting pawn.
     */

    String worker;
    String playerColor;
    private int row;
    private int column;

    public SettingPawnCommand(List<Integer> parameters, String worker, String playerColor) {
        this.row = parameters.get(rowPos);
        this.column = parameters.get(columnPost);
        this.worker=worker;
        this.playerColor = playerColor;
    }

    public String getWorker() {
        return worker;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    /**
     * Sets a pawn with the information of the command.
     * @param gui is the interface.
     */

    @Override
    public void execute(GraphicInterface gui) {
        gui.setPawn(row, column,playerColor,worker);
    }

    /**
     * Sets a pawn with the information of the command.
     * @param field is the interface.
     */
    @Override
    public void execute(Field field) {
        field.viewSetup(row, column, worker, playerColor);
    }


}
