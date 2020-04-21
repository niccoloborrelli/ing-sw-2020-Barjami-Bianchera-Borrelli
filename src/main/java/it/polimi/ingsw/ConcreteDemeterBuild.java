package it.polimi.ingsw;

import java.io.IOException;
import java.util.List;

import static it.polimi.ingsw.ControllerUtility.communicate;
import static it.polimi.ingsw.ControllerUtility.getInt;

/*
Demeter power: "Your worker may build one additional time, but not in the same space"
 */
public class ConcreteDemeterBuild extends PowerBuildingDecoratorAB {

    public ConcreteDemeterBuild(BuildAB buildAB) {
        this.build = buildAB;
    }

    /**
     * This method build in buildSpace once, then it asks to player another space,
     * if it exists, in which worker will build.
     *
     * @param worker      This is the worker that have to build
     * @param buildSpace  This is the space in which worker will build once
     * @param islandBoard This is the game field that contains the list of spaces
     * @throws IOException
     */

    @Override
    public void build(Worker worker, Space buildSpace, IslandBoard islandBoard) throws IOException {
        List<Space>[] spaces;
        int indWorker;
        Space newBuildingSpace;

        build.build(worker, buildSpace, islandBoard);

        communicate(worker.getWorkerPlayer().getSocket(), "Do you want to use your power? 1 if you want, 0 otherwise", 4);
        if (getInt(worker.getWorkerPlayer().getSocket()) == 1) {
            ControllerUtility.communicate(worker.getWorkerPlayer().getSocket(), "", 5);

            worker.getWorkerPlayer().getRestriction().restrictionEffectBuilding(worker, islandBoard);
            spaces = islandBoard.checkAvailableBuilding(worker.getWorkerPlayer());
            indWorker = worker.getWorkerPlayer().getWorkers().indexOf(worker);
            spaces[indWorker].remove(buildSpace);
            if (spaces[indWorker].size() > 0) {
                newBuildingSpace = ControllerUtility.selectPos(spaces[indWorker], worker.getWorkerPlayer());
                build.build(worker, newBuildingSpace, islandBoard);
            }
            return;
        }
        ControllerUtility.communicate(worker.getWorkerPlayer().getSocket(), "", 5);
    }
}