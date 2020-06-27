package it.polimi.ingsw;

public class SelectCellRequestCommand extends RequestCommand {
    private static final String SPLIT_FIRST_SECOND = "+";
    private static final String SPLIT_SECOND_THIRD = "-";
    int firstInt;
    int secondInt;

    public SelectCellRequestCommand(int firstInt, int secondInt){
        this.firstInt=firstInt;
        this.secondInt=secondInt;
    }

    @Override
    public String execute() {
        return SPLIT_FIRST_SECOND + firstInt + SPLIT_SECOND_THIRD + secondInt;
    }
}
