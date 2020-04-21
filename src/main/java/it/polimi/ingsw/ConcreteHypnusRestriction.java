package it.polimi.ingsw;

public class ConcreteHypnusRestriction extends PowerRestrictionAB {

    /*
    If one of your opponent's workers is higher than all of theirs, it cannot move
     */

    public ConcreteHypnusRestriction(RestrictionAB restrictionAB){ this.restrictionAB = restrictionAB; }

    /**
     * This methods controls if there's one worker's player in the highest level and
     * it forbids worker from move.
     * @param player is the worker's owner
     * @param islandBoard is the game field that contains the list of Spaces
     */
    @Override
    public void restrictionEffectMovement(Player player, IslandBoard islandBoard) {
        restrictionAB.restrictionEffectMovement(player,islandBoard);
        int maxlevel = findHighestLevel(islandBoard);
        if(player.getWorkers().get(0).getWorkerSpace().getLevel() == maxlevel)
            removeWorkerfromSpaces(player.getWorkers().get(0), islandBoard);
        else if(player.getWorkers().get(1).getWorkerSpace().getLevel() == maxlevel)
            removeWorkerfromSpaces(player.getWorkers().get(1), islandBoard);
    }

    private int findHighestLevel(IslandBoard islandBoard){
        int maxlevel = 0;
        for(int i = 0; i < 5; i++)
           for(int j = 0; j < 5; j++)
               if(islandBoard.getSpace(i, j).getOccupator() != null) {
                   if (islandBoard.getSpace(i, j).getLevel() > maxlevel)
                       maxlevel = islandBoard.getSpace(i, j).getLevel();
                   else if(islandBoard.getSpace(i, j).getLevel() == maxlevel)
                       maxlevel++;
               }
        return maxlevel;
    }

    private void removeWorkerfromSpaces(Worker worker, IslandBoard islandBoard){
        for (int i = worker.getWorkerSpace().getRow() - 1; i <= worker.getWorkerSpace().getRow() + 1; i++)
            for (int j = worker.getWorkerSpace().getColumn() - 1; j <= worker.getWorkerSpace().getColumn() + 1; j++)
                if ((0 <= i && i < 5) && (0 <= j && j < 5))
                    islandBoard.getSpace(i,j).isAvailableMovement().remove(worker); //se non c'è la remove non fa nulla
    }

    /**
     * This method verify in which space worker can build on
     * @param worker This is the worker who's going to build
     * @param islandBoard This is the game field that contains the list of spaces
     */

    @Override
    public void restrictionEffectBuilding(Worker worker, IslandBoard islandBoard) {
        restrictionAB.restrictionEffectBuilding(worker,islandBoard);
    }
}
