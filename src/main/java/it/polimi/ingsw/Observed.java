package it.polimi.ingsw;

import java.util.List;

public interface Observed {
    public abstract void notify(SpaceInput spaceInput,String action);
    public abstract void notify(int code);
    public abstract void notify(String string);
    public abstract void notify(List<SpaceInput> spaceInputList);
    public void notifyLeft(List<String> strings);
}
