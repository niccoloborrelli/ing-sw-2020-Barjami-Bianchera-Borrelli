package it.polimi.ingsw;

public abstract class PowerWinDecorator extends WinConditionAB {
    protected WinConditionAB winCondition;

    public void setWinCondition(WinConditionAB winCondition) {
        this.winCondition = winCondition;
    }
}
