package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class ConcreteApolloRestrictionTest {

    /*
    controlla che come movimenti possibili ci siano
    caselle adiacenti con worker avversari e che non
    ci siano caselle adiacenti con worker alleati
     */
    @Test
    void ApolloRestrictionTest() throws IOException {
        SwapWorkersRestriction apollo = new SwapWorkersRestriction(new BaseRestriction());
        IslandBoard islandBoard = new IslandBoard();

        Player player = new Player(new Socket("localhost",60010));
        Worker worker1 = player.getWorkers().get(0);
        Worker worker2 = player.getWorkers().get(1);
        worker1.setWorkerPlayer(player);
        worker1.setWorkerSpace(new Space(0,0));
        islandBoard.getSpace(0,0).setOccupator(worker1);
        worker2.setWorkerPlayer(player);
        worker2.setWorkerSpace(new Space(0,1));
        islandBoard.getSpace(0,1).setOccupator(worker2);

        Worker worker3 = new Worker();
        worker3.setWorkerPlayer(new Player(new Socket("localhost",50010)));
        worker3.setWorkerSpace(new Space(1,1));
        islandBoard.getSpace(1,1).setOccupator(worker3);

        apollo.restrictionEffectMovement(player, islandBoard);
        assertTrue(islandBoard.checkAvailableMovement(player)[0].contains(islandBoard.getSpace(1,1))
                    && !islandBoard.checkAvailableMovement(player)[0].contains(islandBoard.getSpace(0,1)));
    }
}
