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
            player.setPowerUsed(false);
            player.setPowerNotUsable(false);
            player.setPowerActivated(false);
        }
    }

    public String toString(){
        return "EndTurnState";
    }
}