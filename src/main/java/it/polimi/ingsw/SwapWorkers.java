package it.polimi.ingsw;

import java.io.IOException;

public class SwapWorkers extends PowerMovementDecoratorAB{

    /*
    Your Worker may move into an opponent worker’s
    space by forcing their worker to the space yours just vacated.
     */
    //APOLLO MOVE

    /**
     * this is a Decorator constructor
     * @param movementAB is the decorated object
     */
    public SwapWorkers(MovementAB movementAB){
        this.movement=movementAB;
    }

    /**
     * this method implements the swap workers power movement
     * @param worker is the Worker who is going to move
     * @param finalSpace is the Space in which the worker is going to move
     * @param board is the IslandBoard containing worker and finalSpace
     */
    @Override
    public void move(Worker worker, Space finalSpace, IslandBoard board) throws IOException {
        Space startSpace = worker.getWorkerSpace();
        if(finalSpace.getOccupator() != null){
            Player opponent = finalSpace.getOccupator().getWorkerPlayer();
            board.sendMessage(worker.getWorkerPlayer().getSocket(), "<message>Swap workers power activated</message>");
            Worker temp = finalSpace.getOccupator();
            worker.getWorkerSpace().setOccupator(temp);
            temp.setWorkerSpace(worker.getWorkerSpace());
            finalSpace.setOccupator(worker);
            worker.setWorkerSpace(finalSpace);
            board.notifyMovement(finalSpace, startSpace, opponent.getPlayerColor());
            board.notifySetUp(finalSpace, worker.getWorkerPlayer().getPlayerColor());

        }
        else {
            movement.move(worker, finalSpace, board);
            board.notifyMovement(startSpace, finalSpace, worker.getWorkerPlayer().getPlayerColor());
        }
    }


}
