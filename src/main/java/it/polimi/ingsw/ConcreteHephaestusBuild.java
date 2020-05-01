package it.polimi.ingsw;

import java.io.IOException;
import java.net.Socket;
/*
Se non c'è la possibilità di costruire non deve chiedere al giocatore se vuole usare il potere
 */

public class ConcreteHephaestusBuild extends PowerBuildingDecoratorAB {

    /*
    Your worker may build one additional block
    (not dome) on top of your first block
     */

    /**
     * This is a classic decorator pattern constructor
     * @param buildAB is the object to decorate
     */
    public ConcreteHephaestusBuild(BuildAB buildAB){
        this.build = buildAB;
    }

    /**
     * This method calls first the base build and then the Hephaestus one
     * @param worker is the worker who builds
     * @param buildSpace is the space where the worker builds
     */
    @Override
    public void build(Worker worker, Space buildSpace, IslandBoard islandBoard) throws IOException {
        if(worker != null && buildSpace != null) {
            build.build(worker, buildSpace, islandBoard);
            if(buildSpace.getLevel()<3)
                upgradeLevelHephaestus(buildSpace, worker.getWorkerPlayer().getSocket());
        }
    }

    /**
     * This method executes the second build if the player wants
     * @param buildSpace is the same space of the first build
     */
    private void upgradeLevelHephaestus(Space buildSpace, Socket socket) throws IOException {
        ControllerUtility.communicate(socket, "Do you want to use your power? 1 if you want, 0 otherwise", 4);
        if(ControllerUtility.getInt(socket) == 1)
                buildSpace.setLevel(buildSpace.getLevel() + 1);
        ControllerUtility.communicate(socket,"", 5);
    }
}


