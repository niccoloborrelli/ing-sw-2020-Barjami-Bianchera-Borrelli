package it.polimi.ingsw;

import java.util.List;

import static it.polimi.ingsw.FinalCommunication.*;

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

    public String getSpecification() {
        return specification;
    }

    @Override
    public void execute(GraphicInterface gui) {
    }

    public void execute(App app){
        switch (specification) {
            case COLOR:
                app.setColorStage();
                break;
            case GODCHOICE:
                app.changeGods(options);
                break;
            case GODSET:
                app.setGodsStage();
                break;
        }
    }

    @Override
    public void execute(Field field) {
        field.printChoices(options, specification, playerName, playerColor);
    }
}
