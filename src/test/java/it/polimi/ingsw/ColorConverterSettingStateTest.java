package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ColorConverterSettingStateTest {

    @Test
    void onInputTest1() throws IOException {
        Player player = new Player();
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        StateManager stateManager = new StateManager();
        ColorSettingState colorSettingState = new ColorSettingState(player);

        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);
        player.setController(controller);
        player.setStateManager(stateManager);
        stateManager.setTurnManager(turnManager);
        controller.getVisitor().setStringInput("aaaaa");

        colorSettingState.onInput(controller.getVisitor());

        System.out.println(player.getPlayerColor());
    }

    @Test
    void onInputTest2() throws IOException {
        Player player = new Player();
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        StateManager stateManager = new StateManager();
        ColorSettingState colorSettingState = new ColorSettingState(player);
        State endGame = new EndGameState(player);

        stateManager.getStateHashMap().put(endGame.toString(), endGame);
        stateManager.getStateHashMap().put(colorSettingState.toString(), colorSettingState);

        List<Line> lineList = new ArrayList<>();
        Line line = new Line(endGame);
        line.setPriority(100);
        lineList.add(line);

        stateManager.getTable().put(colorSettingState, lineList);

        stateManager.setCurrent_state(colorSettingState);

        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);
        player.setController(controller);
        player.setStateManager(stateManager);
        stateManager.setTurnManager(turnManager);
        controller.getVisitor().setStringInput("red");
        turnManager.getAllowedColors().add("red");


        colorSettingState.onInput(controller.getVisitor());

        System.out.println(player.getPlayerColor());
    }

    @Test
    void onStateTransition() {
        Player player = new Player();
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        StateManager stateManager = new StateManager();

        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);
        player.setController(controller);
        player.setStateManager(stateManager);
        stateManager.setTurnManager(turnManager);

       ColorSettingState colorSettingState = new ColorSettingState(player);

       colorSettingState.onStateTransition();

    }
}