package it.polimi.ingsw;

public class SetUpCommand extends ReplyCommand {

    /**
     * Represents a communication of beginning of setting.
     */

    String specification;
    String playerName;
    String playerColor;

    public SetUpCommand(String specification, String playerName, String playerColor) {
        this.specification = specification;
        this.playerName = playerName;
        this.playerColor = playerColor;
    }

    /**
     * Creates the pawn.
     * @param gui is the interface.
     */

    @Override
    public void execute(GraphicInterface gui) {
        gui.workerCreation();
    }

    /**
     * Prints a sentence about beginning of setting.
     * @param field is the interface.
     */

    @Override
    public void execute(Field field) {
        new SentenceBottomRequestCommand(playerColor, playerName, specification).execute(field);
    }
}
