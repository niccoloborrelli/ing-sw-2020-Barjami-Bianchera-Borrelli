package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

public class AdditionalBuildNoInitialFlow extends FlowChanger {

    /*
    "Your worker may build one additional time, but not in the same space"
    DEMETER
    */
    private static final String actionType = "build";

    /**
     * This method change the flow of the action to perform by a player
     */
    @Override
    public void changeFlow(Player player) {
        player.getActionsToPerform().add(actionType);
        if(player.getWorkers().get(0).isChosen())
            player.getWorkers().get(0).setCantBuildFirstSpace(true);
        else
            player.getWorkers().get(1).setCantBuildFirstSpace(true);
    }

    /**
     * This method check if a power is usable by a player
     * @return true if usable, false otherwise
     */
    @Override
    public boolean isUsable(Player player) {
        Worker workerChosen;
        if(player.getWorkers().get(0).isChosen())
            workerChosen = player.getWorkers().get(0);
        else
            workerChosen = player.getWorkers().get(1);

        CheckingUtility.restriction(player, actionType);
        List<Space> building = new ArrayList<>(workerChosen.getPossibleBuilding());
        building.remove(workerChosen.getLastSpaceBuilt());

        return building.size() > 0;
    }
}
