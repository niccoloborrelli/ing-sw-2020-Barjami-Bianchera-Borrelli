package it.polimi.ingsw;

import java.util.List;

import static it.polimi.ingsw.ColorConverter.*;
import static it.polimi.ingsw.CommunicationSentences.*;
import static it.polimi.ingsw.DefinedValues.*;
import static it.polimi.ingsw.FinalCommunication.*;


public class Field{
    private static final int lowCorrective=2;
    private static final int highCorrective=4;
    private static final int unitCorrective=1;

    /**
     * Represents the CLI interface. It's composed by a game field.
     */

    private String[][] field = new String[ROW_CLI][COLUMN_CLI];


    /**
     * The constructor methods initializes a field with all level 0
     */
    public Field() {
        loadEmpty();
        createAll();
    }

    /**
     * Permits the correct creations of all the game field.
     */

    private void loadEmpty() {
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
        System.out.println(ANSI_RESET);
    }

    /**
     * This method prints the build on screen
     *
     * @param row     is the space built row
     * @param column  is the space built column
     * @param level   is the level to build
     * @param hasDome is true if a dome is built
     */
    public void viewBuild(int row, int column, int level, int hasDome) {
        row = lowCorrective * row + lowCorrective;
        column = highCorrective * column + highCorrective;
        if (hasDome == unitCorrective)
            field[row][column] = ANSI_BLUE.escape() + "D";
        else
            field[row][column] = ANSI_BLUE.escape() + level;
        plot();
    }

    /**
     * This method prints the move on screen
     *
     * @param oldRow    is the start space row
     * @param oldColumn is the start space column
     * @param newRow    is the finish space row
     * @param newColumn is the finish space column
     * @param color     is the color player
     */
    public void viewMove(String worker, int oldRow, int oldColumn, int newRow, int newColumn, String color) {
        oldRow = lowCorrective * oldRow + lowCorrective;
        oldColumn = highCorrective * oldColumn + lowCorrective;
        newRow = lowCorrective * newRow + lowCorrective;
        newColumn = highCorrective * newColumn + lowCorrective;

        if(oldRow>=MINROW && oldColumn>=MINCOLUMN)
            field[oldRow][oldColumn] = " ";
        if(newRow>=MINROW && newColumn>=MINCOLUMN)
            field[newRow][newColumn] = color + worker;
        plot();
    }

    /**
     * This method prints the starting position of a worker on screen
     *
     * @param row    is the start space row
     * @param column is the start space column
     */
    public void viewSetup(int row, int column, String worker, String color) {
        row = lowCorrective * row + lowCorrective;
        column = highCorrective * column + lowCorrective;

        field[row][column] = color + worker;
        plot();
    }

    /**
     * This method removes a worker from the field
     *
     * @param row    is the row of the worker to remove
     * @param column is the column of the worker to remove
     */
    public void viewRemoveWorker(int row, int column) {
        row = lowCorrective * row + lowCorrective;
        column = highCorrective * column + lowCorrective;

        field[row][column] = " ";
        plot();
    }

    /**
     * Empties all spaces.
     */

    private void blankSpaces() {
        for (int row = MINROW; row < ROW_CLI; row++)
            for (int column = MINCOLUMN; column < COLUMN_CLI; column++)
                field[row][column] = " ";
    }

    /**
     *This method inserts the numbers outside the board
     */
    private void outsideNumbers() {
        for (int i = lowCorrective, number = MINSIZE; i < ROW_CLI - unitCorrective; i = i + lowCorrective, number++)
            field[i][MINCOLUMN] = number + " ";
        field[MINROW][3] = "  0  ";
        for (int j = 7, number = unitCorrective; j < COLUMN_CLI; j = j + highCorrective, number++)
            field[MINROW][j] = " " + number + "  ";
    }

    /**
     * This method inserts the first column margins
     */
    private void firstMargins() {
        field[FIRST_ROW_CLI][FIRST_COLUMN_CLI] = " |";
        for (int j = SECOND_COLUMN_CLI; j < COLUMN_CLI - unitCorrective; j = j + highCorrective)
            field[FIRST_ROW_CLI][j] = "|";
        field[FIRST_ROW_CLI][COLUMN_CLI - unitCorrective] = "|";
    }

    /**
     * This method inserts all the horizontal margins
     */
    private void horizontalMargins() {
        for (int i = FIRST_ROW_CLI; i < ROW_CLI; i = i + lowCorrective)
            for (int j = lowCorrective; j < COLUMN_CLI - unitCorrective; j++) {
                if (j % highCorrective != unitCorrective)
                    field[i][j] = "--";
            }
    }

    /**
     * This method inserts all the middle vertical margins
     */
    private void middleMargins() {
        for (int i = 3; i < ROW_CLI - lowCorrective; i = i + lowCorrective) {
            field[i][FIRST_COLUMN_CLI] = " |";
            for (int j = SECOND_COLUMN_CLI; j < COLUMN_CLI - unitCorrective; j = j + highCorrective)
                field[i][j] = "+";
            field[i][COLUMN_CLI - unitCorrective] = "|";
        }
    }

    /**
     * This method inserts all the final column vertical margins
     */
    private void finalMargins() {
        field[ROW_CLI - unitCorrective][FIRST_COLUMN_CLI] = " |";
        for (int j = SECOND_COLUMN_CLI; j < COLUMN_CLI - unitCorrective; j = j + highCorrective)
            field[ROW_CLI - unitCorrective][j] = "|";
        field[ROW_CLI - unitCorrective][COLUMN_CLI - unitCorrective] = "|";
    }

    /**
     * This method inserts all the remaining vertical margins
     */
    private void verticalMargins() {
        for (int i = lowCorrective; i < ROW_CLI - unitCorrective; i = i + lowCorrective)
            for (int j = FIRST_COLUMN_CLI; j < COLUMN_CLI; j = j + highCorrective)
                field[i][j] = "|" + "   ";
    }

    /**
     * This method insert the starting levels on every field
     */
    private void startingLevels() {
        for (int i = lowCorrective; i < ROW_CLI - unitCorrective; i = i + lowCorrective)
            for (int j = highCorrective; j < COLUMN_CLI - unitCorrective; j = j + highCorrective)
                field[i][j] = ANSI_BLUE.escape() + "0";
    }

    /**
     * Prints the sentence passed.
     * @param sentence is sentence passed.
     */
    public void printSentence(String sentence){
        System.out.println(sentence);
    }

    /**
     * Prints the list of choices, depending by event.
     * @param stringList contains choices.
     * @param specification specified event.
     * @param playerName is player name.
     * @param playerColor is player color.
     */
    public void printChoices(List<String> stringList, String specification, String playerName, String playerColor) {
        String data = null;
        String prefix = playerColor + playerName + ANSI_RESET;
        switch (specification) {
            case POWER:
                data = prefix + getActivationPhrase(stringList);
                break;
            case PRE_LOBBY:
                data = getPreLobbyPhrase(stringList);
                break;
            case COLOR:
                data = getColorPhrase(stringList);
                break;
            case GODCHOICE:
                data = prefix + getGodChoicePhrase(stringList);
                break;
            case GODSET:
                data = prefix + getGodSetPhrase(stringList, playerName, playerColor);
                break;
            case GOD:
                data = prefix + getGodPowers(stringList);
                break;
            case HELP:
                data = getHelp(stringList);
                break;

        }
        System.out.println(data);
    }

}

