package it.polimi.ingsw;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.crypto.Data;
import javax.xml.parsers.ParserConfigurationException;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @Test
    void giveInputToModelTest1() {
        String data = "<data><code>0</code><message><string>ahahahha</string></message></data>";
        Controller controller = new Controller();
        Player player = new Player();
        player.setPlayerColor("red");
        player.setPlayerName("boh");
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

    @Test
    void updateTest1() {
        new Thread(()->{
            try {
                ServerSocket serverSocket = new ServerSocket(62100);
                Socket sc = serverSocket.accept();
                Controller controller = new Controller();
                HandlerHub handlerHub = new HandlerHub();
                Handler handler = new Handler(sc, handlerHub);
                handlerHub.getHandlerControllerHashMap().put(controller, handler);
                Player player = new Player();
                player.setPlayerName("Steve");
                player.setPlayerColor("red");


                LastChange lastChange = new LastChange();
                lastChange.setSpecification("endTurn");
                lastChange.setCode(0);

                controller.setPlayer(player);
                controller.setHandlerHub(handlerHub);

                controller.update(lastChange);
                serverSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()-> {
            try {
                sleep(50);
                Socket socket = new Socket("localhost", 62100);
                DeliveryMessage deliveryMessage = new DeliveryMessage(socket);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Test
    void updateTest2() throws InterruptedException {
        Thread t1 = new Thread(()->{
            try {
                ServerSocket serverSocket = new ServerSocket(62100);
                Socket sc = serverSocket.accept();
                Controller controller = new Controller();
                HandlerHub handlerHub = new HandlerHub();
                Handler handler = new Handler(sc, handlerHub);
                handlerHub.getHandlerControllerHashMap().put(controller, handler);
                Player player = new Player();
                player.setPlayerName("Steve");
                player.setPlayerColor("red");

                controller.setPlayer(player);
                controller.setHandlerHub(handlerHub);

                List<String> strings = new ArrayList<>();
                strings.add("ok");
                strings.add("no");

                List<Integer> integerList = new ArrayList<>();
                integerList.add(1);
                integerList.add(0);

                List<Space> spaces = new ArrayList<>();
                spaces.add(new Space(2,1));
                spaces.add(new Space(0,1));


                LastChange lastChange = new LastChange();
                lastChange.setSpecification("godChoice");
                lastChange.setCode(1);
                lastChange.setStringList(strings);
                lastChange.setListSpace(spaces);
                lastChange.setWorker(player.getWorkers().get(0));
                lastChange.setIntegerList(integerList);

                controller.update(lastChange);
                serverSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(()-> {
            try {
                sleep(50);
                Socket socket = new Socket("localhost", 62100);
                DeliveryMessage deliveryMessage = new DeliveryMessage(socket);
                sleep(300);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        t1.join();
    }
}