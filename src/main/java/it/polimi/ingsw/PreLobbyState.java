package it.polimi.ingsw;

import java.io.IOException;

public class PreLobbyState extends State{

    private LobbyManager lobbyManager;


    PreLobbyState(Player player, LobbyManager lobbyManager) {
        super(player);
        this.lobbyManager = lobbyManager;
    }

    @Override
    public void onStateTransition() throws IOException {

    }

}
