package it.polimi.ingsw;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class CommandGUIManager implements Command {

    private DeliveryMessage deliveryMessage;
    private GraphicInterface graphicInterface;
    private List<ShowAvCells> showAvCellsList;
    private List<ReplyCommand> replyCommandList;
    private App app;

    SelectPawnRequestCommand pawnChosen;

    public CommandGUIManager(Socket socket) throws IOException {
        this.deliveryMessage = new DeliveryMessage(socket);
        deliveryMessage.setCommand(this);
        showAvCellsList = new ArrayList<>();
        pawnChosen = null;
        replyCommandList = new ArrayList<>();

    }


    public DeliveryMessage getDeliveryMessage() {
        return deliveryMessage;
    }

    public void selectAction(SelectPawnRequestCommand pawnSelected, SelectCellRequestCommand cellSelected){
        if(pawnChosen == null&& pawnSelected!=null) {
            showCells();
            pawnChosen = pawnSelected;
        }else {
            //graphicInterface.resetColorBoard();
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
        System.out.println(generalString.execute());
        deliveryMessage.send(generalString.execute());
    }

    public void manageCommand(UsePowerRequestCommand usePowerRequestCommand){
        deliveryMessage.send(usePowerRequestCommand.execute());
    }

    public void manageCommand(DeselectWorkerRequestCommand deselectWorkerRequestCommand){
        //graphicInterface.resetColorBoard();
        deselectWorkerRequestCommand.execute(graphicInterface);
        pawnChosen=null;
    }

    public void manageCommand(SentenceBottomRequestCommand sentenceBottomRequestCommand){
        sentenceBottomRequestCommand.execute(graphicInterface);
    }

    public void manageCommand(PopUpCommand popUpCommand){
        for(ReplyCommand replyCommand: replyCommandList)
            replyCommand.execute(graphicInterface);
        popUpCommand.execute(graphicInterface);
    }

    public void manageAction(ReplyCommand replyCommand, String specification, String playerName, String playerColor){
        //graphicInterface.printBottom(createActionPhrase(specification, playerName, playerColor));
        replyCommand.execute(graphicInterface);
    }

    public void manageCommand(ShowAvCells showAvCells) {
        showAvCellsList.add(showAvCells);
    }

    public void manageCommand(RemovingCommand removingCommand){
        replyCommandList.add(removingCommand);
    }

    public void setGraphicInterface(GraphicInterface graphicInterface) {
        this.graphicInterface = graphicInterface;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public void manageCommand(SettingPawnCommand settingPawnCommand){
        if(replyCommandList.size()==4){
            for(ReplyCommand replyCommand: replyCommandList)
                replyCommand.execute(graphicInterface);
            replyCommandList.clear();
        }else if(replyCommandList.size()==2){
            replyCommandList.add(settingPawnCommand);
        }else
            settingPawnCommand.execute(graphicInterface);
    }

    @Override
    public void manageCommand(LimitedOptionsCommand limitedOptionsCommand) {
        limitedOptionsCommand.execute(app);
    }

    @Override
    public void manageCommand(TransitionSceneCommand transitionSceneCommand) {
        transitionSceneCommand.execute(app);
    }

    @Override
    public void manageCommand(MoveUpdateCommand moveUpdateCommand) {
        moveUpdateCommand.execute(graphicInterface);
    }

    @Override
    public void manageCommand(BuildUpdateCommand buildUpdateCommand) {
        buildUpdateCommand.execute(graphicInterface);
    }
}

