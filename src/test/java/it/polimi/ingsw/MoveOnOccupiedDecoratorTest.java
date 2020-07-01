package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoveOnOccupiedDecoratorTest {

    @Test
    public void test1() throws IOException {

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


        State endGame = new EndGameState(player1);
        ActionState actionState = new ActionState(player1);
        MoveOnOccupiedDecorator decorator=new MoveOnOccupiedDecorator(actionState,"push");

        stateManager.getStateHashMap().put(endGame.toString(), endGame);
        stateManager.getStateHashMap().put(decorator.toString(), decorator);

        List<Line> lineList = new ArrayList<>();
        Line line = new Line(endGame);
        line.setPriority(100);
        lineList.add(line);

        stateManager.getTable().put(decorator, lineList);

        stateManager.setCurrent_state(decorator);
        stateManager.setTurnManager(turnManager);

        turnManager.getPlayers().add(player1);

        player1.setStateManager(stateManager);
        player1.setIslandBoard(islandBoard);

        player1.setController(controller1);
        player1.getWorkers().get(0).setCantMove(false);
        player2.setStateManager(stateManager);
        player2.setController(controller1);
        WorkerSpaceCouple workerSpaceCouple=new WorkerSpaceCouple();

        player1.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(0,0));
        islandBoard.getSpace(0,0).setOccupator(player1.getWorkers().get(0));
        workerSpaceCouple.setWorker(player1.getWorkers().get(0));
        workerSpaceCouple.setSpace(islandBoard.getSpace(1,1));
        player1.setLastReceivedInput(workerSpaceCouple);

        decorator.onStateTransition();
        socket.close();
        serverSocket.close();
    }

    @Test
    public void test2() throws IOException {

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


        State endGame = new EndGameState(player1);
        ActionState actionState = new ActionState(player1);
        MoveOnOccupiedDecorator decorator=new MoveOnOccupiedDecorator(actionState,"push");

        stateManager.getStateHashMap().put(endGame.toString(), endGame);
        stateManager.getStateHashMap().put(decorator.toString(), decorator);

        List<Line> lineList = new ArrayList<>();
        Line line = new Line(endGame);
        line.setPriority(100);
        lineList.add(line);

        stateManager.getTable().put(decorator, lineList);

        stateManager.setCurrent_state(decorator);
        stateManager.setTurnManager(turnManager);

        turnManager.getPlayers().add(player1);

        player1.setStateManager(stateManager);
        player1.setIslandBoard(islandBoard);

        player1.setController(controller1);
        player1.getWorkers().get(0).setCantMove(false);
        player2.setStateManager(stateManager);
        player2.setController(controller1);
        WorkerSpaceCouple workerSpaceCouple=new WorkerSpaceCouple();

        player1.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(0,0));
        islandBoard.getSpace(0,0).setOccupator(player1.getWorkers().get(0));
        workerSpaceCouple.setWorker(player1.getWorkers().get(0));
        workerSpaceCouple.setSpace(islandBoard.getSpace(1,1));
        player1.setLastReceivedInput(workerSpaceCouple);
        Worker tempWorker=new Worker();
        tempWorker.setWorkerSpace(islandBoard.getSpace(1,1));
        islandBoard.getSpace(1,1).setOccupator(tempWorker);


        decorator.onStateTransition();

        //assertTrue(player.getWorkers().get(0).getWorkerSpace()==islandBoard.getSpace(1,1)&&islandBoard.getSpace(2,2).getOccupator()==tempWorker);
        socket.close();
        serverSocket.close();
    }

    @Test
    public void test3() throws IOException {

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


        State endGame = new EndGameState(player1);
        ActionState actionState = new ActionState(player1);
        MoveOnOccupiedDecorator decorator=new MoveOnOccupiedDecorator(actionState,"swap");

        stateManager.getStateHashMap().put(endGame.toString(), endGame);
        stateManager.getStateHashMap().put(decorator.toString(), decorator);

        List<Line> lineList = new ArrayList<>();
        Line line = new Line(endGame);
        line.setPriority(100);
        lineList.add(line);

        stateManager.getTable().put(decorator, lineList);

        stateManager.setCurrent_state(decorator);
        stateManager.setTurnManager(turnManager);

        turnManager.getPlayers().add(player1);

        player1.setStateManager(stateManager);
        player1.setIslandBoard(islandBoard);

        player1.setController(controller1);
        player1.getWorkers().get(0).setCantMove(false);
        player2.setStateManager(stateManager);
        player2.setController(controller1);
        WorkerSpaceCouple workerSpaceCouple=new WorkerSpaceCouple();

        player1.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(0,0));
        islandBoard.getSpace(0,0).setOccupator(player1.getWorkers().get(0));
        workerSpaceCouple.setWorker(player1.getWorkers().get(0));
        workerSpaceCouple.setSpace(islandBoard.getSpace(1,1));
        player1.setLastReceivedInput(workerSpaceCouple);
        Worker tempWorker=new Worker();
        tempWorker.setWorkerSpace(islandBoard.getSpace(1,1));
        islandBoard.getSpace(1,1).setOccupator(tempWorker);


        decorator.onStateTransition();

        //assertTrue(player.getWorkers().get(0).getWorkerSpace()==islandBoard.getSpace(1,1)&&islandBoard.getSpace(2,2).getOccupator()==tempWorker);
        socket.close();
        serverSocket.close();
    }

    @Test
    public void test4() throws IOException {
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        Player player = new Player();
        WorkerSpaceCouple workerSpaceCouple = new WorkerSpaceCouple();
        IslandBoard islandBoard = new IslandBoard();
        StateManager stateManager = new StateManager();
        State endGame = new EndGameState(player);
        AbstractActionState actionState = new ActionState(player);
        MoveOnOccupiedDecorator decorator=new MoveOnOccupiedDecorator(actionState,"randomword");

        stateManager.getStateHashMap().put(endGame.toString(), endGame);
        stateManager.getStateHashMap().put(decorator.toString(), decorator);


        List<Line> lineList = new ArrayList<>();
        Line line = new Line(endGame);
        line.setPriority(100);
        lineList.add(line);

        stateManager.getTable().put(decorator, lineList);

        stateManager.setTurnManager(turnManager);

        turnManager.getPlayers().add(player);

        player.setStateManager(stateManager);
        player.setIslandBoard(islandBoard);
        player.setController(controller);
        player.getWorkers().get(0).setCantMove(false);


        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);

        stateManager.setCurrent_state(decorator);

        player.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(0,0));
        islandBoard.getSpace(0,0).setOccupator(player.getWorkers().get(0));
        workerSpaceCouple.setWorker(player.getWorkers().get(0));
        workerSpaceCouple.setSpace(islandBoard.getSpace(1,1));
        player.setLastReceivedInput(workerSpaceCouple);
        Worker tempWorker=new Worker();
        tempWorker.setWorkerSpace(islandBoard.getSpace(1,1));
        islandBoard.getSpace(1,1).setOccupator(tempWorker);

        decorator.onInput(new Visitor());
        //assertTrue(player.getWorkers().get(0).getWorkerSpace()==islandBoard.getSpace(1,1)&&islandBoard.getSpace(2,2).getOccupator()==tempWorker);

    }

}