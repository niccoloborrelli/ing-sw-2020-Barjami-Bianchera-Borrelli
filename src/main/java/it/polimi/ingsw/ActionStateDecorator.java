package it.polimi.ingsw;

public abstract class ActionStateDecorator extends AbstractActionState {
     AbstractActionState decorated;
     String effect;

    ActionStateDecorator(AbstractActionState decorated,String effect) {
        super(decorated.getPlayer());
        this.decorated=decorated;
        this.effect=effect;
    }

    public Worker getActingWorker() {
        return decorated.getActingWorker();
    }

    public Space getSpaceToAct() {
        return decorated.getSpaceToAct();
    }

    public Space getStartingSpace(){
        return decorated.getStartingSpace();
    }


}