package it.polimi.ingsw;

public interface Observed {
    public abstract void notify(LastChange lastChange);
}
