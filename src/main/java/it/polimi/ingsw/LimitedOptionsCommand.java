package it.polimi.ingsw;

import java.util.List;

public class LimitedOptionsCommand extends ReplyCommand {

    private List<String> options;
    private String specification;
    private String playerName;
    private String playerColor;

    public LimitedOptionsCommand(List<String> options, String specification, String playerName, String playerColor) {
        this.options = options;
        this.specification = specification;
        this.playerName = playerName;
        this.playerColor = playerColor;
    }


    @Override
    public void execute(GraphicInterface gui) {
    }


    @Override
    public void execute(Field field) {
        field.printChoices(options, specification, playerName, playerColor);
    }
}
