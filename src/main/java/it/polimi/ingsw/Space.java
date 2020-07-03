package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.DefinedValues.MINLEVEL;

public class Space {
    private Worker occupator;
    private int row;
    private int column;
    private boolean hasDome;
    private int level;

    public Space(int row, int column){
        this.occupator = null;
        this.row = row;
        this.column = column;
        this.hasDome = false;
        this.level = MINLEVEL;
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

    public String toString(){
        return "Row: "+row+"- Column: "+column;
    }
}
