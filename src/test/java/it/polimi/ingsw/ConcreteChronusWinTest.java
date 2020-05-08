package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ConcreteChronusWinTest {

    //vince con 5 torri complete
    @Test
    void checkHasWonChronusTest1() throws IOException {
        IslandBoard islandBoard = new IslandBoard();
        islandBoard.getSpace(0,0).setLevel(4);
        islandBoard.getSpace(0,1).setLevel(4);
        islandBoard.getSpace(0,2).setLevel(4);
        islandBoard.getSpace(0,3).setLevel(4);
        islandBoard.getSpace(0,4).setLevel(4);
        CompleteTowerWin chronus = new CompleteTowerWin(new BaseWinCondition());

        //chronus.checkHasWon(islandBoard);
        assertTrue(chronus.gethasWon());
    }

    //non vince con 4 torri complete
    @Test
    void checkHasWonChronusTest2() throws IOException {
        IslandBoard islandBoard = new IslandBoard();
        islandBoard.getSpace(0, 0).setLevel(4);
        islandBoard.getSpace(0, 1).setLevel(4);
        islandBoard.getSpace(0, 2).setLevel(4);
        islandBoard.getSpace(0, 3).setLevel(4);
        CompleteTowerWin chronus = new CompleteTowerWin(new BaseWinCondition());

        //chronus.checkHasWonChronus(islandBoard);
        assertFalse(chronus.gethasWon());
    }
}