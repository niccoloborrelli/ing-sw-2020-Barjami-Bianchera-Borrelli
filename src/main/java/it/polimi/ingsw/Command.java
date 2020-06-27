package it.polimi.ingsw;

public interface Command {
    public void manageCommand(GeneralStringRequestCommand generalStringRequestCommand);
    public void manageCommand(ReplyCommand replyCommand);

}

