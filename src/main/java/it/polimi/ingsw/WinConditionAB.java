package it.polimi.ingsw;

import java.util.List;

public abstract class WinConditionAB {
    private boolean hasWon;

    public boolean gethasWon() {
        return hasWon;
    }

    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }

    public abstract void checkHasWon(Worker worker, int startLevel, IslandBoard islandBoard);

    public abstract void checkHasWon(List<Player> players);
}
