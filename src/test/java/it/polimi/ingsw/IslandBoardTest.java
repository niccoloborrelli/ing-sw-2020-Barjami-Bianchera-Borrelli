package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IslandBoardTest {

    @Test
    void checkAvailableMovement() {
    }

    @Test
    void checkAvailableBuilding() {
    }

    @Test
    void resetBoard() {
        IslandBoard islandBoard = new IslandBoard();
        islandBoard.resetBoard();
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 5; j++){
                if(islandBoard.getSpace(i,j).isAvailableBuilding().size() != 0 || islandBoard.getSpace(i,j).isAvailableMovement().size() != 0)
                    assertFalse(false);
            }
        assertTrue(true);
    }

}