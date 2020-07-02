
package it.polimi.ingsw;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Thread.activeCount;
import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
        String data1 = "<data><code>2</code><message><int>a</int></message></data>";

        controller.giveInputToModel(data1);

        if(controller.getVisitor().getStringInput()==null && controller.getVisitor().getIntInput()==-1
                && controller.getVisitor().getSpaceInput().getWorker()==null && controller.getVisitor().getSpaceInput().getSpace()==null ) {

            controller.giveInputToModel(data);

            assertTrue(controller.getVisitor().getStringInput() == null && controller.getVisitor().getIntInput() == 1
                    && controller.getVisitor().getSpaceInput().getWorker() == null && controller.getVisitor().getSpaceInput().getSpace() == null);
        }else
            fail();
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
        String data1 = "<data><code>1</code><message><space><row>1</row><column>2</column></space><worker>5</worker></message></data>";
        String data2 = "<data><code>1</code><message><space><row>a</row><column>b</column></space><worker>1</worker></message></data>";
        String data3 = "<data><code>1</code><message><space><row>a</row><column>b</column></space><worker>B</worker></message></data>";

        controller.giveInputToModel(data1);

        if(!player.getWorkers().contains(controller.getVisitor().getSpaceInput().getWorker()) ) {
            controller.giveInputToModel(data3);
            if(!player.getWorkers().contains(controller.getVisitor().getSpaceInput().getWorker())) {
                controller.giveInputToModel(data2);
                if (controller.getVisitor().getSpaceInput().getSpace().getRow() == -1 &&
                        controller.getVisitor().getSpaceInput().getSpace().getColumn() == -1) {
                    controller.giveInputToModel(data);

                    assertTrue(controller.getVisitor().getStringInput() == null && controller.getVisitor().getIntInput() == -1
                            && controller.getVisitor().getSpaceInput().getWorker().equals(player.getWorkers().get(1)) &&
                            controller.getVisitor().getSpaceInput().getSpace().equals(islandBoard.getSpace(1, 2)));
                }else
                    fail();
            }else
                fail();
        }else
            fail();
    }

    @Test
    void giveInputToModelTest4() throws InterruptedException {
        Thread client = clientThread();
        Thread server = new Thread(()->{
            try {
                ServerSocket serverSocket = new ServerSocket(62100);
                Socket clientSocket = serverSocket.accept();
                Controller controller = new Controller();
                HandlerHub handlerHub = new HandlerHub();
                Handler handler = new Handler(clientSocket, handlerHub);
                handlerHub.getHandlerControllerHashMap().put(controller, handler);
                controller.setHandlerHub(handlerHub);
                controller.createGodMap();
                controller.createPowerGodMap();
                Player player = new Player();
                player.setLastChange(new LastChange());
                player.getLastChange().setCode(0);
                player.getLastChange().setSpecification("endTurn");
                controller.setPlayer(player);
                player.setController(controller);

                String message1 = "<data><code>0</code><message><string>-help</string></message></data>";
                String message2 =  "<data><code>0</code><message><string>-god</string></message></data>";
                String message3 = "<data><code>0</code><message><string>-god Atlas</string></message></data>";
                String message4 = "<data><code>0</code><message><string>-godAtlas</string></message></data>";
                String message5 = "<data><code>0</code><message><string>quit</string></message></data>";

                controller.giveInputToModel(message1);
                controller.giveInputToModel(message2);
                controller.giveInputToModel(message3);
                controller.giveInputToModel(message4);
                controller.giveInputToModel(message5);

                serverSocket.close();
                clientSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        server.start();
        client.start();
        server.join();

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
    void updateTest1() throws InterruptedException {
        Thread t = new Thread(()->{
            try {
                ServerSocket serverSocket = new ServerSocket(62100);
                Controller controller = createSetUp(serverSocket);
                Player player = new Player();
                player.setPlayerName("Steve");
                player.setPlayerColor("red");
                controller.setPlayer(player);

                createMessageToPrint(player, "endTurn");
                controller.update();

                createMessageToPrint(player, "endGame");
                controller.update();

                serverSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Thread client = clientThread();

        t.start();
        client.start();
        t.join();

    }

    @Test
    void updateTest2() throws InterruptedException {
        Thread t1 = new Thread(()->{
            try {
                ServerSocket serverSocket = new ServerSocket(62100);
                Player player = new Player();
                player.setPlayerName("Steve");
                player.setPlayerColor("red");

                Controller controller = createSetUp(serverSocket);

                controller.setPlayer(player);

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

                player.setLastChange(lastChange);

                controller.update();

                lastChange.setWorker(player.getWorkers().get(1));

                controller.update();

                serverSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = clientThread();

        t1.start();
        t2.start();
        t1.join();

    }

    @Test
    void updateTest3() throws InterruptedException {
        Thread t1 = new Thread(()->{
            try {
                ServerSocket serverSocket = new ServerSocket(62100);
                Player player = new Player();
                player.setPlayerName("Steve");
                player.setPlayerColor("red");

                Controller controller = createSetUp(serverSocket);

                controller.setPlayer(player);


                LastChange lastChange = new LastChange();
                lastChange.setSpecification("move");
                lastChange.setCode(2);
                lastChange.setSpace(new Space(1,2));
                lastChange.setWorker(player.getWorkers().get(0));
                player.getWorkers().get(0).setWorkerSpace(new Space(1,1));
                player.setLastChange(lastChange);

                controller.update();

                lastChange.setSpecification("build");
                lastChange.setCode(2);
                lastChange.setSpace(new Space(1,2));
                lastChange.setWorker(player.getWorkers().get(0));
                player.getWorkers().get(0).setWorkerSpace(new Space(1,1));
                player.setLastChange(lastChange);

                controller.update();


                serverSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = clientThread();

        t1.start();
        t2.start();
        t1.join();

    }

    @Test
    void updateTest4() throws InterruptedException {
        Thread t1 = new Thread(()->{
            try {
                ServerSocket serverSocket = new ServerSocket(62100);
                Player player = new Player();
                player.setPlayerName("Steve");
                player.setPlayerColor("red");

                Controller controller = createSetUp(serverSocket);

                controller.setPlayer(player);


                LastChange lastChange = new LastChange();
                lastChange.setSpecification("lose");
                lastChange.setCode(3);
                player.setLastChange(lastChange);

                controller.update();

                serverSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = clientThread();

        t1.start();
        t2.start();
        t1.join();

    }

    @Test
    void updateTest5() throws InterruptedException {
        Thread t1 = new Thread(()->{
            try {
                ServerSocket serverSocket = new ServerSocket(62100);
                Player player = new Player();
                player.setPlayerName("Steve");
                player.setPlayerColor("red");

                Controller controller = createSetUp(serverSocket);

                controller.setPlayer(player);


                LastChange lastChange = new LastChange();
                lastChange.setSpecification("error");
                lastChange.setCode(3);

                controller.update(lastChange);

                serverSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = clientThread();

        t1.start();
        t2.start();
        t1.join();

    }

    @Test
    void updateTest6() throws InterruptedException {
        Thread t1 = new Thread(()->{
            try {
                ServerSocket serverSocket = new ServerSocket(62100);
                Player player = new Player();
                player.setPlayerName("Steve");
                player.setPlayerColor("red");

                Controller controller = createSetUp(serverSocket);

                controller.setPlayer(player);


                LastChange lastChange = new LastChange();
                lastChange.setSpecification("win");
                lastChange.setCode(3);

                controller.update(lastChange);

                serverSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = clientThread();

        t1.start();
        t2.start();
        t1.join();

    }

    private Thread clientThread(){
       return new Thread(()-> {
            try {
                sleep(50);
                Socket socket = new Socket("localhost", 62100);
                DeliveryMessage deliveryMessage = new DeliveryMessage(socket);
                deliveryMessage.startReading();
                sleep(300);
                socket.close();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private Controller createSetUp(ServerSocket serverSocket) throws IOException {
        Socket sc = serverSocket.accept();
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        Handler handler = new Handler(sc, handlerHub);
        handlerHub.getHandlerControllerHashMap().put(controller, handler);
        controller.setHandlerHub(handlerHub);
        return controller;
    }

    private void createMessageToPrint(Player player, String specification){
        LastChange lastChange = new LastChange();
        lastChange.setSpecification("endTurn");
        lastChange.setCode(0);
        player.setLastChange(lastChange);
    }

    @Test
    void createGodMap1() throws NoSuchMethodException, ClassNotFoundException {
        Controller controller = new Controller();
        Player player = new Player();
        StateManager stateManager = new StateManager();

        player.setStateManager(stateManager);
        controller.setPlayer(player);

        controller.createGodMap();
        controller.decoratePlayer(player);
    }
}

