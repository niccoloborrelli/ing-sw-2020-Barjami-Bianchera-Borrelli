package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ReadyForActionStateTest {

    @Test
    void readyForActionTest1() throws IOException {
        Player p=new Player();
        Worker worker1=p.getWorkers().get(0);
        Worker worker2=p.getWorkers().get(1);
        IslandBoard islandBoard=new IslandBoard();
        p.setIslandBoard(islandBoard);
        Space space1=islandBoard.getSpace(1,0);
        Space space2=islandBoard.getSpace(4,4);
        worker2.setCantMove(false);
        worker1.setWorkerSpace(space1);
        worker2.setWorkerSpace(space2);
        space1.setOccupator(worker1);
        space2.setOccupator(worker2);
        ReadyForActionState readyForActionState=new ReadyForActionState(p);
        List<String> actions=new ArrayList<String>();
        actions.add("move");
        p.setActionsToPerform(actions);
        readyForActionState.onStateTransition();



        Visitor visitor=new Visitor();
        visitor.setWorkerSpaceCouple(new WorkerSpaceCouple(worker1,islandBoard.getSpace(2,0)));
        readyForActionState.onInput(visitor);

    }
}