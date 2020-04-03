package it.polimi.ingsw;

import java.util.List;

public class BaseRestriction extends RestrictionAB{


    @Override // (VERIFICATA)
    public void restrictionEffectMovement(Player player, IslandBoard islandBoard) {
        islandBoard.resetBoard();
        for (Worker worker : player.getWorkers())
            setPossibleMovement(worker, islandBoard);
    }

    /*
    setta i movimenti possibili di un worker nello spazio circostante alla sua casella
     */
    private void setPossibleMovement(Worker worker, IslandBoard islandBoard){
        for (int i = worker.getWorkerSpace().getRow() - 1; i <= worker.getWorkerSpace().getRow() + 1; i++)
            for (int j = worker.getWorkerSpace().getColumn() - 1; j <= worker.getWorkerSpace().getColumn() + 1; j++){
                if ((0 <= i && i < 5) && (0 <= j && j < 5))
                    if (islandBoard.getSpace(i, j).getOccupator() == null && islandBoard.getSpace(i, j).getLevel() - worker.getWorkerSpace().getLevel() < 2 && !islandBoard.getSpace(i, j).HasDome())
                        islandBoard.getSpace(i, j).addAvailableMovement(worker);
            }
    }

    @Override //(VERIFICATA)
    public void restrictionEffectBuilding(Worker worker, IslandBoard islandBoard) {
        islandBoard.resetBoard();
        setPossibleBuilding(worker, islandBoard);
    }

    /*
    setta le costruzioni possibili di un worker nello spazio circostante alla sua casella
     */
    private void setPossibleBuilding(Worker worker, IslandBoard islandBoard){
        for (int i = worker.getWorkerSpace().getRow() - 1; i <= worker.getWorkerSpace().getRow() + 1; i++)
            for (int j = worker.getWorkerSpace().getColumn() - 1; j <= worker.getWorkerSpace().getColumn() + 1; j++) {
                if ((0 <= i && i < 5) && (0 <= j && j < 5))
                    if (islandBoard.getSpace(i, j).getOccupator() == null && !islandBoard.getSpace(i, j).HasDome())
                        islandBoard.getSpace(i, j).addAvailableBuilding(worker);
            }
    }

}
