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

    @Override
    public void manageCommand(SentenceBottomRequestCommand sentenceBottomRequestCommand) {
        sentenceBottomRequestCommand.execute(field);
    }

    @Override
    public void manageCommand(PopUpCommand popUpCommand) {
        popUpCommand.execute(field);
    }

    @Override
    public void manageCommand(ShowAvCells showAvCells) {
        showAvCells.execute(field);
    }

    @Override
    public void manageCommand(RemovingCommand removingCommand) {
        removingCommand.execute(field);
    }

    @Override
    public void manageCommand(SettingPawnCommand settingPawnCommand) {
        settingPawnCommand.execute(field);
    }

    @Override
    public void manageCommand(LimitedOptionsCommand limitedOptionsCommand) {
        limitedOptionsCommand.execute(field);
    }

    @Override
    public void manageCommand(TransitionSceneCommand transitionSceneCommand) {
        transitionSceneCommand.execute(field);
    }

    @Override
    public void manageCommand(MoveUpdateCommand moveUpdateCommand) {
        moveUpdateCommand.execute(field);
    }

    @Override
    public void manageCommand(BuildUpdateCommand buildUpdateCommand) {
        buildUpdateCommand.execute(field);
    }

    @Override
    public void manageCommand(SetUpCommand setUpCommand) {
        setUpCommand.execute(field);
    }

    @Override
    public void manageCommand(QuitCommand quitCommand) {
        System.out.println("Vediamo se si disconnette da solo");
        deliveryMessage.quitGame(true);
        quitCommand.execute(field);

    }

    @Override
    public void manageCommand(ExitCommand exitCommand) {
        System.out.println("Nada");
    }


}
