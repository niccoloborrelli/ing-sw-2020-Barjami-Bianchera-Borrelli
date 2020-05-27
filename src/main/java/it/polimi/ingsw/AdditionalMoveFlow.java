package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.DefinedValues.actionType1;

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
        player.getActionsToPerform().add(0, actionType1);
        if(!player.getWorkers().get(0).isCantMove())
            player.getWorkers().get(0).setCantMoveFirstSpace(true);
        else
            player.getWorkers().get(1).setCantMoveFirstSpace(true);
    }

    /**
     * This method check if a power is usable by a player
     * @return true if usable, false otherwise
     */
    @Override
    public boolean isUsable(Player player) {
        Worker workerChosen;
        if(!player.getWorkers().get(0).isCantMove())
            workerChosen = player.getWorkers().get(0);
        else
            workerChosen = player.getWorkers().get(1);

        CheckingUtility.calculateValidSpace(player,player.getIslandBoard(), actionType1);
        List<Space> movement = new ArrayList<>(workerChosen.getPossibleMovements());
        movement.remove(workerChosen.getLastSpaceOccupied());

        return movement.size() > 0;
    }
}
