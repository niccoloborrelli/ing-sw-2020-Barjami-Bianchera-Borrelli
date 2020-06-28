package it.polimi.ingsw;

public class SetUpCommand extends ReplyCommand {

    String specification;
    String playerName;
    String playerColor;

    public SetUpCommand(String specification, String playerName, String playerColor) {
        this.specification = specification;
        this.playerName = playerName;
        this.playerColor = playerColor;
    }

    @Override
    public void execute(GraphicInterface gui) {
        gui.workerCreation();
    }

    @Override
    public void execute(Field field) {
        new SentenceBottomRequestCommand(playerColor, playerName, specification).execute(field);
    }
}
