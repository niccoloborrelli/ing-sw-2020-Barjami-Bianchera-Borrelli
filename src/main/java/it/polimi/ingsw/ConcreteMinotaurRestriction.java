package it.polimi.ingsw;

public class ConcreteMinotaurRestriction extends PowerRestrictionAB{
    public ConcreteMinotaurRestriction(RestrictionAB restrictionAB){
        this.restrictionAB=restrictionAB;
    }

    @Override // (VERIFICATA)
    public void restrictionEffectMovement(Player player, IslandBoard islandBoard) {
        this.restrictionAB.restrictionEffectMovement(player,islandBoard); //setto i movimenti base

        for (Worker worker : player.getWorkers())
            setMinotaurMovement(worker, islandBoard);
    }

    /*
    setta i movimenti possibili di un worker nello spazio circostante alla sua casella
     */
    private void setMinotaurMovement(Worker worker, IslandBoard islandBoard){
        for (int i = worker.getWorkerSpace().getRow() - 1; i <= worker.getWorkerSpace().getRow() + 1; i++)
            for (int j = worker.getWorkerSpace().getColumn() - 1; j <= worker.getWorkerSpace().getColumn() + 1; j++){
                if ((0 <= i && i < 5) && (0 <= j && j < 5) && (i!=worker.getWorkerSpace().getRow() || j != worker.getWorkerSpace().getColumn()))
                    if (islandBoard.getSpace(i, j).getLevel() - worker.getWorkerSpace().getLevel() < 2 && !islandBoard.getSpace(i, j).HasDome() && islandBoard.getSpace(i,j).getOccupator().getWorkerPlayer() != worker.getWorkerPlayer())
                         if(spaceBehind(worker.getWorkerSpace(),islandBoard.getSpace(i, j), islandBoard) != null)
                             islandBoard.getSpace(i,j).addAvailableMovement(worker);
            }
    }

    //ritorna lo spazio dietro a finishspace rispetto a start space, es se start space e' 0/0 e finish e' 1/1 questo ritorna 2/2
    //ritorna null se questo è fuori dalla mappa oppure e' gia' occupato
    private Space spaceBehind(Space startSpcace,Space finishSpace,IslandBoard islandBoard){
        int startColumn = startSpcace.getColumn();
        int startRow = startSpcace.getRow();
        int finishRow = finishSpace.getRow();
        int finishColumn = finishSpace.getColumn();
        int behindColumn;
        int behindRow;

        if(finishColumn == startColumn+1)
            behindColumn = finishColumn+1;
        else if(finishColumn == startColumn-1)
            behindColumn = finishColumn-1;
        else
            behindColumn = finishColumn;

        if(finishRow == startRow+1)
            behindRow = finishRow+1;
        else if(finishRow == startRow-1)
            behindRow = finishRow-1;
        else
            behindRow = finishRow;

        if(behindColumn < 0 || behindColumn > 4 || behindRow < 0 || behindRow > 4)
            return null;
        else if(islandBoard.getSpace(behindRow, behindColumn).getOccupator() != null)
            return null;

        return islandBoard.getSpace(behindRow, behindColumn);
    }

    //non modifica la build, richiama dunque la restriction effect building dell'oggetto decorated
    public void restrictionEffectBuilding(Worker worker, IslandBoard islandBoard) {
        restrictionAB.restrictionEffectBuilding(worker,islandBoard);
    }

}
