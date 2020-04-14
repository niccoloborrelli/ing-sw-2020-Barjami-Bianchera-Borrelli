package it.polimi.ingsw;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseWinConditionTest {

    /*
    controlla se si vince senza worker
     */
    @Test
    void checkHasWonTest1() {
        BaseWinCondition bwc = new BaseWinCondition();
        IslandBoard islandBoard = new IslandBoard();
        bwc.checkHasWon(null, 2, islandBoard);
        assertFalse(bwc.gethasWon());
    }

    /*
    controlla se si vince salendo da livello 2 a 3
    */
    @Test
    void checkHasWonTest2() {
        Worker worker = new Worker();
        IslandBoard islandBoard = new IslandBoard();
        BaseWinCondition bwc = new BaseWinCondition();
        Space space = new Space(2,2);
        space.setLevel(3);
        worker.setWorkerSpace(space);
        bwc.checkHasWon(worker,2, islandBoard);
        assertTrue(bwc.gethasWon());
    }

    /*
    controlla se il worker non ha vinto
     */
    @Test
    void checkHasWonTest3(){
        Worker worker = new Worker();
        IslandBoard islandBoard = new IslandBoard();
        BaseWinCondition bwc = new BaseWinCondition();
        Space space = new Space(2,2);
        space.setLevel(3);
        worker.setWorkerSpace(space);
        bwc.checkHasWon(worker, 3, islandBoard);
        assertFalse(bwc.gethasWon());
    }
}