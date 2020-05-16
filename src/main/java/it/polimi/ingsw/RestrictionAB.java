package it.polimi.ingsw;

public abstract class RestrictionAB {

    public abstract void restrictionEffectMovement(Player player, IslandBoard islandBoard);
    public abstract void restrictionEffectBuilding(Player player, IslandBoard islandBoard);
    //non fa nulla ma mi serve
    public boolean setRestrictionAB(RestrictionAB restriction){
        return false;
    }
}
