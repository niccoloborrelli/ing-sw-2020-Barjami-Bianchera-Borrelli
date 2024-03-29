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
     * This method checks if the player has won by the base win condition or by the Chronus one
     * @param player is the player to check
     */
    @Override
    public void checkHasWon(Player player) throws IOException {
        winCondition.checkHasWon(player);
        if(!player.isHasWon())
            checkHasWonTower(player);
    }

    /**
     * Controls if there's enough complete tower.
     * @param player is player that invoke the control.
     */
    private void checkHasWonTower(Player player) {
        int completeTower = MINSIZE;
        for(int row = MINROW; row < DIM; row++)
            for(int column = MINCOLUMN; column < DIM; column++)
                if(player.getIslandBoard().getSpace(row, column).getLevel() == DOME_LEVEL && player.getIslandBoard().getSpace(row, column).HasDome())
                    completeTower++;
        if(completeTower >= COMPLETE_TOWER_TO_WIN) {
            player.setHasWon(true);
        }
    }
}
