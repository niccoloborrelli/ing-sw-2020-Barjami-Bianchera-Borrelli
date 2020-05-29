package it.polimi.ingsw;

import java.io.IOException;
import java.util.List;

import static it.polimi.ingsw.DefinedValues.*;

public class JumpMoreLevelsWin extends PowerWinDecorator {

    /*
    You also win if your worker moves down two or more levels
    PAN
     */

    /**
     * This is a classic decorator pattern constructor
     * @param winConditionAB is the object to decorate
     */
    public JumpMoreLevelsWin(WinConditionAB winConditionAB){
        this.winCondition = winConditionAB;
    }

    /**
     * This method checks if the player has won by the base win condition or by the Pan one
     * @param player is the player to check
     */
    @Override
    public void checkHasWon(Player player) throws IOException {
        winCondition.checkHasWon(player);
        if(!player.isHasWon())
            checkHasWonJump(player);
    }

    private void checkHasWonJump(Player player) {
        Worker workerChosen = getWorkerChosen(player);
        if(workerChosen != null)
            if (workerChosen.getLastSpaceOccupied().getLevel() - workerChosen.getWorkerSpace().getLevel() >= JUMP_LEVELS_TO_WIN)
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
