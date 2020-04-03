package it.polimi.ingsw;

public class BaseMovement extends MovementAB{

    /*
    booleano perchè restituisce true se si è effettivamente mosso
     */
    @Override //(VERIFICATA)
    public boolean move(Worker worker, Space finishSpace) {     //controlla che worker e buildspace siano diversi da null
        if (worker != null && finishSpace != null)              //e ritorna true se si è mosso, false altrimenti
            return changeSpace(worker, finishSpace);            //se uno dei due argomenti è null stampa errore
        System.out.println("Worker o Space hanno valore null");
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
