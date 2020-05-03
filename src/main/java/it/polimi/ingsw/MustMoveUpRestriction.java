package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

public class MustMoveUpRestriction extends PowerRestrictionAB {

    /*
    If possible, at least one Worker must move up this turn
     */
    //PERSEPHONE
    private boolean someoneCanGoUp;

    public MustMoveUpRestriction(RestrictionAB restrictionAB){
        someoneCanGoUp = false;
        this.restrictionAB=restrictionAB;
    }

    @Override // (VERIFICATA)
    public void restrictionEffectMovement(Player player, IslandBoard islandBoard) {
        restrictionAB.restrictionEffectMovement(player,islandBoard);
        for (Worker worker : player.getWorkers())  //controllo se uno dei due worker puo' andare in una posizione superiore
            possibleUpperMove(worker,islandBoard);

        for (Worker worker : player.getWorkers())
            setPossibleMovementPersephone(worker, islandBoard);
    }

    /*
    setta i movimenti possibili di un worker nello spazio circostante alla sua casella tramite la modifica di persephone
     */
    private void setPossibleMovementPersephone(Worker worker, IslandBoard islandBoard){
        if(someoneCanGoUp){
            List<Space> possibleMovements = islandBoard.checkAvailableMovement(worker.getWorkerPlayer())[worker.getWorkerPlayer().getWorkers().indexOf(worker)];
            for (Space s: possibleMovements) {
                if(s.getLevel() <= worker.getWorkerSpace().getLevel()) {
                    possibleMovements.remove(s);
                    //s.removeAvailableMovement(worker); //METTODO DA AGGIUNGERE IN SPACE
                }
            }
        }
    }

    //restituisce true se esiste un possibile movimento verso posizione piu' in alto, false se non ce ne sono
    private void possibleUpperMove(Worker worker, IslandBoard islandBoard){
        List<Space> possibleMovements = islandBoard.checkAvailableMovement(worker.getWorkerPlayer())[worker.getWorkerPlayer().getWorkers().indexOf(worker)];
        for (Space s: possibleMovements) {
            if(s.getLevel() > worker.getWorkerSpace().getLevel()) {
                someoneCanGoUp = true;
                return;
            }
        }
    }

    @Override //(VERIFICATA)
    public void restrictionEffectBuilding(Worker worker, IslandBoard islandBoard) {
         restrictionAB.restrictionEffectBuilding(worker, islandBoard);
    }
}
