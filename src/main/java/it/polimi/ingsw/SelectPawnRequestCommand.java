package it.polimi.ingsw;

public class SelectPawnRequestCommand extends RequestCommand {
    private static final String WORKER = "w";
    private int indexOfPawn;

    public SelectPawnRequestCommand(int indexOfPawn){
        this.indexOfPawn=indexOfPawn;
    }

    @Override
    public String execute() {
        return WORKER+indexOfPawn;
    }
}
