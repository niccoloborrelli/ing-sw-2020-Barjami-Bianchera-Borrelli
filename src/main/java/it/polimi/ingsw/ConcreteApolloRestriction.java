package it.polimi.ingsw;

public class ConcreteApolloRestriction extends  PowerRestrictionAB {

    /**
     * this is a Decorator constructor
     * @param restrictionAB is the decorated object
     */
    public ConcreteApolloRestriction(RestrictionAB restrictionAB){
        this.restrictionAB=restrictionAB;
    }

    /**
     * this method sets the spaces in the islandBoard in which player's workers can move and build
     * @param player is the player who's workers possible movement and building spaces are to be set
     * @param islandBoard is the board in which player is playing
     */
    public void restrictionEffectMovement(Player player, IslandBoard islandBoard) {
        islandBoard.resetBoard();
        for (Worker worker : player.getWorkers())
            setPossibleMovement(worker, islandBoard);
    }

    /*
    setta i movimenti possibili di un worker nello spazio circostante alla sua casella inserisce nella lista anche se una
    space ha gia' un occupatore
     */

    /**
     * this method sets the spaces in the islandBoard in which the worker can move thanks to Apollo's power
     * @param worker is the worker who's spaces for possible movement are going to be set
     * @param islandBoard is the islandBoard containing the worker
     */
    private void setPossibleMovement(Worker worker, IslandBoard islandBoard){
        for (int i = worker.getWorkerSpace().getRow() - 1; i <= worker.getWorkerSpace().getRow() + 1; i++)
            for (int j = worker.getWorkerSpace().getColumn() - 1; j <= worker.getWorkerSpace().getColumn() + 1; j++){
                //non deve controllare il posto dove gia' era
                if ((0 <= i && i < 5) && (0 <= j && j < 5)&&(i!=worker.getWorkerSpace().getRow()||j!=worker.getWorkerSpace().getColumn()))
                    if (islandBoard.getSpace(i, j).getLevel() - worker.getWorkerSpace().getLevel() < 2 && !islandBoard.getSpace(i, j).HasDome()&&islandBoard.getSpace(i,j).getOccupator().getWorkerPlayer()!=worker.getWorkerPlayer())
                        islandBoard.getSpace(i, j).addAvailableMovement(worker);
            }
    }

    /**
     * this method sets the spaces in the islandBoard in which the worker can build
     * @param worker is the worker who's spaces for possible building are going to be set
     * @param islandBoard is the islandBoard containing the worker
     */
    @Override //(VERIFICATA)
    public void restrictionEffectBuilding(Worker worker, IslandBoard islandBoard) {
        restrictionAB.restrictionEffectBuilding(worker,islandBoard);
    }
}
