package it.polimi.ingsw;

public interface Observed {
    public abstract void notify(SpaceInput spaceInput,String action);
    public abstract void notify(int code);
}
