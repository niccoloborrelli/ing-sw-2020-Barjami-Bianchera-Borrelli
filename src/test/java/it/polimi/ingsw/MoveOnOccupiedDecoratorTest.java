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
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        Player player = new Player();
        WorkerSpaceCouple workerSpaceCouple = new WorkerSpaceCouple();
        IslandBoard islandBoard = new IslandBoard();
        StateManager stateManager = new StateManager();
        State endGame = new EndGameState(player);
        ActionState actionState = new ActionState(player);
        AbstractActionState decorator=new MoveOnOccupiedDecorator(actionState,"push");

        stateManager.getStateHashMap().put(endGame.toString(), endGame);
        stateManager.getStateHashMap().put(decorator.toString(), decorator);

        List<Line> lineList = new ArrayList<>();
        Line line = new Line(endGame);
        line.setPriority(100);
        lineList.add(line);

        stateManager.getTable().put(decorator, lineList);

        stateManager.setCurrent_state(decorator);
        stateManager.setTurnManager(turnManager);

        turnManager.getPlayers().add(player);

        player.setStateManager(stateManager);
        player.setIslandBoard(islandBoard);

        player.setController(controller);
        player.getWorkers().get(0).setCantMove(false);

        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);

        player.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(0,0));
        islandBoard.getSpace(0,0).setOccupator(player.getWorkers().get(0));
        workerSpaceCouple.setWorker(player.getWorkers().get(0));
        workerSpaceCouple.setSpace(islandBoard.getSpace(1,1));
        player.setLastReceivedInput(workerSpaceCouple);

        decorator.onStateTransition();

    }

    @Test
    public void test2() throws IOException {
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        Player player = new Player();
        WorkerSpaceCouple workerSpaceCouple = new WorkerSpaceCouple();
        IslandBoard islandBoard = new IslandBoard();
        StateManager stateManager = new StateManager();
        State endGame = new EndGameState(player);
        AbstractActionState actionState = new ActionState(player);
        MoveOnOccupiedDecorator decorator=new MoveOnOccupiedDecorator(actionState,"push");

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


        decorator.onStateTransition();

        //assertTrue(player.getWorkers().get(0).getWorkerSpace()==islandBoard.getSpace(1,1)&&islandBoard.getSpace(2,2).getOccupator()==tempWorker);

    }

    @Test
    public void test3() throws IOException {
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        Player player = new Player();
        WorkerSpaceCouple workerSpaceCouple = new WorkerSpaceCouple();
        IslandBoard islandBoard = new IslandBoard();
        StateManager stateManager = new StateManager();
        State endGame = new EndGameState(player);
        AbstractActionState actionState = new ActionState(player);
        MoveOnOccupiedDecorator decorator=new MoveOnOccupiedDecorator(actionState,"swap");

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


        decorator.onStateTransition();

        //assertTrue(player.getWorkers().get(0).getWorkerSpace()==islandBoard.getSpace(1,1)&&islandBoard.getSpace(2,2).getOccupator()==tempWorker);

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