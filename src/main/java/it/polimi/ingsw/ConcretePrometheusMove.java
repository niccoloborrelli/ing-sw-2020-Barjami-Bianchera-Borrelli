package it.polimi.ingsw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConcretePrometheusMove extends PowerMovementDecoratorAB {

    /*
    If your worker not move up, it may
    build both before and after moving
     */

    /**
     * This is a classic decorator pattern constructor
     * @param movementAB is the object to decorate
     */
    public ConcretePrometheusMove(MovementAB movementAB){
        this.movement = movementAB;
    }

    /**
     * This method checks first if the player wants to use the power:
     * if he do then calls the Prometheus move
     * if he don't then calls the base move
     * @param worker is the worker who is going to move or to build and then move
     * @param finishSpace is the space where the worker is going to move
     */
    @Override
    public void move(Worker worker, Space finishSpace, IslandBoard islandBoard) throws IOException {

        ControllerUtility.communicate(worker.getWorkerPlayer().getSocket(), "Do you want to use your power? 1 if you want, 0 otherwise", 4);
        if(ControllerUtility.getInt(worker.getWorkerPlayer().getSocket()) == 1){
            ControllerUtility.communicate(worker.getWorkerPlayer().getSocket(), "", 5);
            buildAndMovePrometheus(worker, islandBoard);
        }
        else {
            ControllerUtility.communicate(worker.getWorkerPlayer().getSocket(), "", 5);
            movement.move(worker, finishSpace, islandBoard);
        }
    }

    /**
     * This method first calls the methods to calculate the possible space to build,
     * then it asks the player which space and calls the base build,
     * then it calls the method to calculate the possible space to move with the
     * same level of the start space, then it asks the player which space
     * and calls the base move
     * @param worker is the worker who is going to build and move
     */
    private void buildAndMovePrometheus(Worker worker, IslandBoard islandBoard) throws IOException {
        
        //effettuo le operazioni di restriction e check building, poi costruisco sulla casella scelta
        worker.getWorkerPlayer().getRestriction().restrictionEffectBuilding(worker, islandBoard);
        List<Space> possibleBuildings = islandBoard.checkAvailableBuilding(worker.getWorkerPlayer())[worker.getWorkerPlayer().getWorkers().indexOf(worker)];
        Space selectedSpace = ControllerUtility.selectPos(possibleBuildings, worker.getWorkerPlayer());
        worker.getWorkerPlayer().getBuild().build(worker, selectedSpace, islandBoard);

        //effettuo di nuovo le operazioni di restriction e check movement
        worker.getWorkerPlayer().getRestriction().restrictionEffectMovement(worker.getWorkerPlayer(), islandBoard);
        List<Space> possibleMovements = islandBoard.checkAvailableMovement(worker.getWorkerPlayer())[worker.getWorkerPlayer().getWorkers().indexOf(worker)];

        //rimuovo le caselle con livello diverso dalla casella di partenza
        List<Space> possibleMovementsSameLevel = cleanList(possibleMovements, worker.getWorkerSpace().getLevel());

        //il giocatore sceglie una nuova casella su cui spostarsi e si muove
        selectedSpace = ControllerUtility.selectPos(possibleMovementsSameLevel, worker.getWorkerPlayer());
        movement.move(worker, selectedSpace, islandBoard);
    }

    /**
     * @param list is the list of all the possible movements for the worker
     * @param level is the level of the start space
     * @return the list of all the possible movement with the same level of level
     */
    private List<Space> cleanList(List<Space> list, int level){
        List<Space> returnList = new ArrayList<>();

        for (Space s: list) {
            if(s.getLevel() == level)
                returnList.add(s);
        }
        return returnList;
    }
}


