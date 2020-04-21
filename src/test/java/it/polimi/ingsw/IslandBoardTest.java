package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IslandBoardTest {

    @Test
    void checkAvailableMovementW1() {
             int i = 3;
             int j = 3;
             Worker workerA;
             Player player = new Player("nome");
             workerA=player.getWorkers().get(0);
             IslandBoard board = new IslandBoard();
             board.getSpace(i,j).addAvailableMovement(workerA);
             List<Space>[] ritorno = board.checkAvailableMovement(player);

             assertTrue(ritorno[0].contains(board.getSpace(i,j)) && !ritorno[1].contains(board.getSpace(i,j)));

    }

    @Test
    void checkAvailableMovementW2() {
        int i = 3;
        int j = 3;
        Worker workerA;
        Player player = new Player("nome");
        workerA = player.getWorkers().get(1);
        IslandBoard board = new IslandBoard();
        board.getSpace(i,j).addAvailableMovement(workerA);
        List<Space>[] ritorno = board.checkAvailableMovement(player);

        assertTrue(ritorno[1].contains(board.getSpace(i,j)) && !ritorno[0].contains(board.getSpace(i,j)));

    }

    @Test
    void checkAvailableBuildingW1() {
            int i = 3;
            int j = 3;
            Worker workerA;
            Player player = new Player("nome");
            workerA = player.getWorkers().get(0);
            IslandBoard board = new IslandBoard();
            board.getSpace(i,j).addAvailableBuilding(workerA);
            List<Space>[] ritorno = board.checkAvailableBuilding(player);

            assertTrue(ritorno[0].contains(board.getSpace(i,j)) && !ritorno[1].contains(board.getSpace(i,j)));

    }
    @Test
    void checkAvailableBuildingW2() {
        int i = 3;
        int j = 3;
        Worker workerA;
        Player player = new Player("nome");
        workerA = player.getWorkers().get(1);
        IslandBoard board = new IslandBoard();
        board.getSpace(i,j).addAvailableBuilding(workerA);
        List<Space>[] ritorno = board.checkAvailableBuilding(player);

        assertTrue(ritorno[1].contains(board.getSpace(i,j))&&!ritorno[0].contains(board.getSpace(i,j)));
    }

    @Test
    void resetBoard() {
        IslandBoard islandBoard = new IslandBoard();
        islandBoard.resetBoard();
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 5; j++){
                if(islandBoard.getSpace(i,j).isAvailableBuilding().size() != 0 || islandBoard.getSpace(i,j).isAvailableMovement().size() != 0)
                    assertFalse(false);
            }
        assertTrue(true);
    }

}