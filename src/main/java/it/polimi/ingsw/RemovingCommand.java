package it.polimi.ingsw;

import java.util.List;

public class RemovingCommand extends ReplyCommand {

    int row;
    int column;

    public RemovingCommand(List<Integer> integerList) {
        this.row = integerList.get(0);
        this.column = integerList.get(1);
    }


    @Override
    public void execute(GraphicInterface gui) {
    }



    @Override
    public void execute(Field field) {
        field.viewRemoveWorker(row, column);
    }
}
