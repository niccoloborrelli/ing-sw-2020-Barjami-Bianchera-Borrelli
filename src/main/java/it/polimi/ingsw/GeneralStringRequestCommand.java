package it.polimi.ingsw;

public class GeneralStringRequestCommand extends RequestCommand {

    /**
     * Represents a command containing a string that can be used.
     *
     */

    private String message;

    public GeneralStringRequestCommand(String message) {
        this.message = message;
    }

    /**
     * Returns the string that's contained.
     * @return the string that's contained.
     */

    @Override
    public String execute() {
        return message;
    }

}
