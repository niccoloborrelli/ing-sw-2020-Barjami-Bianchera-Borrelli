package it.polimi.ingsw;

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

import static org.junit.jupiter.api.Assertions.*;

class PowerActivationStateTest {

    @Test
    void onInput() throws ClassNotFoundException, IOException, NoSuchMethodException, ParserConfigurationException, SAXException {
        ServerSocket serverSocket = new ServerSocket(60101);
        Socket socket = new Socket("localhost", 60101);
        HandlerHub handlerHub = new HandlerHub();
        Player player1 = new Player();
        Controller controller1 = new Controller();
        Player player2 = new Player();
        Controller controller2 = new Controller();
        handlerHub.addHandlerForSocket(socket, controller1);
        handlerHub.addHandlerForSocket(socket, controller2);
        player1.setController(controller1);
        player2.setController(controller2);
        controller1.setPlayer(player1);
        controller2.setPlayer(player2);

        StateManager stateManager = new StateManager();
        stateManager.createBaseStates(player1);
        stateManager.createBaseStates(player2);
        player1.setStateManager(stateManager);
        player2.setStateManager(stateManager);

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

        PowerActivationState activationState = new PowerActivationState(player1, new AdditionalBuildFlow("moveUp"));
        stateManager.addNewState(activationState);
        State readyForActionState = stateManager.getState("ReadyForActionState");
        State endTurnState = stateManager.getState("endTurn");
        Class cl = Class.forName("it.polimi.ingsw.Player");
        Method m = cl.getDeclaredMethod("isInGame");
        Method m1 = cl.getDeclaredMethod("isWorkerPlaced");
        stateManager.addNewFinishSpace(endTurnState,activationState,m,true,2);
        stateManager.addNewFinishSpace(activationState,readyForActionState,m,true,3);
        stateManager.addNewConditions(endTurnState, activationState, m1, true, 2);
        stateManager.sortAllTable();
        IslandBoard islandBoard = new IslandBoard();
        player1.setIslandBoard(islandBoard);

        Worker worker1 = player1.getWorkers().get(0);
        Worker worker2 = player1.getWorkers().get(1);
        worker1.setWorkerSpace(islandBoard.getSpace(3,2));
        islandBoard.getSpace(3,2).setOccupator(worker1);
        worker2.setWorkerSpace(islandBoard.getSpace(2,2));
        islandBoard.getSpace(2,2).setOccupator(worker2);

        player1.getStateManager().setCurrent_state(activationState);
        player2.getStateManager().setCurrent_state(player2.getStateManager().getState("ColorSettingState"));

        Visitor visitor = new Visitor();
        visitor.setIntInput(1);
        activationState.onInput(visitor);
    }

    @Test
    void onStateTransitionTest1() throws IOException, NoSuchMethodException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(60105);
        Socket socket = new Socket("localhost", 60105);
        HandlerHub handlerHub = new HandlerHub();
        Player player1 = new Player();
        Controller controller1 = new Controller();
        Player player2 = new Player();
        Controller controller2 = new Controller();
        handlerHub.addHandlerForSocket(socket, controller1);
        handlerHub.addHandlerForSocket(socket, controller2);
        player1.setController(controller1);
        player2.setController(controller2);
        controller1.setPlayer(player1);
        controller2.setPlayer(player2);

        StateManager stateManager = new StateManager();
        stateManager.createBaseStates(player1);
        stateManager.createBaseStates(player2);
        player1.setStateManager(stateManager);
        player2.setStateManager(stateManager);

        TurnManager turnManager = new TurnManager();
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        turnManager.setPlayers(players);
        player1.getStateManager().setTurnManager(turnManager);
        player2.getStateManager().setTurnManager(turnManager);

        PowerActivationState activationState = new PowerActivationState(player1, new AdditionalBuildFlow("moveUp"));
        stateManager.addNewState(activationState);
        State readyForActionState = stateManager.getState("ReadyForActionState");
        State endTurnState = stateManager.getState("endTurn");
        Class cl = Class.forName("it.polimi.ingsw.Player");
        Method m = cl.getDeclaredMethod("isInGame");
        Method m1 = cl.getDeclaredMethod("isWorkerPlaced");
        stateManager.addNewFinishSpace(endTurnState,activationState,m,true,2);
        stateManager.addNewFinishSpace(activationState,readyForActionState,m,true,3);
        stateManager.addNewConditions(endTurnState, activationState, m1, true, 2);
        stateManager.sortAllTable();
        IslandBoard islandBoard = new IslandBoard();
        player1.setIslandBoard(islandBoard);

        Worker worker1 = player1.getWorkers().get(0);
        Worker worker2 = player1.getWorkers().get(1);
        worker1.setWorkerSpace(islandBoard.getSpace(3,2));
        islandBoard.getSpace(3,2).setOccupator(worker1);
        worker2.setWorkerSpace(islandBoard.getSpace(2,2));
        islandBoard.getSpace(2,2).setOccupator(worker2);

        activationState.onStateTransition();
    }

    @Test
    void onStateTransitionTest2() throws IOException, NoSuchMethodException, ClassNotFoundException, ParserConfigurationException, SAXException {
        ServerSocket serverSocket = new ServerSocket(60102);
        Socket socket = new Socket("localhost", 60102);
        HandlerHub handlerHub = new HandlerHub();
        Player player1 = new Player();
        Controller controller1 = new Controller();
        Player player2 = new Player();
        Controller controller2 = new Controller();
        handlerHub.addHandlerForSocket(socket, controller1);
        handlerHub.addHandlerForSocket(socket, controller2);
        player1.setController(controller1);
        player2.setController(controller2);
        controller1.setPlayer(player1);
        controller2.setPlayer(player2);

        StateManager stateManager = new StateManager();
        stateManager.createBaseStates(player1);
        stateManager.createBaseStates(player2);
        player1.setStateManager(stateManager);
        player2.setStateManager(stateManager);

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

        PowerActivationState activationState = new PowerActivationState(player1, new AdditionalBuildFlow("moveUp"));
        stateManager.addNewState(activationState);
        State readyForActionState=stateManager.getState("ReadyForActionState");
        State endTurnState=stateManager.getState("endTurn");
        Class cl = Class.forName("it.polimi.ingsw.Player");
        Method m = cl.getDeclaredMethod("isInGame");
        Method m1 = cl.getDeclaredMethod("isWorkerPlaced");
        stateManager.addNewFinishSpace(endTurnState,activationState,m,true,2);
        stateManager.addNewFinishSpace(activationState,readyForActionState,m,true,3);
        stateManager.addNewConditions(endTurnState, activationState, m1, true, 2);
        stateManager.sortAllTable();
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

        player1.getStateManager().setCurrent_state(activationState);
        player2.getStateManager().setCurrent_state(player2.getStateManager().getState("ColorSettingState"));
        activationState.onStateTransition();
    }
}