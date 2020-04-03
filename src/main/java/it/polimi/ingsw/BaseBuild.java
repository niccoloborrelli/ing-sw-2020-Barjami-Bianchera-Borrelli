package it.polimi.ingsw;

public class BaseBuild extends BuildAB {

    /*
    booleano perchè restituisce true se ha effettivamente costruito
     */
    @Override // (VERIFICATA)
    public boolean build(Worker worker, Space buildSpace) { //controlla che worker e buildspace siano diverse da null
        if (worker != null && buildSpace != null)           //e ritorna true se ha costruito false altrimenti
            return upgradeLevel(buildSpace,worker);         //se la costruzione non e' andata per via di worker o space a null riporta tramite
                                                            //una system.out il fatto
        System.out.println("worker o space dati hanno vallore null");
        return false;

    }

    private boolean upgradeLevel(Space buildSpace,Worker worker){ // restituisce true se la costruzione e' andata a buon fine false altrimenti
        if(buildSpace.isAvailableBuilding().contains(worker)) {
            buildSpace.setLevel(buildSpace.getLevel() + 1);
            domeBuilding(buildSpace);
            return true;
        }
        return false;
    }

    private void domeBuilding(Space buildSpace){ //inserisce la cupola se il livello passato e' <4
        if (buildSpace.getLevel() == 4)
            buildSpace.setHasDome(true);
    }

}