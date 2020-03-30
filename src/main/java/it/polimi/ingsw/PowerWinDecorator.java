package it.polimi.ingsw;

public abstract class PowerWinDecorator extends WinConditionAB {
    private WinConditionAB winCondition;

    public WinConditionAB getWinCondition() {
        return winCondition;
    }

    public void setWinCondition(WinConditionAB winCondition) {
    }
}
