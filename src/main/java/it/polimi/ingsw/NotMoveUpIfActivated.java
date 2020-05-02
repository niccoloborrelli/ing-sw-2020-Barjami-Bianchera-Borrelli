package it.polimi.ingsw;

public class NotMoveUpIfActivated extends PowerRestrictionAB {

    /*
    Power of Athena
     */
    /*
    If one of your workers moved up on your last turn,
    opponent workers cannot move up this turn
     */

    public NotMoveUpIfActivated(RestrictionAB restrictionAB){
        this.restrictionAB = restrictionAB;
    }

    /**
     * This method set every possible movement for player's workers
     * If player hasn't Athena as God and player (that has got it) use its power,
     * the method keeps out space that has higher level than worker's start space
     * @param player This is the player that have to move his workers in this round
     * @param islandBoard This is the game field that contains the spaces
     */
    @Override
    public void restrictionEffectMovement(Player player, IslandBoard islandBoard) {
        restrictionAB.restrictionEffectMovement(player, islandBoard);
        if(islandBoard.isRestrictionPower())
            for (Worker worker : player.getWorkers())
                setPossibleMovement(worker, islandBoard);

    }

    private void setPossibleMovement(Worker worker, IslandBoard islandBoard) {
        for (int i = worker.getWorkerSpace().getRow() - 1; i <= worker.getWorkerSpace().getRow() + 1; i++)
            for (int j = worker.getWorkerSpace().getColumn() - 1; j <= worker.getWorkerSpace().getColumn() + 1; j++) {
                if ((0 <= i && i < 5) && (0 <= j && j < 5) && islandBoard.getSpace(i,j).isAvailableMovement().contains(worker))
                    if(worker.getWorkerSpace().getLevel() < islandBoard.getSpace(i,j).getLevel())
                        islandBoard.getSpace(i,j).isAvailableMovement().remove(worker);

            }
    }

    /**
     * This method verify which space worker can build on
     * @param worker This is the worker who's going to build
     * @param islandBoard This is the game field that contains the list of spaces
     */
    @Override
    public void restrictionEffectBuilding (Worker worker, IslandBoard islandBoard){
        restrictionAB.restrictionEffectBuilding(worker, islandBoard);
        }
}
