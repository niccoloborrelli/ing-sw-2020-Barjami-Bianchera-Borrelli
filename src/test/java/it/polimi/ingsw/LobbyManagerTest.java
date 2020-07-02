package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class LobbyManagerTest {

    @Test
    void addPlayer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(60101);
        Socket socket = new Socket("localhost", 60101);
        HandlerHub globalHub = new HandlerHub();
        Player player1 = new Player();
        Controller controller1 = new Controller();
        Player player2 = new Player();
        Controller controller2 = new Controller();
        Player player3 = new Player();
        Controller controller3 = new Controller();
        globalHub.addHandlerForSocket(socket, controller1);
        globalHub.addHandlerForSocket(socket, controller2);
        globalHub.addHandlerForSocket(socket, controller3);
        player1.setController(controller1);
        player2.setController(controller2);
        player3.setController(controller3);
        controller1.setPlayer(player1);
        controller2.setPlayer(player2);
        controller3.setPlayer(player3);

        StateManager stateManager = new StateManager();
        stateManager.createBaseStates(player1);
        stateManager.createBaseStates(player2);
        stateManager.createBaseStates(player3);
        player1.setStateManager(stateManager);
        player2.setStateManager(stateManager);
        player3.setStateManager(stateManager);

        LobbyManager lobbyManager = new LobbyManager(globalHub);
        lobbyManager.addPlayer(player1, 2);
        lobbyManager.addPlayer(player3, 3);
        lobbyManager.addPlayer(player2, 2);

        serverSocket.close();
        socket.close();
    }
}