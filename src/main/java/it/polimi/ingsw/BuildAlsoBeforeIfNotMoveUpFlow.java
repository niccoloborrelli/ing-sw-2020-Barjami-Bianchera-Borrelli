package it.polimi.ingsw;

import java.util.List;

public class BuildAlsoBeforeIfNotMoveUpFlow extends FlowChanger {

    /*
    If your worker not move up, it may build both before and after moving
    PROMETHEUS
    */
    private static final String actionType = "build";

    /**
     * This method change the flow of the action to perform by a player
     */
    @Override
    public void changeFlow(Player player) {
        player.getActionsToPerform().add(0, actionType);
        for (Worker w: player.getWorkers()) {
            w.setCantMoveUp(true);
        }
    }

    /**
     * This method check if a power is usable by a player
     * @return true if usable, false otherwise
     */
    @Override
    public boolean isUsable(Player player) {
        CheckingUtility.restriction(player, actionType);

        return (player.getWorkers().get(0).getPossibleBuilding().size() > 0 ||
                player.getWorkers().get(1).getPossibleBuilding().size() > 0);
    }
}
