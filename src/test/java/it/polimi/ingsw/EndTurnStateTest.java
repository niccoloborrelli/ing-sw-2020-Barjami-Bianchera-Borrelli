package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EndTurnStateTest {

    @Test
    void onStateTransition() throws IOException {
        ServerSocket serverSocket = new ServerSocket(60101);
        Socket socket = new Socket("localhost", 60101);
        HandlerHub handlerHub = new HandlerHub();
        Player player1 = new Player();
        Controller controller1 = new Controller();
        Player player2 = new Player();
        Controller controller2 = new Controller();
        handlerHub.getHandlerControllerHashMap().put(controller1, new Handler(socket, handlerHub));
        handlerHub.getHandlerControllerHashMap().put(controller2, new Handler(socket, handlerHub));
        controller1.setHandlerHub(handlerHub);
        controller2.setHandlerHub(handlerHub);
        player1.setController(controller1);
        player2.setController(controller2);
        controller1.setPlayer(player1);
        controller2.setPlayer(player2);
        TurnManager turnManager = new TurnManager();
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        turnManager.setPlayers(players);
        player1.getStateManager().setTurnManager(turnManager);
        player2.getStateManager().setTurnManager(turnManager);

        State endTurn1 = new EndTurnState(player1);
        State endTurn2 = new EndTurnState(player2);

        player1.getActionsToPerform().clear();
        player1.getStateManager().setCurrent_state(endTurn1);
        endTurn1.onStateTransition();

        serverSocket.close();
        socket.close();

    }
}