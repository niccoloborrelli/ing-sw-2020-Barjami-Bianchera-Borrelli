package it.polimi.ingsw;

public abstract class WinConditionAB {
    private Player player;
    private boolean hasWon;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean gethasWon() {
        return hasWon;
    }

    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }

    public abstract void checkHasWon(Worker worker, int startLevel);
}
