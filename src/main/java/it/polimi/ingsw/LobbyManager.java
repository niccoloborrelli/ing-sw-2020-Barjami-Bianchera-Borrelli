package it.polimi.ingsw;

import java.io.IOException;
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
     * When a lobby is full, the controller and the handler of the players in the lobby
     * are removed from the globalhub and inserted into the lobby handlerhub, the players are
     * set in the NameSettingState and the game begins
     * @param player is the player to add
     * @param numberOfPlayers is the number of players of the game the player wants to play
     */
    public void addPlayer(Player player, int numberOfPlayers){
        if(lobbies.size() == 0){
            lobbies.add(new Lobby(player, numberOfPlayers));
            return;
        }
        for (Lobby l: lobbies) {
            if(canAddPlayer(l, player, numberOfPlayers)) {
                l.getPlayers().add(player);
                if(lobbyFull(l))
                    createGame(l);
                return;
            }
        }
        lobbies.add(new Lobby(player, numberOfPlayers));
    }

    private boolean canAddPlayer(Lobby l, Player player, int numberOfPlayers){
        for (Player p: l.getPlayers()) {
            if(globalHub.getHandlerControllerHashMap().get(p.getController()).getSc().isClosed())
                return false;
        }
        return l.getNumberOfPlayers() == numberOfPlayers && l.getPlayers().size() < l.getNumberOfPlayers() && !l.getPlayers().contains(player);
    }

    private boolean lobbyFull(Lobby lobby){
        return lobby.getPlayers().size() == lobby.getNumberOfPlayers();
    }

    private void createGame(Lobby lobby){
        IslandBoard islandBoard = new IslandBoard();
        TurnManager turnManager = new TurnManager();
        HandlerHub handlerHub = new HandlerHub();
        createLocalHub(handlerHub, lobby.getPlayers());
        turnManager.setPlayers(lobby.getPlayers());
        for (Player p: lobby.getPlayers()) {
            p.setIslandBoard(islandBoard);
            State name = p.getStateManager().getState("NameSettingState");
            p.getStateManager().setCurrent_state(name);
            try {
                p.getState().onStateTransition();
            } catch (IOException ignored) {
            }
            p.getStateManager().setTurnManager(turnManager);
        }
        lobbies.remove(lobby);
    }

    private void createLocalHub(HandlerHub handlerHub, List<Player> playerList) {
        for(Player p: playerList){
            Handler handler = globalHub.getHandlerControllerHashMap().get(p.getController());
            handlerHub.getHandlerControllerHashMap().put(p.getController(), handler);
            p.getController().setHandlerHub(handlerHub);
            handler.setHandlerHub(handlerHub);
            globalHub.getHandlerControllerHashMap().remove(p.getController());
        }
    }
}
