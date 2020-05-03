package it.polimi.ingsw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CanBuildTwiceNotPerimeter extends PowerBuildingDecoratorAB {

    /*
    Your worker may build one additional time,
    but this cannot be on a perimeter space
     */

    private final int FIRSTPOSSIBLEVALUE = 0;
    private final int SECONDPOSSIBLEVALUE = 1;
    private final int MINIMUMSIZE = 0;
    private final int MINROW = 0;
    private final int MAXROW = 4;
    private final int MINCOLUMN = 0;
    private final int MAXCOLUMN = 4;
    private final int FIRSTINDEX = 0;

    /**
     * This is a classic decorator pattern constructor
     * @param buildAB is the object to decorate
     */
    public CanBuildTwiceNotPerimeter(BuildAB buildAB){
        this.build = buildAB;
    }

    /**
     * This method calls first the base build and then if the
     * player wants calculate the possible building space and
     * asks him which space he want to build
     * @param worker is the worker who is going to build
     * @param buildSpace is the space of the first build
     */
    @Override
    public void build(Worker worker, Space buildSpace, IslandBoard islandBoard) throws IOException {
        List<Integer> list = new ArrayList<>();
        list.add(FIRSTPOSSIBLEVALUE);
        list.add(SECONDPOSSIBLEVALUE);

        build.build(worker, buildSpace, islandBoard);
        List<Space> spaces = checkPossibleBuild(worker, islandBoard);

        if(spaces.size()>MINIMUMSIZE)
            if( islandBoard.requiredInt(worker.getWorkerPlayer().getSocket(), "<message>Do you want to use your power? 1 if you want, 0 otherwise</message>",list)== SECONDPOSSIBLEVALUE){
                String message = generateMessage(spaces);
                Space selectedSpace = islandBoard.requiredSpace(worker.getWorkerPlayer().getSocket(), message, spaces);
                build.build(worker, selectedSpace, islandBoard);
        }
    }

    private List<Space> checkPossibleBuild(Worker worker, IslandBoard islandBoard){
        List<Space>[] spaces;
        int indexOfWorker = worker.getWorkerPlayer().getWorkers().indexOf(worker);

        worker.getWorkerPlayer().getRestriction().restrictionEffectBuilding(worker, islandBoard);
        spaces = islandBoard.checkAvailableBuilding(worker.getWorkerPlayer());
        cancelPerimeter(spaces[indexOfWorker]);

        return spaces[indexOfWorker];
    }

    /**
     * This method removes from the list of the possible spaces
     * all the spaces on the perimeter
     * @param list is the list of the possible building spaces
     */
    private void cancelPerimeter (List<Space> list){
        list.removeIf(s -> (s.getColumn() == MINCOLUMN || s.getColumn() == MAXCOLUMN || s.getRow() == MINROW || s.getRow() == MAXROW));
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
