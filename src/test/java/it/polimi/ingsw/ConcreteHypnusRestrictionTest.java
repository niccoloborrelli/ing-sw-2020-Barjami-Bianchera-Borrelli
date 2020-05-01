package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ConcreteHypnusRestrictionTest {

    /*
    Questo test controlla che, se un worker è
    più in alto di tutti, allora non può muoversi
     */
    @Test
    void restrictionEffectMovementHypnusTest1() {
        IslandBoard islandBoard = new IslandBoard();
        Player player = new Player(new Socket());
        player.setRestriction(new ConcreteHypnusRestriction(new BaseRestriction()));

        Space space = islandBoard.getSpace(3,3);
        space.setLevel(1);
        space.setOccupator(player.getWorkers().get(0));
        player.getWorkers().get(0).setWorkerSpace(space);
        player.getWorkers().get(1).setWorkerSpace(islandBoard.getSpace(0,0));
        islandBoard.getSpace(0,0).setOccupator(player.getWorkers().get(1));

        player.getRestriction().restrictionEffectMovement(player, islandBoard);
        assertEquals(0, islandBoard.checkAvailableMovement(player)[0].size());
    }

    /*
    Questo test controlla che se due worker
    sono al livello massimo possono comunque muoversi
     */
    @Test
    void restrictionEffectMovementHypnusTest2() {
        IslandBoard islandBoard = new IslandBoard();
        Player player = new Player(new Socket());
        player.setRestriction(new ConcreteHypnusRestriction(new BaseRestriction()));

        Space space1 = islandBoard.getSpace(3,3);
        space1.setLevel(1);
        space1.setOccupator(player.getWorkers().get(0));
        player.getWorkers().get(0).setWorkerSpace(space1);

        Space space2 = islandBoard.getSpace(0,0);
        space2.setLevel(1);
        player.getWorkers().get(1).setWorkerSpace(space2);
        space2.setOccupator(player.getWorkers().get(1));

        player.getRestriction().restrictionEffectMovement(player, islandBoard);
        assertTrue(islandBoard.checkAvailableMovement(player)[0].size() != 0 &&
                            islandBoard.checkAvailableMovement(player)[1].size() != 0);
    }
}