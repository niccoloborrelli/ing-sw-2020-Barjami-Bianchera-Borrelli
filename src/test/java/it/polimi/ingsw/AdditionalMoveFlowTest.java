package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class AdditionalMoveFlowTest {

    /*
    Questo test controlla che, se ha spazi possibli dove
    muoversi, l'action "move" viene aggiunta prima di "build"
     */
    @Test
    void changeFlowTest1() {
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        player.setIslandBoard(islandBoard);
        Worker worker1 = player.getWorkers().get(0);
        Worker worker2 = player.getWorkers().get(1);

        worker1.setWorkerSpace(islandBoard.getSpace(0,0));
        islandBoard.getSpace(0,0).setOccupator(worker1);
        worker2.setWorkerSpace(islandBoard.getSpace(4,4));
        islandBoard.getSpace(4,4).setOccupator(worker2);

        worker1.setMovedThisTurn(true);
        worker1.setLastSpaceOccupied(islandBoard.getSpace(1,1));
        AdditionalMoveFlow additionalMoveFlow = new AdditionalMoveFlow();
        player.getActionsToPerform().remove("move");
        if(additionalMoveFlow.isUsable(player)) {
            additionalMoveFlow.changeFlow(player);
        }
        System.out.println("cantMoveFirstSpace: " + worker1.isCantMoveFirstSpace());
        assertEquals(2, player.getActionsToPerform().size());
    }

    /*
    Questo test controlla che, se non ha spazi possibli dove
    muoversi, l'action "move" non viene aggiunta prima di "build"
     */
    @Test
    void changeFlowTest2() {
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        player.setIslandBoard(islandBoard);
        Worker worker1 = player.getWorkers().get(0);
        Worker worker2 = player.getWorkers().get(1);

        worker2.setWorkerSpace(islandBoard.getSpace(0,0));
        islandBoard.getSpace(0,0).setOccupator(worker2);
        worker1.setWorkerSpace(islandBoard.getSpace(1,0));
        islandBoard.getSpace(1,0).setOccupator(worker1);
        islandBoard.getSpace(0,1).setHasDome(true);

        worker2.setMovedThisTurn(true);
        worker2.setLastSpaceOccupied(islandBoard.getSpace(1,1));
        AdditionalMoveFlow additionalMoveFlow = new AdditionalMoveFlow();
        player.getActionsToPerform().remove("move");
        System.out.println(player.getActionsToPerform());
        if(additionalMoveFlow.isUsable(player))
            additionalMoveFlow.changeFlow(player);
        else
            System.out.println("AdditionalMove not possible");
        System.out.println(player.getActionsToPerform());
        assertEquals(1, player.getActionsToPerform().size());

    }
}