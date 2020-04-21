package it.polimi.ingsw;

public class BaseBuild extends BuildAB {

    /*
    booleano perchè restituisce true se ha effettivamente costruito
     */

    /**
     * This method implements the base build
     * @param worker is the worker that builds
     * @param buildSpace is the space where the worker builds
     */
    @Override // (VERIFICATA)
    public void build(Worker worker, Space buildSpace, IslandBoard islandBoard) {
        if (worker != null && buildSpace != null)
            upgradeLevel(buildSpace, worker);
    }

    /**
     * This method change the level of a space
     * @param buildSpace is the space where the level is going to change
     */
    private void upgradeLevel(Space buildSpace,Worker worker){
        if(buildSpace.isAvailableBuilding().contains(worker)) {
            buildSpace.setLevel(buildSpace.getLevel() + 1);
            domeBuilding(buildSpace);
        }
    }

    /**
     * This method set the boolean "HasDome" as true if
     * the space level is 4
     * @param buildSpace is the space to control
     */
    private void domeBuilding(Space buildSpace){
        if (buildSpace.getLevel() == 4)
            buildSpace.setHasDome(true);
    }

}