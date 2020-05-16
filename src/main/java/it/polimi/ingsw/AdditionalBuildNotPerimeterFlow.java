package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

public class AdditionalBuildNotPerimeterFlow extends FlowChanger {

    /*
    Your worker may build one additional time, but this cannot be on a perimeter space
    HESTIA
    */
    private final int MINROW = 0;
    private final int MAXROW = 4;
    private final int MINCOLUMN = 0;
    private final int MAXCOLUMN = 4;
    private static final String actionType = "build";

    /**
     * This method change the flow of the action to perform by a player
     */
    @Override
    public void changeFlow(Player player) {
        player.getActionsToPerform().add(actionType);
        if(player.getWorkers().get(0).isChosen())
            player.getWorkers().get(0).setCantBuildPerimeter(true);
        else
            player.getWorkers().get(1).setCantBuildPerimeter(true);
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
        building.removeIf(s -> (s.getColumn() == MINCOLUMN || s.getColumn() == MAXCOLUMN || s.getRow() == MINROW || s.getRow() == MAXROW));

        return building.size() > 0;
    }
}
