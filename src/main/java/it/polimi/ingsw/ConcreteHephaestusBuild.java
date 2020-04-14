package it.polimi.ingsw;

import java.util.Scanner;

public class ConcreteHephaestusBuild extends PowerBuildingDecoratorAB {

    public ConcreteHephaestusBuild(BuildAB buildAB){
        this.build = buildAB;
    }

    //upgradeLevelHephaestus effettua la doppia costruzione
    //se il livello è minore di 3 (non può costruire
    // una cupola) e se lo vuole il giocatore
    //dopo aver effettuato la baseBuild

    @Override
    public boolean build(Worker worker, Space buildSpace, IslandBoard islandBoard) {
        if(worker != null && buildSpace != null) {
            build.build(worker, buildSpace, islandBoard);
            upgradeLevelHephaestus(buildSpace);
            return true;
        }
        return false;
    }

    private void upgradeLevelHephaestus(Space buildSpace){

        if(buildSpace.getLevel() < 3 && controller.usePower())
                buildSpace.setLevel(buildSpace.getLevel() + 1);
    }
}


