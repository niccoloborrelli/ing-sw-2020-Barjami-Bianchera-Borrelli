package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

//import static it.polimi.ingsw.DefinedValues.actionTypeB;
//import static it.polimi.ingsw.DefinedValues.actionTypeM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AdditionalBuildFlowTest {

    /*
    Questo test controlla che, se ha spazi possibili dove costruire,
    l'action "build" viene aggiunta alla lista actionToPerform
     */
    @Test
    void changeFlowInitialSpaceTest1() {
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        player.setIslandBoard(islandBoard);
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        player.getWorkers().add(worker1);
        player.getWorkers().add(worker2);
        worker1.setWorkerPlayer(player);
        worker2.setWorkerPlayer(player);

        worker1.setWorkerSpace(islandBoard.getSpace(0,0));
        islandBoard.getSpace(0,0).setOccupator(worker1);
        worker2.setWorkerSpace(islandBoard.getSpace(4,4));
        islandBoard.getSpace(4,4).setOccupator(worker2);

        worker1.setChosen(true);
        worker1.setLastSpaceBuilt(islandBoard.getSpace(1,1));
        AdditionalBuildFlow additionalBuildFlow = new AdditionalBuildFlow("initialSpace");

        if(additionalBuildFlow.isUsable(player))
            additionalBuildFlow.changeFlow(player);
        System.out.println(player.getActionsToPerform());
        System.out.println("Worker chosen -> cantBuildFirstSpace: " + worker1.isCantBuildFirstSpace());
        assertEquals(1, player.getActionsToPerform().size());
    }

    /*
    Questo test controlla che, se non ha spazi possibili dove
    costruire, non venga aggiunta l'action "build" alla lista
     */
    @Test
    void changeFlowInitialSpaceTest2() {
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        player.setIslandBoard(islandBoard);
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        player.getWorkers().add(worker1);
        player.getWorkers().add(worker2);
        worker1.setWorkerPlayer(player);
        worker2.setWorkerPlayer(player);

        worker1.setWorkerSpace(islandBoard.getSpace(0, 0));
        islandBoard.getSpace(0, 0).setOccupator(worker1);
        worker2.setWorkerSpace(islandBoard.getSpace(4, 4));
        islandBoard.getSpace(4, 4).setOccupator(worker2);

        worker1.setChosen(true);
        worker1.setLastSpaceBuilt(islandBoard.getSpace(1, 1));
        AdditionalBuildFlow additionalBuildFlow = new AdditionalBuildFlow("initialSpace");
        islandBoard.getSpace(0,1).setHasDome(true);
        islandBoard.getSpace(1,0).setHasDome(true);

        if(additionalBuildFlow.isUsable(player))
            additionalBuildFlow.changeFlow(player);
        else
            System.out.println("AdditionalBuild not possible");
        assertEquals(0, player.getActionsToPerform().size());
    }

    /*
    Questo test controlla che, se ha spazi possibili dove costruire,
    l'action "build" viene aggiunta alla lista actionToPerform
     */
    @Test
    void changeFlowPerimeterSpaceTest1(){
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        player.setIslandBoard(islandBoard);
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        player.getWorkers().add(worker1);
        player.getWorkers().add(worker2);
        worker1.setWorkerPlayer(player);
        worker2.setWorkerPlayer(player);

        worker1.setWorkerSpace(islandBoard.getSpace(0, 0));
        islandBoard.getSpace(0, 0).setOccupator(worker1);
        worker2.setWorkerSpace(islandBoard.getSpace(4, 4));
        islandBoard.getSpace(4, 4).setOccupator(worker2);

        worker2.setChosen(true);
        worker2.setLastSpaceBuilt(islandBoard.getSpace(3,4));
        AdditionalBuildFlow additionalBuildFlow = new AdditionalBuildFlow("perimeterSpace");

        if(additionalBuildFlow.isUsable(player))
            additionalBuildFlow.changeFlow(player);
        System.out.println("Worker chosen -> cantBuildPerimeter: " + worker2.isCantBuildPerimeter());
        assertEquals(1, player.getActionsToPerform().size());
    }

    /*
    Questo test controlla che, se non ha spazi possibili dove
    costruire, non venga aggiunta l'action "build" alla lista
     */
    @Test
    void changeFlowPerimeterSpaceTest2(){
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        player.setIslandBoard(islandBoard);
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        player.getWorkers().add(worker1);
        player.getWorkers().add(worker2);
        worker1.setWorkerPlayer(player);
        worker2.setWorkerPlayer(player);

        worker1.setWorkerSpace(islandBoard.getSpace(0, 0));
        islandBoard.getSpace(0, 0).setOccupator(worker1);
        worker2.setWorkerSpace(islandBoard.getSpace(4, 4));
        islandBoard.getSpace(4, 4).setOccupator(worker2);

        worker2.setChosen(true);
        worker2.setLastSpaceBuilt(islandBoard.getSpace(3,4));
        AdditionalBuildFlow additionalBuildFlow = new AdditionalBuildFlow("perimeterSpace");
        islandBoard.getSpace(3,3).setHasDome(true);

        if(additionalBuildFlow.isUsable(player))
            additionalBuildFlow.changeFlow(player);
        else
            System.out.println("AdditionalBuild not possible");
        assertEquals(0, player.getActionsToPerform().size());
    }

    /*
    Questo test controlla che, se ha spazi possibili dove costruire,
    l'action "build" viene aggiunta alla lista actionToPerform
     */
    @Test
    void changFlowDomeBuildTest1(){
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        player.setIslandBoard(islandBoard);
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        player.getWorkers().add(worker1);
        player.getWorkers().add(worker2);
        worker1.setWorkerPlayer(player);
        worker2.setWorkerPlayer(player);

        worker1.setWorkerSpace(islandBoard.getSpace(0,0));
        islandBoard.getSpace(0,0).setOccupator(worker1);
        worker2.setWorkerSpace(islandBoard.getSpace(4,4));
        islandBoard.getSpace(4,4).setOccupator(worker2);

        worker1.setChosen(true);
        worker1.setLastSpaceBuilt(islandBoard.getSpace(1,1));
        AdditionalBuildFlow additionalBuildFlow = new AdditionalBuildFlow("dome");
        islandBoard.getSpace(1,1).setLevel(2);

        if(additionalBuildFlow.isUsable(player))
            additionalBuildFlow.changeFlow(player);
        System.out.println("Worker chosen -> cantBuildDome: " + worker1.isCantBuildDome());
        assertEquals(1, player.getActionsToPerform().size());
    }

    /*
    Questo test controlla che, se non ha spazi possibili dove
    costruire, non venga aggiunta l'action "build" alla lista
     */
    @Test
    void changeFlowDomeBuildTest2(){
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        player.setIslandBoard(islandBoard);
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        player.getWorkers().add(worker1);
        player.getWorkers().add(worker2);
        worker1.setWorkerPlayer(player);
        worker2.setWorkerPlayer(player);

        worker1.setWorkerSpace(islandBoard.getSpace(0,0));
        islandBoard.getSpace(0,0).setOccupator(worker1);
        worker2.setWorkerSpace(islandBoard.getSpace(4,4));
        islandBoard.getSpace(4,4).setOccupator(worker2);

        worker1.setChosen(true);
        worker1.setLastSpaceBuilt(islandBoard.getSpace(1,1));
        islandBoard.getSpace(1,1).setLevel(3);
        AdditionalBuildFlow additionalBuildFlow = new AdditionalBuildFlow("dome");

        if(additionalBuildFlow.isUsable(player))
            additionalBuildFlow.changeFlow(player);
        else
            System.out.println("AdditionalBuild not possible");
        assertEquals(0, player.getActionsToPerform().size());
    }

    /*
    Questo test controlla che, se ha spazi possibili dove costruire, l'action
    "build" viene aggiunta alla lista actionToPerform in prima posizione
     */
    @Test
    void changeFlowBeforeMoveTest1(){
        Player player = new Player();
      //  player.getActionsToPerform().add(actionTypeM);
        //player.getActionsToPerform().add(actionTypeB);
        IslandBoard islandBoard = new IslandBoard();
        player.setIslandBoard(islandBoard);
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        player.getWorkers().add(worker1);
        player.getWorkers().add(worker2);
        worker1.setWorkerPlayer(player);
        worker2.setWorkerPlayer(player);

        worker1.setWorkerSpace(islandBoard.getSpace(0,0));
        islandBoard.getSpace(0,0).setOccupator(worker1);
        worker2.setWorkerSpace(islandBoard.getSpace(4,4));
        islandBoard.getSpace(4,4).setOccupator(worker2);

        AdditionalBuildFlow additionalBuildFlow = new AdditionalBuildFlow("moveUp");
        if(additionalBuildFlow.isUsable(player))
            additionalBuildFlow.changeFlow(player);
        System.out.println("Worker1 -> cantMoveUp: " + worker1.isCantMoveUp() + "\n" +
                           "Worker2 -> cantMoveUp: " + worker2.isCantMoveUp());
        assertTrue(player.getActionsToPerform().size() == 3 && player.getActionsToPerform().get(0).equals("build"));
    }

    /*
    Questo test controlla che, se entrambi i worker non hanno spazi
    possibili dove costruire, non venga aggiunta l'action "build" alla lista
     */
    @Test
    void changeFlowBeforeMoveTest2(){
        Player player = new Player();
        //player.getActionsToPerform().add(actionTypeM);
        //player.getActionsToPerform().add(actionTypeB);
        IslandBoard islandBoard = new IslandBoard();
        player.setIslandBoard(islandBoard);
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        player.getWorkers().add(worker1);
        player.getWorkers().add(worker2);
        worker1.setWorkerPlayer(player);
        worker2.setWorkerPlayer(player);

        worker1.setWorkerSpace(islandBoard.getSpace(0,0));
        islandBoard.getSpace(0,0).setOccupator(worker1);
        worker2.setWorkerSpace(islandBoard.getSpace(0,1));
        islandBoard.getSpace(0,1).setOccupator(worker2);

        islandBoard.getSpace(1,0).setHasDome(true);
        islandBoard.getSpace(1,1).setHasDome(true);
        islandBoard.getSpace(1,2).setHasDome(true);
        islandBoard.getSpace(0,2).setHasDome(true);

        AdditionalBuildFlow additionalBuildFlow = new AdditionalBuildFlow("moveUp");
        if(additionalBuildFlow.isUsable(player))
            additionalBuildFlow.changeFlow(player);
        else
            System.out.println("AdditionalBuild not possible");
        assertEquals(2, player.getActionsToPerform().size());
    }
}