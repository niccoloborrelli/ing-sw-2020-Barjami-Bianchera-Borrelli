package it.polimi.ingsw;

import java.io.IOException;

public class ActionState extends AbstractActionState {
    private static final String actionType1 = "move";
    private static final String actionType2 = "build";
    private static final int workerInInput = 1;
    private static final int rowInInput = 2;
    private static final int columnInInput = 3;
    private static final int firstIndex=0;
    private Worker actingWorker;
    private Space spaceToAct;
    private Space startingSpace;

    ActionState(Player player) {
        super(player);
    }

    @Override
    public void onStateTransition() {
        String input=player.getLastReceivedInput();
        String action=player.getActionsToPerform().get(firstIndex);
        Worker actingWorker=player.getWorkers().get(parseInput(input,workerInInput));
        int row=parseInput(input,rowInInput);
        int column=parseInput(input,columnInInput);
        Space spaceToAct=player.getIslandBoard().getSpace(row,column);
        this.actingWorker=actingWorker;
        this.spaceToAct=spaceToAct;
        this.startingSpace=actingWorker.getWorkerSpace();

        if(spaceToAct.getOccupator()==null) {
            if (action.equals(actionType1)) {
                move(actingWorker, spaceToAct);
            } else if (action.equals((actionType2))) {
                build(actingWorker, spaceToAct);
            }

            game.checkForWin(); //LA CHECK FOR WIN LA FACCIO QUI
            player.getStateManager().setNextState(this);
        }
    }

    private int parseInput(String input,int occurrence){
        String temp;
        int start=0;
        int end=0;
        boolean out;
        String tempString=input;
        for (int i = firstIndex; i < occurrence; i++) {
            start = tempString.indexOf("-");
            tempString = tempString.substring(start+1);
            end = tempString.indexOf("-");
        }
        return Integer.parseInt(tempString.substring(firstIndex,end));
    }

    /**
     * This method implements the base move
     * @param movingWorker is the worker that moves
     * @param finishSpace is the final space of the worker
     */
    private void move(Worker movingWorker,Space finishSpace){
        if(finishSpace.getOccupator()==null){
            player.removeAction();
            movingWorker.setMovedThisTurn(true);
            movingWorker.setCantBuild(false);
            movingWorker.setLastSpaceOccupied(movingWorker.getWorkerSpace());
            changeSpace(movingWorker, finishSpace);
            setOtherWorkers(movingWorker);
        }
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
        buildSpace.setLevel(buildSpace.getLevel() + 1);
        domeBuilding(buildSpace);
    }

    /**
     * This method set the boolean "HasDome" as true if
     * the space level is 4
     * @param buildSpace is the space to control
     */
    private void domeBuilding(Space buildSpace){
        if (buildSpace.getLevel() == 4)
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
}