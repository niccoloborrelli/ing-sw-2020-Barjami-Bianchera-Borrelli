package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JumpMoreLevelsWinTest {

    /*
    Questo test verifica che se scende da un livello 3 a 1 vince
     */
    @Test
    void checkHasWonTest1() throws IOException {

        Worker worker = new Worker();
        WinConditionAB winCondition = new JumpMoreLevelsWin(new BaseWinCondition());
        IslandBoard islandBoard = new IslandBoard();

        worker.setWorkerSpace(islandBoard.getSpace(1,1));
        islandBoard.getSpace(1,1).setOccupator(worker);
        islandBoard.getSpace(1,1).setLevel(1);

        //winCondition.checkHasWon(worker, 3, islandBoard);

        assertTrue(winCondition.gethasWon());
    }

    /*
    Questo verifica che se la differenza di livelli è 1 non vince
     */

    @Test
    void checkHasWonTest2() throws IOException {
        Worker worker = new Worker();
        WinConditionAB winCondition = new JumpMoreLevelsWin(new BaseWinCondition());
        IslandBoard islandBoard = new IslandBoard();

        worker.setWorkerSpace(islandBoard.getSpace(1,1));
        islandBoard.getSpace(1,1).setOccupator(worker);
        islandBoard.getSpace(1,1).setLevel(1);

        //winCondition.checkHasWon(worker, 2, islandBoard);

        assertFalse(winCondition.gethasWon());
    }
}