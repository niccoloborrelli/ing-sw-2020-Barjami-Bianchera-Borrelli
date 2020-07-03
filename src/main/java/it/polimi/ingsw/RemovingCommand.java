package it.polimi.ingsw;

import java.util.List;

public class RemovingCommand extends ReplyCommand {
    private static final int rowPos=0;
    private static final int columnPos=1;
    /**
     * Represents a command which will remove a component in a determined position
     * in interface.
     */

    private int row;
    private int column;

    public RemovingCommand(List<Integer> integerList) {
        this.row = integerList.get(rowPos);
        this.column = integerList.get(columnPos);
    }

    /**
     * Removes a specific object in a place with these coordinates.
     * @param gui is interface.
     */
    @Override
    public void execute(GraphicInterface gui) {
        gui.remove(row, column);
    }

    /**
     * Removes a letter in a place with these coordinates.
     * @param field is interface.
     */

    @Override
    public void execute(Field field) {
        field.viewRemoveWorker(row, column);
    }
}
