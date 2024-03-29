package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class EndGameStateTest {

    @Test
    void onStateTransition() throws IOException {
        ServerSocket serverSocket = new ServerSocket(62100);
        StateManager stateManager = new StateManager();
        Socket socket = new Socket("localhost", 62100);
        Socket client = serverSocket.accept();
        Controller controller = new Controller();
        Player player = new Player();
        player.setHasWon(true);
        HandlerHub handlerHub = new HandlerHub();
        handlerHub.getHandlerControllerHashMap().put(controller, new Handler(client, handlerHub));
        controller.setHandlerHub(handlerHub);
        player.setController(controller);
        controller.setPlayer(player);

        State state = new EndGameState(player);
        stateManager.getStateHashMap().put(state.toString(), state);
        synchronized (player) {
            state.onStateTransition();
        }

        socket.close();
        serverSocket.close();

    }
}