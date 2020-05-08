package it.polimi.ingsw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
Demeter power: "Your worker may build one additional time, but not in the same space"
 */
public class AdditionalBuildNoInitial extends PowerBuildingDecoratorAB {

    private final int MINIMUMSIZE = 0;
    private final int FIRSTPOSSIBLEVALUE = 0;
    private final int SECONDPOSSIBLEVALUE = 1;
    private final int FIRSTINDEX = 0;

    public AdditionalBuildNoInitial(BuildAB buildAB) {
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
        List<Space> availableSpaces;
        int power = FIRSTPOSSIBLEVALUE;
        Space newBuildingSpace;
        String message;
        List<Integer> list = new ArrayList<>();
        list.add(FIRSTPOSSIBLEVALUE);
        list.add(SECONDPOSSIBLEVALUE);

        build.build(worker, buildSpace, islandBoard);
        availableSpaces = checkPossibleBuild(worker, buildSpace, islandBoard);

        if(availableSpaces.size()>MINIMUMSIZE){
            power = islandBoard.requiredInt(worker.getWorkerPlayer().getSocket(), "<message>Do you want to use your power? 1 if you want, 0 otherwise</message>", list);
            if (power == SECONDPOSSIBLEVALUE) {
                    message = generateMessage(availableSpaces);
                    newBuildingSpace = islandBoard.requiredSpace(worker.getWorkerPlayer().getSocket(), message,availableSpaces);
                    build.build(worker, newBuildingSpace, islandBoard);
                }
            }
        return;
    }

    private List<Space> checkPossibleBuild(Worker worker, Space buildSpace, IslandBoard islandBoard){
    List<Space>[] spaces;
    int indexOfWorker = worker.getWorkerPlayer().getWorkers().indexOf(worker);


    worker.getWorkerPlayer().getRestriction().restrictionEffectBuilding(worker, islandBoard);
    spaces = islandBoard.checkAvailableBuilding(worker.getWorkerPlayer());
    spaces[indexOfWorker].remove(buildSpace);

    return spaces[indexOfWorker];
    }

    private String generateMessage(List<Space> availableSpaces){
        String message = "<message>Choose the index of position you want to build on </message>";
        int i = FIRSTINDEX;
        for(Space s: availableSpaces){
            message = message + "<Space" + i + "><Row>" + s.getRow() + "</Row><Column>" + s.getColumn() + "</Column><Index>" + i + "</Index></Space" + i + ">";
        }
        return message;
    }
}