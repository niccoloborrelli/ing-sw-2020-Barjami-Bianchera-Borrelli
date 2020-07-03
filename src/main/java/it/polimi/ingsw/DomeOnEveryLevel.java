package it.polimi.ingsw;

import static it.polimi.ingsw.DefinedValues.actionType2;

public class DomeOnEveryLevel extends FlowChanger {
    @Override
    public void changeFlow(Player player) {
        for (Worker tempWorker:player.getWorkers()) {
            tempWorker.setMustBuildDome(true);
        }
    }

    @Override
    public boolean isUsable(Player player) {
        CheckingUtility.calculateValidSpace(player,player.getIslandBoard(),actionType2);
        for (Worker tempWorker:player.getWorkers()) {
            if(tempWorker.getPossibleBuilding().size()>0)
                return true;
        }
        return false;
    }
}
