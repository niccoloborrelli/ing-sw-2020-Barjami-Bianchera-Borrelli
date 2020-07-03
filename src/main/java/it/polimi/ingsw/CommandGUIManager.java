package it.polimi.ingsw;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.CommunicationSentences.createAll;
import static it.polimi.ingsw.DefinedValues.MINSIZE;
import static it.polimi.ingsw.FinalCommunication.*;

public class CommandGUIManager implements Command {

    /**
     * Manages every possible command that could generate client or server side.
     */
    private static final int maxSizeList=2;
    private static final int settingValue=1;

    private DeliveryMessage deliveryMessage;
    private GraphicInterface graphicInterface;
    private List<ShowAvCells> showAvCellsList;
    private List<SettingPawnCommand> settingPawnCommands;
    private List<RemovingCommand> removingCommandList;
    private List<TransitionSceneCommand> switchingScene;
    private App app;
    SelectPawnRequestCommand pawnChosen;

    public CommandGUIManager(Socket socket) throws IOException {
        this.deliveryMessage = new DeliveryMessage(socket);
        deliveryMessage.setCommand(this);
        showAvCellsList = new ArrayList<>();
        pawnChosen = null;
        settingPawnCommands = new ArrayList<>();
        removingCommandList = new ArrayList<>();
        switchingScene = new ArrayList<>();
        createAll();
    }


    public DeliveryMessage getDeliveryMessage() {
        return deliveryMessage;
    }


    /**
     * Selects a command between two command passed.
     * @param pawnSelected is command that represents pawn selected.
     * @param cellSelected is command that represents cell selected.
     */
    public void selectAction(SelectPawnRequestCommand pawnSelected, SelectCellRequestCommand cellSelected){
        if(pawnChosen == null && pawnSelected!=null) {
            pawnChosen = pawnSelected;
            if(showAvCellsList.size()>MINSIZE)
                showCells();
        }else {
            graphicInterface.resetColorBoard();
            sendActionToServer(cellSelected);
            pawnChosen = null;
        }
    }

    /**
     * Sends action request to server.
     * @param cellSelected is command that represented cell selected.
     */
    private void sendActionToServer(SelectCellRequestCommand cellSelected){
        if(pawnChosen!=null) {
            String pawn = pawnChosen.execute();
            String cell = cellSelected.execute();

            deliveryMessage.send(pawn + cell);
            pawnChosen = null;
        }
    }


    /**
     * Shows available cells for pawn chosen.
     */
    private void showCells(){
        String indexOfPawn = pawnChosen.execute().substring(MINSIZE+1);
        int pawnSelected = Integer.parseInt(indexOfPawn);
        if(pawnSelected<showAvCellsList.size()) {
            if (showAvCellsList.get(pawnSelected) != null)
                showAvCellsList.get(pawnSelected).execute(graphicInterface);
        }
    }

    public void manageCommand(GeneralStringRequestCommand generalString){
        if(switchingScene.size()>MINSIZE) {
                switchingScene.get(MINSIZE).execute(app);
                switchingScene.remove(MINSIZE);
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

    public void manageCommand(SetUpCommand setUpCommand){
        if(graphicInterface==null)
            app.set3DGui();
        if(!graphicInterface.isSetAllsWorker())
            setUpCommand.execute(graphicInterface);
    }

    @Override
    public void manageCommand(QuitCommand quitCommand) {
        deliveryMessage.quitGame(true);
        if(graphicInterface!=null)
            quitCommand.execute(graphicInterface);
        else
            quitCommand.execute(app);
    }

    public void manageCommand(SentenceBottomRequestCommand sentenceBottomRequestCommand){
        sentenceBottomRequestCommand.execute(graphicInterface);

    }

    public void manageCommand(PopUpCommand popUpCommand){
        for(RemovingCommand removingCommand: removingCommandList) {
            removingCommand.execute(graphicInterface);
        }
        removingCommandList.clear();
        showAvCellsList.clear();
        popUpCommand.execute(graphicInterface);
    }

    public void manageCommand(ShowAvCells showAvCells) {
        showAvCellsList.add(showAvCells);
    }

    public void manageCommand(RemovingCommand removingCommand){
        removingCommandList.add(removingCommand);
    }

    public void setGraphicInterface(GraphicInterface graphicInterface) {
        this.graphicInterface = graphicInterface;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public void manageCommand(SettingPawnCommand settingPawnCommand){
        if(graphicInterface==null)
            app.set3DGui();
        if(settingPawnCommands.size()==settingValue && removingCommandList.size()==maxSizeList){
            settingPawnCommands.add(settingPawnCommand);
            createSwap();
            settingPawnCommands.clear();
            removingCommandList.clear();
            showAvCellsList.clear();
        }else if(removingCommandList.size()==maxSizeList){
            settingPawnCommands.add(settingPawnCommand);
        }else
            settingPawnCommand.execute(graphicInterface);
    }

    private void createSwap(){
        graphicInterface.move(settingPawnCommands.get(MINSIZE).getRow(), settingPawnCommands.get(MINSIZE).getColumn(),
                settingPawnCommands.get(MINSIZE+1).getRow(), settingPawnCommands.get(MINSIZE+1).getColumn(), settingPawnCommands.get(MINSIZE+1).getWorker());
    }

    @Override
    public void manageCommand(LimitedOptionsCommand limitedOptionsCommand) {
        if(limitedOptionsCommand.getSpecification().equals(COLOR)){
            TransitionSceneCommand transitionSceneCommand = new TransitionSceneCommand(null, null, WAITING_COLOR);
            switchingScene.add(transitionSceneCommand);
        }else if(limitedOptionsCommand.getSpecification().equals(PRE_LOBBY)){
            TransitionSceneCommand transitionSceneCommand = new TransitionSceneCommand(null, null, WAITING_PLAYER);
            switchingScene.add(transitionSceneCommand);
        }else if(limitedOptionsCommand.getSpecification().equals(GODCHOICE)) {
            TransitionSceneCommand transitionSceneCommand = new TransitionSceneCommand(null, null, WAITING_GAME);
            switchingScene.add(transitionSceneCommand);

        }
        if(limitedOptionsCommand.getSpecification().equals(POWER))
            limitedOptionsCommand.execute(graphicInterface);
        else
            limitedOptionsCommand.execute(app);
    }

    @Override
    public void manageCommand(TransitionSceneCommand transitionSceneCommand) {
        transitionSceneCommand.execute(app);
    }

    @Override
    public void manageCommand(MoveUpdateCommand moveUpdateCommand) {
        showAvCellsList.clear();
        moveUpdateCommand.execute(graphicInterface);
    }

    @Override
    public void manageCommand(BuildUpdateCommand buildUpdateCommand) {
        showAvCellsList.clear();
        buildUpdateCommand.execute(graphicInterface);
    }

    @Override
    public void manageCommand(ExitCommand exitCommand){
        deliveryMessage.quitGame(true);
    }


}

