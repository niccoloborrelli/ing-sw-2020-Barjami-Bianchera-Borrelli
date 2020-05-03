package it.polimi.ingsw;

import java.util.List;

public class ConcreteChronusWin extends PowerWinDecorator {

    /*
    You also win when there are at least
    five Complete Towers on the board
     */

    /**
     * This is a classic decorator pattern constructor
     * @param winConditionAB is the object to decorate
     */
    public ConcreteChronusWin(WinConditionAB winConditionAB){
        this.winCondition = winConditionAB;
    }

    /**
     * This method checks if the player has won by the base win condition or the Chronus one
     * @param worker is the worker moved by the player
     * @param startLevel is the level at the beginning of the round
     */
    @Override
    public void checkHasWon(Worker worker, int startLevel, IslandBoard islandBoard) {
        winCondition.checkHasWon(worker, startLevel, islandBoard);
        if(!gethasWon())
            checkHasWonChronus(islandBoard);
    }

    /**
     * This method checks if the player is the only remained
     * @param players is the list of all the players in the game
     */
    @Override
    public void checkHasWon(List<Player> players) {
        winCondition.checkHasWon(players);
    }

    /**
     * The method counts how many complete tower are on the board,
     * if there are at least 5 it sets as true the boolean attribute
     * "HasWon" of the player
     */
    private void checkHasWonChronus(IslandBoard islandBoard){ //(VERIFICATA)
        int completeTower = 0;
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 5; j++)
                if(islandBoard.getSpace(i,j).getLevel() == 4)
                    completeTower++;
        if(completeTower > 4)
            setHasWon(true);
    }
}
