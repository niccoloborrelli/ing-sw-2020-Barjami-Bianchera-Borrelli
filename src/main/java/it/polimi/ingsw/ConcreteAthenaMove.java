package it.polimi.ingsw;

public class ConcreteAthenaMove extends PowerMovementDecoratorAB {

    public ConcreteAthenaMove(MovementAB movementAB){
        this.movement = movementAB;
    }

    @Override
    public boolean move(Worker worker, Space finishSpace, IslandBoard islandBoard) {
        if(worker != null && finishSpace != null) {
            if (finishSpace.getLevel() <= worker.getWorkerSpace().getLevel())
                worker.getWorkerPlayer().setUsepower(true);
            movement.move(worker, finishSpace, islandBoard);
            return true;
        }else
            return false;
    }
}
