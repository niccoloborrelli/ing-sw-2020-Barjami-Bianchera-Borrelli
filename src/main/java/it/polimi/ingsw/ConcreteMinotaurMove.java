package it.polimi.ingsw;

public class ConcreteMinotaurMove extends PowerMovementDecoratorAB {
    
    public ConcreteMinotaurMove(MovementAB movementAB){
        this.movement=movementAB;
    }

    public boolean move(Worker worker, Space finishSpace, IslandBoard islandBoard) {
        if(finishSpace.getOccupator()!=null){
            //sposta una posizione indietro l'occupatore dello spazio
            moveBehind(worker.getWorkerSpace(),finishSpace,islandBoard);
        }
        movement.move(worker,finishSpace,islandBoard);
        return  true;
    }


    public void moveBehind(Space workerSpace,Space finishSpace,IslandBoard islandBoard){
        int startColumn = workerSpace.getColumn();
        int startRow = workerSpace.getRow();
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

        islandBoard.getSpace(behindRow, behindColumn).setOccupator(finishSpace.getOccupator());
        finishSpace.getOccupator().setWorkerSpace(islandBoard.getSpace(behindRow,behindColumn));
        finishSpace.setOccupator(null);
    }


    }
