package it.polimi.ingsw;

public abstract class PowerBuildingDecoratorAB extends  BuildAB {
    protected BuildAB build;

    public void setBuild(BuildAB build) {
        this.build = build;
    }
}
