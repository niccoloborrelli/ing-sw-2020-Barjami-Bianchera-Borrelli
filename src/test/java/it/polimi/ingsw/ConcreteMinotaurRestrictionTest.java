package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConcreteMinotaurRestrictionTest {

    /*
    Controlla se un worker può spostare un altro worker della stesso player, controlla anche
    se può spingere un worker avversario ai limiti del campo da gioco
     */
    @Test
    public void restrictionEffectMovementTest1() throws IOException {
        RestrictionAB restriction = new ConcreteMinotaurRestriction(new BaseRestriction());

        IslandBoard islandBoard = new IslandBoard();
        Player player1 = new Player(new Socket("localhost",60010));
        Player player2 = new Player(new Socket("localhost",50010));

        player1.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(1,3));
        islandBoard.getSpace(1,3).setOccupator(player1.getWorkers().get(0));
        player1.getWorkers().get(1).setWorkerSpace(islandBoard.getSpace(1,2));
        islandBoard.getSpace(1,2).setOccupator(player1.getWorkers().get(1));

        player2.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(1,4));
        islandBoard.getSpace(1,4).setOccupator(player2.getWorkers().get(0));

        restriction.restrictionEffectMovement(player1,islandBoard);

        assertTrue(islandBoard.getSpace(1,4).isAvailableMovement().size() == 0 &&
                islandBoard.getSpace(1,3).isAvailableMovement().size() == 0 &&
                islandBoard.getSpace(1,2).isAvailableMovement().size() == 0);
    }

    /*
    Controlla se sposta il worker avversario se dietro ha una cupola
     */

    @Test
    public void restrictionEffectMovementTest2() throws IOException {
        RestrictionAB restriction = new ConcreteMinotaurRestriction(new BaseRestriction());

            IslandBoard islandBoard = new IslandBoard();
            Player player1 = new Player(new Socket("localhost",60010));
            Player player2 = new Player(new Socket("localhost",50010));

            player1.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(0,0));
            islandBoard.getSpace(0,0).setOccupator(player1.getWorkers().get(0));
            player1.getWorkers().get(1).setWorkerSpace(islandBoard.getSpace(1,1));
            islandBoard.getSpace(1,1).setOccupator(player1.getWorkers().get(1));

            player2.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(1,2));
            islandBoard.getSpace(1,2).setOccupator(player2.getWorkers().get(0));

            islandBoard.getSpace(1,3).setHasDome(true);

            restriction.restrictionEffectMovement(player1,islandBoard);

        assertEquals(0, islandBoard.getSpace(1, 2).isAvailableMovement().size());
        }


    /*
    Controlla se il worker sposta il worker avversario anche se dietro ha un altro worker,
    inoltre controlla se il worker sposta un worker avversario se non ha nulla dietro
     */

    @Test
    public void restrictionEffectMovementTest3() throws IOException {
            RestrictionAB restriction = new ConcreteMinotaurRestriction(new BaseRestriction());

            IslandBoard islandBoard = new IslandBoard();
            Player player1 = new Player(new Socket("localhost",60010));
            Player player2 = new Player(new Socket("localhost",50010));

            player1.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(0,0));
            islandBoard.getSpace(0,0).setOccupator(player1.getWorkers().get(0));
            player1.getWorkers().get(1).setWorkerSpace(islandBoard.getSpace(2,2));
            islandBoard.getSpace(2,2).setOccupator(player1.getWorkers().get(1));

            player2.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(1,2));
            islandBoard.getSpace(1,1).setOccupator(player2.getWorkers().get(0));
            player2.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(1,2));
            islandBoard.getSpace(3,3).setOccupator(player2.getWorkers().get(0));

            restriction.restrictionEffectMovement(player1,islandBoard);

            assertTrue(islandBoard.getSpace(1,1).isAvailableMovement().size() == 0 &&
                    islandBoard.getSpace(3,3).isAvailableMovement().contains(player1.getWorkers().get(1)));
        }

    }