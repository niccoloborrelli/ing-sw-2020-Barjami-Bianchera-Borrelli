package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

public class ConcretePrometheusMove extends PowerMovementDecoratorAB {

    public ConcretePrometheusMove(MovementAB movementAB){
        this.movement = movementAB;
    }

    /*
    se il giocatore vuole costruire prima di muoversi
    sceglierà la casella su cui farlo e poi
    sceglierà un'altra casella dove muoversi
    avente lo stesso livello di quella di partenza
     */

    @Override
    public boolean move(Worker worker, Space finishSpace, IslandBoard islandBoard) {

        if(controller.usePower()){
            return buildAndMovePrometheus(worker, islandBoard);
        }
        else
            return movement.move(worker, finishSpace, islandBoard);
    }

    private boolean buildAndMovePrometheus(Worker worker, IslandBoard islandBoard){
        
        //effettuo le operazioni di restriction e check building, poi costruisco sulla casella scelta
        worker.getWorkerPlayer().getRestriction().restrictionEffectBuilding(worker, islandBoard);
        List<Space> possibleBuildings = islandBoard.checkAvailableBuilding(worker.getWorkerPlayer())[worker.getWorkerPlayer().getWorkers().indexOf(worker)];
        Space selectedSpace = controller.selectSpace(possibleBuildings);
        worker.getWorkerPlayer().getBuild().build(worker, selectedSpace, islandBoard);

        //effettuo di nuovo le operazioni di restriction e check movement
        worker.getWorkerPlayer().getRestriction().restrictionEffectMovement(worker.getWorkerPlayer(), islandBoard);
        List<Space> possibleMovements = islandBoard.checkAvailableMovement(worker.getWorkerPlayer())[worker.getWorkerPlayer().getWorkers().indexOf(worker)];

        //rimuovo le caselle con livello diverso dalla casella di partenza
        List<Space> possibleMovementsSameLevel = cleanList(possibleMovements, worker.getWorkerSpace().getLevel());

        //il giocatore sceglie una nuova casella su cui spostarsi e si muove
        selectedSpace = controller.selectSpace(possibleMovementsSameLevel);
        return movement.move(worker, selectedSpace, islandBoard);
    }

    private List<Space> cleanList(List<Space> list, int level){
        List<Space> returnList = new ArrayList<>();

        for (Space s: list) {
            if(s.getLevel() == level)
                returnList.add(s);
        }
        return returnList;
    }
}


