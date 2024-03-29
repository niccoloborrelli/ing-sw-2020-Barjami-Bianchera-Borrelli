package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckingUtilityTest {

    @Test
    void getListsTest1() {
        Player player = new Player();
        List<ArrayList<Space>> result;
        Worker worker1 = player.getWorkers().get(0);
        Worker worker2 = player.getWorkers().get(1);

        worker1.getPossibleMovements().add(new Space(1, 1));
        worker1.getPossibleMovements().add(new Space(2, 2));

        result = CheckingUtility.getLists(player, "move");

        assertTrue(result.get(0).size() == 2 && result.get(1).size() == 0);

    }

    @Test
    void getListsTest2() {
        Player player = new Player();
        List<ArrayList<Space>> result;
        Worker worker1 = player.getWorkers().get(0);
        Worker worker2 = player.getWorkers().get(1);

        worker1.getPossibleBuilding().add(new Space(1, 1));
        worker1.getPossibleBuilding().add(new Space(2, 2));

        result = CheckingUtility.getLists(player, "build");

        assertTrue(result.get(0).size() == 2 && result.get(1).size() == 0);

    }

    @Test
    void getListsTest3() {
        Player player = new Player();
        List<ArrayList<Space>> result;
        Worker worker1 = player.getWorkers().get(0);
        Worker worker2 = player.getWorkers().get(1);

        worker1.getPossibleBuilding().add(new Space(1, 1));
        worker1.getPossibleBuilding().add(new Space(2, 2));

        result = CheckingUtility.getLists(player, "none");

        assertEquals(0, result.size());
    }

    @Test
    void calculateValidSpaceTest1() {
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        player.setIslandBoard(islandBoard);

        Worker worker1 = player.getWorkers().get(0);
        worker1.setWorkerPlayer(player);
        Worker worker2 = player.getWorkers().get(1);
        worker2.setWorkerPlayer(player);

        worker1.setCantMove(true);

        worker2.setCantMoveDown(true);

        worker1.setWorkerSpace(islandBoard.getSpace(0, 0));
        islandBoard.getSpace(0, 0).setOccupator(worker1);

        worker2.setWorkerSpace(islandBoard.getSpace(2, 3));
        islandBoard.getSpace(2, 3).setOccupator(worker2);
        islandBoard.getSpace(2, 3).setLevel(1);

        islandBoard.getSpace(2, 2).setLevel(2);
        islandBoard.getSpace(2, 4).setLevel(3);

        CheckingUtility.calculateValidSpace(player, islandBoard, "move");

        assertTrue(worker1.getPossibleMovements().size() == 0 && worker2.getPossibleMovements().size() == 1 &&
                worker2.getPossibleMovements().contains(islandBoard.getSpace(2, 2)));

    }

    @Test
    void calculateValidSpaceTest2() {
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        player.setIslandBoard(islandBoard);

        Worker worker1 = player.getWorkers().get(0);
        worker1.setWorkerPlayer(player);
        Worker worker2 = player.getWorkers().get(1);
        worker2.setWorkerPlayer(player);

        worker1.setCantBuild(true);

        worker2.setCantMoveUp(true);
        worker2.setCantMoveFirstSpace(true);
        worker2.setLastSpaceOccupied(islandBoard.getSpace(1, 0));

        worker1.setWorkerSpace(islandBoard.getSpace(0, 0));
        islandBoard.getSpace(0, 0).setOccupator(worker1);

        worker2.setWorkerSpace(islandBoard.getSpace(1, 1));
        islandBoard.getSpace(1, 1).setOccupator(worker2);

        islandBoard.getSpace(0, 0).setLevel(3);
        islandBoard.getSpace(0, 1).setLevel(1);

        islandBoard.getSpace(2, 2).setHasDome(true);

        CheckingUtility.calculateValidSpace(player, islandBoard, "move");

        assertTrue(worker1.getPossibleMovements().size() == 2 && worker1.getPossibleMovements().contains(islandBoard.getSpace(0, 1))
                && worker2.getPossibleMovements().size() == 4);
    }

    @Test
    void calculateValidSpaceTest3() {
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        player.setIslandBoard(islandBoard);

        Worker worker1 = player.getWorkers().get(0);
        worker1.setWorkerPlayer(player);
        Worker worker2 = player.getWorkers().get(1);
        worker2.setWorkerPlayer(player);

        worker1.setCantBuild(true);

        worker2.setCantBuild(false);
        worker2.setCantBuildDome(true);
        worker2.setCantBuildUnder(false);
        worker2.setCantBuildPerimeter(true);
        worker2.setCantBuildFirstSpace(true);

        worker2.setLastSpaceBuilt(islandBoard.getSpace(2, 2));

        worker1.setWorkerSpace(islandBoard.getSpace(0, 0));
        islandBoard.getSpace(0, 0).setOccupator(worker1);

        worker2.setWorkerSpace(islandBoard.getSpace(1, 1));
        islandBoard.getSpace(1, 1).setOccupator(worker2);

        islandBoard.getSpace(1, 2).setLevel(3);

        CheckingUtility.calculateValidSpace(player, islandBoard, "build");

        assertTrue(worker1.getPossibleBuilding().size() == 0 && worker2.getPossibleBuilding().size() == 2 &&
                worker2.getPossibleBuilding().contains(islandBoard.getSpace(1, 1)));
    }

    @Test
    void calculateValidSpacesTest3(){
        Player player1 = new Player();
        Player player2 = new Player();
        IslandBoard islandBoard = new IslandBoard();
        player1.setIslandBoard(islandBoard);
        player2.setIslandBoard(islandBoard);

        player1.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(1,1));
        islandBoard.getSpace(1,1).setOccupator(player1.getWorkers().get(0));
        player1.getWorkers().get(1).setWorkerSpace(islandBoard.getSpace(2,2));
        islandBoard.getSpace(2,2).setOccupator(player1.getWorkers().get(1));

        player2.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(0,0));
        islandBoard.getSpace(0,0).setOccupator(player2.getWorkers().get(0));
        player2.getWorkers().get(1).setWorkerSpace(islandBoard.getSpace(2,1));
        islandBoard.getSpace(2,1).setOccupator(player2.getWorkers().get(1));

        player1.setCantPush(false);
        player2.setCantPush(false);

        CheckingUtility.calculateValidSpace(player1, islandBoard, "move");
        CheckingUtility.calculateValidSpace(player2, islandBoard, "move");


        assertTrue(player1.getWorkers().get(0).getPossibleMovements().size()==6 &&
                player1.getWorkers().get(0).getPossibleMovements().contains(islandBoard.getSpace(2,1)) &&
                player2.getWorkers().get(0).getPossibleMovements().size()==2 &&
                player2.getWorkers().get(1).getPossibleMovements().contains(islandBoard.getSpace(2,2)) &&
                player2.getWorkers().get(1).getPossibleMovements().contains(islandBoard.getSpace(1,1)));

    }

    @Test
    void calculateValidSpacesTest4(){
        Player player1 = new Player();
        Player player2 = new Player();
        IslandBoard islandBoard = new IslandBoard();
        player1.setIslandBoard(islandBoard);
        player2.setIslandBoard(islandBoard);

        player1.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(1,1));
        islandBoard.getSpace(1,1).setOccupator(player1.getWorkers().get(0));
        player1.getWorkers().get(1).setWorkerSpace(islandBoard.getSpace(2,2));
        islandBoard.getSpace(2,2).setOccupator(player1.getWorkers().get(1));

        player2.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(0,0));
        islandBoard.getSpace(0,0).setOccupator(player2.getWorkers().get(0));
        player2.getWorkers().get(1).setWorkerSpace(islandBoard.getSpace(2,1));
        islandBoard.getSpace(2,1).setOccupator(player2.getWorkers().get(1));

        player1.setCantSwap(false);

        CheckingUtility.calculateValidSpace(player1, islandBoard, "move");

        assertTrue(player1.getWorkers().get(0).getPossibleMovements().size()==7 &&
                !player1.getWorkers().get(0).getPossibleMovements().contains(islandBoard.getSpace(2,2)));
    }



    /*
    questo test controlla che la restriction MustMoveUp sia eseguita correttamente
     */
    @Test
    public void mustMoveUpTest1() {
        IslandBoard islandBoard = new IslandBoard();
        Player ciro = new Player();
        Player franco = new Player();
        ciro.setIslandBoard(islandBoard);
        franco.setIslandBoard(islandBoard);

        islandBoard.setMustMoveUp(true);
        ciro.setNotMustMoveUp(true);

        franco.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(0, 0));
        franco.getWorkers().get(1).setWorkerSpace(islandBoard.getSpace(3, 0));
        islandBoard.getSpace(0, 0).setOccupator(franco.getWorkers().get(0));
        islandBoard.getSpace(3, 0).setOccupator(franco.getWorkers().get(1));
        islandBoard.getSpace(0, 1).setLevel(1);

        CheckingUtility.calculateValidSpace(franco, franco.getIslandBoard(), "move");
        assertTrue(franco.getWorkers().get(0).getPossibleMovements().size() == 1 &&
                franco.getWorkers().get(1).getPossibleMovements().size() == 0);
    }

    /*
    questo test controlla che la restriction MustMoveUp sia eseguita correttamente
     */
    @Test
    public void mustMoveUpTest2() {
        IslandBoard islandBoard = new IslandBoard();
        Player ciro = new Player();
        Player franco = new Player();
        ciro.setIslandBoard(islandBoard);
        franco.setIslandBoard(islandBoard);

        islandBoard.setMustMoveUp(true);
        ciro.setNotMustMoveUp(true);

        franco.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(0, 0));
        franco.getWorkers().get(1).setWorkerSpace(islandBoard.getSpace(3, 0));
        islandBoard.getSpace(0, 0).setOccupator(franco.getWorkers().get(0));
        islandBoard.getSpace(3, 0).setOccupator(franco.getWorkers().get(1));

        CheckingUtility.calculateValidSpace(franco, franco.getIslandBoard(), "move");
        assertTrue(franco.getWorkers().get(0).getPossibleMovements().size() == 3 &&
                franco.getWorkers().get(1).getPossibleMovements().size() == 5);
    }

    /*
    questo test controlla che la restriction HigherNoMove sia eseguita correttamente
     */
    @Test
    public void higherNoMoveTest1(){
        IslandBoard islandBoard = new IslandBoard();
        Player ciro = new Player();
        Player franco = new Player();
        ciro.setIslandBoard(islandBoard);
        franco.setIslandBoard(islandBoard);

        islandBoard.setHigherNoMove(true);
        ciro.setNotHigherNoMove(true);

        franco.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(0, 0));
        franco.getWorkers().get(1).setWorkerSpace(islandBoard.getSpace(3, 0));
        islandBoard.getSpace(0, 0).setOccupator(franco.getWorkers().get(0));
        islandBoard.getSpace(3, 0).setOccupator(franco.getWorkers().get(1));
        islandBoard.getSpace(0,0).setLevel(2);

        CheckingUtility.calculateValidSpace(franco, franco.getIslandBoard(), "move");
        assertTrue(franco.getWorkers().get(0).getPossibleMovements().size() == 0 &&
                    franco.getWorkers().get(1).getPossibleMovements().size() == 5);
    }

    /*
    questo test controlla che la restriction HigherNoMove sia eseguita correttamente
     */
    @Test
    public void higherNoMoveTest2(){
        IslandBoard islandBoard = new IslandBoard();
        Player ciro = new Player();
        Player franco = new Player();
        ciro.setIslandBoard(islandBoard);
        franco.setIslandBoard(islandBoard);

        islandBoard.setHigherNoMove(true);
        ciro.setNotHigherNoMove(true);

        franco.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(0, 0));
        franco.getWorkers().get(1).setWorkerSpace(islandBoard.getSpace(3, 0));
        islandBoard.getSpace(0, 0).setOccupator(franco.getWorkers().get(0));
        islandBoard.getSpace(3, 0).setOccupator(franco.getWorkers().get(1));
        islandBoard.getSpace(0,0).setLevel(2);
        islandBoard.getSpace(3,0).setLevel(2);

        CheckingUtility.calculateValidSpace(franco, franco.getIslandBoard(), "move");
        assertTrue(franco.getWorkers().get(0).getPossibleMovements().size() == 3 &&
                franco.getWorkers().get(1).getPossibleMovements().size() == 5);
    }
}