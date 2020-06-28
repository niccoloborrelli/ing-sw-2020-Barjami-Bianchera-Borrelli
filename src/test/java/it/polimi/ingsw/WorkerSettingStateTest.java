package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


class WorkerSettingStateTest {

    @Test
    void onInputTest1() throws IOException {
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        Player player = new Player();
        WorkerSpaceCouple workerSpaceCouple = new WorkerSpaceCouple();
        IslandBoard islandBoard = new IslandBoard();
        StateManager stateManager = new StateManager();
        State endGame = new EndGameState(player);
        WorkerSettingState workerSettingState = new WorkerSettingState(player);

        stateManager.getStateHashMap().put(endGame.toString(), endGame);
        stateManager.getStateHashMap().put(workerSettingState.toString(), workerSettingState);

        List<Line> lineList = new ArrayList<>();
        Line line = new Line(endGame);
        line.setPriority(100);
        lineList.add(line);

        stateManager.getTable().put(workerSettingState, lineList);
        stateManager.setCurrent_state(workerSettingState);
        stateManager.setTurnManager(turnManager);

        turnManager.getPlayers().add(player);

        player.setStateManager(stateManager);
        player.setIslandBoard(islandBoard);
        player.setController(controller);

        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);
        controller.getVisitor().setWorkerSpaceCouple(workerSpaceCouple);

        workerSpaceCouple.setWorker(player.getWorkers().get(0));
        workerSpaceCouple.setSpace(islandBoard.getSpace(0,0));

        workerSettingState.onStateTransition();
        workerSettingState.onInput(controller.getVisitor());

        assertEquals(player.getWorkers().get(0).getWorkerSpace(), islandBoard.getSpace(0, 0));
    }

    @Test
    void onInputTest2() throws IOException {
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        Player player = new Player();
        WorkerSpaceCouple workerSpaceCouple = new WorkerSpaceCouple();
        IslandBoard islandBoard = new IslandBoard();
        StateManager stateManager = new StateManager();
        State endGame = new EndGameState(player);
        WorkerSettingState workerSettingState = new WorkerSettingState(player);

        stateManager.getStateHashMap().put(endGame.toString(), endGame);
        stateManager.getStateHashMap().put(workerSettingState.toString(), workerSettingState);

        List<Line> lineList = new ArrayList<>();
        Line line = new Line(endGame);
        line.setPriority(100);
        lineList.add(line);

        stateManager.getTable().put(workerSettingState, lineList);
        stateManager.setCurrent_state(workerSettingState);
        stateManager.setTurnManager(turnManager);

        turnManager.getPlayers().add(player);

        player.setStateManager(stateManager);
        player.setIslandBoard(islandBoard);
        player.setController(controller);

        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);
        controller.getVisitor().setWorkerSpaceCouple(workerSpaceCouple);

        workerSpaceCouple.setWorker(player.getWorkers().get(0));
        workerSpaceCouple.setSpace(islandBoard.getSpace(0,0));

        player.getWorkers().get(1).setWorkerSpace(islandBoard.getSpace(0,1));
        workerSettingState.onStateTransition();
        workerSettingState.onInput(controller.getVisitor());

        assertEquals(player.getWorkers().get(0).getWorkerSpace(), islandBoard.getSpace(0, 0));
    }

    @Test
    void onInputTest3() throws IOException {
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        Player player = new Player();
        WorkerSpaceCouple workerSpaceCouple = new WorkerSpaceCouple();
        IslandBoard islandBoard = new IslandBoard();
        StateManager stateManager = new StateManager();
        State endGame = new EndGameState(player);
        WorkerSettingState workerSettingState = new WorkerSettingState(player);

        stateManager.getStateHashMap().put(endGame.toString(), endGame);
        stateManager.getStateHashMap().put(workerSettingState.toString(), workerSettingState);

        List<Line> lineList = new ArrayList<>();
        Line line = new Line(endGame);
        line.setPriority(100);
        lineList.add(line);

        stateManager.getTable().put(workerSettingState, lineList);
        stateManager.setCurrent_state(workerSettingState);
        stateManager.setTurnManager(turnManager);

        turnManager.getPlayers().add(player);

        player.setStateManager(stateManager);
        player.setIslandBoard(islandBoard);
        player.setController(controller);

        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);
        controller.getVisitor().setWorkerSpaceCouple(workerSpaceCouple);

        workerSpaceCouple.setWorker(player.getWorkers().get(0));
        workerSpaceCouple.setSpace(islandBoard.getSpace(0, 0));

        player.getWorkers().get(1).setWorkerSpace(islandBoard.getSpace(0, 0));
        islandBoard.getSpace(0,0).setOccupator(player.getWorkers().get(1));
        workerSettingState.onStateTransition();
        workerSettingState.onInput(controller.getVisitor());

        assertNull(player.getWorkers().get(0).getWorkerSpace());
    }
}