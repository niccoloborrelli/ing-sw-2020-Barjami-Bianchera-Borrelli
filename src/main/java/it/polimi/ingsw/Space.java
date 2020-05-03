package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

public class Space {
    private Worker occupator;
    private int row;
    private int column;
    private boolean hasDome;
    private int level;
    private List<Worker> availableMovement;
    private List<Worker> availableBuilding;

    public Space(int row, int column){
        this.occupator = null;
        this.row = row;
        this.column = column;
        this.hasDome = false;
        this.level = 0;
        this.availableBuilding = new ArrayList<Worker>();
        this.availableMovement = new ArrayList<Worker>();
    }

    public Worker getOccupator() {
        return occupator;
    }

    public void setOccupator(Worker occupator) {
        this.occupator = occupator;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean HasDome() {
        return hasDome;
    }

    public void setHasDome(boolean hasDome) {
        this.hasDome = hasDome;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<Worker> isAvailableMovement() {
        return availableMovement;
    }

    public void resetAvailableMovement() { this.availableMovement = new ArrayList<Worker>(); } //(VERIFICATA)

    public void addAvailableMovement(Worker worker){ this.availableMovement.add(worker); } //(VERIFICATA)

    public List<Worker> isAvailableBuilding() {
        return availableBuilding;
    }

    public void resetAvailableBuilding() { this.availableBuilding = new ArrayList<Worker>(); } //(VERIFICATA)

    public void addAvailableBuilding(Worker worker){
        this.availableBuilding.add(worker);
    } //(VERIFICATA)

    public void removeAvailableMovement(Worker worker) { availableMovement.remove(worker); }

}
