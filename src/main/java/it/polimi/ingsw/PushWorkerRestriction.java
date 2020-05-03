package it.polimi.ingsw;

public class PushWorkerRestriction extends PowerRestrictionAB{

    /*
    Minotaur's power
     */
    /*
    Your Worker may move into an opponent Worker’s space, if their Worker can be
    forced one space straight backwards to an unoccupied space at any level.
     */

    /**
     * this is a decorator pattern constructor
     * @param restrictionAB is the decorated object
     */
    public PushWorkerRestriction(RestrictionAB restrictionAB){
        this.restrictionAB=restrictionAB;
    }

    /**
     * this method sets the spaces in the islandBoard in which player's workers can move and build
     * @param player is the player who's workers possible movement and building spaces are to be set
     * @param islandBoard is the board in which player is playing
     */
    @Override // (VERIFICATA)
    public void restrictionEffectMovement(Player player, IslandBoard islandBoard) {
        this.restrictionAB.restrictionEffectMovement(player,islandBoard); //setto i movimenti base

        for (Worker worker : player.getWorkers())
            setMinotaurMovement(worker, islandBoard);
    }

    /**
     * this method sets the spaces in the islandBoard in which the worker can move thanks to Minotaur's power
     * @param worker is the worker who's spaces for possible movement are going to be set
     * @param islandBoard is the islandBoard containing the worker
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

    /**
     * this method is used for the implementation of minotaur move, it returns the space
     * behind the position of the moving Worker of the minotaur's holder
     * @param startSpcace is the starting Space of the minotaur's holder worker
     * @param finishSpace is the position in which the minotaur's holder worker is going to move
     * @param islandBoard  is the IslandBoard which contains the given finishSpace and the workerSpace
     * @return the Space behind finishSpace from startSpace prospective
     */
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

    /**
     * this method sets the spaces in the islandBoard in which the worker can build
     * @param worker is the worker who's spaces for possible building are going to be set
     * @param islandBoard is the islandBoard containing the worker
     */
    public void restrictionEffectBuilding(Worker worker, IslandBoard islandBoard) {
        restrictionAB.restrictionEffectBuilding(worker,islandBoard);
    }

}
