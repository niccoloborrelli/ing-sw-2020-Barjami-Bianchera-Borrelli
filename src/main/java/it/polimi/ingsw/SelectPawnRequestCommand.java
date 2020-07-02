package it.polimi.ingsw;

public class SelectPawnRequestCommand extends RequestCommand {
    /**
     * Represents a selection of worker.
     */
    private static final String WORKER = "w";
    private int indexOfPawn;

    public SelectPawnRequestCommand(int indexOfPawn){
        this.indexOfPawn=indexOfPawn;
    }

    /**
     * Creates a string with pawn information.
     * @return the string with pawn information.
     */

    @Override
    public String execute() {
        return WORKER+indexOfPawn;
    }
}
