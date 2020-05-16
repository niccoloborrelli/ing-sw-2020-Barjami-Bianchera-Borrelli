package it.polimi.ingsw;

public class CanBuildTwiceNotDomeFlow extends FlowChanger {

    /*
    Your worker may build one additional block (not dome) on top of your first block
    HEPHAESTUS
    */
    private static final int MAXIMUMLEVEL = 3;
    private static final String actionType = "build";

    /**
     * This method change the flow of the action to perform by a player
     */
    @Override
    public void changeFlow(Player player) {
        player.getActionsToPerform().add(actionType);
        if(player.getWorkers().get(0).isChosen())
            player.getWorkers().get(0).setCantBuildDome(true);
        else
            player.getWorkers().get(1).setCantBuildDome(true);
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

        return workerChosen.getLastSpaceBuilt().getLevel() < MAXIMUMLEVEL;
    }
}
