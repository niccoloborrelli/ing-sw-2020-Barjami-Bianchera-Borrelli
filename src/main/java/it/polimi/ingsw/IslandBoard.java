package it.polimi.ingsw;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/*
Attenzione con areWorkersInGame perchè va settato a true ogni inizio turno
 */
public  class  IslandBoard {
    private Space[][] spaces;
    private static final int dim=5;
    /**
     * IslandBoard constructor that initialize a 5x5 array of space
     */
    public IslandBoard() throws IOException {
        spaces = new Space[dim][dim];
        for(int i = 0; i < dim; i++)
            for(int j = 0; j < dim; j++)
                spaces[i][j]= new Space(i,j);
    }

    /**
     * @param row is the coordinate of the row
     * @param column is the coordinate of the column
     * @return the space with coordinates row and column
     */
    public Space getSpace(int row, int column){
        return spaces[row][column];
    }
}

