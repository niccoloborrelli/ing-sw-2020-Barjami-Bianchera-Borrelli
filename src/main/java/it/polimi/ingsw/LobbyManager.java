package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

public class LobbyManager {

    private List<Lobby> lobbies;

    public LobbyManager() {
        this.lobbies = new ArrayList<>();
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
            if(l.getNumberOfPlayers() == numberOfPlayers && l.getPlayers().size() < l.getNumberOfPlayers() && !l.getPlayers().contains(player)) {
                    l.getPlayers().add(player);
                if(l.getPlayers().size() == l.getNumberOfPlayers()){
                    TurnManager turnManager = new TurnManager();
                    turnManager.setPlayers(l.getPlayers());
                    System.out.println("Game set for " + l.getNumberOfPlayers() + " players");
                    return;
                }
            }
        }
        lobbies.add(new Lobby(player, numberOfPlayers));
    }
}
