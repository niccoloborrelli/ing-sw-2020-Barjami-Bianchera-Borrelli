package it.polimi.ingsw;

public class ConcreteApolloRestriction extends  PowerRestrictionAB {

    public ConcreteApolloRestriction(RestrictionAB restrictionAB){
        this.restrictionAB = restrictionAB;
    }

    public void restrictionEffectMovement(Player player, IslandBoard islandBoard) {
        islandBoard.resetBoard();
        for (Worker worker : player.getWorkers())
            setPossibleMovement(worker, islandBoard);
    }

    /*
    setta i movimenti possibili di un worker nello spazio circostante alla sua casella inserisce nella lista anche se una
    space ha gia' un occupatore
     */
    private void setPossibleMovement(Worker worker, IslandBoard islandBoard){
        for (int i = worker.getWorkerSpace().getRow() - 1; i <= worker.getWorkerSpace().getRow() + 1; i++)
            for (int j = worker.getWorkerSpace().getColumn() - 1; j <= worker.getWorkerSpace().getColumn() + 1; j++){
                //non deve controllare il posto dove gia' era
                if ((0 <= i && i < 5) && (0 <= j && j < 5) && (i != worker.getWorkerSpace().getRow() || j != worker.getWorkerSpace().getColumn()))
                    if (islandBoard.getSpace(i, j).getLevel() - worker.getWorkerSpace().getLevel() < 2 && !islandBoard.getSpace(i, j).HasDome() && islandBoard.getSpace(i,j).getOccupator().getWorkerPlayer() != worker.getWorkerPlayer())
                        islandBoard.getSpace(i, j).addAvailableMovement(worker);
            }
    }

    @Override //(VERIFICATA)
    public void restrictionEffectBuilding(Worker worker, IslandBoard islandBoard) {
        restrictionAB.restrictionEffectBuilding(worker, islandBoard);
    }
}
