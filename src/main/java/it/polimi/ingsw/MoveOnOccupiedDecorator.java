package it.polimi.ingsw;

import java.io.IOException;

import static it.polimi.ingsw.DefinedValues.MINSIZE;
import static it.polimi.ingsw.FinalCommunication.*;

public class MoveOnOccupiedDecorator extends ActionStateDecorator{

    /**
     * Modifies the move action.
     */

    private static final String ACTIONTYPE1="move";
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
        String action=player.getActionsToPerform().get(MINSIZE);
        decorated.onStateTransition();
        Space spaceToAct=decorated.getSpaceToAct();
        Worker actingWorker=decorated.getActingWorker();
        if(spaceToAct.getOccupator()!=null&&spaceToAct.getOccupator()!=actingWorker){
            if(action.equals(ACTIONTYPE1)) {
                moveAbility(actingWorker,spaceToAct);
                actingWorker.setMovedThisTurn(true);
                player.getStateManager().getTurnManager().checkWin();
                if(player.isHasWon())
                    notifyWin();
                if(player.isInGame()) {
                    for(Worker tempWorker: player.getWorkers())
                        tempWorker.clearLists();
                    player.getStateManager().setNextState(player);
                }
            }
        }
    }

    /**
     * Permits swap or push back abilities.
     * @param actingWorker is worker who has to move.
     * @param spaceToAct is space which worker wants to move in.
     */

    private void moveAbility(Worker actingWorker,Space spaceToAct){
        player.removeAction();
        actingWorker.setMovedThisTurn(true);
        actingWorker.setCantBuild(false);

        if(effect.equals(SWAP))
            swap(actingWorker,spaceToAct);
        else if(effect.equals(PUSHBACK))
            pushBack(actingWorker,spaceToAct);
    }

    /**
     * Swaps acting worker and worker in movement space.
     * @param actingWorker is worker that has to move.
     * @param spaceToAct is space in which worker would move.
     */

    private void swap(Worker actingWorker,Space spaceToAct){
        notifyDeleted(player, actingWorker);
        Worker enemyToSwap=spaceToAct.getOccupator();
        notifyEnemy(enemyToSwap, true);
        Space startingSpace=actingWorker.getWorkerSpace();
        actingWorker.setLastSpaceOccupied(startingSpace);
        actingWorker.setWorkerSpace(spaceToAct);
        enemyToSwap.setWorkerSpace(startingSpace);
        spaceToAct.setOccupator(actingWorker);
        startingSpace.setOccupator(enemyToSwap);
        enemyToSwap.setLastSpaceOccupied(spaceToAct);
        notifyEnemy(enemyToSwap, false);
        notifySwap(player, actingWorker);
    }

    /**
     * Notifies enemy of changes.
     * @param enemyToSwap is enemy to swap.
     * @param deleted if it has to be deleted.
     */

    private void notifyEnemy(Worker enemyToSwap, boolean deleted){
        Player enemyPlayer = findPlayer(enemyToSwap);
        if(deleted)
            notifyDeleted(enemyPlayer, enemyToSwap);
        else
            notifySwap(enemyPlayer, enemyToSwap);
    }

    /**
     * Notifies swap.
     * @param player is player that do the swap.
     * @param worker is worker that swap.
     */

    private void notifySwap(Player player, Worker worker){
        LastChange lastChange = new LastChange();
        lastChange.setCode(UPDATE_GAME_FIELD);
        lastChange.setSpecification(WORKERSETTING);
        lastChange.setWorker(worker);
        lastChange.setSpace(worker.getWorkerSpace());
        player.notify(lastChange);
    }

    /**
     * Notifies deletion of worker.
     * @param player has workers.
     * @param worker is worker to delete.
     */

    private void notifyDeleted(Player player, Worker worker){
        LastChange lastChange = new LastChange();
        Space spaceOfWorker = worker.getWorkerSpace();
        worker.setWorkerSpace(new Space(INVALID_VALUE,INVALID_VALUE));
        lastChange.setSpecification(DELETED);
        lastChange.setCode(UPDATE_GAME_FIELD);
        lastChange.setWorker(worker);
        lastChange.setSpace(spaceOfWorker);
        player.notify(lastChange);
        worker.setWorkerSpace(spaceOfWorker);
    }

    /**
     * Finds a player from his worker.
     * @param workerOfPlayer is player's worker.
     * @return the player of worker.
     */

    private Player findPlayer(Worker workerOfPlayer){
        Player playerToFind = player;
        TurnManager turnManager = player.getStateManager().getTurnManager();
        for(Player player: turnManager.getPlayers()){
            if(player.getWorkers().contains(workerOfPlayer))
                playerToFind = player;
        }

        return playerToFind;
    }

    /**
     * Pushes back enemy worker.
     * @param actingWorker is worker that pushes.
     * @param spaceToAct is space in which worker would move.
     */

    private void pushBack(Worker actingWorker,Space spaceToAct){
        int rowToPush=spaceToAct.getRow()*2-actingWorker.getWorkerSpace().getRow(); //trovo la posizione dietro a quella di acting worker rispetto a spaceToAct
        int columnToPush=spaceToAct.getColumn()*2-actingWorker.getWorkerSpace().getColumn();
        Space startingSpace=actingWorker.getWorkerSpace();
        Space finishPushSpace=player.getIslandBoard().getSpace(rowToPush,columnToPush);

        Worker workerToPush=spaceToAct.getOccupator(); //sposto il worker in posizione spaceToAct nel space trovato prima
        workerToPush.setLastSpaceOccupied(spaceToAct);
        workerToPush.setWorkerSpace(finishPushSpace);
        notifyPushed(workerToPush, workerToPush.getLastSpaceOccupied());

        actingWorker.setWorkerSpace(spaceToAct); //sposto actingWorker in spaceToAct
        actingWorker.setLastSpaceOccupied(startingSpace);
        notifyActionPerformed(new WorkerSpaceCouple(actingWorker, actingWorker.getLastSpaceOccupied()), MOVE);

        finishPushSpace.setOccupator(workerToPush);
        spaceToAct.setOccupator(actingWorker);
        startingSpace.setOccupator(null);
    }

    /**
     * Notifies push-back.
     * @param worker is worker that's be pushed.
     * @param space is space in which it will be pushed.
     */

    private void notifyPushed(Worker worker, Space space){
        Player player = findPlayer(worker);
        LastChange lastChange = new LastChange();
        lastChange.setCode(UPDATE_GAME_FIELD);
        lastChange.setSpecification(MOVE);
        lastChange.setWorker(worker);
        lastChange.setSpace(space);
        player.notify(lastChange);

    }


}
