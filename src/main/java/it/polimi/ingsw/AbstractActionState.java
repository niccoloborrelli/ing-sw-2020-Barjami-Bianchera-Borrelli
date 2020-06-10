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
        LastChange actionPerformed = player.getLastChange();
        actionPerformed.setCode(2);
        actionPerformed.setSpecification(action);
        actionPerformed.setSpace(wsc.getSpace());
        actionPerformed.setWorker(wsc.getWorker());

        player.notifyController();
    }

    public void notifyWin(){
        LastChange winChange = player.getLastChange();
        winChange.setCode(3);
        winChange.setSpecification(WINSPECIFICATION);
        player.notifyController();
    }
}
