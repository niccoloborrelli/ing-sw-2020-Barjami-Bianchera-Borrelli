package it.polimi.ingsw;

public class BaseMovement extends MovementAB{

    /**
     * This method implements the base move
     * @param worker is the worker that moves
     * @param finishSpace is the final space of the worker
     */
    @Override //(VERIFICATA)
    public void move(Worker worker, Space finishSpace, IslandBoard islandBoard) {
        if (worker != null && finishSpace != null)
            changeSpace(worker, finishSpace);
        System.out.println("Worker or space is null");
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

}
