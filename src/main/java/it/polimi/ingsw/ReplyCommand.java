package it.polimi.ingsw;

public abstract class ReplyCommand {

    /**
     * It's a command that represents a reply to a client request.
     * @param gui
     */

    /**
     * Executes the command in the interface.
     * @param gui is the interface.
     */

    public abstract void execute(GraphicInterface gui);

    /**
     * Executes the command in the interface.
     * @param field is the interface.
     */
    public abstract void execute(Field field);
}
