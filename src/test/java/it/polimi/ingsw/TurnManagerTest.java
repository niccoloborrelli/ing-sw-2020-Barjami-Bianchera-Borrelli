package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TurnManagerTest {

    /*
    Questo test verifica che se ci sono 5 torri complete
    e un giocatore ha quel potere allora vince
     */
    @Test
    void checkWinTest1() throws IOException {
        IslandBoard islandBoard = new IslandBoard();
        Player ciro = new Player();
        Player francois = new Player();
        ciro.setIslandBoard(islandBoard);
        francois.setIslandBoard(islandBoard);

        List<Player> players = new ArrayList<>();
        players.add(ciro);
        players.add(francois);
        TurnManager turnManager = new TurnManager();
        turnManager.setPlayers(players);
        francois.setWinCondition(new CompleteTowerWin(new BaseWinCondition()));
        ciro.setWinCondition(new BaseWinCondition());

        islandBoard.getSpace(0,0).setLevel(4);
        islandBoard.getSpace(0,1).setLevel(4);
        islandBoard.getSpace(0,2).setLevel(4);
        islandBoard.getSpace(0,3).setLevel(4);
        islandBoard.getSpace(0,4).setLevel(4);

        turnManager.checkWin();
        assertTrue(francois.isHasWon());
    }

    /*
    Questo test verifica la win condition di scendere di 2 o più livelli
     */
    @Test
    void checkWinTest2() throws IOException {
        IslandBoard islandBoard = new IslandBoard();
        Player ciro = new Player();
        Player francois = new Player();
        ciro.setIslandBoard(islandBoard);
        francois.setIslandBoard(islandBoard);

        List<Player> players = new ArrayList<>();
        players.add(ciro);
        players.add(francois);
        TurnManager turnManager = new TurnManager();
        turnManager.setPlayers(players);
        francois.setWinCondition(new JumpMoreLevelsWin(new BaseWinCondition()));
        ciro.setWinCondition(new BaseWinCondition());

        francois.getWorkers().get(1).setMovedThisTurn(true);
        francois.getWorkers().get(1).setWorkerSpace(islandBoard.getSpace(0,1));
        francois.getWorkers().get(1).setLastSpaceOccupied(islandBoard.getSpace(0,0));
        islandBoard.getSpace(0,0).setLevel(2);

        turnManager.checkWin();
        assertTrue(francois.isHasWon());
    }

    /*
    Questo test verifica la base win condition
     */
    @Test
    void checkWinTest3() throws IOException {
        IslandBoard islandBoard = new IslandBoard();
        Player ciro = new Player();
        Player francois = new Player();
        ciro.setIslandBoard(islandBoard);
        francois.setIslandBoard(islandBoard);

        List<Player> players = new ArrayList<>();
        players.add(ciro);
        players.add(francois);
        TurnManager turnManager = new TurnManager();
        turnManager.setPlayers(players);
        francois.setWinCondition(new JumpMoreLevelsWin(new BaseWinCondition()));
        ciro.setWinCondition(new BaseWinCondition());

        ciro.getWorkers().get(0).setMovedThisTurn(true);
        ciro.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(0,0));
        ciro.getWorkers().get(0).setLastSpaceOccupied(islandBoard.getSpace(0,1));
        islandBoard.getSpace(0,0).setLevel(3);
        islandBoard.getSpace(0,1).setLevel(2);

        turnManager.checkWin();
        assertTrue(ciro.isHasWon());
    }

    /*
    Questo test verifica che se è rimasto in partita un
    solo giocatore allora vince
     */
    @Test
    void checkWinTest4() throws IOException {
        Player ciro = new Player();
        Player francois = new Player();
        List<Player> players = new ArrayList<>();
        players.add(ciro);
        players.add(francois);
        TurnManager turnManager = new TurnManager();
        turnManager.setPlayers(players);

        francois.setWinCondition(new BaseWinCondition());
        ciro.setInGame(false);

        turnManager.checkWin();
        assertTrue(francois.isHasWon());
    }

    /*
    Questo test verifica l'inserimento corretto dei colori
     */
    @Test
    public void insertColor(){
        Player ciro = new Player();
        Player francois = new Player();

        List<Player> players = new ArrayList<>();
        players.add(ciro);
        players.add(francois);

        TurnManager turnManager = new TurnManager();
        turnManager.setPlayers(players);
        turnManager.setColor(ciro, "red");
        turnManager.setColor(francois, "cyan");

        System.out.println("Il colore di ciro è " + ciro.getPlayerColor() + "questo" + Color.RESET);
        System.out.println("Il colore di francois è " + francois.getPlayerColor() + "questo" + Color.RESET);
    }
}