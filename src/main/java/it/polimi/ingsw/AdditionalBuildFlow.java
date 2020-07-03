package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.DefinedValues.*;

public class AdditionalBuildFlow extends FlowChanger {

    private static final String INITIALSPACE = "initialSpace";
    private static final String PERIMETERSPACE = "perimeterSpace";
    private static final String MOVEUP = "moveUp";
    private static final String DOME = "dome";
    private static final String FALSE = "false";
    private static final String TRUE = "true";


    /*
    Your worker may build one additional time, but not in the same space
    DEMETER

    Your worker may build one additional time, but this cannot be on a perimeter space
    HESTIA

    If your worker not move up, it may build both before and after moving
    PROMETHEUS

    Your worker may build one additional block (not dome) on top of your first block
    HEPHAESTUS
    */
    private boolean noInitialSpace;
    private boolean noPerimeter;
    private boolean beforeMove;
    private boolean noDome;

    /**
     * @param typeBuild indicates which power is activated
     */
    public AdditionalBuildFlow(String typeBuild){
        switch (typeBuild){
            case INITIALSPACE:
                noInitialSpace = true;
                break;
            case PERIMETERSPACE:
                noPerimeter = true;
                break;
            case MOVEUP:
                beforeMove = true;
                break;
            case DOME:
                noDome = true;
        }
    }

    /**
     * This method change the flow of the action to perform by a player
     */
    @Override
    public void changeFlow(Player player) {
        if(beforeMove){
            player.getActionsToPerform().add(MINSIZE, actionType2);
            setFlagBeforeMove(player);
            return;
        }
        player.getActionsToPerform().add(actionType2);
        Worker workerChosen = getWorkerChosen(player);
        setFlag(workerChosen);
    }

    /**
     * This method check if a power is usable by a player
     * @return true if usable, false otherwise
     */
    @Override
    public boolean isUsable(Player player) {
        if(beforeMove){
            setPlayerCantBuild(player, FALSE);
            CheckingUtility.calculateValidSpace(player, player.getIslandBoard(), actionType2);
            setPlayerCantBuild(player, TRUE);
            return playerCanBuildAndNotGoUp(player);
        }

        Worker workerChosen = getWorkerChosen(player);
        CheckingUtility.calculateValidSpace(player, player.getIslandBoard(), actionType2);
        List<Space> building = new ArrayList<>(workerChosen.getPossibleBuilding());

        if(noInitialSpace){
            building.remove(workerChosen.getLastSpaceBuilt());
        }
        else if(noPerimeter){
            building.removeIf(s -> (s.getColumn() == MINCOLUMN || s.getColumn() == MAXCOLUMN || s.getRow() == MINROW || s.getRow() == MAXROW));
        }
        else if(noDome){
            return workerChosen.getLastSpaceBuilt().getLevel() < MAXIMUMLEVEL;
        }
        return building.size() > MINSIZE;
    }

    /**
     * Finds which worker is chosen.
     * @param player owns workers.
     * @return the worker chosen.
     */
    private Worker getWorkerChosen(Player player){
        if(!player.getWorkers().get(firstWorker).isCantBuild())
            return player.getWorkers().get(firstWorker);
        else
            return player.getWorkers().get(secondWorker);
    }

    /**
     * Sets the flag of worker.
     * @param worker is worker checked.
     */
    private void setFlag(Worker worker){
        if(noInitialSpace)
            worker.setCantBuildFirstSpace(true);
        else if(noPerimeter)
            worker.setCantBuildPerimeter(true);
        else if(noDome) {
            worker.setCanBuildOnlyInTheFirstPlace(true);
            worker.setCantBuildDome(true);
        }
    }

    /**
     * This method checks if a player with "BuildAlsoBeforeIfNotMoveUp" can use his power
     * @param player is the player with the "BuildAlsoBeforeIfNotMoveUp" power
     * @return true if possible, false otherwise
     */
    private boolean playerCanBuildAndNotGoUp(Player player){
        CheckingUtility.calculateValidSpace(player, player.getIslandBoard(), actionType1);
        List<Space> movement0 = new ArrayList<>(player.getWorkers().get(firstWorker).getPossibleMovements());
        movement0.removeIf(space -> space.getLevel() > player.getWorkers().get(firstWorker).getWorkerSpace().getLevel());
        List<Space> movement1 = new ArrayList<>(player.getWorkers().get(secondWorker).getPossibleMovements());
        movement1.removeIf(space -> space.getLevel() > player.getWorkers().get(secondWorker).getWorkerSpace().getLevel());

        return ((player.getWorkers().get(firstWorker).getPossibleBuilding().size() > MINSIZE && movement0.size() > MINSIZE) ||
                (player.getWorkers().get(secondWorker).getPossibleBuilding().size() > MINSIZE && movement1.size() > MINSIZE));
    }

    /**
     * Sets flag befor move.
     * @param player is set flags' player.
     */
    private void setFlagBeforeMove(Player player){
        for (Worker w: player.getWorkers()) {
            w.setCantMoveUp(true);
            w.setCantBuild(false);
        }
    }

    /**
     * Sets flag "can't build" to player.
     * @param player is player with flags.
     * @param type is type of setting.
     */
    private void setPlayerCantBuild(Player player, String type){
        for(Worker w: player.getWorkers()) {
            if(type.equals(TRUE))
                w.setCantBuild(true);
            else if(type.equals(FALSE))
                w.setCantBuild(false);
            }
    }

}
