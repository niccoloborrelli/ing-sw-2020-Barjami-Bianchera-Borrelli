package it.polimi.ingsw;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Accepter {

    public void accept() throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(60100);
        LobbyManager lobbyManager = new LobbyManager();
        HandlerHub globalhub = new HandlerHub();

        while (true) {
            Socket sc = serverSocket.accept();
            Thread accepter = new Thread(() -> {
                try {
                    Player player = new Player();
                    StateManager stateManager = new StateManager();
                    player.setStateManager(stateManager);
                    Controller controller = new Controller();
                    globalhub.addHandlerForSocket(sc, controller);
                    player.setState(new PreLobbyState(player, lobbyManager));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, "accepter");

            accepter.start();
            accepter.join();
        }
    }

}
