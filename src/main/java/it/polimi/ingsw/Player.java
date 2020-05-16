package it.polimi.ingsw;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Player {
    private String playerName;
    private String playerColor;
    private String playerGod;
    private List<Worker> workers;
    private boolean inGame;
    private WinConditionAB winCondition;
    private Socket socket;
    private State state;
    private List<String> actionsToPerform;
    private StateManager stateManager;
    private String lastReceivedInput;
    private IslandBoard islandBoard;
    private boolean empty=actionsToPerform.isEmpty();

    public Player(Socket sc){
        this.workers = new ArrayList<Worker>();
        this.inGame = true;
        this.socket = sc;
        actionsToPerform=new LinkedList<String>();

    }

    /**
     * calls the onInput of the state in wich the player is set
     * @param input the input wich will make the state of player change
     * @throws IOException
     */
    public void onInput(String input) throws IOException {
            state.onInput(input);
    }

    public void setActionsToPerform(List<String> actions){
        this.actionsToPerform=actions;
    }

    /**
     * Permits to add an actions to actionsToPerform before the players turn has finished, this action is added in the last position of the fifo list
     * @param action is the action to add
     */

    public void addAnAction(String action){
        LinkedList tempList=(LinkedList)this.actionsToPerform;
        tempList.addLast(action);
    }

    /**
     * this method is used tho get the first element of the list ActionsToPerform and to remove it from the list
     * @return first element of the list actionsToPerform
     */
    public String removeAction(){
        LinkedList<String> tempList=(LinkedList)this.actionsToPerform;
        return tempList.pollFirst();
    }

    private void workerSetup(){
        Worker tempWorker=new Worker();
        tempWorker.setWorkerPlayer(this);
        workers.add(tempWorker);
        tempWorker=new Worker();
        tempWorker.setWorkerPlayer(this);
        workers.add(tempWorker);
    }


    /**
     * sets the stateManager, an object of a class wich comands the state flow of the FSM
     * @param stateManager
     */
    public void setStateManager(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    public StateManager getStateManager() {
        return stateManager;
    }

    public List<String> getActionsToPerform() {
        return actionsToPerform;
    }

    public String getLastReceivedInput() {
        return lastReceivedInput;
    }

    public void setLastReceivedInput(String lastReceivedInput) {
        this.lastReceivedInput = lastReceivedInput;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setPlayerColor(String playerColor) {
        this.playerColor = playerColor;
    }

    public void setPlayerGod(String playerGod) {
        this.playerGod = playerGod;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerColor() {
        return playerColor;
    }

    public String getPlayerGod() {
        return playerGod;
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public boolean isInGame() {
        return inGame;
    }

    public WinConditionAB getWinCondition() {
        return winCondition;
    }

    public void setWinCondition(WinConditionAB winCondition) {
        this.winCondition = winCondition;
    }

    public Socket getSocket(){
        return socket;
    }

    public IslandBoard getIslandBoard() {
        return islandBoard;
    }

    public void setIslandBoard(IslandBoard islandBoard) {
        this.islandBoard = islandBoard;
    }

    public boolean isEmpty() {
        return empty;
    }
}
