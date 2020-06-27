package it.polimi.ingsw;

import java.io.IOException;
import java.net.Socket;

public class CommandCLIManager implements Command{


    private DeliveryMessage deliveryMessage;
    private Field field;

    public CommandCLIManager(Socket sc) throws IOException {
        this.deliveryMessage = new DeliveryMessage(sc);
        deliveryMessage.setCommand(this);
        this.field = new Field();
    }

    public DeliveryMessage getDeliveryMessage() {
        return deliveryMessage;
    }

    public void manageCommand(GeneralStringRequestCommand generalStringRequestCommand){
        deliveryMessage.send(generalStringRequestCommand.execute());
    }

    public void manageCommand(ReplyCommand replyCommand){
        replyCommand.execute(field);
    }
}
