package it.polimi.ingsw;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class SpaceTest {

    @org.junit.jupiter.api.Test
    void resetAvailableMovement() {
        Space space = new Space(1,1);
        Worker worker = new Worker();
        space.addAvailableMovement(worker);
        space.resetAvailableMovement();
        assertTrue(space.isAvailableMovement().size() == 0);
    }

    @org.junit.jupiter.api.Test
    void addAvailableMovement() {
        Space space = new Space(1,1);
        Worker worker = new Worker();
        int start = space.isAvailableMovement().size();
        space.addAvailableMovement(worker);

        assertTrue(space.isAvailableMovement().size() == start +1 && space.isAvailableMovement().get(start) == worker);
    }

    @org.junit.jupiter.api.Test
    void resetAvailableBuilding() {
        Space space = new Space(1,1);
        Worker worker = new Worker();
        space.addAvailableBuilding(worker);
        space.resetAvailableBuilding();
        assertTrue(space.isAvailableBuilding().size() == 0);
    }

    @org.junit.jupiter.api.Test
    void addAvailableBuilding() {
        Space space = new Space(1,1);
        Worker worker = new Worker();
        int start = space.isAvailableBuilding().size();
        space.addAvailableBuilding(worker);

        assertTrue(space.isAvailableBuilding().size() == start +1 && space.isAvailableBuilding().get(start) == worker);
    }
}