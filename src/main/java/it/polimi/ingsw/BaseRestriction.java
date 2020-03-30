package it.polimi.ingsw;

public class BaseRestriction extends RestrictionAB{

    /*
    setta i movimenti possibili di un worker nello spazio circostante alla sua casella
     */
    @Override // (VERIFICATA)
    public void restrictionEffectMovement(Player player, IslandBoard islandBoard) {
        islandBoard.resetBoard();
        for (Worker temp : player.getWorkers())
            for (int i = temp.getWorkerSpace().getRow() - 1; i <= temp.getWorkerSpace().getRow() + 1; i++)
                for (int j = temp.getWorkerSpace().getColumn() - 1; j <= temp.getWorkerSpace().getColumn() + 1; j++) {
                    if ((0 <= i && i < 5) && (0 <= j && j < 5))
                        if (islandBoard.getSpace(i, j).getOccupator() == null && islandBoard.getSpace(i, j).getLevel() - temp.getWorkerSpace().getLevel() < 2 && !islandBoard.getSpace(i, j).HasDome())
                            islandBoard.getSpace(i, j).addAvailableMovement(temp);
                }

    }

    /*
    setta le costruzioni possibili di un worker nello spazio circostante alla sua casella
     */
    @Override //(VERIFICATA)
    public void restrictionEffectBuilding(Player player, IslandBoard islandBoard) {
        islandBoard.resetBoard();
        for (Worker temp : player.getWorkers())
            for (int i = temp.getWorkerSpace().getRow() - 1; i <= temp.getWorkerSpace().getRow() + 1; i++)
                for (int j = temp.getWorkerSpace().getColumn() - 1; j <= temp.getWorkerSpace().getColumn() + 1; j++) {
                    if ((0 <= i && i < 5) && (0 <= j && j < 5))
                        if (islandBoard.getSpace(i, j).getOccupator() == null && !islandBoard.getSpace(i, j).HasDome())
                            islandBoard.getSpace(i, j).addAvailableBuilding(temp);
                }
    }
}
