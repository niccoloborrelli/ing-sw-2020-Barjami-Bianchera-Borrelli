package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseMovementTest {

    /*moveTest1 controlla se accade qualcosa se
    entrambi i parametri passati sono null
    */
    @Test
    void moveTest1() {
        BaseMovement bm = new BaseMovement();
        assertFalse(bm.move(null, null));
    }

    /*moveTest2 controlla sia se startPlace diventa vuoto
    sia se worker si sposta effettivamente in un posto libero
    */
    @Test
    void moveTest2(){
        BaseMovement bm = new BaseMovement();
        Worker worker1 = new Worker();
        Space finishPlace = new Space(0,0);
        Space startPlace = new Space(1,1);
        boolean moved = false;

        worker1.setWorkerSpace(startPlace);
        startPlace.setOccupator(worker1);
        finishPlace.addAvailableMovement(worker1);
        moved = bm.move(worker1,finishPlace);

        if(startPlace.getOccupator() == null && moved)
            System.out.println("Il worker si è mosso");
        else
            System.out.println("L'occupator di startPlace non è null");
        assertEquals(finishPlace, worker1.getWorkerSpace());
    }

    /*moveTest3 controlla se Worker si sposta in un posto
    in cui non può muoversi
    */
    @Test
    void moveTest3() {
        BaseMovement bm = new BaseMovement();
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        Space finishSpace = new Space(0, 0);
        finishSpace.addAvailableMovement(worker2);
        assertFalse(bm.move(worker1,finishSpace));

    }
}