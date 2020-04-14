package it.polimi.ingsw;

public class ConcreteApolloMove extends PowerMovementDecoratorAB{

    public ConcreteApolloMove(MovementAB movementAB){
        this.movement = movementAB;
    }

    public boolean move(Worker worker, Space finalSpace, IslandBoard board){
        if(finalSpace.getOccupator() != null){   //se c'e' qualcuno lo sposto al suo posto!
            System.out.println("Apollo power activated");
            Worker temp = finalSpace.getOccupator();
            worker.getWorkerSpace().setOccupator(temp);
            finalSpace.setOccupator(worker);
        }
        else
            movement.move(worker, finalSpace, board);
        return true;
    }
}
