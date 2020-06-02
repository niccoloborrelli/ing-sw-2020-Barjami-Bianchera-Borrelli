package it.polimi.ingsw;

import java.io.IOException;

public class ActionState extends AbstractActionState {
    private static final String actionType1 = "move";
    private static final String actionType2 = "build";
    private static final int firstIndex=0;
    private Worker actingWorker;
    private Space spaceToAct;
    private Space startingSpace;
    private String action;
    ActionState(Player player) {
        super(player);
    }

    @Override
    public void onStateTransition() throws IOException {
        WorkerSpaceCouple input=player.getLastReceivedInput();
        actingSetting(input);
        for(Worker tempWorker:player.getWorkers())
            tempWorker.clearLists();
        concreteActing(input);
    }

    private void actingSetting(WorkerSpaceCouple input){
        action=player.getActionsToPerform().get(firstIndex);
        this.actingWorker=input.getWorker();
        this.spaceToAct=input.getSpace();
        this.startingSpace=actingWorker.getWorkerSpace();
    }

    private void concreteActing(WorkerSpaceCouple input) throws IOException {
        if(spaceToAct.getOccupator()==null&&action.equals(actionType1))
            movingAction(input);
        else if(action.equals(actionType2))
            buildingAction(input);
    }

    private void movingAction(WorkerSpaceCouple input) throws IOException {
        input.setSpace(actingWorker.getWorkerSpace());
        move(actingWorker, spaceToAct);
        actingWorker.setMovedThisTurn(true);
        notifyActionPerformed(input,action);
        player.getStateManager().getTurnManager().checkWin();
        if(player.isHasWon())
            notifyWin();
        if(player.isInGame()){
            for(Worker tempWorker:player.getWorkers())
                tempWorker.clearLists();
            player.getStateManager().setNextState(player);
        }
    }

    private void buildingAction(WorkerSpaceCouple input) throws IOException {
        input.setSpace(spaceToAct);
        build(actingWorker, spaceToAct);
        notifyActionPerformed(input,action);
        player.getStateManager().getTurnManager().checkWin();
        if(player.isHasWon())
            notifyWin();
        if(player.isInGame()) {
            player.getStateManager().setNextState(player);
        }
    }
    /**
     * This method implements the base move
     * @param movingWorker is the worker that moves
     * @param finishSpace is the final space of the worker
     */
    private void move(Worker movingWorker,Space finishSpace){
            player.removeAction();
            movingWorker.setMovedThisTurn(true);
            movingWorker.setCantBuild(false);
            movingWorker.setLastSpaceOccupied(movingWorker.getWorkerSpace());
            changeSpace(movingWorker, finishSpace);
            setOtherWorkers(movingWorker);
    }

    /**
     * This method change the worker space and the space occupatore
     * @param worker is the worker of the move
     * @param finishSpace is the final space of the worker
     */
    private void changeSpace(Worker worker, Space finishSpace) {
        worker.getWorkerSpace().setOccupator(null);
        worker.setWorkerSpace(finishSpace);
        finishSpace.setOccupator(worker);
    }

    private void build(Worker buildingWorker,Space finishSpace){
        player.removeAction();
        buildingWorker.setCantMove(false);

        upgradeLevel(finishSpace, buildingWorker);
        setOtherWorkers(buildingWorker);
        buildingWorker.setLastSpaceBuilt(finishSpace);
    }

    /**
     * This method change the level of a space
     * @param buildSpace is the space where the level is going to change
     */
    private void upgradeLevel(Space buildSpace,Worker worker){
        if(buildSpace.getLevel()==3)
            worker.setMustBuildDome(true);

        if(!worker.isMustBuildDome())
            buildSpace.setLevel(buildSpace.getLevel() + 1);

        domeBuilding(buildSpace,worker.isMustBuildDome());

        for(Worker wtemp:player.getWorkers())
            wtemp.setMustBuildDome(false);
    }

    /**
     * This method set the boolean "HasDome" as true if
     * the space level is 4
     * @param buildSpace is the space to control
     */
    private void domeBuilding(Space buildSpace,boolean mustBuildDome){
        if (mustBuildDome)
            buildSpace.setHasDome(true);
    }

    private void setOtherWorkers(Worker activeWorker){
        Player player=activeWorker.getWorkerPlayer();
        for (Worker w:player.getWorkers()) {
            if(w!=activeWorker){
                w.setCantBuild(true);
                w.setCantMove(true);
            }
        }
    }

    public Worker getActingWorker() {
        return actingWorker;
    }

    public Space getSpaceToAct() {
        return spaceToAct;
    }

    public Space getStartingSpace(){
        return startingSpace;
    }

    public String toString(){
        return "ActionState";
    }

    @Override
    public String getAction() {
        return action;
    }
}