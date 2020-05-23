package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.DefinedValues.*;

public class AdditionalBuildFlow extends FlowChanger {

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
            case "initialSpace":
                noInitialSpace = true;
                break;
            case  "perimeterSpace":
                noPerimeter = true;
                break;
            case "moveUp":
                beforeMove = true;
                break;
            case "dome":
                noDome = true;
        }
    }


    /**
     * This method change the flow of the action to perform by a player
     */
    @Override
    public void changeFlow(Player player) {
        if(beforeMove){
            player.getActionsToPerform().add(0, actionTypeB);
            for (Worker w: player.getWorkers()) {
                w.setCantMoveUp(true);
            }
            return;
        }

        player.getActionsToPerform().add(actionTypeB);
        if(noInitialSpace){
            if(player.getWorkers().get(0).isChosen())
                player.getWorkers().get(0).setCantBuildFirstSpace(true);
            else
                player.getWorkers().get(1).setCantBuildFirstSpace(true);
        } else if (noPerimeter){
            if(player.getWorkers().get(0).isChosen())
                player.getWorkers().get(0).setCantBuildPerimeter(true);
            else
                player.getWorkers().get(1).setCantBuildPerimeter(true);
        } else if(noDome){
            if(player.getWorkers().get(0).isChosen())
                player.getWorkers().get(0).setCantBuildDome(true);
            else
                player.getWorkers().get(1).setCantBuildDome(true);
        }
    }

    /**
     * This method check if a power is usable by a player
     * @return true if usable, false otherwise
     */
    @Override
    public boolean isUsable(Player player) {
        if(beforeMove){
            CheckingUtility.calculateValidSpace(player, player.getIslandBoard(), actionTypeB);

            return (player.getWorkers().get(0).getPossibleBuilding().size() > 0 ||
                    player.getWorkers().get(1).getPossibleBuilding().size() > 0);
        }

        Worker workerChosen;
        if(player.getWorkers().get(0).isChosen())
            workerChosen = player.getWorkers().get(0);
        else
            workerChosen = player.getWorkers().get(1);

        CheckingUtility.calculateValidSpace(player, player.getIslandBoard(), actionTypeB);
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
        return building.size() > 0;
    }
}
