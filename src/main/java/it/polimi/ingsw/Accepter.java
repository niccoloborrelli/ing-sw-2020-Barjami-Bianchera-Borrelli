package it.polimi.ingsw;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Accepter {

    public void accept() throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(60100);
        HandlerHub globalHub = new HandlerHub();
        LobbyManager lobbyManager = new LobbyManager(globalHub);

        while (true) {
            Socket sc = serverSocket.accept();
            Thread accepter = new Thread(() -> {
                try {
                    System.out.println("Collegato");
                    Player player = new Player();
                    StateManager stateManager = new StateManager();
                    player.setStateManager(stateManager);
                    Controller controller = new Controller();
                    globalHub.addHandlerForSocket(sc, controller);
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
