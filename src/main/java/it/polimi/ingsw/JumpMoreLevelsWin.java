package it.polimi.ingsw;

import java.io.IOException;
import java.util.List;

public class JumpMoreLevelsWin extends PowerWinDecorator {

    private final int numberLevels = 2;

    /*
    You also win if your worker
    moves down two or more levels
     */
    //PAN

    /**
     * This is a classic decorator pattern constructor
     * @param winConditionAB is the object to decorate
     */
    public JumpMoreLevelsWin(WinConditionAB winConditionAB){
        this.winCondition = winConditionAB;
    }

    /**
     * This method checks if the player has won by the base win condition or the Pan one
     * @param worker is the worker moved by the player
     * @param startLevel is the level at the beginning of the round
     */
    @Override
    public void checkHasWon(Worker worker, int startLevel, IslandBoard islandBoard) throws IOException {
        winCondition.checkHasWon(worker, startLevel, islandBoard);
        if(!gethasWon())
            checkHasWonJump(worker, startLevel, islandBoard);
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
    private void checkHasWonJump(Worker worker, int startLevel, IslandBoard islandBoard) throws IOException { //(VERIFICATA)
        if(startLevel - worker.getWorkerSpace().getLevel() >= numberLevels) {
            setHasWon(true);
            islandBoard.notifyWin(worker.getWorkerPlayer().getSocket());
        }
    }

}
