package it.polimi.ingsw;

import static it.polimi.ingsw.DefinedValues.MINSIZE;
import static it.polimi.ingsw.DefinedValues.endTurn;
import static it.polimi.ingsw.FinalCommunication.*;

public class EndTurnState extends State {

    /**
     * Represents the end of turn.
     * In this state players can do almost nothing.
     */

    public EndTurnState(Player player) {
        super(player);
    }

    @Override
    public void onStateTransition() {
        resetFlags();
        notifyTurnFinished();
        player.getStateManager().getTurnManager().setNextPlayer(player);
    }

    /**
     * Resets flags.
     */
    private void resetFlags(){
        resetWorkers();
        resetPlayerActions();
    }

    /**
     * Resets workers' flag.
     */
    private void resetWorkers(){
        for (Worker tempWorker:player.getWorkers()) {
            tempWorker.resetWorker();
            player.setPowerUsed(false);
            player.setPowerNotUsable(false);
            player.setPowerActivated(false);
        }
    }

    /**
     * Resets player possible action.
     */
    private void resetPlayerActions(){
        if(player.getActionsToPerform().size()==MINSIZE) {
            player.getActionsToPerform().add(MOVE);
            player.getActionsToPerform().add(BUILD);
        }
    }

    /**
     * Notifies the end of turn.
     */
    private void notifyTurnFinished(){
        LastChange finishedTurn = player.getLastChange();
        finishedTurn.setCode(UPDATE_TO_PRINT);
        finishedTurn.setSpecification(ENDTURN);
        player.notifyController();
    }

    public String toString(){
        return endTurn;
    }
}