package it.polimi.ingsw;

import java.util.List;

import static it.polimi.ingsw.FinalCommunication.*;

public class LimitedOptionsCommand extends ReplyCommand {

    /**
     * Represents a limited possibility of choosing options.
     */

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

    /**
     * Sets visible determined buttons in GUI.
     * @param gui is where buttons are set visible.
     */

    @Override
    public void execute(GraphicInterface gui) {
        if(specification.equals(POWER))
            gui.showPower();
    }

    /**
     * Depending of event permits the creation of scene with limited possibilities.
     * @param app is where scene are set.
     */

    public void execute(App app){
        switch (specification) {
            case COLOR:
                app.setColorStage(options);
                break;
            case GODCHOICE:
                app.changeGods(options);
                break;
            case GODSET:
                app.setGodsStage();
                break;
        }
    }

    /**
     * Prints list of choices.
     * @param field permits to print list of choices.
     */


    @Override
    public void execute(Field field) {
        field.printChoices(options, specification, playerName, playerColor);
    }
}
