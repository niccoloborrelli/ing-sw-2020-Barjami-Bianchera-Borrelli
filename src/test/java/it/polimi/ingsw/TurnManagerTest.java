package it.polimi.ingsw;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TurnManagerTest {

    /*
    Questo test verifica che se ci sono 5 torri complete
    e un giocatore ha quel potere allora vince
     */
    @Test
    void checkWinTest1() throws IOException, ParserConfigurationException, SAXException {
        ServerSocket serverSocket = new ServerSocket(60102);
        Socket socket = new Socket("localhost", 60102);
        HandlerHub handlerHub = new HandlerHub();

        IslandBoard islandBoard = new IslandBoard();
        Player ciro = new Player();
        Player francois = new Player();
        ciro.setIslandBoard(islandBoard);
        francois.setIslandBoard(islandBoard);

        Controller controller1 = new Controller();
        Controller controller2 = new Controller();
        ciro.setController(controller1);
        francois.setController(controller2);
        controller1.setPlayer(ciro);
        controller2.setPlayer(francois);
        handlerHub.addHandlerForSocket(socket, controller1);
        handlerHub.addHandlerForSocket(socket, controller2);

        List<Player> players = new ArrayList<>();
        players.add(ciro);
        players.add(francois);
        TurnManager turnManager = new TurnManager();
        turnManager.setPlayers(players);
        francois.setWinCondition(new CompleteTowerWin(new BaseWinCondition()));
        ciro.setWinCondition(new BaseWinCondition());

        StateManager stateManager1 = new StateManager();
        StateManager stateManager2 = new StateManager();
        stateManager1.createBaseStates(ciro);
        stateManager2.createBaseStates(francois);
        ciro.setStateManager(stateManager1);
        francois.setStateManager(stateManager2);

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream file = classLoader.getResourceAsStream("table.txt");
        TableXML tableXML = new TableXML(file, ciro);
        HashMap<State, List<Line>> table = tableXML.readXML(ciro.getStateManager().getStateHashMap());
        ciro.getStateManager().setTable(table);
        file = classLoader.getResourceAsStream("table.txt");
        TableXML tableXML2 = new TableXML(file, francois);
        HashMap<State, List<Line>> table2 = tableXML2.readXML(francois.getStateManager().getStateHashMap());
        francois.getStateManager().setTable(table2);

        ciro.getStateManager().setCurrent_state(ciro.getStateManager().getState("ReadyForActionState"));
        francois.getStateManager().setCurrent_state(francois.getStateManager().getState("ReadyForActionState"));

        islandBoard.getSpace(0,0).setLevel(3);
        islandBoard.getSpace(0,0).setHasDome(true);
        islandBoard.getSpace(0,1).setLevel(3);
        islandBoard.getSpace(0,1).setHasDome(true);
        islandBoard.getSpace(0,2).setLevel(3);
        islandBoard.getSpace(0,2).setHasDome(true);
        islandBoard.getSpace(0,3).setLevel(3);
        islandBoard.getSpace(0,3).setHasDome(true);
        islandBoard.getSpace(0,4).setLevel(3);
        islandBoard.getSpace(0,4).setHasDome(true);

        synchronized (francois) {
            turnManager.checkWin();
        }
        assertTrue(francois.isHasWon());

        serverSocket.close();
        socket.close();
    }

    /*
    Questo test verifica la win condition di scendere di 2 o più livelli
     */
    @Test
    void checkWinTest2() throws IOException, ParserConfigurationException, SAXException {
        ServerSocket serverSocket = new ServerSocket(60102);
        Socket socket = new Socket("localhost", 60102);
        HandlerHub handlerHub = new HandlerHub();

        IslandBoard islandBoard = new IslandBoard();
        Player ciro = new Player();
        Player francois = new Player();
        ciro.setIslandBoard(islandBoard);
        francois.setIslandBoard(islandBoard);

        Controller controller1 = new Controller();
        Controller controller2 = new Controller();
        ciro.setController(controller1);
        francois.setController(controller2);
        controller1.setPlayer(ciro);
        controller2.setPlayer(francois);
        handlerHub.addHandlerForSocket(socket, controller1);
        handlerHub.addHandlerForSocket(socket, controller2);

        List<Player> players = new ArrayList<>();
        players.add(ciro);
        players.add(francois);
        TurnManager turnManager = new TurnManager();
        turnManager.setPlayers(players);
        francois.setWinCondition(new JumpMoreLevelsWin(new BaseWinCondition()));
        ciro.setWinCondition(new BaseWinCondition());

        StateManager stateManager1 = new StateManager();
        StateManager stateManager2 = new StateManager();
        stateManager1.createBaseStates(ciro);
        stateManager2.createBaseStates(francois);
        ciro.setStateManager(stateManager1);
        francois.setStateManager(stateManager2);

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream file = classLoader.getResourceAsStream("table.txt");
        TableXML tableXML = new TableXML(file, ciro);
        HashMap<State, List<Line>> table = tableXML.readXML(ciro.getStateManager().getStateHashMap());
        ciro.getStateManager().setTable(table);
        file = classLoader.getResourceAsStream("table.txt");
        TableXML tableXML2 = new TableXML(file, francois);
        HashMap<State, List<Line>> table2 = tableXML2.readXML(francois.getStateManager().getStateHashMap());
        francois.getStateManager().setTable(table2);

        ciro.getStateManager().setCurrent_state(ciro.getStateManager().getState("ReadyForActionState"));
        francois.getStateManager().setCurrent_state(francois.getStateManager().getState("ReadyForActionState"));

        francois.getWorkers().get(1).setMovedThisTurn(true);
        francois.getWorkers().get(1).setWorkerSpace(islandBoard.getSpace(0,1));
        francois.getWorkers().get(1).setLastSpaceOccupied(islandBoard.getSpace(0,0));
        islandBoard.getSpace(0,0).setLevel(2);

        synchronized (francois) {
            turnManager.checkWin();
        }

        serverSocket.close();
        socket.close();
    }

    /*
    Questo test verifica la base win condition
     */
    @Test
    void checkWinTest3() throws IOException, ParserConfigurationException, SAXException {
        ServerSocket serverSocket = new ServerSocket(60102);
        Socket socket = new Socket("localhost", 60102);
        HandlerHub handlerHub = new HandlerHub();

        IslandBoard islandBoard = new IslandBoard();
        Player ciro = new Player();
        Player francois = new Player();
        ciro.setIslandBoard(islandBoard);
        francois.setIslandBoard(islandBoard);

        Controller controller1 = new Controller();
        Controller controller2 = new Controller();
        ciro.setController(controller1);
        francois.setController(controller2);
        controller1.setPlayer(ciro);
        controller2.setPlayer(francois);
        handlerHub.addHandlerForSocket(socket, controller1);
        handlerHub.addHandlerForSocket(socket, controller2);

        List<Player> players = new ArrayList<>();
        players.add(ciro);
        players.add(francois);
        TurnManager turnManager = new TurnManager();
        turnManager.setPlayers(players);
        francois.setWinCondition(new JumpMoreLevelsWin(new BaseWinCondition()));
        ciro.setWinCondition(new BaseWinCondition());

        StateManager stateManager1 = new StateManager();
        StateManager stateManager2 = new StateManager();
        stateManager1.createBaseStates(ciro);
        stateManager2.createBaseStates(francois);
        ciro.setStateManager(stateManager1);
        francois.setStateManager(stateManager2);

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream file = classLoader.getResourceAsStream("table.txt");
        TableXML tableXML = new TableXML(file, ciro);
        HashMap<State, List<Line>> table = tableXML.readXML(ciro.getStateManager().getStateHashMap());
        ciro.getStateManager().setTable(table);
        file = classLoader.getResourceAsStream("table.txt");
        TableXML tableXML2 = new TableXML(file, francois);
        HashMap<State, List<Line>> table2 = tableXML2.readXML(francois.getStateManager().getStateHashMap());
        francois.getStateManager().setTable(table2);

        ciro.getStateManager().setCurrent_state(ciro.getStateManager().getState("ReadyForActionState"));
        francois.getStateManager().setCurrent_state(francois.getStateManager().getState("ReadyForActionState"));

        ciro.getWorkers().get(0).setMovedThisTurn(true);
        ciro.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(0,0));
        ciro.getWorkers().get(0).setLastSpaceOccupied(islandBoard.getSpace(0,1));
        islandBoard.getSpace(0,0).setLevel(3);
        islandBoard.getSpace(0,1).setLevel(2);

        synchronized (ciro) {
            turnManager.checkWin();
        }

        serverSocket.close();
        socket.close();
    }

    /*
    Questo test verifica che se è rimasto in partita un
    solo giocatore allora vince
     */
    @Test
    void checkWinTest4() throws IOException, ParserConfigurationException, SAXException {
        ServerSocket serverSocket = new ServerSocket(60102);
        Socket socket = new Socket("localhost", 60102);
        HandlerHub handlerHub = new HandlerHub();

        Player ciro = new Player();
        Player francois = new Player();
        List<Player> players = new ArrayList<>();
        players.add(ciro);
        players.add(francois);
        TurnManager turnManager = new TurnManager();
        turnManager.setPlayers(players);

        Controller controller1 = new Controller();
        Controller controller2 = new Controller();
        ciro.setController(controller1);
        francois.setController(controller2);
        controller1.setPlayer(ciro);
        controller2.setPlayer(francois);
        handlerHub.addHandlerForSocket(socket, controller1);
        handlerHub.addHandlerForSocket(socket, controller2);

        francois.setWinCondition(new BaseWinCondition());
        ciro.setInGame(false);

        StateManager stateManager1 = new StateManager();
        StateManager stateManager2 = new StateManager();
        stateManager1.createBaseStates(ciro);
        stateManager2.createBaseStates(francois);
        ciro.setStateManager(stateManager1);
        francois.setStateManager(stateManager2);

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream file = classLoader.getResourceAsStream("table.txt");
        TableXML tableXML = new TableXML(file, ciro);
        HashMap<State, List<Line>> table = tableXML.readXML(ciro.getStateManager().getStateHashMap());
        ciro.getStateManager().setTable(table);
        file = classLoader.getResourceAsStream("table.txt");
        TableXML tableXML2 = new TableXML(file, francois);
        HashMap<State, List<Line>> table2 = tableXML2.readXML(francois.getStateManager().getStateHashMap());
        francois.getStateManager().setTable(table2);

        ciro.getStateManager().setCurrent_state(ciro.getStateManager().getState("ReadyForActionState"));
        francois.getStateManager().setCurrent_state(francois.getStateManager().getState("ReadyForActionState"));

        synchronized (francois) {
            turnManager.checkWin();
        }

        serverSocket.close();
        socket.close();
    }

    /*
    Questo test verifica l'inserimento corretto dei colori
     */
    /*
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

        turnManager.setColor(ciro, "purple");
        turnManager.setColor(francois, "white");

        System.out.println("Il colore di ciro è " + ciro.getPlayerColor() + "questo" + Color.RESET);
        System.out.println("Il colore di francois è " + francois.getPlayerColor() + "questo" + Color.RESET);

        turnManager.setColor(ciro, "grey");
        System.out.println("Il colore di ciro è " + ciro.getPlayerColor() + "questo" + Color.RESET);
    }
     */

    @Test
    void setNextPlayerTest1() throws ParserConfigurationException, SAXException, IOException {
        Player player1 = new Player();
        Player player2 = new Player();
        StateManager stateManager1 = new StateManager();
        StateManager stateManager2 = new StateManager();
        Controller controller1 = new Controller();
        Controller controller2 = new Controller();
        EndGameState endGameState1 = new EndGameState(player1);
        EndGameState endGameState2 = new EndGameState(player2);
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();

        controller1.setHandlerHub(handlerHub);
        controller2.setHandlerHub(handlerHub);

        player1.setStateManager(stateManager1);
        player2.setStateManager(stateManager2);

        stateManager1.createBaseStates(player1);
        stateManager2.createBaseStates(player2);

        player1.setController(controller1);
        player2.setController(controller2);

        controller1.setPlayer(player1);
        controller2.setPlayer(player2);

        controller1.createFluxTable();
        controller2.createFluxTable();

        stateManager1.setTurnManager(turnManager);
        stateManager2.setTurnManager(turnManager);

        turnManager.getPlayers().add(player1);
        turnManager.getPlayers().add(player2);

        player1.setHasWon(true);

        player1.getStateManager().setCurrent_state(stateManager1.getStateHashMap().get("EndTurnState"));
        player2.getStateManager().setCurrent_state(stateManager2.getStateHashMap().get("EndTurnState"));

        turnManager.setNextPlayer(player1);
    }

    @Test
    void setNextPlayerTest2() {
        Player player1 = new Player();
        Player player2 = new Player();
        StateManager stateManager1 = new StateManager();
        StateManager stateManager2 = new StateManager();
        TurnManager turnManager = new TurnManager();
        EndTurnState endTurnState = new EndTurnState(player1);

        turnManager.getPlayers().add(player1);
        turnManager.getPlayers().add(player2);

        player1.setStateManager(stateManager1);
        player2.setStateManager(stateManager2);

        player1.getStateManager().setCurrent_state(endTurnState);
        player2.getStateManager().setCurrent_state(new ActionState(player2));

        turnManager.setNextPlayer(player1);

        assertEquals(player1.getStateManager().getCurrent_state(), endTurnState);

    }

    @Test
    void removeGodTest(){
        TurnManager turnManager = new TurnManager();
        List<String> nameList = new ArrayList<>();
        nameList.add("A");
        List<String> colorList = new ArrayList<>();
        colorList.add("red");
        turnManager.setAllowedColors(colorList);
        turnManager.setNotAllowedNames(nameList);
        turnManager.addName("B");
        String god1 = "Apollo";
        String god2 = "Prometheus";
        List<String> stringGods = new ArrayList<>();

        stringGods.add(god1);
        stringGods.add(god2);

        turnManager.setAvailableGods(stringGods);

        turnManager.removeGod("Apollo");

        assertTrue(turnManager.getAvailableGods().size()==1 && turnManager.getAllowedColors().size()==1 && turnManager.getNotAllowedNames().size()==2);
    }

    @Test
    void removeGodTest2(){
        TurnManager turnManager = new TurnManager();
        String god1 = "Apollo";
        String god2 = "Prometheus";

        turnManager.addGod(god1);
        turnManager.addGod(god2);

        turnManager.removeGod("Apollo");

        assertEquals(1, turnManager.getAvailableGods().size());
    }
}