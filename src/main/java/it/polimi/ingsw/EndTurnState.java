package it.polimi.ingsw;

public class EndTurnState extends State {

    EndTurnState(Player player) {
        super(player);
    }

    @Override
    public void onStateTransition() {
        for (Worker tempWorker:player.getWorkers()) {
            tempWorker.resetWorker();
            player.setPowerUsed(false);
            player.setPowerNotUsable(false);
            player.setPowerActivated(false);
        }
        if(player.getActionsToPerform().size()==0) {
            player.getActionsToPerform().add("move");
            player.getActionsToPerform().add("build");
        }
        System.out.println("Ho finito il turno");
        player.getStateManager().getTurnManager().setNextPlayer(player);

    }

    public String toString(){
        return "EndTurnState";
    }
}