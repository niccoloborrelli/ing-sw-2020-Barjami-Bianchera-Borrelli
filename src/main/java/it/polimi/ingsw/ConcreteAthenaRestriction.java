package it.polimi.ingsw;

public class ConcreteAthenaRestriction extends PowerRestrictionAB {

    public ConcreteAthenaRestriction(RestrictionAB restrictionAB){
        this.restrictionAB = restrictionAB;
    }

    @Override
    public void restrictionEffectMovement(Player player, IslandBoard islandBoard, boolean usePower) {
        restrictionAB.restrictionEffectMovement(player, islandBoard, usePower);
        if(usePower)
            for (Worker worker : player.getWorkers())
                setPossibleMovement(worker, islandBoard);

    }

    private void setPossibleMovement(Worker worker, IslandBoard islandBoard) {
        for (int i = worker.getWorkerSpace().getRow() - 1; i <= worker.getWorkerSpace().getRow() + 1; i++)
            for (int j = worker.getWorkerSpace().getColumn() - 1; j <= worker.getWorkerSpace().getColumn() + 1; j++) {
                if ((0 <= i && i < 5) && (0 <= j && j < 5) && islandBoard.getSpace(i,j).isAvailableMovement().contains(worker))
                    if(worker.getWorkerSpace().getLevel() < islandBoard.getSpace(i,j).getLevel())
                        islandBoard.getSpace(i,j).isAvailableBuilding().remove(worker);

            }
    }

    @Override
    public void restrictionEffectBuilding (Worker worker, IslandBoard islandBoard){
        restrictionAB.restrictionEffectBuilding(worker, islandBoard);
        }
}
