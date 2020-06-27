package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GodSetStateTest {
    @Test
    public void godSetStateTest1() throws IOException {
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        StateManager stateManager = new StateManager();
        State endGame = new EndGameState(player);
        State godSetState = new GodSetState(player);

        stateManager.getStateHashMap().put(endGame.toString(), endGame);
        stateManager.getStateHashMap().put(godSetState.toString(), godSetState);

        List<Line> lineList = new ArrayList<>();
        Line line = new Line(endGame);
        line.setPriority(100);
        lineList.add(line);

        stateManager.getTable().put(godSetState, lineList);

        stateManager.setCurrent_state(godSetState);
        stateManager.setTurnManager(turnManager);

        turnManager.getPlayers().add(player);

        player.setStateManager(stateManager);
        player.setIslandBoard(islandBoard);

        player.setController(controller);
        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);

        godSetState.onStateTransition();
    }

    @Test
    public void godSetTest2() throws IOException {
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        StateManager stateManager = new StateManager();
        State endGame = new EndGameState(player);
        State godSetState = new GodSetState(player);

        stateManager.getStateHashMap().put(endGame.toString(), endGame);
        stateManager.getStateHashMap().put(godSetState.toString(), godSetState);

        List<Line> lineList = new ArrayList<>();
        Line line = new Line(endGame);
        line.setPriority(100);
        lineList.add(line);

        stateManager.getTable().put(godSetState, lineList);

        stateManager.setCurrent_state(godSetState);
        stateManager.setTurnManager(turnManager);

        turnManager.getPlayers().add(player);

        player.setStateManager(stateManager);
        player.setIslandBoard(islandBoard);

        player.setController(controller);
        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);

        godSetState.onStateTransition();

        Visitor visitor=new Visitor();
        visitor.setStringInput("Athena");
        godSetState.onInput(visitor);
    }

    @Test
    public void godSetTest3() throws IOException {
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        StateManager stateManager = new StateManager();
        State endGame = new EndGameState(player);
        State godSetState = new GodSetState(player);

        stateManager.getStateHashMap().put(endGame.toString(), endGame);
        stateManager.getStateHashMap().put(godSetState.toString(), godSetState);

        List<Line> lineList = new ArrayList<>();
        Line line = new Line(endGame);
        line.setPriority(100);
        lineList.add(line);

        stateManager.getTable().put(godSetState, lineList);

        stateManager.setCurrent_state(godSetState);
        stateManager.setTurnManager(turnManager);

        turnManager.getPlayers().add(player);

        player.setStateManager(stateManager);
        player.setIslandBoard(islandBoard);

        player.setController(controller);
        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);

        godSetState.onStateTransition();

        Visitor visitor=new Visitor();
        visitor.setStringInput("notAllowedName");
        godSetState.onInput(visitor);
        godSetState.getPlayer();
        godSetState.getAllowedInputs();
    }

}