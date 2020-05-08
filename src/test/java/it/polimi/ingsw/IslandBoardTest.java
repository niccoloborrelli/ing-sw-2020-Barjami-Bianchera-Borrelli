package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
Manca da testare la parte di areWorkersInGame
 */
class IslandBoardTest {

    /*
    Questo test controlla se lo spazio in cui può muoversi un worker viene effettivamente aggiunto solo alla sua lista
     */
    @Test
    void checkAvailableMovementW1() throws IOException {
             int i = 3;
             int j = 3;
             Worker workerA;
             Player player = new Player(null);
             workerA=player.getWorkers().get(0);
             IslandBoard board = new IslandBoard();
             board.getSpace(i,j).addAvailableMovement(workerA);
             List<Space>[] ritorno = board.checkAvailableMovement(player);

             assertTrue(ritorno[0].contains(board.getSpace(i,j)) && !ritorno[1].contains(board.getSpace(i,j)));

    }

    /*
    Questo test controlla se lo spazio viene aggiunto alla lista del secondo worker
     */
    @Test
    void checkAvailableMovementW2() throws IOException {
        int i = 3;
        int j = 3;
        Worker workerA;
        Player player = new Player(null);
        workerA = player.getWorkers().get(1);
        IslandBoard board = new IslandBoard();
        board.getSpace(i,j).addAvailableMovement(workerA);
        List<Space>[] ritorno = board.checkAvailableMovement(player);

        assertTrue(ritorno[1].contains(board.getSpace(i,j)) && !ritorno[0].contains(board.getSpace(i,j)));

    }

    @Test
    void checkAvailableBuildingW1() throws IOException {
            int i = 3;
            int j = 3;
            Worker workerA;
            Player player = new Player(null);
            workerA = player.getWorkers().get(0);
            IslandBoard board = new IslandBoard();
            board.getSpace(i,j).addAvailableBuilding(workerA);
            List<Space>[] ritorno = board.checkAvailableBuilding(player);

            assertTrue(ritorno[0].contains(board.getSpace(i,j)) && !ritorno[1].contains(board.getSpace(i,j)));

    }
    @Test
    void checkAvailableBuildingW2() throws IOException {
        int i = 3;
        int j = 3;
        Worker workerA;
        Player player = new Player(null);
        workerA = player.getWorkers().get(1);
        IslandBoard board = new IslandBoard();
        board.getSpace(i,j).addAvailableBuilding(workerA);
        List<Space>[] ritorno = board.checkAvailableBuilding(player);

        assertTrue(ritorno[1].contains(board.getSpace(i,j))&&!ritorno[0].contains(board.getSpace(i,j)));
    }

    @Test
    void resetBoard() throws IOException {
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