package it.polimi.ingsw;

import java.io.IOException;

public class EndTurnState extends State {

    EndTurnState(Player player) {
        super(player);
    }

    @Override
    public void onStateTransition() {
        for (Worker tempWorker:player.getWorkers()) {
            tempWorker.resetWorker();
        }
        player.getStateManager().setNextState(this);
    }

    public String toString(){
        return "EndTurnState";
    }
}