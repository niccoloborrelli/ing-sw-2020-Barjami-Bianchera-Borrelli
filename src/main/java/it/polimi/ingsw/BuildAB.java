package it.polimi.ingsw;

import java.io.IOException;

public abstract class BuildAB {

    public abstract void build(Worker worker, Space buildSpace, IslandBoard islandBoard) throws IOException;

}
