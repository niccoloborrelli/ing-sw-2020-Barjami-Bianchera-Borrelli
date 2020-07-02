package it.polimi.ingsw;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.DefinedValues.*;

/*
Attenzione con areWorkersInGame perchè va settato a true ogni inizio turno
 */
public  class  IslandBoard {
    /**
     * Represents game field.
     */

    private Space[][] spaces;

    private boolean higherNoMove;
    private boolean mustMoveUp;

    /**
     * IslandBoard constructor that initialize a 5x5 array of space
     */
    public IslandBoard() {
        spaces = new Space[MAXROW+1][MAXCOLUMN+1];
        for(int i = 0; i <= MAXROW; i++)
            for(int j = 0; j <= MAXCOLUMN; j++)
                spaces[i][j]= new Space(i,j);
    }

    /**
     * @param row is the coordinate of the row
     * @param column is the coordinate of the column
     * @return the space with coordinates row and column
     */
    public Space getSpace(int row, int column){
        if(row>=MINROW && row<=MAXCOLUMN && column>=MINCOLUMN && column<=MAXCOLUMN)
            return spaces[row][column];
        else
            return null;
    }

    public boolean isHigherNoMove() {
        return higherNoMove;
    }

    public void setHigherNoMove(boolean higherNoMove) {
        this.higherNoMove = higherNoMove;
    }

    public boolean isMustMoveUp() {
        return mustMoveUp;
    }

    public void setMustMoveUp(boolean mustMoveUp) {
        this.mustMoveUp = mustMoveUp;
    }
}

