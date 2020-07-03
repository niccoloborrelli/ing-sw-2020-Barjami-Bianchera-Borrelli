package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.DefinedValues.*;

public class AdditionalMoveFlow extends FlowChanger {

    /*
    Your worker may move one additional time, but not in the initial space
    ARTEMIS
    */

    /**
     * This method change the flow of the action to perform by a player
     */
    @Override
    public void changeFlow(Player player) {
        player.getActionsToPerform().add(MINSIZE, actionType1);
        Worker workerChosen = getWorkerChosen(player);
        setFlag(workerChosen);
    }

    /**
     * This method check if a power is usable by a player
     * @return true if usable, false otherwise
     */
    @Override
    public boolean isUsable(Player player) {
        Worker workerChosen = getWorkerChosen(player);
        CheckingUtility.calculateValidSpace(player, player.getIslandBoard(), actionType1);
        List<Space> movement = new ArrayList<>(workerChosen.getPossibleMovements());
        movement.remove(workerChosen.getLastSpaceOccupied());

        return movement.size() > MINSIZE;
    }

    /**
     * Finds which worker is chosen.
     * @param player owns workers.
     * @return the worker chosen.
     */
    private Worker getWorkerChosen(Player player){
        if(player.getWorkers().get(firstWorker).isMovedThisTurn())
            return player.getWorkers().get(firstWorker);
        else
            return player.getWorkers().get(secondWorker);
    }

    /**
     * Sets the flag of worker.
     * @param worker is worker checked.
     */
    private void setFlag(Worker worker){
        worker.setCantMoveFirstSpace(true);
    }
}
