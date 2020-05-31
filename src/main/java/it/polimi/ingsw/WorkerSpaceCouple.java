package it.polimi.ingsw;

public class WorkerSpaceCouple {
    private Worker worker;
    private Space space;
    public WorkerSpaceCouple(){

    };
    public WorkerSpaceCouple(Worker worker, Space space){
        this.worker=worker;
        this.space=space;
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
