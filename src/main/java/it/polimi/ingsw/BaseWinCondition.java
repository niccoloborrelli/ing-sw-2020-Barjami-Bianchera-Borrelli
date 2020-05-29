package it.polimi.ingsw;

import java.io.IOException;
import java.util.List;

public class BaseWinCondition extends WinConditionAB {

    /**
     * this method permits to set a player hasWon attribute to true if the worker has moved from a Space at level 2 to a
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

    private Worker getWorkerChosen(Player player){
        if(player.getWorkers().get(0).isMovedThisTurn())
            return player.getWorkers().get(0);
        else if(player.getWorkers().get(1).isMovedThisTurn())
            return player.getWorkers().get(1);
        return null;
    }
}
