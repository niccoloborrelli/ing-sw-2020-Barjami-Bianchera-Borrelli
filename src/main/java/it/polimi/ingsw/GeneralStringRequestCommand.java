package it.polimi.ingsw;

public class GeneralStringRequestCommand extends RequestCommand {

    String message;

    public GeneralStringRequestCommand(String message) {
        this.message = message;
    }

    @Override
    public String execute() {
        return message;
    }

}
