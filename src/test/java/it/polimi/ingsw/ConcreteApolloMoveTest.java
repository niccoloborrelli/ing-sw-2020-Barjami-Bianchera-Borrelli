package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConcreteApolloMoveTest {

    /*
    verifica che è stato effettuato lo switch tra worker avversari
     */
    @Test
    void changeSpaceApolloTest(){
        ConcreteApolloMove apollo = new ConcreteApolloMove(new BaseMovement());
        IslandBoard islandBoard = new IslandBoard();

        Worker worker1 = new Worker();
        worker1.setWorkerPlayer(new Player("Ciro"));
        worker1.setWorkerSpace(new Space(0,0));
        islandBoard.getSpace(0,0).setOccupator(worker1);

        Worker worker2 = new Worker();
        worker2.setWorkerPlayer(new Player("François"));
        worker2.setWorkerSpace(new Space(1,1));
        islandBoard.getSpace(1,1).setOccupator(worker2);

        apollo.move(worker1, islandBoard.getSpace(1,1), islandBoard);
        assertTrue(islandBoard.getSpace(0,0).getOccupator() == worker2 &&
            islandBoard.getSpace(1,1).getOccupator() == worker1);
    }
}
