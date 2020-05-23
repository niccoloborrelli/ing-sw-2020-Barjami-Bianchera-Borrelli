package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

public class Lobby {

    private List<Player> players;
    private int numberOfPlayers;

    public Lobby(Player player, int numberOfPlayers){
        players = new ArrayList<>();
        players.add(player);
        this.numberOfPlayers = numberOfPlayers;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
