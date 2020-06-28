package it.polimi.ingsw;

import java.util.List;

public class SettingPawnCommand extends ReplyCommand {

    String worker;
    String playerColor;
    private int row;
    private int column;

    public SettingPawnCommand(List<Integer> parameters, String worker, String playerColor) {
        this.row = parameters.get(0);
        this.column = parameters.get(1);
        this.worker=worker;
        this.playerColor = playerColor;
    }


    @Override
    public void execute(GraphicInterface gui) {
        //gui.setPawn(row, column,playerColor,worker);
    }

    @Override
    public void execute(Field field) {
        field.viewSetup(row, column, worker, playerColor);
    }


}
