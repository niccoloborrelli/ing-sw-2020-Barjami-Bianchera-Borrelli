package it.polimi.ingsw;

public class EndTurnState extends State {

    private static final String ENDTURNSPECIFICATION="endTurn";
    EndTurnState(Player player) {
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
            player.getActionsToPerform().add("move");
            player.getActionsToPerform().add("build");
        }
    }

    private void notifyTurnFinished(){
        LastChange finishedTurn = player.getLastChange();
        finishedTurn.setCode(0);
        finishedTurn.setSpecification(ENDTURNSPECIFICATION);
        player.notifyController();
    }

    public String toString(){
        return "EndTurnState";
    }
}