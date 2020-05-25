package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

public class LobbyManager {

    private List<Lobby> lobbies;
    private HandlerHub globalHub;

    public LobbyManager(HandlerHub globalHub) {
        this.lobbies = new ArrayList<>();
        this.globalHub = globalHub;
    }

    /**
     * This method adds a player in a lobby if the number of players is the same
     * the player wants and if there's an empty slot
     * Otherwise it creates a new lobby with the player
     * @param player is the player to add
     * @param numberOfPlayers is the number of players of the game the player wants to play
     */
    public void addPlayer(Player player, int numberOfPlayers){
        if(lobbies.size() == 0){
            lobbies.add(new Lobby(player, numberOfPlayers));
        }
        for (Lobby l: lobbies) {
            if(canAddPlayer(l ,player, numberOfPlayers)) {
                l.getPlayers().add(player);
                if(lobbyFull(l)){
                    createGame(l);
                    return;
                }
                return;
            }
        }
        lobbies.add(new Lobby(player, numberOfPlayers));
    }

    private boolean canAddPlayer(Lobby l, Player player, int numberOfPlayers){
        return l.getNumberOfPlayers() == numberOfPlayers && l.getPlayers().size() < l.getNumberOfPlayers() && !l.getPlayers().contains(player);
    }

    private boolean lobbyFull(Lobby lobby){
        return lobby.getPlayers().size() == lobby.getNumberOfPlayers();
    }

    private void createGame(Lobby lobby){
        TurnManager turnManager = new TurnManager();
        HandlerHub handlerHub = new HandlerHub();
        createLocalHub(handlerHub, lobby.getPlayers());
        turnManager.setPlayers(lobby.getPlayers());
        for (Player p: lobby.getPlayers()) {
            p.setState(p.getStateManager().getState("NameSettingState"));
            p.getStateManager().setTurnManager(turnManager);
        }
    }

    private void createLocalHub(HandlerHub handlerHub, List<Player> playerList) {
        for(Player p: playerList){
            Handler handler = globalHub.getHandlerControllerHashMap().get(p.getController());
            handlerHub.getHandlerControllerHashMap().put(p.getController(),handler);
            p.getController().setHandlerHub(handlerHub);
            handler.setHandlerHub(handlerHub);
            globalHub.getHandlerControllerHashMap().remove(p.getController());
        }
    }
}