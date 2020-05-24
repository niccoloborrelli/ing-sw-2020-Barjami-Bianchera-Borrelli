package it.polimi.ingsw;

public class SpaceInput  {
    private Worker worker;
    private Space space;
    private int anInt;
    public SpaceInput(){

    };
    public SpaceInput(Worker worker,Space space){
        this.worker=worker;
        this.space=space;
    }

    public int getAnInt() {
        return anInt;
    }

    public void setAnInt(int anInt) {
        this.anInt = anInt;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        this.space = space;
    }
}
