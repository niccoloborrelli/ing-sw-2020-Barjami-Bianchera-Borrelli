package it.polimi.ingsw;

import java.io.IOException;

import static it.polimi.ingsw.ControllerUtility.communicate;
import static it.polimi.ingsw.ControllerUtility.getInt;


public class ConcreteAtlasBuild extends PowerBuildingDecoratorAB {

    /*
    Your worker may build a dome at any level
    */

    public ConcreteAtlasBuild(BuildAB buildAB){
        this.build = buildAB;
    }

    /**
     * This method allow worker to build a dome at any level
     * @param worker This is the worker that have to build
     * @param buildSpace This is the place in which worker will build
     * @param islandBoard This is the game field that contains the list of spaces
     */
    @Override
    public void build(Worker worker, Space buildSpace, IslandBoard islandBoard) throws IOException {

        communicate(worker.getWorkerPlayer().getSocket(),"Do you want to use your power? 1 if you want, 0 otherwise", 4);
        if(getInt(worker.getWorkerPlayer().getSocket()) == 1) {
            ControllerUtility.communicate(worker.getWorkerPlayer().getSocket(), "", 5);
            int startLevel = buildSpace.getLevel();
            buildSpace.setLevel(3);
            build.build(worker, buildSpace, islandBoard);
            buildSpace.setLevel(startLevel);
        } else {
            ControllerUtility.communicate(worker.getWorkerPlayer().getSocket(), "", 5);
            build.build(worker, buildSpace, islandBoard);
        }
    }
}
