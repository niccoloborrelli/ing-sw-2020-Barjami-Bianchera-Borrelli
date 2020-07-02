package it.polimi.ingsw;

import java.util.List;

public class SettingPawnCommand extends ReplyCommand {

    /**
     * Represents a reply to a request of a setting pawn.
     */

    String worker;
    String playerColor;
    private int row;
    private int column;

    public SettingPawnCommand(List<Integer> parameters, String worker, String playerColor) {
        this.row = parameters.get(0);
        this.column = parameters.get(1);
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
