package it.polimi.ingsw;

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

    private void resetFlags(){
        resetWorkers();
        resetPlayerActions();
    }

    private void resetWorkers(){
        for (Worker tempWorker:player.getWorkers()) {
            tempWorker.resetWorker();
            player.setPowerUsed(false);
            player.setPowerNotUsable(false);
            player.setPowerActivated(false);
        }
    }

    private void resetPlayerActions(){
        if(player.getActionsToPerform().size()==0) {
            player.getActionsToPerform().add(MOVE);
            player.getActionsToPerform().add(BUILD);
        }
    }

    private void notifyTurnFinished(){
        LastChange finishedTurn = player.getLastChange();
        finishedTurn.setCode(0);
        finishedTurn.setSpecification(ENDTURN);
        player.notifyController();
    }

    public String toString(){
        return "EndTurnState";
    }
}