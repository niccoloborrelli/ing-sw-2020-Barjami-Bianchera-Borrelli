
package it.polimi.ingsw;


public class DeselectWorkerRequestCommand extends ReplyCommand {

    /**
     * Represents a de-selection of a pawn.
     */

    /**
     * Resets cells color in board.
     * @param gui is the interface.
     */

    @Override
    public void execute(GraphicInterface gui) {
        gui.resetColorBoard();
    }

    @Override
    public void execute(Field field) {}
}

