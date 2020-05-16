package it.polimi.ingsw;

import java.io.IOException;

public class BaseMovement extends MovementAB{

    /**
     * This method implements the base move
     * @param worker is the worker that moves
     * @param finishSpace is the final space of the worker
     */
    @Override //(VERIFICATA)
    public void move(Worker worker, Space finishSpace, IslandBoard islandBoard) throws IOException {
        worker.setCantBuild(false);
        if (worker != null && finishSpace != null) {
            Space startSpace = worker.getWorkerSpace();
            changeSpace(worker, finishSpace);
            //islandBoard.notifyMovement(startSpace, finishSpace, worker.getWorkerPlayer().getPlayerColor());
        }
        setOtherWorkers(worker);
    }

    /**
     * This method change the worker space and the space occupatore
     * @param worker is the worker of the move
     * @param finishSpace is the final space of the worker
     */
    private void changeSpace(Worker worker, Space finishSpace) {
        if (finishSpace.isAvailableMovement().contains(worker)) {
            worker.getWorkerSpace().setOccupator(null);
            worker.setWorkerSpace(finishSpace);
            finishSpace.setOccupator(worker);
        }
    }

    private void setOtherWorkers(Worker activeWorker){
        Player player=activeWorker.getWorkerPlayer();
        for (Worker w:player.getWorkers()) {
            if(w!=activeWorker){
                w.setCantBuild(true);
                w.setCantMove(true);
            }
        }
    }
}
