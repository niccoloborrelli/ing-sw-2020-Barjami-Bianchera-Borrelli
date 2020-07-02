package it.polimi.ingsw;

public class SelectCellRequestCommand extends RequestCommand {
    /**
     * Represents a selection of a cell.
     */
    private static final String SPLIT_FIRST_SECOND = "+";
    private static final String SPLIT_SECOND_THIRD = "-";
    int firstInt;
    int secondInt;

    public SelectCellRequestCommand(int firstInt, int secondInt){
        this.firstInt=firstInt;
        this.secondInt=secondInt;
    }

    /**
     * Creates a string containing information about coordinates of cell.
     * @return the string containing information about coordinates of cell.
     */

    @Override
    public String execute() {
        return SPLIT_FIRST_SECOND + firstInt + SPLIT_SECOND_THIRD + secondInt;
    }
}
