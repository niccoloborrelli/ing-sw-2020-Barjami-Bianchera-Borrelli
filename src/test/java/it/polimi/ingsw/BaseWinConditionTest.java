package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BaseWinConditionTest {

    @Test
    void checkHashWonTest1() throws IOException {
        Player player = new Player();
        Space start = new Space(0,0);
        Space finish = new Space(1,1);
        WinConditionAB winConditionAB = new BaseWinCondition();

        start.setLevel(2);
        finish.setLevel(3);
        player.getWorkers().get(0).setMovedThisTurn(true);
        player.getWorkers().get(0).setLastSpaceOccupied(start);
        player.getWorkers().get(0).setWorkerSpace(finish);
        player.setWinCondition(winConditionAB);

        winConditionAB.checkHasWon(player);
        assertTrue(player.isHasWon());
    }

    @Test
    void checkHashWonTest2() throws IOException {
        Player player = new Player();
        Space start = new Space(0,0);
        Space finish = new Space(1,1);
        WinConditionAB winConditionAB = new BaseWinCondition();

        start.setLevel(2);
        finish.setLevel(3);
        player.getWorkers().get(0).setLastSpaceOccupied(start);
        player.getWorkers().get(0).setWorkerSpace(finish);
        player.setWinCondition(winConditionAB);

        winConditionAB.checkHasWon(player);

        assertFalse(player.isHasWon());
    }

}