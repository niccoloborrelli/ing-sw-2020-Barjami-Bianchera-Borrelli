package it.polimi.ingsw;

import org.junit.jupiter.api.Test;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

class BaseBuildTest {

    //Se worker o space è null non deve fare niente
    @Test
    void buildTest1() {
        IslandBoard islandBoard = new IslandBoard();
        Space space = new Space(1,1);
        BaseBuild myBuild = new BaseBuild();
        myBuild.build(null,space,islandBoard);
        assertTrue(space.getLevel() == 0);
    }

    //costruendo su un space di livello 2 il suo livello dopo la costruzione deve andare a 3
    @Test
    void buildTest2() {
        Worker worker = new Worker();
        IslandBoard islandBoard = new IslandBoard();
        Space space = new Space(1,1);
        BaseBuild myBuild = new BaseBuild();
        space.setLevel(2);
        space.addAvailableBuilding(worker);
        myBuild.build(worker, space, islandBoard);
        assertTrue(space.getLevel() == 3);
    }

    //costruendo su un space di livello 3 il booleano has dome deve essere true
    @Test
    void buildTest3() {
        Worker worker = new Worker();
        IslandBoard islandBoard = new IslandBoard();
        Space space = new Space(1,1);
        BaseBuild myBuild = new BaseBuild();
        space.setLevel(3);
        space.addAvailableBuilding(worker);
        myBuild.build(worker, space, islandBoard);
        assertTrue(space.HasDome());
    }

}