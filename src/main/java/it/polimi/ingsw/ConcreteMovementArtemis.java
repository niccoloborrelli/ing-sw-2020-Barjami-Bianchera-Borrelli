package it.polimi.ingsw;

public class ConcreteMovementArtemis extends PowerMovementDecoratorAB {
    private PowerMovementDecoratorAB powerMovementDecoratorAB;


    @Override
    public boolean move(Worker worker, Space finishSpace){
        return true;
    }

}
