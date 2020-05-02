package it.polimi.ingsw;

import java.io.IOException;

public class PushWorker extends PowerMovementDecoratorAB {

    /*
    Minotaur's power
    */
    /*
    Your Worker may move into an opponent Worker’s space, if their Worker can be
    forced one space straight backwards to an unoccupied space at any level.
     */

    /**
     * this is a decorator pattern constructor
     * @param movementAB is the decorated object
     */
    public PushWorker(MovementAB movementAB){
        this.movement=movementAB;
    }


    /**
     * this method permits to move a worker using Minoataur's power
     * @param worker is the Worker who is going to be moved
     * @param finishSpace is the Space in which the worker is going to be moved
     * @param islandBoard is the IslandBoard which contains the given finishSpace and the worker
     */
    public void move(Worker worker, Space finishSpace,IslandBoard islandBoard) throws IOException {
        if(finishSpace.getOccupator() != null){
            //sposta una posizione indietro l'occupatore dello spazio
            moveBehind(worker.getWorkerSpace(), finishSpace, islandBoard);
        }
        Space startSpace = worker.getWorkerSpace();
        movement.move(worker, finishSpace, islandBoard);
        islandBoard.notifyMovement(startSpace, finishSpace, worker.getWorkerPlayer().getPlayerColor());
    }

    /**
     * this method is used for the implementation of minotaur move, it permits to move the enemy Worker in the Space
     * behind the position of the moving Worker of the minotaur's holder
     * @param workerSpace is the starting Space of the minotaur's holder worker
     * @param finishSpace is the position in which the minotaur's holder worker is going to move
     * @param islandBoard  is the IslandBoard which contains the given finishSpace and the workerSpace
     */

    public void moveBehind(Space workerSpace, Space finishSpace, IslandBoard islandBoard) throws IOException {
        int startColumn = workerSpace.getColumn();
        int startRow = workerSpace.getRow();
        int finishRow = finishSpace.getRow();
        int finishColumn = finishSpace.getColumn();
        int behindColumn;
        int behindRow;

        if(finishColumn == startColumn + 1)
            behindColumn = finishColumn + 1;
        else if(finishColumn == startColumn - 1)
            behindColumn = finishColumn - 1;
        else
            behindColumn = finishColumn;

        if(finishRow == startRow+1)
            behindRow = finishRow+1;
        else if(finishRow == startRow-1)
            behindRow = finishRow-1;
        else
            behindRow = finishRow;

        islandBoard.getSpace(behindRow,behindColumn).setOccupator(finishSpace.getOccupator());
        finishSpace.getOccupator().setWorkerSpace(islandBoard.getSpace(behindRow,behindColumn));
        finishSpace.setOccupator(null);
        islandBoard.notifyMovement(islandBoard.getSpace(startRow, startColumn), islandBoard.getSpace(finishRow, finishColumn),
                            islandBoard.getSpace(finishRow, finishColumn).getOccupator().getWorkerPlayer().getPlayerColor());
    }


    }
