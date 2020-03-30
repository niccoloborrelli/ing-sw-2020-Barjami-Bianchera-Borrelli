package it.polimi.ingsw;

public class ConcreteMovementApollo extends PowerMovementDecoratorAB{

    private PowerMovementDecoratorAB powerMovementDecoratorAB;

    @Override
    public boolean move(Worker worker, Space finishSpace){
        return true;
    }
}
