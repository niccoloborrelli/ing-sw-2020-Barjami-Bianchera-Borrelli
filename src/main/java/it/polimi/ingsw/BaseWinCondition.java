package it.polimi.ingsw;

import java.io.IOException;
import java.util.List;

public class BaseWinCondition extends WinConditionAB {

    /**
     * this method permits to set a WinConditionAb hasWon attribute to true if the worker has moved from a Space at level 2 to a
     * Space at level 3
     * @param player is the player to check
     */
    @Override
    public void checkHasWon(Player player) {
        Worker workerChosen;
        if(player.getWorkers().get(0).isMovedThisTurn()) {
            workerChosen = player.getWorkers().get(0);
            if (workerChosen.getLastSpaceOccupied().getLevel() == 2 && workerChosen.getWorkerSpace().getLevel() == 3) {
                player.setHasWon(true);
            }
        }
        else if(player.getWorkers().get(1).isMovedThisTurn()){
            workerChosen = player.getWorkers().get(1);
            if (workerChosen.getLastSpaceOccupied().getLevel() == 2 && workerChosen.getWorkerSpace().getLevel() == 3) {
                player.setHasWon(true);
            }
        }
    }
}
