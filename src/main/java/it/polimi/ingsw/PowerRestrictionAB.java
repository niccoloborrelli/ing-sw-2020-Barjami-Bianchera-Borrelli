package it.polimi.ingsw;

public abstract class PowerRestrictionAB extends RestrictionAB {
    protected RestrictionAB restrictionAB;
    public boolean setRestrictionAB(RestrictionAB restriction){
        this.restrictionAB=restriction;
        return true;
    }
}
