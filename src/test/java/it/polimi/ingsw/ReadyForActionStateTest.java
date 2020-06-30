/*package it.polimi.ingsw;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

class ReadyForActionStateTest {

    @Test
    void onInput() throws IOException {
        ServerSocket serverSocket = new ServerSocket(60103);
        Socket socket = new Socket("localhost", 60103);
        HandlerHub handlerHub = new HandlerHub();
        Player player1 = new Player();
        player1.setPlayerName("pino");
        player1.setPlayerColor("red");
        player1.setPlayerGod("Pan");
        Controller controller1 = new Controller();
        Player player2 = new Player();
        player2.setPlayerName("gigi");
        player2.setPlayerColor("white");
        player2.setPlayerGod("Chronus");
        Controller controller2 = new Controller();
        handlerHub.addHandlerForSocket(socket, controller1);
        handlerHub.addHandlerForSocket(socket, controller2);
        player1.setController(controller1);
        player2.setController(controller2);
        controller1.setPlayer(player1);
        controller2.setPlayer(player2);

        IslandBoard islandBoard = new IslandBoard();
        player1.setIslandBoard(islandBoard);
        player2.setIslandBoard(islandBoard);

        StateManager stateManager = new StateManager();
        stateManager.createBaseStates(player1);
        //stateManager.createBaseStates(player2);
        player1.setStateManager(stateManager);
        player2.setStateManager(stateManager);

        TurnManager turnManager = new TurnManager();
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        turnManager.setPlayers(players);
        player1.getStateManager().setTurnManager(turnManager);
        player2.getStateManager().setTurnManager(turnManager);

        Worker worker1 = player1.getWorkers().get(0);
        Worker worker2 = player1.getWorkers().get(1);
        worker2.setWorkerPlayer(player1);
        worker1.setWorkerSpace(player1.getIslandBoard().getSpace(3,2));
        player1.getIslandBoard().getSpace(3,2).setOccupator(worker1);
        worker2.setWorkerSpace(player1.getIslandBoard().getSpace(2,2));
        player1.getIslandBoard().getSpace(2,2).setOccupator(worker2);

        Visitor visitor = new Visitor();
        WorkerSpaceCouple workerSpaceCouple = new WorkerSpaceCouple(worker1, player1.getIslandBoard().getSpace(3,2));
        visitor.setWorkerSpaceCouple(workerSpaceCouple);
        State ready = player1.getStateManager().getState("ReadyForActionState");
        ready.onStateTransition();
        ready.onInput(visitor);
    }

    @Test
    void onStateTransitionTest1() throws IOException {
        ServerSocket serverSocket = new ServerSocket(60104);
        Socket socket = new Socket("localhost", 60104);
        HandlerHub handlerHub = new HandlerHub();
        Player player1 = new Player();
        player1.setPlayerName("pino");
        player1.setPlayerColor("red");
        player1.setPlayerGod("Pan");
        Controller controller1 = new Controller();
        Player player2 = new Player();
        player2.setPlayerName("gigi");
        player2.setPlayerColor("white");
        player2.setPlayerGod("Chronus");
        Controller controller2 = new Controller();
        handlerHub.addHandlerForSocket(socket, controller1);
        handlerHub.addHandlerForSocket(socket, controller2);
        player1.setController(controller1);
        player2.setController(controller2);
        controller1.setPlayer(player1);
        controller2.setPlayer(player2);

        StateManager stateManager = new StateManager();
        stateManager.createBaseStates(player1);
        //stateManager.createBaseStates(player2);
        player1.setStateManager(stateManager);
        player2.setStateManager(stateManager);

        TurnManager turnManager = new TurnManager();
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        turnManager.setPlayers(players);
        player1.getStateManager().setTurnManager(turnManager);
        player2.getStateManager().setTurnManager(turnManager);

        IslandBoard islandBoard = new IslandBoard();
        player1.setIslandBoard(islandBoard);

        Worker worker1 = player1.getWorkers().get(0);
        Worker worker2 = player1.getWorkers().get(1);
        worker1.setWorkerSpace(islandBoard.getSpace(3,2));
        islandBoard.getSpace(3,2).setOccupator(worker1);
        worker2.setWorkerSpace(islandBoard.getSpace(2,2));
        islandBoard.getSpace(2,2).setOccupator(worker2);

        State ready = player1.getStateManager().getState("ReadyForActionState");
        ready.onStateTransition();
    }

    @Test
    void onStateTransitionTest2() throws IOException, NoSuchMethodException, ClassNotFoundException, ParserConfigurationException, SAXException {
        ServerSocket serverSocket = new ServerSocket(60105);
        Socket socket = new Socket("localhost", 60105);
        HandlerHub handlerHub = new HandlerHub();
        Player player1 = new Player();
        player1.setPlayerName("pino");
        player1.setPlayerColor("red");
        player1.setPlayerGod("Pan");
        Controller controller1 = new Controller();
        Player player2 = new Player();
        player2.setPlayerName("gigi");
        player2.setPlayerColor("white");
        player2.setPlayerGod("Chronus");
        Controller controller2 = new Controller();
        handlerHub.addHandlerForSocket(socket, controller1);
        handlerHub.addHandlerForSocket(socket, controller2);
        player1.setController(controller1);
        player2.setController(controller2);
        controller1.setPlayer(player1);
        controller2.setPlayer(player2);

        StateManager stateManager1 = new StateManager();
        StateManager stateManager2 = new StateManager();
        stateManager1.createBaseStates(player1);
        stateManager2.createBaseStates(player2);
        player1.setStateManager(stateManager1);
        player2.setStateManager(stateManager2);

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        TableXML tableXML = new TableXML(new File(Objects.requireNonNull(classLoader.getResource("table.txt")).getFile()), player1);
        HashMap<State, List<Line>> table = tableXML.readXML(player1.getStateManager().getStateHashMap());
        player1.getStateManager().setTable(table);
        TableXML tableXML2 = new TableXML(new File(Objects.requireNonNull(classLoader.getResource("table.txt")).getFile()), player2);
        HashMap<State, List<Line>> table2 = tableXML2.readXML(player2.getStateManager().getStateHashMap());
        player2.getStateManager().setTable(table2);

        TurnManager turnManager = new TurnManager();
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        turnManager.setPlayers(players);
        player1.getStateManager().setTurnManager(turnManager);
        player2.getStateManager().setTurnManager(turnManager);

        IslandBoard islandBoard = new IslandBoard();
        player1.setIslandBoard(islandBoard);

        Worker worker1 = player1.getWorkers().get(0);
        Worker worker2 = player1.getWorkers().get(1);
        worker1.setWorkerSpace(islandBoard.getSpace(0,0));
        islandBoard.getSpace(0,0).setOccupator(worker1);
        worker2.setWorkerSpace(islandBoard.getSpace(0,1));
        islandBoard.getSpace(0,1).setOccupator(worker2);
        islandBoard.getSpace(1,0).setHasDome(true);
        islandBoard.getSpace(1,1).setHasDome(true);
        islandBoard.getSpace(1,2).setHasDome(true);
        islandBoard.getSpace(0,2).setHasDome(true);

        State ready = player1.getStateManager().getState("ReadyForActionState");
        player1.getStateManager().setCurrent_state(ready);
        player2.getStateManager().setCurrent_state(player2.getStateManager().getState("ColorSettingState"));
        ready.onStateTransition();
    }

}*/