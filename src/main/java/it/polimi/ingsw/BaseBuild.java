package it.polimi.ingsw;

public class BaseBuild extends BuildAB {

    /*
    booleano perchè restituisce true se ha effettivamente costruito
     */
    @Override // (VERIFICATA)
    public boolean build(Worker worker, Space buildSpace, IslandBoard islandBoard) {
        if (worker != null && buildSpace != null)                         //controlla che worker e buildspace siano diverse da null
            return upgradeLevel(buildSpace, worker);                      //e ritorna true se ha costruito false altrimenti
                                                                          //se la costruzione non e' andata per via di worker o space
        System.out.println("worker o space dati hanno vallore null");     //a null riporta tramite una system.out il fatto
        return false;

    }

    // restituisce true se la costruzione e' andata a buon fine false altrimenti
    private boolean upgradeLevel(Space buildSpace,Worker worker){
        if(buildSpace.isAvailableBuilding().contains(worker)) {
            buildSpace.setLevel(buildSpace.getLevel() + 1);
            domeBuilding(buildSpace);
            return true;
        }
        return false;
    }

    private void domeBuilding(Space buildSpace){ //inserisce la cupola se il livello passato e' 4
        if (buildSpace.getLevel() == 4)
            buildSpace.setHasDome(true);
    }

}