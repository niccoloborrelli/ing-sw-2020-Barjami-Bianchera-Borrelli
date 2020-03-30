package it.polimi.ingsw;

public class ConcreteMovementMinotaur extends PowerMovementDecoratorAB {
    private PowerMovementDecoratorAB powerMovementDecoratorAB;

    @Override
    public boolean move(Worker worker, Space finishSpace){
        return true;
    }

}
