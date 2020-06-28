package it.polimi.ingsw;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.CommunicationSentences.createAll;
import static it.polimi.ingsw.FinalCommunication.*;

public class CommandGUIManager implements Command {

    private DeliveryMessage deliveryMessage;
    private GraphicInterface graphicInterface;
    private List<ShowAvCells> showAvCellsList;
    private List<ReplyCommand> replyCommandList;
    private List<TransitionSceneCommand> switchingScene;
    private App app;

    SelectPawnRequestCommand pawnChosen;

    public CommandGUIManager(Socket socket) throws IOException {
        this.deliveryMessage = new DeliveryMessage(socket);
        deliveryMessage.setCommand(this);
        showAvCellsList = new ArrayList<>();
        pawnChosen = null;
        replyCommandList = new ArrayList<>();
        switchingScene = new ArrayList<>();
        createAll();
    }


    public DeliveryMessage getDeliveryMessage() {
        return deliveryMessage;
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
        if(pawnChosen!=null) {
            String pawn = pawnChosen.execute();
            String cell = cellSelected.execute();

            deliveryMessage.send(pawn + cell);
            pawnChosen = null;
        }
    }


    private void showCells(){
        int pawnSelected = Integer.parseInt(pawnChosen.execute());
        if(showAvCellsList.get(pawnSelected) != null)
            showAvCellsList.get(pawnSelected).execute(graphicInterface);
    }

    public void manageCommand(GeneralStringRequestCommand generalString){
        if(switchingScene.size()>0) {
                switchingScene.get(0).execute(app);
                switchingScene.remove(0);
            }
        deliveryMessage.send(generalString.execute());
    }

    public void manageCommand(UsePowerRequestCommand usePowerRequestCommand){
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
        if(limitedOptionsCommand.getSpecification().equals(COLOR)){
            TransitionSceneCommand transitionSceneCommand = new TransitionSceneCommand(null, null, WAITING_COLOR);
            switchingScene.add(transitionSceneCommand);
        }else if(limitedOptionsCommand.getSpecification().equals(PRE_LOBBY)){
            TransitionSceneCommand transitionSceneCommand = new TransitionSceneCommand(null, null, WAITING_PLAYER);
            switchingScene.add(transitionSceneCommand);
        }if(limitedOptionsCommand.getSpecification().equals(GODCHOICE)){
            TransitionSceneCommand transitionSceneCommand = new TransitionSceneCommand(null, null, GODCHOICE);
            switchingScene.add(transitionSceneCommand);
        }

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

