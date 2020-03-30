package it.polimi.ingsw;

public class BaseBuild extends AbstractBuild {

    /*
    booleano perchè restituisce true se ha effettivamente costruito
     */
    @Override // (VERIFICATA)
    public boolean build(Worker worker, Space buildSpace) {
        if (worker != null && buildSpace != null)
            if(buildSpace.isAvailableBuilding().contains(worker)) {
                buildSpace.setLevel(buildSpace.getLevel() + 1);
                if (buildSpace.getLevel() == 4)
                    buildSpace.setHasDome(true);
                return true;
            }
        return false;
    }
}
