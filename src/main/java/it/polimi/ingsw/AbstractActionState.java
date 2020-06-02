package it.polimi.ingsw;

import java.io.IOException;

public abstract class AbstractActionState extends State {

    AbstractActionState(Player player) {
        super(player);
    }

    private static final String WINSPECIFICATION = "win";
    public abstract Worker getActingWorker();
    public abstract Space getSpaceToAct();
    public abstract Space getStartingSpace();
    public abstract String getAction();
    public void notifyActionPerformed(WorkerSpaceCouple wsc,String action){
        LastChange actionPerformed = new LastChange();
        actionPerformed.setCode(2);
        actionPerformed.setSpecification(action);
        actionPerformed.setSpace(wsc.getSpace());
        actionPerformed.setWorker(wsc.getWorker());
        //player.notify(actionPerformed);
    }

    public void notifyWin(){
        LastChange winChange = new LastChange();
        winChange.setCode(3);
        winChange.setSpecification(WINSPECIFICATION);
        //player.notify(winChange);
    }
}
