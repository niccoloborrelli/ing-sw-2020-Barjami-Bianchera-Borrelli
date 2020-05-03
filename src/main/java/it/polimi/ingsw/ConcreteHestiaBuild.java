package it.polimi.ingsw;

import java.io.IOException;
import java.util.List;

public class ConcreteHestiaBuild extends PowerBuildingDecoratorAB {

    /*
    Your worker may build one additional time,
    but this cannot be on a perimeter space
     */

    /**
     * This is a classic decorator pattern constructor
     * @param buildAB is the object to decorate
     */
    public ConcreteHestiaBuild(BuildAB buildAB){
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

        build.build(worker, buildSpace, islandBoard);
        ControllerUtility.communicate(worker.getWorkerPlayer().getSocket(), "Do you want to use your power? 1 if you want, 0 otherwise",4);
        if(ControllerUtility.getInt(worker.getWorkerPlayer().getSocket()) == 1){
            ControllerUtility.communicate(worker.getWorkerPlayer().getSocket(),"",5);
            worker.getWorkerPlayer().getRestriction().restrictionEffectBuilding(worker, islandBoard);
            List<Space> spaces = islandBoard.checkAvailableBuilding(worker.getWorkerPlayer())[worker.getWorkerPlayer().getWorkers().indexOf(worker)];
            cancelPerimeter(spaces);
            if(spaces.size() > 0) {
                Space selectedSpace = ControllerUtility.selectPos(spaces, worker.getWorkerPlayer());
                build.build(worker, selectedSpace, islandBoard);
            }
            else
                ControllerUtility.communicate(worker.getWorkerPlayer().getSocket(), "You can build nowhere else", 0);
        }
    }

    /**
     * This method removes from the list of the possible spaces
     * all the spaces on the perimeter
     * @param list is the list of the possible building spaces
     */
    private void cancelPerimeter (List<Space> list){
        list.removeIf(s -> (s.getColumn() == 0 || s.getColumn() == 4 || s.getRow() == 0 || s.getRow() == 4));
    }
}
