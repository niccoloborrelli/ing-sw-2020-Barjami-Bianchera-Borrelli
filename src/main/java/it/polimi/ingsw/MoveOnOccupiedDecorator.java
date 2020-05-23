package it.polimi.ingsw;

import java.io.IOException;

public class MoveOnOccupiedDecorator extends ActionStateDecorator{

    private static final String actionType1="move";
    private static final String actionType2="build";
    private static final String PushBack="Push";
    private static final String Swap="Swap";

    MoveOnOccupiedDecorator(AbstractActionState decorated,String effect) {
        super(decorated,effect);
    }


    @Override
    public void onInput(Visitor visitor){
        decorated.onInput(visitor);
    }

    @Override
    public void onStateTransition() throws IOException {
        String action=player.getActionsToPerform().get(0);
        decorated.onStateTransition();
        Space spaceToAct=decorated.getSpaceToAct();
        Worker actingWorker=decorated.getActingWorker();
        if(spaceToAct.getOccupator()!=null&&spaceToAct.getOccupator()!=actingWorker){
            if(action.equals(actionType1)) {
                moveAbility(actingWorker,spaceToAct);
                player.getStateManager().getTurnManager().checkWin(); //LA CHECK FOR WIN LA FACCIO QUI
                player.getStateManager().setNextState(this);
            }
        }
    }

    private void moveAbility(Worker actingWorker,Space spaceToAct){
        player.removeAction();
        actingWorker.setMovedThisTurn(true);
        actingWorker.setCantBuild(false);

        if(effect.equals(Swap))
            swap(actingWorker,spaceToAct);
        else if(effect.equals(PushBack))
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
