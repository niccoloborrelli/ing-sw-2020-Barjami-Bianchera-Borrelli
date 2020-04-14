package it.polimi.ingsw;

import java.util.List;

public class ConcreteDemeterBuild extends PowerBuildingDecoratorAB {

    public ConcreteDemeterBuild(BuildAB buildAB){
        this.build = buildAB;
    }


    /*
    Potere di Demetra: costruisci un blocco aggiuntivo in una posizione diversa da quella di partenza
    Procedura:
        -build normale
        -richiede al giocatore tramite il controller se deve usare il potere
        -se accetta il controller setta a true "usepower", altrimenti a false
        -in caso negativo finisce il metodo, in caso positivo calcola se può costruire altri blocchi
        -nel caso potesse, chiede di inserire lo spazio (valido) in cui vuole costruire tramite il controller
        -effettua una seconda build
     Osservazioni
        1) Torna true se si è usata il potere, false se non si è usato
     Problemi:
        -- necessario IslandBoard come parametro per effettuare la nuova restriction
        -- necessario ControllerInput per richiedere il controller
     BISOGNA NEL CASO MODIFICARE BUILD
     */
    @Override
    public boolean build(Worker worker, Space buildSpace, IslandBoard islandBoard) {
        List<Space>[] spaces;
        int indWorker;
        Space newBuildingSpace
        build.build(worker, buildSpace, islandBoard);

        if (controller.usePower()) {
            worker.getWorkerPlayer().getRestriction().restrictionEffectBuilding(worker, islandBoard);
            spaces = islandBoard.checkAvailableBuilding(worker.getWorkerPlayer());
            indWorker = worker.getWorkerPlayer().getWorkers().indexOf(worker);

            if (spaces[indWorker].size() > 0){
                do {
                    newBuildingSpace = controller.selectSpace();
                }while (newBuildingSpace.equals(buildSpace) && newBuildingSpace.isAvailableBuilding().contains(worker));

                build.build(worker, newBuildingSpace, islandBoard);
                return true;
            }
        }
        return false;
    }
}