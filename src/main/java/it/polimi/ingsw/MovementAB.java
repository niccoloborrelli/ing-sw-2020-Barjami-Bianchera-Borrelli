package it.polimi.ingsw;

import java.io.IOException;

public abstract class MovementAB {
    public abstract void move(Worker worker, Space finishSpace, IslandBoard islandBoard) throws IOException;

}
