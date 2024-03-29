package it.polimi.ingsw;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Accepter {

    private static final int ServerPort=60100;
    /**
     * The method accepts socket, create a player with a state manager and a controller and
     * then add the socket and the controller to a globalhub that contains all players waiting
     * for a game, it starts the thread that reads the client scanner and finally set the
     * player state to PreLobbyState
     */
    public void accept() throws IOException {
        ServerSocket serverSocket = new ServerSocket(ServerPort);
        System.out.println("SERVER ONLINE");
        HandlerHub globalHub = new HandlerHub();
        LobbyManager lobbyManager = new LobbyManager(globalHub);
        try {
            manageAccepter(serverSocket,globalHub,lobbyManager);
        }
        catch (IOException e){
            serverSocket.close();
        }

    }

    /**
     * Creates a player with his communicative infrastructure and adds him to a global hub.
     * @param serverSocket is server socket.
     * @param globalHub is global hub.
     * @param lobbyManager manages lobbies.
     * @throws IOException is server socket aren't valid.
     */
    private void manageAccepter(ServerSocket serverSocket,HandlerHub globalHub,LobbyManager lobbyManager) throws IOException {
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
            new Thread(() -> {
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
