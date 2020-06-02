package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class WorkerSettingStateTest {

    @Test
    void workerSetTestGeneric1() throws IOException {
        Player p=new Player();
        Worker worker1=p.getWorkers().get(0);
        Worker worker2=p.getWorkers().get(1);
        IslandBoard islandBoard=new IslandBoard();
        p.setIslandBoard(islandBoard);
      //  p.setState(new WorkerSettingState(p));
        Visitor visitor=new Visitor();
        p.getState().onStateTransition();
        visitor.setWorkerSpaceCouple(new WorkerSpaceCouple(worker1,islandBoard.getSpace(0,0)));
        p.getState().onInput(visitor);
        visitor.setWorkerSpaceCouple(new WorkerSpaceCouple(worker2,islandBoard.getSpace(1,0)));
        p.onInput(visitor);
    }

}