package it.polimi.ingsw;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class CommandGUIManager implements Command {

    private DeliveryMessage deliveryMessage;
    private GraphicInterface graphicInterface;
    private List<ShowAvCells> showAvCellsList;
    private App app;

    public DeliveryMessage getDeliveryMessage() {
        return deliveryMessage;
    }

    public void setDeliveryMessage(DeliveryMessage deliveryMessage) {
        this.deliveryMessage = deliveryMessage;
    }

    SelectPawnRequestCommand pawnChosen;

    public CommandGUIManager(Socket socket) throws IOException {
        this.deliveryMessage = new DeliveryMessage(socket);
        showAvCellsList = new ArrayList<>();
        pawnChosen = null;

    }

    public void selectAction(SelectPawnRequestCommand pawnSelected, SelectCellRequestCommand cellSelected){
        if(pawnChosen == null&& pawnSelected!=null) {
            showCells();
            pawnChosen = pawnSelected;
        }else {
            graphicInterface.resetColorBoard();
            sendActionToServer(cellSelected);
        }
    }

    private void sendActionToServer(SelectCellRequestCommand cellSelected){
        String pawn = pawnChosen.execute();
        String cell = cellSelected.execute();

        deliveryMessage.send(pawn+cell);
    }


    private void showCells(){
        int pawnSelected = Integer.parseInt(pawnChosen.execute());
        if(showAvCellsList.get(pawnSelected) != null)
            showAvCellsList.get(pawnSelected).execute(graphicInterface);
    }

    public void manageCommand(GeneralStringRequestCommand generalString){
        deliveryMessage.send(generalString.execute());
    }

    @Override
    public void manageCommand(ReplyCommand replyCommand) {
    }

    public void manageCommand(UsePowerRequestCommand usePowerRequestCommand){
       // graphicInterface.turnOffGodBottom();
        deliveryMessage.send(usePowerRequestCommand.execute());
    }

    public void manageCommand(DeselectWorkerRequestCommand deselectWorkerRequestCommand){
        graphicInterface.resetColorBoard();
        deselectWorkerRequestCommand.execute(graphicInterface);
        pawnChosen=null;
    }

    public void manageCommand(SentenceBottomRequestCommand sentenceBottomRequestCommand){
        sentenceBottomRequestCommand.execute(graphicInterface);
    }

    public void manageCommand(PopUpCommand popUpCommand){
        popUpCommand.execute(graphicInterface);
    }

    public void manageAction(ReplyCommand replyCommand, String specification, String playerName, String playerColor){
        //graphicInterface.printBottom(createActionPhrase(specification, playerName, playerColor));
        replyCommand.execute(graphicInterface);
    }

    public void manageCommand(ShowAvCells showAvCells) {
        showAvCellsList.add(showAvCells);
    }

    public void setGraphicInterface(GraphicInterface graphicInterface) {
        this.graphicInterface = graphicInterface;
    }

    public void setApp(App app) {
        this.app = app;
    }
}

