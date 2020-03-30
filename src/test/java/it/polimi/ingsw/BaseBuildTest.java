package it.polimi.ingsw;

import org.junit.jupiter.api.Test;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

class BaseBuildTest {

    //Se worker non è inserito nella lista availableBuilding il metodo build deve resistuire false
    @Test
    void buildTest1() {
        Worker worker = new Worker();
        Space space = new Space(1,1);
        BaseBuild myBuild = new BaseBuild();
        assertTrue(!myBuild.build(worker, space));
    }

    //costruendo su un space di livello 2 il suo livello dopo la costruzione deve andare a 3
    @Test
    void buildTest2() {
        Worker worker = new Worker();
        Space space = new Space(1,1);
        BaseBuild myBuild = new BaseBuild();
        space.setLevel(2);
        space.addAvailableBuilding(worker);
        myBuild.build(worker,space);
        assertTrue(space.getLevel() == 3);
    }

    //costruendo su un space di livello 3 il booleano has dome deve essere true
    @Test
    void buildTest3() {
        Worker worker = new Worker();
        Space space = new Space(1,1);
        BaseBuild myBuild = new BaseBuild();
        space.setLevel(3);
        space.addAvailableBuilding(worker);
        myBuild.build(worker,space);
        assertTrue(space.HasDome());
    }

}