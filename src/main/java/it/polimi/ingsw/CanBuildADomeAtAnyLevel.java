package it.polimi.ingsw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CanBuildADomeAtAnyLevel extends PowerBuildingDecoratorAB {

    /*
    Power of Atlas
     */
    /*
    Your worker may build a dome at any level
    */

    private final int FIRSTPOSSIBLEVALUE = 0;
    private final int SECONDPOSSIBLEVALUE = 1;
    private final int MAXLEVEL = 3;

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
        list.add(FIRSTPOSSIBLEVALUE);
        list.add(SECONDPOSSIBLEVALUE);

        int power = islandBoard.requiredInt(worker.getWorkerPlayer().getSocket(),"<message>Do you want to use your power? 1 if you want, 0 otherwise</message>", list);
        if(power == SECONDPOSSIBLEVALUE) {
            int startLevel = buildSpace.getLevel();
            buildSpace.setLevel(MAXLEVEL);
            build.build(worker, buildSpace, islandBoard);
            buildSpace.setLevel(startLevel);
        } else {
            build.build(worker, buildSpace, islandBoard);
        }
    }
}
