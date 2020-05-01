package it.polimi.ingsw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CanBuildADomeAtAnyLevel extends PowerBuildingDecoratorAB {

    /*
    Your worker may build a dome at any level
    */

    public CanBuildADomeAtAnyLevel(BuildAB buildAB){
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

        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);

        int power = islandBoard.requiredInt(worker.getWorkerPlayer().getSocket(),"Do you want to use your power? 1 if you want, 0 otherwise", list);
        if(power == 1) {
            int startLevel = buildSpace.getLevel();
            buildSpace.setLevel(3);
            build.build(worker, buildSpace, islandBoard);
            buildSpace.setLevel(startLevel);
        } else {
            build.build(worker, buildSpace, islandBoard);
        }
    }
}
