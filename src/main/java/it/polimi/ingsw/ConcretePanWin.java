package it.polimi.ingsw;

import java.util.List;

public class ConcretePanWin extends PowerWinDecorator {

    /*
    You also win if your worker
    moves down two or more levels
     */

    /**
     * This is a classic decorator pattern constructor
     * @param winConditionAB is the object to decorate
     */
    public ConcretePanWin(WinConditionAB winConditionAB){
        this.winCondition = winConditionAB;
    }

    /**
     * This method checks if the player has won by the base win condition or the Pan one
     * @param worker is the worker moved by the player
     * @param startLevel is the level at the beginning of the round
     */
    @Override
    public void checkHasWon(Worker worker, int startLevel, IslandBoard islandBoard) {
        winCondition.checkHasWon(worker, startLevel, islandBoard);
        if(!gethasWon())
            checkHasWonPan(worker, startLevel);
    }

    /**
     * This method checks if the player is the only remained
     * @param players is the list of all the players in the game
     */
    @Override
    public void checkHasWon(List<Player> players){
        winCondition.checkHasWon(players);
    }

    /**
     * This method checks if the player has move down at least 2 levels
     * @param worker is the worker moved by the player
     * @param startLevel is the level at the beginning of the round
     */
    private void checkHasWonPan(Worker worker, int startLevel){ //(VERIFICATA)
        if(startLevel == 2 && worker.getWorkerSpace().getLevel() == 0)
            setHasWon(true);
        else if(startLevel == 3 && (worker.getWorkerSpace().getLevel() == 0 || worker.getWorkerSpace().getLevel() == 1))
            setHasWon((true));
    }

}
