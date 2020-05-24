package it.polimi.ingsw;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Player implements Observed{
    private String playerName;
    private String playerColor;
    private String playerGod;
    private List<Worker> workers;
    private WinConditionAB winCondition;
    private State state;
    private List<String> actionsToPerform;
    private StateManager stateManager;
    private SpaceInput lastReceivedInput;
    private IslandBoard islandBoard;

    private boolean powerActivated;
    private boolean powerNotUsable;
    private boolean empty;
    private boolean inGame;
    private boolean cantSwap;
    private boolean cantPush;
    private boolean powerUsed;
    private boolean domeEveryWhere;
    private Controller controller;

    public Player(){
        this.workers = new ArrayList<Worker>();
        this.inGame = true;
        actionsToPerform=new LinkedList<String>();
        cantSwap=true;
        cantPush=true;
        powerUsed=false;
        domeEveryWhere=false;
        stateManager = new StateManager();
    }

    /**
     * calls the onInput of the state in wich the player is set
     * @param visitor the input wich will make the state of player change
     * @throws IOException
     */
    public void onInput(Visitor visitor) throws IOException {
            state.onInput(visitor);
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

    public SpaceInput getLastReceivedInput() {
        return lastReceivedInput;
    }

    public void setLastReceivedInput(SpaceInput lastReceivedInput) {
        this.lastReceivedInput = lastReceivedInput;
    }

    public State getState() {
        return state;
    }

    public boolean hasActionsToPerform(){
        if(actionsToPerform.size()==0)
            return false;
        return true;
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

    public IslandBoard getIslandBoard() {
        return islandBoard;
    }

    public void setIslandBoard(IslandBoard islandBoard) {
        this.islandBoard = islandBoard;
    }

    public boolean isEmpty() {
        return actionsToPerform.isEmpty();
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public boolean isCantSwap() {
        return cantSwap;
    }

    public void setCantSwap(boolean cantSwap) {
        this.cantSwap = cantSwap;
    }

    public boolean isCantPush() {
        return cantPush;
    }

    public void setCantPush(boolean cantPush) {
        this.cantPush = cantPush;
    }

    public boolean isPowerUsed() {
        return powerUsed;
    }

    public void setPowerUsed(boolean powerUsed) {
        this.powerUsed = powerUsed;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void notify(SpaceInput spaceInput, String action) {
        controller.update(spaceInput,action);
    }

    @Override
    public void notify(int code) {
        controller.update(code);
    }

    public boolean isDomeEveryWhere() {
        return domeEveryWhere;
    }

    public void setDomeEveryWhere(boolean domeEveryWhere) {
        this.domeEveryWhere = domeEveryWhere;
    }

    public boolean isPowerNotUsable() {
        return powerNotUsable;
    }

    public void setPowerNotUsable(boolean powerNotUsable) {
        this.powerNotUsable = powerNotUsable;
    }

    public boolean isPowerActivated() {
        return powerActivated;
    }

    public void setPowerActivated(boolean powerActivated) {
        this.powerActivated = powerActivated;
    }

    public boolean hasMovesToDo(){
        if(actionsToPerform.contains("move"))
            return false;
        return  true;
    }
    public boolean hasBuildsToDo(){
        if(actionsToPerform.contains("build"))
            return false;
        return  true;
    }
}
