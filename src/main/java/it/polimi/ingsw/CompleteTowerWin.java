package it.polimi.ingsw;

import java.io.IOException;
import java.util.List;

import static it.polimi.ingsw.DefinedValues.*;
import static it.polimi.ingsw.DefinedValues.COMPLETE_TOWER_TO_WIN;

public class CompleteTowerWin extends PowerWinDecorator {

    /*
    You also win when there are at least five Complete Towers on the board
    CHRONUS
     */

    /**
     * This is a classic decorator pattern constructor
     * @param winConditionAB is the object to decorate
     */
    public CompleteTowerWin(WinConditionAB winConditionAB){ this.winCondition = winConditionAB;}

    /**
     * This method checks if the player has won by the base win condition or the Chronus one
     * @param player is the player to check
     */
    @Override
    public void checkHasWon(Player player) throws IOException {
        winCondition.checkHasWon(player);
        if(!gethasWon())
            checkHasWonTower(player);
    }

    /**
     * The method counts how many complete tower are on the board,
     * if there are at least 5 it sets as true the boolean attribute
     * "HasWon" of the player
     */
    private void checkHasWonTower(Player player) throws IOException { //(VERIFICATA)
        int completeTower = 0;
        for(int row = MINROW; row < DIM; row++)
            for(int column = MINCOLUMN; column < DIM; column++)
                if(player.getIslandBoard().getSpace(row, column).getLevel() == DOME_LEVEL)
                    completeTower++;
        if(completeTower >= COMPLETE_TOWER_TO_WIN) {
            player.getWinCondition().setHasWon(true);
            //notifyWin();
        }
    }
}
