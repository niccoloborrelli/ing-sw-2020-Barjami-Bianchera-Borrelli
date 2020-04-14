package it.polimi.ingsw;

import java.util.List;

public class ConcreteHestiaBuild extends PowerBuildingDecoratorAB {

    public ConcreteHestiaBuild(BuildAB buildAB){
        this.build = buildAB;
    }

    /*
    se il giocatore vuole costruire di nuovo
    verranno rieffettuate r.E.B. e c.A.B dalla
    quale verranno rimossi gli space perimetrali,
    poi il giocatore sceglierà dove costruire
    tramite controller e sarà effettuata la build
     */
    @Override
    public boolean build(Worker worker, Space buildSpace, IslandBoard islandBoard) {

        boolean hasBuild = build.build(worker, buildSpace, islandBoard);
        if(controller.usePower()){
            worker.getWorkerPlayer().getRestriction().restrictionEffectBuilding(worker, islandBoard);
            List<Space> spaces = islandBoard.checkAvailableBuilding(worker.getWorkerPlayer())[worker.getWorkerPlayer().getWorkers().indexOf(worker)];
            cancelPerimeter(spaces);
            Space selectedSpace = controller.selectSpace(spaces);
            return build.build(worker, selectedSpace, islandBoard);
        }
        else
            return hasBuild;
    }

    private void cancelPerimeter (List<Space> list){
        list.removeIf(s -> (s.getColumn() == 0 || s.getColumn() == 4 || s.getRow() == 0 || s.getRow() == 4));
    }
}
