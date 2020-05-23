package it.polimi.ingsw;

import java.io.IOException;
import java.util.List;

public abstract class WinConditionAB {
    private boolean hasWon;

    public boolean gethasWon() {
        return hasWon;
    }

    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }

    public abstract void checkHasWon(Player player) throws IOException;
}
