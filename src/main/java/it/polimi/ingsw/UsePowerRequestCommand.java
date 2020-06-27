package it.polimi.ingsw;

public class UsePowerRequestCommand extends RequestCommand {

    int choice;

    public UsePowerRequestCommand(int choice) {
        this.choice = choice;
    }

    @Override
    public String execute() {
        return String.valueOf(choice);
    }
}
