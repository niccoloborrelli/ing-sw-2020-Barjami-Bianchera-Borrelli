package it.polimi.ingsw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Player implements Observed{
    private String playerName;
    private String playerColor;
    private String playerGod;
    private List<Worker> workers;
    private WinConditionAB winCondition;
    private List<String> actionsToPerform;
    private StateManager stateManager;
    private WorkerSpaceCouple lastReceivedInput;
    private IslandBoard islandBoard;
    private LastChange lastChange;

    private static final int NUMBEROFWORKERS=2;
    private boolean powerActivated;
    private boolean powerNotUsable;
    private boolean empty;
    private boolean inGame;
    private boolean cantSwap;
    private boolean cantPush;
    private boolean powerUsed;
    private boolean domeEveryWhere;
    private boolean hasWon;
    private boolean notHigherNoMove;
    private boolean notMustMoveUp;


    private Controller controller;

    public Player(){
        this.workers = new ArrayList<Worker>();
        this.inGame = true;
        lastChange=new LastChange();
        actionsToPerform=new LinkedList<String>();
        cantSwap=true;
        cantPush=true;
        powerUsed=false;
        domeEveryWhere=false;
        stateManager = new StateManager();
        for(int i=0;i<NUMBEROFWORKERS;i++) {
            workers.add(new Worker());
            workers.get(i).setWorkerPlayer(this);
        }
        actionsToPerform.add("move");
        actionsToPerform.add("build");
        playerGod=null;
        winCondition = new BaseWinCondition();
    }

    public void setLastChange(LastChange lastChange) {
        this.lastChange = lastChange;
    }

    /**
     * calls the onInput of the state in wich the player is set
     * @param visitor the input wich will make the state of player change
     * @throws IOException
     */
    public void onInput(Visitor visitor) throws IOException {
            getState().onInput(visitor);
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

    public WorkerSpaceCouple getLastReceivedInput() {
        return lastReceivedInput;
    }

    public void setLastReceivedInput(WorkerSpaceCouple lastReceivedInput) {
        this.lastReceivedInput = lastReceivedInput;
    }

    public State getState() {
        return stateManager.getCurrent_state();
    }

    public boolean hasActionsToPerform() {
        if (actionsToPerform.size() == 0)
            return false;
        return true;
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
    public void notify(LastChange lastChange){
        controller.update(lastChange);
    }

    public void notifyController(){
        controller.update();
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

    public boolean hasAction(){ return actionsToPerform.size() > 0;}

    public boolean hasMovesToDo(){
        return actionsToPerform.contains("move");
    }

    public boolean hasBuildsToDo(){
        return actionsToPerform.contains("build");
    }

    public void setHasWon(boolean hasWon) { this.hasWon = hasWon; }

    public boolean isHasWon() {
        return hasWon;
    }

    public boolean isChallenger(){
        return this.equals(stateManager.getTurnManager().getPlayers().get(0));
    }

    public boolean isValidGod() {
        return playerGod != null;
    }

    public boolean isWorkerPlaced(){
        for(Worker worker: workers){
            if(worker.getWorkerSpace()==null) {
                return false;
            }
        }
        return true;
    }

    public String toString(){
        return playerName;
    }

    public boolean isGodSetFormed(){
        return stateManager.getTurnManager().getAvailableGods().size()>0 || playerGod!=null;
    }

    public boolean isLastGod(){return stateManager.getTurnManager().getAvailableGods().size()==1;}

    public boolean isChallengerWorkerSet(){
        return stateManager.getTurnManager().getPlayers().get(0).getWorkers().get(0).getWorkerSpace()!=null;
    }

    public boolean isNotHigherNoMove() {
        return notHigherNoMove;
    }

    public void setNotHigherNoMove(boolean notHigherNoMove) {
        this.notHigherNoMove = notHigherNoMove;
    }

    public boolean isNotMustMoveUp() {
        return notMustMoveUp;
    }

    public void setNotMustMoveUp(boolean notMustMoveUp) {
        this.notMustMoveUp = notMustMoveUp;
    }

    public LastChange getLastChange() {
        return lastChange;
    }

}
