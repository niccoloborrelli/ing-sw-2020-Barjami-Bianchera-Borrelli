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
            Player player = new Player();
            StateManager stateManager = new StateManager();
            stateManager.createBaseStates(player);
            player.setStateManager(stateManager);
            Controller controller = new Controller();
            player.setController(controller);
            controller.setPlayer(player);
            globalHub.addHandlerForSocket(sc, controller);
            new Thread(()->{
                Thread t = globalHub.createThreadHandle(globalHub.getHandlerControllerHashMap().get(controller));
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            player.getStateManager().setCurrent_state(new PreLobbyState(player, lobbyManager));
            player.getState().onStateTransition();
        }


    }

}
