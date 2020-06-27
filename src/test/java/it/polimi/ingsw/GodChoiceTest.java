package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GodChoiceTest {
    @Test
    public void godChoiceTest1() throws IOException {
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        StateManager stateManager = new StateManager();
        State endGame = new EndGameState(player);
        State godChoice = new GodChoice(player);

        stateManager.getStateHashMap().put(endGame.toString(), endGame);
        stateManager.getStateHashMap().put(godChoice.toString(), godChoice);

        List<Line> lineList = new ArrayList<>();
        Line line = new Line(endGame);
        line.setPriority(100);
        lineList.add(line);

        stateManager.getTable().put(godChoice, lineList);

        stateManager.setCurrent_state(godChoice);
        stateManager.setTurnManager(turnManager);

        turnManager.getPlayers().add(player);

        player.setStateManager(stateManager);
        player.setIslandBoard(islandBoard);

        player.setController(controller);
        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);

        List <String>godNameList=new ArrayList<String>();
        godNameList.add("name0");
        turnManager.setAvailableGods(godNameList);
        godChoice.onStateTransition();
        System.out.print(player.getPlayerGod());
    }

    @Test
    public void godChoiceTest2() throws IOException{
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        StateManager stateManager = new StateManager();
        State endGame = new EndGameState(player);
        State godChoice = new GodChoice(player);

        stateManager.getStateHashMap().put(endGame.toString(), endGame);
        stateManager.getStateHashMap().put(godChoice.toString(), godChoice);

        List<Line> lineList = new ArrayList<>();
        Line line = new Line(endGame);
        line.setPriority(100);
        lineList.add(line);

        stateManager.getTable().put(godChoice, lineList);

        stateManager.setCurrent_state(godChoice);
        stateManager.setTurnManager(turnManager);

        turnManager.getPlayers().add(player);

        player.setStateManager(stateManager);
        player.setIslandBoard(islandBoard);

        player.setController(controller);
        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);

        List <String>godNameList=new ArrayList<String>();
        godNameList.add("name0");
        godNameList.add("name1");
        turnManager.setAvailableGods(godNameList);
        godChoice.onStateTransition();
        Visitor visitor=new Visitor();
        visitor.setStringInput("name1");
        godChoice.onInput(visitor);
    }

    @Test
    public void godChoiceTest3() throws IOException{
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        StateManager stateManager = new StateManager();
        State endGame = new EndGameState(player);
        State godChoice = new GodChoice(player);

        stateManager.getStateHashMap().put(endGame.toString(), endGame);
        stateManager.getStateHashMap().put(godChoice.toString(), godChoice);

        List<Line> lineList = new ArrayList<>();
        Line line = new Line(endGame);
        line.setPriority(100);
        lineList.add(line);

        stateManager.getTable().put(godChoice, lineList);

        stateManager.setCurrent_state(godChoice);
        stateManager.setTurnManager(turnManager);

        turnManager.getPlayers().add(player);

        player.setStateManager(stateManager);
        player.setIslandBoard(islandBoard);

        player.setController(controller);
        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);

        List <String>godNameList=new ArrayList<String>();
        godNameList.add("name0");
        godNameList.add("name1");
        turnManager.setAvailableGods(godNameList);
        godChoice.onStateTransition();
        Visitor visitor=new Visitor();
        visitor.setStringInput("notAvailableName");
        godChoice.onInput(visitor);
    }

}