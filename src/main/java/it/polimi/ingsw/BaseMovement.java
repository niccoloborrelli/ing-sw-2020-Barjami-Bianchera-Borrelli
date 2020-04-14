package it.polimi.ingsw;

public class BaseMovement extends MovementAB{

    /*
    booleano perchè restituisce true se si è effettivamente mosso
     */
    @Override //(VERIFICATA)
    public boolean move(Worker worker, Space finishSpace, IslandBoard islandBoard) {
        if (worker != null && finishSpace != null)              //controlla che worker e buildspace siano diversi da null
            return changeSpace(worker, finishSpace);            //e ritorna true se si è mosso, false altrimenti
        System.out.println("Worker o Space hanno valore null"); //se uno dei due argomenti è null stampa errore
        return false;
    }

    private boolean changeSpace(Worker worker, Space finishSpace) { //restituisce true se il movimento è andato abuon fine
        if (finishSpace.isAvailableMovement().contains(worker)) {
            worker.getWorkerSpace().setOccupator(null);
            worker.setWorkerSpace(finishSpace);
            finishSpace.setOccupator(worker);
            return true;
        }
        return false;
    }

}
