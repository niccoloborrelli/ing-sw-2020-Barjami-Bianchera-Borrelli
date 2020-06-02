package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class ActionStateTest {

    @Test
    public  void generalActionTest1() throws IOException {
        Player player=new Player();
        IslandBoard islandBoard=new IslandBoard();
        player.setIslandBoard(islandBoard);
        Worker w1=player.getWorkers().get(0);
        Worker w2=player.getWorkers().get(1);
        w1.setWorkerSpace(islandBoard.getSpace(0,0));
        islandBoard.getSpace(0,0).setOccupator(w1);

        Worker w3=new Worker();
        w3.setWorkerPlayer(new Player());

        islandBoard.getSpace(1,1).setLevel(2);
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                if(islandBoard.getSpace(i,j).getOccupator()==null)
                    System.out.print(0+" ");
                else
                    System.out.print("x");
            }
            System.out.println();
        }

        ActionState actionState=new ActionState(player);

        AbstractActionState athena=new OnMoveUpDecorator(actionState,"denyUpperMove");
        player.getStateManager().setCurrent_state(athena);
        WorkerSpaceCouple workerSpaceCouple =new WorkerSpaceCouple();
        workerSpaceCouple.setWorker(w1);
        workerSpaceCouple.setSpace(islandBoard.getSpace(1,1));
        player.setLastReceivedInput(workerSpaceCouple);
        player.getState().onStateTransition();
        System.out.println();
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                if(islandBoard.getSpace(i,j).getOccupator()==null)
                    System.out.print(islandBoard.getSpace(i,j).getLevel()+" ");
                else
                    System.out.print("x");
            }
            System.out.println();
        }
        System.out.println(player.getActionsToPerform().get(0));
/*
        ReadyForActionState readyForActionState = new ReadyForActionState(player);
        player.getStateManager().setCurrent_state(readyForActionState);

        player.getState().onStateTransition();

        for(Worker w:player.getWorkers()){
            for(Space c:w.getPossibleMovements()) {
                System.out.println("worker:" + w.getWorkerPlayer().getWorkers().indexOf(w) + "-" +c.getRow()+c.getColumn());
            }
        }
*/
    }


}