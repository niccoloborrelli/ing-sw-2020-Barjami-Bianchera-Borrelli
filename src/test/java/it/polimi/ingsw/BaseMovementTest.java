package it.polimi.ingsw;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BaseMovementTest {

    /*moveTest1 controlla se accade qualcosa se il worker non esiste
    */
    @Test
    void moveTest1() throws IOException {
        BaseMovement bm = new BaseMovement();
        IslandBoard islandBoard = new IslandBoard();
        Space space = new Space(1,1);
        bm.move(null, space, islandBoard);
        assertFalse(space.getOccupator()!=null);
    }

    /*moveTest2 controlla sia se startPlace diventa vuoto
    sia se worker si sposta effettivamente in un posto libero
    */
    @Test
    void moveTest2() throws IOException {
        BaseMovement bm = new BaseMovement();
        Worker worker1 = new Worker();
        IslandBoard islandBoard = new IslandBoard();
        Space finishPlace = new Space(0,0);
        Space startPlace = new Space(1,1);
        boolean moved = false;

        worker1.setWorkerSpace(startPlace);
        startPlace.setOccupator(worker1);
        finishPlace.addAvailableMovement(worker1);
        bm.move(worker1,finishPlace, islandBoard);

        if(startPlace.getOccupator() == null)
            System.out.println("Il worker si è mosso");
        else
            System.out.println("L'occupator di startPlace non è null");
        assertEquals(finishPlace, worker1.getWorkerSpace());
    }

    /*moveTest3 controlla se Worker si sposta in un posto
    in cui non può muoversi
    */
    @Test
    void moveTest3() throws IOException {
        BaseMovement bm = new BaseMovement();
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        IslandBoard islandBoard = new IslandBoard();

        worker1.setWorkerSpace(islandBoard.getSpace(1,1));
        Space finishSpace = islandBoard.getSpace(0,0);
        finishSpace.addAvailableMovement(worker2);
        bm.move(worker1,finishSpace, islandBoard);
        assertTrue(worker1.getWorkerSpace().equals(islandBoard.getSpace(1,1)) && islandBoard.getSpace(0,0).getOccupator() == null);

    }
}