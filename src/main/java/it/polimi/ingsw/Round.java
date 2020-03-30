package it.polimi.ingsw;

import java.util.List;

public class Round {
    private Player activePlayer;
    private boolean playerWon;

    public Player getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public boolean hasWon(IslandBoard islandBoard) {
        return playerWon;
    }

    public void setHasWon(boolean hasWon) {
        this.playerWon = hasWon;
    }

    public Worker chooseWorker(List<Worker> workers) {
        return new Worker();
    }

    public int hasLost(IslandBoard islandBoard){
        return 0;

    }
}