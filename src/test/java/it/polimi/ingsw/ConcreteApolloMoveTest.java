package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class ConcreteApolloMoveTest {

    /*
    verifica che è stato effettuato lo switch tra worker avversari
     */
    @Test
    void changeSpaceApolloTest() throws IOException {
        ConcreteApolloMove apollo = new ConcreteApolloMove(new BaseMovement());
        IslandBoard islandBoard = new IslandBoard();

        Worker worker1 = new Worker();
        worker1.setWorkerPlayer(new Player(new Socket("localhost",60010)));
        worker1.setWorkerSpace(new Space(0,0));
        islandBoard.getSpace(0,0).setOccupator(worker1);

        Worker worker2 = new Worker();
        worker2.setWorkerPlayer(new Player(new Socket("localhost",50010)));
        worker2.setWorkerSpace(new Space(1,1));
        islandBoard.getSpace(1,1).setOccupator(worker2);

        apollo.move(worker1, islandBoard.getSpace(1,1), islandBoard);
        assertTrue(islandBoard.getSpace(0,0).getOccupator() == worker2 &&
            islandBoard.getSpace(1,1).getOccupator() == worker1);
    }
}
