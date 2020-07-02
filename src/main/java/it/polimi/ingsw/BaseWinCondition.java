package it.polimi.ingsw;

import java.io.IOException;
import java.util.List;

public class BaseWinCondition extends WinConditionAB {

    /**
     * Permits to set a player hasWon attribute to true if the worker has moved from a Space at level 2 to a
     * Space at level 3
     * @param player is the player to check
     */
    @Override
    public void checkHasWon(Player player) {
        Worker workerChosen = getWorkerChosen(player);
        if(workerChosen != null)
            if (workerChosen.getLastSpaceOccupied().getLevel() == 2 && workerChosen.getWorkerSpace().getLevel() == 3)
                player.setHasWon(true);
    }

    /**
     * Finds which worker moved in its turn.
     * @param player is worker's player.
     * @return worker moved in this turn if it exists, otherwise null.
     */

    private Worker getWorkerChosen(Player player){
        for(Worker worker: player.getWorkers()) {
            if(worker.isMovedThisTurn())
                return worker;
        }
        return null;
    }
}
