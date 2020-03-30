package it.polimi.ingsw;

public class BaseMovement extends MovementAB{

    /*
    booleano perchè restituisce true se si è effettivamente mosso
     */
    @Override //(VERIFICATA)
    public boolean move(Worker worker, Space finishSpace) {
        if (worker != null && finishSpace != null)
            if (finishSpace.isAvailableMovement().contains(worker)) {
                worker.getWorkerSpace().setOccupator(null);
                worker.setWorkerSpace(finishSpace);
                finishSpace.setOccupator(worker);
                return true;
            }
        return false;
    }


}
