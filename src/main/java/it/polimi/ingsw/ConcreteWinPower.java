package it.polimi.ingsw;

public class ConcreteWinPower extends PowerWinDecorator {
    private PowerWinDecorator powerWinDecorator;

    public PowerWinDecorator getPowerWinDecorator() {
        return powerWinDecorator;
    }

    public void setPowerWinDecorator(PowerWinDecorator powerWinDecorator) {
        this.powerWinDecorator = powerWinDecorator;
    }

    @Override
    public void checkHasWon(Worker worker, int startLevel) {

    }
}
