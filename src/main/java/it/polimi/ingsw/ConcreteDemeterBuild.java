package it.polimi.ingsw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);

        build.build(worker, buildSpace, islandBoard);

        int power = islandBoard.requiredInt(worker.getWorkerPlayer().getSocket(), "Do you want to use your power? 1 if you want, 0 otherwise", list);

        if (power == 1) {
            worker.getWorkerPlayer().getRestriction().restrictionEffectBuilding(worker, islandBoard);
            spaces = islandBoard.checkAvailableBuilding(worker.getWorkerPlayer());
            indWorker = worker.getWorkerPlayer().getWorkers().indexOf(worker);
            spaces[indWorker].remove(buildSpace);
            if (spaces[indWorker].size() > 0) {
                newBuildingSpace = islandBoard.requiredPosition(spaces[indWorker], worker.getWorkerPlayer());
                build.build(worker, newBuildingSpace, islandBoard);
            }
            return;
        }
    }
}