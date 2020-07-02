package it.polimi.ingsw;

public class UsePowerRequestCommand extends RequestCommand {

    /**
     * Represents a request of using power.
     */

    int choice;

    public UsePowerRequestCommand(int choice) {
        this.choice = choice;
    }

    /**
     * Traduces information in a choice.
     * @return the value of choice.
     */

    @Override
    public String execute() {
        return String.valueOf(choice);
    }
}
