package it.polimi.ingsw;

public abstract class RequestCommand {

    /**
     * Represents a client request.
     */

    /**
     * Executes the command.
     * @return a string containing information about command.
     */
    public abstract String execute();
}
