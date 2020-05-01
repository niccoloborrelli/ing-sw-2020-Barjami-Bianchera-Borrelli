package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ConcretePersephoneRestrictionTest {

    /*
    Questo test controlla che, se un worker può salire,
    allora può andare solo in caselle con livello più alto
    e uno che non può salire non ha caselle in cui muoversi
     */
    @Test
    void restrictionEffectMovementPersephoneTest1() {
        IslandBoard islandBoard = new IslandBoard();
        Player player = new Player(new Socket());
        player.setRestriction(new ConcretePersephoneRestriction(new BaseRestriction()));

        islandBoard.getSpace(0,0).setLevel(1);
        player.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(0,1));
        player.getWorkers().get(1).setWorkerSpace(islandBoard.getSpace(0,2));
        islandBoard.getSpace(0,1).setOccupator(player.getWorkers().get(0));
        islandBoard.getSpace(0,2).setOccupator(player.getWorkers().get(1));

        player.getRestriction().restrictionEffectMovement(player, islandBoard);
        assertTrue(islandBoard.checkAvailableMovement(player)[0].size() == 1 &&
                            islandBoard.checkAvailableMovement(player)[1].size() == 0);
    }

    /*
    Questo test controlla che, se entrambi i
    worker non possono salire, allora possono
    muoversi incondizonatamente
     */
    @Test
    void restrictionEffectMovementPersephoneTest2() {
        IslandBoard islandBoard = new IslandBoard();
        Player player = new Player(new Socket());
        player.setRestriction(new ConcretePersephoneRestriction(new BaseRestriction()));

        player.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(0,1));
        player.getWorkers().get(1).setWorkerSpace(islandBoard.getSpace(0,2));
        islandBoard.getSpace(0,1).setOccupator(player.getWorkers().get(0));
        islandBoard.getSpace(0,2).setOccupator(player.getWorkers().get(1));

        player.getRestriction().restrictionEffectMovement(player, islandBoard);
        assertTrue(islandBoard.checkAvailableMovement(player)[0].size() != 0 &&
                            islandBoard.checkAvailableMovement(player)[1].size() != 0);
    }
}