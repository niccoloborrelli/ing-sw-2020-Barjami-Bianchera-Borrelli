package it.polimi.ingsw;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class God {
    private String godName;
    private boolean powerUsed;
    private String godPower;
    private List<PowerBuildingDecoratorAB> buildEffects;
    private List<PowerMovementDecoratorAB> moveEffects;
    private List<PowerRestrictionAB> ownRestrictionEffects;
    private List<PowerRestrictionAB> enemyRestrictionEffects;
    private List<PowerWinDecorator> winConditionEffects;

    public God(String name){
        this.godName=name;
        buildEffects=new ArrayList<PowerBuildingDecoratorAB>();
        moveEffects=new ArrayList<PowerMovementDecoratorAB>();
        ownRestrictionEffects=new ArrayList<PowerRestrictionAB>();
        enemyRestrictionEffects=new ArrayList<PowerRestrictionAB>();
        winConditionEffects=new ArrayList<PowerWinDecorator>();
    }

    public void addBuildEffect(PowerBuildingDecoratorAB buildDecorator){
        buildEffects.add(buildDecorator);
    }
    public void addMoveEffect(PowerMovementDecoratorAB moveDecorator){
        moveEffects.add(moveDecorator);
    }
    public void addOwnRestrictionEffect(PowerRestrictionAB restDecorator){
        ownRestrictionEffects.add(restDecorator);
    }
    public void addEnemyRestrictionEffect(PowerRestrictionAB restDecorator){
        enemyRestrictionEffects.add(restDecorator);
    }
    public void addWinConditionEffect(PowerWinDecorator winDecorator){
        winConditionEffects.add(winDecorator);
    }

    public List<PowerBuildingDecoratorAB> getBuildEffects() {
        return buildEffects;
    }

    public List<PowerMovementDecoratorAB> getMoveEffects() {
        return moveEffects;
    }

    public List<PowerRestrictionAB> getOwnRestrictionEffects() {
        return ownRestrictionEffects;
    }

    public List<PowerRestrictionAB> getEnemyRestrictionEffects() {
        return enemyRestrictionEffects;
    }

    public List<PowerWinDecorator> getWinConditionEffects() {
        return winConditionEffects;
    }

    public String getGodName() {
        return godName;
    }

    public boolean isPowerUsed() {
        return powerUsed;
    }

    public String getGodPower() {
        return godPower;
    }

    public void setGodName(String godName) {
        this.godName = godName;
    }

    public void setPowerUsed(boolean powerUsed) {
        this.powerUsed = powerUsed;
    }

    public void setGodPower(String godPower) {
        this.godPower = godPower;
    }
}
