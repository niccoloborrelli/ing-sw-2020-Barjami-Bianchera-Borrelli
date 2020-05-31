package it.polimi.ingsw;

import java.io.IOException;

public class MoveOnOccupiedDecorator extends ActionStateDecorator{

    private static final String ACTIONTYPE1="move";
    private static final String ACTIONTYPE2="build";
    private static final String PUSHBACK="push";
    private static final String SWAP="swap";

    MoveOnOccupiedDecorator(AbstractActionState decorated,String effect) {
        super(decorated,effect);
    }


    @Override
    public void onInput(Visitor visitor) throws IOException {
        decorated.onInput(visitor);
    }

    @Override
    public void onStateTransition() throws IOException {
        String action=player.getActionsToPerform().get(0);
        decorated.onStateTransition();
        Space spaceToAct=decorated.getSpaceToAct();
        Worker actingWorker=decorated.getActingWorker();
        if(spaceToAct.getOccupator()!=null&&spaceToAct.getOccupator()!=actingWorker){
            if(action.equals(ACTIONTYPE1)) {
                moveAbility(actingWorker,spaceToAct);
                actingWorker.setMovedThisTurn(true);
                notifyActionPerformed(new WorkerSpaceCouple(actingWorker,actingWorker.getWorkerSpace()),action);
                player.getStateManager().getTurnManager().checkWin();
                if(player.isHasWon())
                    notifyWin();
                player.getStateManager().setNextState(player);
            }
        }
    }

    private void moveAbility(Worker actingWorker,Space spaceToAct){
        player.removeAction();
        actingWorker.setMovedThisTurn(true);
        actingWorker.setCantBuild(false);

        if(effect.equals(SWAP))
            swap(actingWorker,spaceToAct);
        else if(effect.equals(PUSHBACK))
            pushBack(actingWorker,spaceToAct);
    }

    private void swap(Worker actingWorker,Space spaceToAct){
        Worker enemyToSwap=spaceToAct.getOccupator();
        Space startingSpace=actingWorker.getWorkerSpace();
        actingWorker.setLastSpaceOccupied(startingSpace);
        actingWorker.setWorkerSpace(spaceToAct);
        enemyToSwap.setWorkerSpace(startingSpace);
        spaceToAct.setOccupator(actingWorker);
        startingSpace.setOccupator(enemyToSwap);
        enemyToSwap.setLastSpaceOccupied(spaceToAct);
    }

    private void pushBack(Worker actingWorker,Space spaceToAct){
        int rowToPush=spaceToAct.getRow()*2-actingWorker.getWorkerSpace().getRow(); //trovo la posizione dietro a quella di acting worker rispetto a spaceToAct
        int columnToPush=spaceToAct.getColumn()*2-actingWorker.getWorkerSpace().getColumn();
        Space startingSpace=actingWorker.getWorkerSpace();
        Space finishPushSpace=player.getIslandBoard().getSpace(rowToPush,columnToPush);

        Worker workerToPush=spaceToAct.getOccupator(); //sposto il worker in posizione spaceToAct nel space trovato prima
        workerToPush.setLastSpaceOccupied(spaceToAct);
        workerToPush.setWorkerSpace(finishPushSpace);

        actingWorker.setWorkerSpace(spaceToAct); //sposto actingWorker in spaceToAct
        actingWorker.setLastSpaceOccupied(startingSpace);

        finishPushSpace.setOccupator(workerToPush);
        spaceToAct.setOccupator(actingWorker);
        startingSpace.setOccupator(null);
    }

}
