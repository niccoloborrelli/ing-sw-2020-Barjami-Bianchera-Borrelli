package it.polimi.ingsw;

import java.io.IOException;
import static it.polimi.ingsw.FinalCommunication.*;

public abstract class AbstractActionState extends State {

    AbstractActionState(Player player) {
        super(player);
    }

    private static final String WINSPECIFICATION = "win";
    public abstract Worker getActingWorker();
    public abstract Space getSpaceToAct();
    public abstract Space getStartingSpace();
    public abstract String getAction();

    /**
     * Notifies action performed.
     * @param wsc contains information about action performed.
     * @param action is action performed.
     */
    public void notifyActionPerformed(WorkerSpaceCouple wsc,String action){
        LastChange actionPerformed = player.getLastChange();
        actionPerformed.setCode(UPDATE_GAME_FIELD);
        actionPerformed.setSpecification(action);
        actionPerformed.setSpace(wsc.getSpace());
        actionPerformed.setWorker(wsc.getWorker());

        player.notifyController();
    }

    /**
     * Notifies the win.
     */
    public void notifyWin(){
        LastChange winChange = player.getLastChange();
        winChange.setCode(UPDATE_ENDGAME);
        winChange.setSpecification(WINSPECIFICATION);
        player.notifyController();
    }
}
