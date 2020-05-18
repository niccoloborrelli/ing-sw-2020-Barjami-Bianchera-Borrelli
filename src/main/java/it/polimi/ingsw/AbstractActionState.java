package it.polimi.ingsw;

public abstract class AbstractActionState extends State {

    AbstractActionState(Player player) {
        super(player);
    }

    public abstract  Worker getActingWorker();
    public abstract Space getSpaceToAct();
    public abstract Space getStartingSpace();
}
