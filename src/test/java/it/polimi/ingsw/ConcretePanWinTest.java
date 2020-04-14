package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConcretePanWinTest {

    //vince scendendo di 2 dal secondo livello
    @Test
    void checkHasWonPanTest1() {
        ConcretePanWin pan = new ConcretePanWin(new BaseWinCondition());
        Worker worker = new Worker();
        Space space = new Space(1,1);
        worker.setWorkerSpace(space);
        pan.checkHasWonPan(worker,2);
        assertTrue(pan.gethasWon());
    }

    //non vince scendendo di 1
    @Test
    void checkHasWonPanTest2(){
        ConcretePanWin pan = new ConcretePanWin(new BaseWinCondition());
        Worker worker = new Worker();
        Space space = new Space(1,1);
        space.setLevel(1);
        worker.setWorkerSpace(space);
        pan.checkHasWonPan(worker,2);
        assertFalse(pan.gethasWon());
    }

    //vince scendendo di 2 dal terzo livello
    @Test
    void checkHasWonPanTest3() {
        ConcretePanWin pan = new ConcretePanWin(new BaseWinCondition());
        Worker worker = new Worker();
        Space space = new Space(1,1);
        space.setLevel(1);
        worker.setWorkerSpace(space);
        pan.checkHasWonPan(worker,3);
        assertTrue(pan.gethasWon());
    }
}