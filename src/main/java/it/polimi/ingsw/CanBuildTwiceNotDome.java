package it.polimi.ingsw;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
/*
Se non c'è la possibilità di costruire non deve chiedere al giocatore se vuole usare il potere
 */

public class CanBuildTwiceNotDome extends PowerBuildingDecoratorAB {

    /*
    Hephaestus power
     */
    /*
    Your worker may build one additional block
    (not dome) on top of your first block
     */

    private final int MAXIMUMLEVEL = 3;
    private final int FIRSTPOSSIBLEVALUE = 0;
    private final int SECONDPOSSIBLEVALUE = 1;
    private final int UPGRADEBUILD = 1;

    /**
     * This is a classic decorator pattern constructor
     * @param buildAB is the object to decorate
     */
    public CanBuildTwiceNotDome(BuildAB buildAB){
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
            if(buildSpace.getLevel()<MAXIMUMLEVEL)
                upgradeLevelHephaestus(buildSpace, worker.getWorkerPlayer().getSocket(), islandBoard);
        }
    }

    /**
     * This method executes the second build if the player wants
     * @param buildSpace is the same space of the first build
     */
    private void upgradeLevelHephaestus(Space buildSpace, Socket socket, IslandBoard islandBoard) throws IOException {
        List<Integer> available = new ArrayList<>();
        available.add(FIRSTPOSSIBLEVALUE);
        available.add(SECONDPOSSIBLEVALUE);

        if(islandBoard.requiredInt(socket, "<message>Do you want to use your power? 1 if you want, 0 otherwise</message>", available) == SECONDPOSSIBLEVALUE)
                buildSpace.setLevel(buildSpace.getLevel() + UPGRADEBUILD);
    }
}


