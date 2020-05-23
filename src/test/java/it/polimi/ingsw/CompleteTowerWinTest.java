package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class CompleteTowerWinTest {

    //vince con 5 torri complete
    @Test
    void checkHasWonChronusTest1() throws IOException {
        IslandBoard islandBoard = new IslandBoard();
        islandBoard.getSpace(0,0).setLevel(4);
        islandBoard.getSpace(0,1).setLevel(4);
        islandBoard.getSpace(0,2).setLevel(4);
        islandBoard.getSpace(0,3).setLevel(4);
        islandBoard.getSpace(0,4).setLevel(4);

        Player player = new Player();
        CompleteTowerWin chronus = new CompleteTowerWin(new BaseWinCondition());
        player.setWinCondition(chronus);
        player.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(1,1));
        islandBoard.getSpace(1,1).setOccupator(player.getWorkers().get(0));

        chronus.checkHasWon(player);
        assertTrue(chronus.gethasWon());
    }

    //non vince con 4 torri complete
    @Test
    void checkHasWonChronusTest2() throws IOException {
        IslandBoard islandBoard = new IslandBoard();
        islandBoard.getSpace(0, 0).setLevel(4);
        islandBoard.getSpace(0, 1).setLevel(4);
        islandBoard.getSpace(0, 2).setLevel(4);
        islandBoard.getSpace(0, 3).setLevel(4);

        Player player = new Player();
        CompleteTowerWin chronus = new CompleteTowerWin(new BaseWinCondition());
        player.setWinCondition(chronus);
        player.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(1,1));
        islandBoard.getSpace(1,1).setOccupator(player.getWorkers().get(0));

        chronus.checkHasWon(player);
        assertFalse(chronus.gethasWon());
    }
}