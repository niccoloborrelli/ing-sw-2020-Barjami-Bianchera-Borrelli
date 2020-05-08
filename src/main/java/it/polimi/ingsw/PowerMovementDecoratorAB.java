package it.polimi.ingsw;

public abstract class PowerMovementDecoratorAB extends MovementAB{
    protected MovementAB movement;

    public void setMovement(MovementAB movement) {
        this.movement = movement;
    }
}
