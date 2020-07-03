package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ActionStateTest {

    @Test
    void onStateTransitionTest1() throws IOException {
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        Player player = new Player();
        WorkerSpaceCouple workerSpaceCouple = new WorkerSpaceCouple();
        IslandBoard islandBoard = new IslandBoard();
        StateManager stateManager = new StateManager();
        State endGame = new EndGameState(player);
        ActionState actionState = new ActionState(player);

        stateManager.getStateHashMap().put(endGame.toString(), endGame);
        stateManager.getStateHashMap().put(actionState.toString(), actionState);

        List<Line> lineList = new ArrayList<>();
        Line line = new Line(endGame);
        line.setPriority(100);
        lineList.add(line);

        stateManager.getTable().put(actionState, lineList);

        stateManager.setCurrent_state(actionState);
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
        workerSpaceCouple.setSpace(islandBoard.getSpace(2,1));
        player.setLastReceivedInput(workerSpaceCouple);

        int oldSize = player.getActionsToPerform().size();

        synchronized (player) {
            actionState.onStateTransition();
        }
        islandBoard.getSpace(0,0).toString();

        assertTrue(player.getActionsToPerform().size()==oldSize-1 && player.getWorkers().get(0).getWorkerSpace().equals(islandBoard.getSpace(2,1)) &&
                islandBoard.getSpace(0,0).getOccupator()==null && actionState.getActingWorker().equals(player.getWorkers().get(0)) &&
                actionState.getSpaceToAct().equals(islandBoard.getSpace(2,1)) && actionState.getStartingSpace().equals(islandBoard.getSpace(0,0)) &&
                actionState.getAction().equals("move"));


    }

    @Test
    void onStateTransitionTest2() throws IOException {
            Controller controller = new Controller();
            HandlerHub handlerHub = new HandlerHub();
            TurnManager turnManager = new TurnManager();
            Player player = new Player();
            WorkerSpaceCouple workerSpaceCouple = new WorkerSpaceCouple();
            IslandBoard islandBoard = new IslandBoard();
            StateManager stateManager = new StateManager();
            State endGame = new EndGameState(player);
            State actionState = new ActionState(player);

            stateManager.getStateHashMap().put(endGame.toString(), endGame);
            stateManager.getStateHashMap().put(actionState.toString(), actionState);

            List<Line> lineList = new ArrayList<>();
            Line line = new Line(endGame);
            line.setPriority(100);
            lineList.add(line);

            stateManager.getTable().put(actionState, lineList);

            stateManager.setCurrent_state(actionState);
            stateManager.setTurnManager(turnManager);

            turnManager.getPlayers().add(player);


            player.setStateManager(stateManager);
            player.setIslandBoard(islandBoard);
            player.getActionsToPerform().remove("move");
            player.setController(controller);
            player.getWorkers().get(0).setCantMove(false);


            controller.setHandlerHub(handlerHub);
            controller.setPlayer(player);

            player.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(0,0));
            islandBoard.getSpace(0,0).setOccupator(player.getWorkers().get(0));
            workerSpaceCouple.setWorker(player.getWorkers().get(0));
            workerSpaceCouple.setSpace(islandBoard.getSpace(2,1));
            player.setLastReceivedInput(workerSpaceCouple);

            int oldSize = player.getActionsToPerform().size();

        synchronized (player) {
            actionState.onStateTransition();
        }

            assertTrue(player.getActionsToPerform().size()==oldSize-1 && player.getWorkers().get(0).getWorkerSpace().equals(islandBoard.getSpace(0,0)) &&
                    islandBoard.getSpace(2,1).getOccupator()==null && islandBoard.getSpace(2,1).getLevel()==1);
    }

    @Test
    void onStateTransitionTest3() throws IOException {
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        Player player = new Player();
        WorkerSpaceCouple workerSpaceCouple = new WorkerSpaceCouple();
        IslandBoard islandBoard = new IslandBoard();
        StateManager stateManager = new StateManager();
        State endGame = new EndGameState(player);
        State actionState = new ActionState(player);

        stateManager.getStateHashMap().put(endGame.toString(), endGame);
        stateManager.getStateHashMap().put(actionState.toString(), actionState);

        List<Line> lineList = new ArrayList<>();
        Line line = new Line(endGame);
        line.setPriority(100);
        lineList.add(line);

        stateManager.getTable().put(actionState, lineList);

        stateManager.setCurrent_state(actionState);
        stateManager.setTurnManager(turnManager);

        turnManager.getPlayers().add(player);


        player.setStateManager(stateManager);
        player.setIslandBoard(islandBoard);
        player.getActionsToPerform().remove("move");
        player.setController(controller);
        player.getWorkers().get(0).setCantMove(false);


        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);

        player.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(0, 0));
        islandBoard.getSpace(0, 0).setOccupator(player.getWorkers().get(0));
        workerSpaceCouple.setWorker(player.getWorkers().get(0));
        workerSpaceCouple.setSpace(islandBoard.getSpace(2, 1));
        islandBoard.getSpace(2, 1).setLevel(3);
        player.setLastReceivedInput(workerSpaceCouple);

        synchronized (player) {
            actionState.onStateTransition();
        }

        assertTrue(islandBoard.getSpace(2, 1).HasDome());

    }

    @Test
    void onStateTransitionTest4() throws IOException {
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        Player player = new Player();
        WorkerSpaceCouple workerSpaceCouple = new WorkerSpaceCouple();
        IslandBoard islandBoard = new IslandBoard();
        StateManager stateManager = new StateManager();
        State endGame = new EndGameState(player);
        State actionState = new ActionState(player);

        stateManager.getStateHashMap().put(endGame.toString(), endGame);
        stateManager.getStateHashMap().put(actionState.toString(), actionState);

        List<Line> lineList = new ArrayList<>();
        Line line = new Line(endGame);
        line.setPriority(100);
        lineList.add(line);

        stateManager.getTable().put(actionState, lineList);

        stateManager.setCurrent_state(actionState);
        stateManager.setTurnManager(turnManager);

        turnManager.getPlayers().add(player);


        player.setStateManager(stateManager);
        player.setIslandBoard(islandBoard);
        player.getActionsToPerform().remove("move");
        player.setController(controller);
        player.getWorkers().get(0).setCantMove(false);


        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);

        player.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(0, 0));
        islandBoard.getSpace(0, 0).setOccupator(player.getWorkers().get(0));
        workerSpaceCouple.setWorker(player.getWorkers().get(0));
        workerSpaceCouple.setSpace(islandBoard.getSpace(2, 1));
        player.getWorkers().get(0).setMustBuildDome(true);
        player.setLastReceivedInput(workerSpaceCouple);

        synchronized (player) {
            actionState.onStateTransition();
        }

        assertTrue(islandBoard.getSpace(2, 1).HasDome());
    }

    @Test
    void onStateTransitionTest5() throws IOException {
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        Player player = new Player();
        WorkerSpaceCouple workerSpaceCouple = new WorkerSpaceCouple();
        IslandBoard islandBoard = new IslandBoard();
        StateManager stateManager = new StateManager();
        State endGame = new EndGameState(player);
        ActionState actionState = new ActionState(player);

        stateManager.getStateHashMap().put(endGame.toString(), endGame);
        stateManager.getStateHashMap().put(actionState.toString(), actionState);

        List<Line> lineList = new ArrayList<>();
        Line line = new Line(endGame);
        line.setPriority(100);
        lineList.add(line);

        stateManager.getTable().put(actionState, lineList);

        stateManager.setCurrent_state(actionState);
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
        islandBoard.getSpace(0,0).setLevel(2);
        islandBoard.getSpace(2,1).setLevel(3);
        workerSpaceCouple.setWorker(player.getWorkers().get(0));
        workerSpaceCouple.setSpace(islandBoard.getSpace(2,1));
        player.setLastReceivedInput(workerSpaceCouple);

        synchronized (player) {
            actionState.onStateTransition();
        }

        assertTrue(player.isHasWon());

    }

}