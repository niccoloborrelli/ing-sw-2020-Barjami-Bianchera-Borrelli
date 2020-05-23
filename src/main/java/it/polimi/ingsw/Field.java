package it.polimi.ingsw;

import static it.polimi.ingsw.Color.*;
import static it.polimi.ingsw.DefinedValues.*;

public class Field {

    private String[][] field = new String[ROW_CLI][COLUMN_CLI];

    /*
        CONVERSIONE ISLANDBOARD -> VIEW
        Righe:  0 -> 2         Colonne: 0 -> 2
                1 -> 4                  1 -> 6
                2 -> 6                  2 -> 10
                3 -> 8                  3 -> 14
                4 -> 10                 4 -> 18
                (2x + 2)                (4x + 2)
    */

    /**
     * The constructor methods initializes a field with all level 0
     */
    public Field(){
        loadEmpty();
    }

    private void loadEmpty(){
        blankSpaces();
        outsideNumbers();
        firstMargins();
        horizontalMargins();
        middleMargins();
        finalMargins();
        verticalMargins();
        startingLevels();
    }

    /**
     * This methods prints the board on screen
     */
    public final void plot() {
        for (int r = MINROW; r < ROW_CLI; r++) {
            for (int c = MINCOLUMN; c < COLUMN_CLI; c++) {
                System.out.print(ANSI_GREEN.escape() + field[r][c]);
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * This method prints the build on screen
     * @param row is the space built row
     * @param column is the space built column
     * @param level is the level to build
     * @param hasDome is true if a dome is built
     */
    public void viewBuild(int row, int column, int level, int hasDome){
        row = 2 * row + 2;
        column = 4 * column + 4;
        if(hasDome == 1)
            field[row][column] = ANSI_BLUE.escape() + "C";
        else
            field[row][column] = ANSI_BLUE.escape() + level;
        plot();
    }

    /**
     * This method prints the move on screen
     * @param oldRow is the start space row
     * @param oldColumn is the start space column
     * @param newRow is the finish space row
     * @param newColumn is the finish space column
     * @param color is the color player
     */
    public void viewMove(int oldRow, int oldColumn, int newRow, int newColumn, String color){
        oldRow = 2 * oldRow + 2;
        oldColumn = 4 * oldColumn + 2;
        newRow = 2 * newRow + 2;
        newColumn = 4 * newColumn + 2;

        field[oldRow][oldColumn] = " ";
        field[newRow][newColumn] = color + "W";
        plot();
    }

    /**
     * This method prints the starting position of a worker on screen
     * @param row is the start space row
     * @param column is the start space column
     */
    public void viewSetup(int row, int column, String color){
        row = 2 * row + 2;
        column = 4* column + 2;

        field[row][column] = color + "W";
        plot();
    }

    /**
     * This method removes a worker from the field
     * @param row is the row of the worker to remove
     * @param column is the column of the worker to remove
     */
    public void viewRemoveWorker(int row, int column){
        row = 2 * row + 2;
        column = 4 * column + 2;

        field[row][column] = " ";
    }

    private void blankSpaces(){
        for(int row = MINROW; row < ROW_CLI; row++)
            for(int column = MINCOLUMN; column < COLUMN_CLI; column++)
                field[row][column] = " ";
    }

    private void outsideNumbers(){
        for(int i = 2, number = 0; i < ROW_CLI - 1; i = i + 2, number++)
            field[i][MINCOLUMN] = number + " ";
        field[MINROW][3] = "  0  ";
        for(int j = 7, number = 1; j < COLUMN_CLI; j = j + 4, number++)
            field[MINROW][j] = " " + number + "  ";
    }

    private void firstMargins(){
        field[FIRST_ROW_CLI][FIRST_COLUMN_CLI] = " ┌";
        for(int j = SECOND_COLUMN_CLI; j < COLUMN_CLI - 1; j = j + 4)
            field[FIRST_ROW_CLI][j] = "┬";
        field[FIRST_ROW_CLI][COLUMN_CLI - 1] = "┐";
    }

    private void horizontalMargins(){
        for(int i = FIRST_ROW_CLI; i < ROW_CLI; i = i + 2)
            for(int j = 2; j < COLUMN_CLI - 1; j++){
                if(j % 4 != 1)
                    field[i][j] = "--";
            }
    }

    private void middleMargins(){
        for(int i = 3; i < ROW_CLI - 2; i = i + 2) {
            field[i][FIRST_COLUMN_CLI] = " ├";
            for(int j = SECOND_COLUMN_CLI; j < COLUMN_CLI - 1; j = j + 4)
                field[i][j] = "+";
            field[i][COLUMN_CLI - 1] = "┤";
        }
    }

    private void finalMargins(){
        field[ROW_CLI - 1][FIRST_COLUMN_CLI] = " └";
        for(int j = SECOND_COLUMN_CLI; j < COLUMN_CLI - 1; j = j + 4)
            field[ROW_CLI - 1][j] = "┴";
        field[ROW_CLI - 1][COLUMN_CLI - 1] = "┘";
    }

    private void verticalMargins(){
        for(int i = 2; i < ROW_CLI - 1 ; i = i + 2)
            for(int j = FIRST_COLUMN_CLI; j < COLUMN_CLI; j = j + 4)
                field[i][j] = "│   ";
    }

    private void startingLevels(){
        for(int i = 2; i < ROW_CLI - 1 ; i = i + 2)
            for(int j = 4; j < COLUMN_CLI - 1; j = j + 4)
                field[i][j] = ANSI_BLUE.escape() + "0";
    }
}
