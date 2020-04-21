package it.polimi.ingsw;

import java.io.IOException;

public class ConcreteApolloMove extends PowerMovementDecoratorAB{

    /**
     * this is a Decorator constructor
     * @param movementAB is the decorated object
     */
    public ConcreteApolloMove(MovementAB movementAB){
        this.movement=movementAB;
    }

    /**
     * this method implements the Apollo power movement
     * @param worker is the Worker who is going to move
     * @param finalSpace is the Space in which the worker is going to move
     * @param board is the IslandBoard containing worker and finalSpace
     */
    @Override
    public void move(Worker worker,Space finalSpace,IslandBoard board) throws IOException {
        if(finalSpace.getOccupator()!=null){   //se c'e' qualcuno lo sposto al suo posto!
            System.out.println("attivato potere apollo");
            Worker temp=finalSpace.getOccupator();
            worker.getWorkerSpace().setOccupator(temp);
            finalSpace.setOccupator(worker);
        }
        else
            movement.move(worker,finalSpace,board);
    }


}
