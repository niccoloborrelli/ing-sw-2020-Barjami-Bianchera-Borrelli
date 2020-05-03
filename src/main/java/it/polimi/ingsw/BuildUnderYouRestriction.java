package it.polimi.ingsw;

public class BuildUnderYouRestriction extends PowerRestrictionAB {

    /*
    Your Worker may build a block under itself.
     */
    //ZEUS

    public BuildUnderYouRestriction(RestrictionAB restrictionAB){
        this.restrictionAB = restrictionAB;
    }

    @Override
    public void restrictionEffectMovement(Player player, IslandBoard islandBoard) {
        restrictionAB.restrictionEffectMovement(player, islandBoard);
    }

    @Override
    public void restrictionEffectBuilding(Worker worker, IslandBoard islandBoard) {
        restrictionAB.restrictionEffectBuilding(worker, islandBoard);
        if(worker.getWorkerSpace().getLevel() < 3)
            worker.getWorkerSpace().isAvailableBuilding().add(worker);
    }
}
