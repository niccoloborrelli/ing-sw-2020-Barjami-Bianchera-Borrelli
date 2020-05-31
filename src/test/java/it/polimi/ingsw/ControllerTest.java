package it.polimi.ingsw;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.crypto.Data;
import javax.xml.parsers.ParserConfigurationException;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @Test
    void giveInputToModelTest1() {
        String data = "<data><code>0</code><message><string>ahahahha</string></message></data>";
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        State state = new StateTest(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        player.getStateManager().setCurrent_state(state);

        controller.giveInputToModel(data);

        assertTrue(controller.getVisitor().getStringInput().equals("ahahahha") && controller.getVisitor().getIntInput()==-1
        && controller.getVisitor().getSpaceInput().getWorker()==null && controller.getVisitor().getSpaceInput().getSpace()==null );
    }

    @Test
    void giveInputToModelTest2() {
        String data = "<data><code>2</code><message><int>1</int></message></data>";
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        State state = new StateTest(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        player.getStateManager().setCurrent_state(state);

        controller.giveInputToModel(data);

        assertTrue(controller.getVisitor().getStringInput()==null && controller.getVisitor().getIntInput()==1
                && controller.getVisitor().getSpaceInput().getWorker()==null && controller.getVisitor().getSpaceInput().getSpace()==null );
    }

    @Test
    void giveInputToModelTest3() {
        String data = "<data><code>1</code><message><space><row>1</row><column>2</column></space><worker>1</worker></message></data>";
        Controller controller = new Controller();
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        player.setIslandBoard(islandBoard);
        controller.setPlayer(player);
        State state = new StateTest(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        player.getStateManager().setCurrent_state(state);

        controller.giveInputToModel(data);

        assertTrue(controller.getVisitor().getStringInput()==null && controller.getVisitor().getIntInput()==-1
                && controller.getVisitor().getSpaceInput().getWorker().equals(player.getWorkers().get(1)) &&
                controller.getVisitor().getSpaceInput().getSpace().equals(islandBoard.getSpace(1,2)) );
    }



    @Test
    void createFluxTableTest() {
        Player player = new Player();
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        Controller controller = new Controller();
        controller.setPlayer(player);

        try {
            controller.createFluxTable();
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        HashMap<State, List<Line>> table = stateManager.getTable();
        for (State start: table.keySet()) {
            System.out.println("\n");
            System.out.println("Lo stato di partenza è: " + start);
            for (Line line : table.get(start)) {
                System.out.println("Lo stato di arrivo è: " + line.getFinishState());
                System.out.println("Il livello di priorità è: " + line.getPriority());
                for (Method m : line.getConditions().keySet()) {
                    System.out.println("Il metodo da usare è: " + m);
                    System.out.println("Il valore atteso è: " + line.getConditions().get(m));
                }
            }
        }
    }
}