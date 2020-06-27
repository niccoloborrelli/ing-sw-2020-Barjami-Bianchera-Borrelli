package it.polimi.ingsw;

public class DomeOnEveryLevel extends FlowChanger {
    @Override
    public void changeFlow(Player player) {
        for (Worker tempWorker:player.getWorkers()) {
            tempWorker.setMustBuildDome(true);
        }
    }

    @Override
    public boolean isUsable(Player player) {
        System.out.println("arriva qui");
        CheckingUtility.calculateValidSpace(player,player.getIslandBoard(),"build");
        for (Worker tempWorker:player.getWorkers()) {
            if(tempWorker.getPossibleBuilding().size()>0)
                return true;
        }
        System.out.println("arriva  anche qui");
        return false;
    }
}
