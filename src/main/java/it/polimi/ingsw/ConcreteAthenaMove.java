package it.polimi.ingsw;

import java.io.IOException;

public class ConcreteAthenaMove extends PowerMovementDecoratorAB {

    /*
    If one of your workers moved up on your last turn,
    opponent workers cannot move up this turn
     */

    public ConcreteAthenaMove(MovementAB movementAB) {
        this.movement = movementAB;
    }

    /**
     * This method moves the worker in the finishSpace and indicates if power could be activated
     * @param worker      This is the worker that will move
     * @param finishSpace This indicates space in which worker will move
     * @param islandBoard This is the game field that contains spaces
     */
    @Override
    public void move(Worker worker, Space finishSpace, IslandBoard islandBoard) throws IOException {
        if (worker != null && finishSpace != null) {
            if (finishSpace.getLevel() > worker.getWorkerSpace().getLevel()) //cambiato segno (era <=)
                islandBoard.setRestrictionPower(true);
            else
                islandBoard.setRestrictionPower(false);         //aggiunto else altrimenti sarebbe sempre true
            movement.move(worker, finishSpace, islandBoard);
        }
    }
}
