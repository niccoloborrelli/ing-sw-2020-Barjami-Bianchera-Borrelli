package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NameSettingStateTest {

    @Test
    void onInputTest1() throws IOException {
        Player player = new Player();
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        StateManager stateManager = new StateManager();
        NameSettingState nameSettingState = new NameSettingState(player);

        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);
        player.setController(controller);
        player.setStateManager(stateManager);
        stateManager.setTurnManager(turnManager);
        controller.getVisitor().setStringInput("Carmelo");
        turnManager.getNotAllowedNames().add("Carmelo");

        nameSettingState.onInput(controller.getVisitor());

        System.out.println(player.getPlayerName());
    }

    @Test
    void onInputTest2() throws IOException {
        Player player = new Player();
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        StateManager stateManager = new StateManager();
        NameSettingState nameSettingState = new NameSettingState(player);
        State endGame = new EndGameState(player);

        stateManager.getStateHashMap().put(endGame.toString(), endGame);
        stateManager.getStateHashMap().put(nameSettingState.toString(), nameSettingState);

        List<Line> lineList = new ArrayList<>();
        Line line = new Line(endGame);
        line.setPriority(100);
        lineList.add(line);

        stateManager.getTable().put(nameSettingState, lineList);

        stateManager.setCurrent_state(nameSettingState);

        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);
        player.setController(controller);
        player.setStateManager(stateManager);
        stateManager.setTurnManager(turnManager);
        controller.getVisitor().setStringInput("Carmelo");


        nameSettingState.onInput(controller.getVisitor());

        System.out.println(player.getPlayerName());
    }

    @Test
    void onStateTransition() {
        Player player = new Player();
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();

        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);
        player.setController(controller);

        NameSettingState nameSettingState = new NameSettingState(player);

        nameSettingState.onStateTransition();
    }
}