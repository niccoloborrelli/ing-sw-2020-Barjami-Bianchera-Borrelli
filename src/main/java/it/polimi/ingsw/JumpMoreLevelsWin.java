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
     * This method checks if the player has won by the base win condition or the Pan one
     * @param player is the player to check
     */
    @Override
    public void checkHasWon(Player player) throws IOException {
        winCondition.checkHasWon(player);
        if(!gethasWon())
            checkHasWonJump(player);
    }

    /**
     * This method checks if the player has move down at least 2 levels
     * @param player is the player to check
     */
    private void checkHasWonJump(Player player) throws IOException {//(VERIFICATA)
        Worker workerChosen;
        if(player.getWorkers().get(0).isChosen()) {
            workerChosen = player.getWorkers().get(0);
            if (workerChosen.getLastSpaceOccupied().getLevel() - workerChosen.getWorkerSpace().getLevel() >= JUMP_LEVELS_TO_WIN) {
                setHasWon(true);
                //notifyWin();
            }
        }
        else if(player.getWorkers().get(1).isChosen()){
            workerChosen = player.getWorkers().get(1);
            if (workerChosen.getLastSpaceOccupied().getLevel() - workerChosen.getWorkerSpace().getLevel() >= JUMP_LEVELS_TO_WIN) {
                setHasWon(true);
                //notifyWin();
            }
        }
    }

}
