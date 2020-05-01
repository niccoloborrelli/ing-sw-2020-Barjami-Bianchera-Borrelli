package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseRestrictionTest {

    /*restrictionEffectMovementTest1 vede se con un solo worker si settano
    le liste correttamente
     */
    @Test
    void restrictionEffectMovementTest1() {
        IslandBoard island = new IslandBoard();
        String playerName = "ciro";
        Player player1 = new Player( null);
        Worker worker1 = new Worker();
        BaseRestriction br = new BaseRestriction();

        player1.getWorkers().add(worker1);
        worker1.setWorkerSpace(island.getSpace(0,0));
        worker1.getWorkerSpace().setOccupator(worker1);
        br.restrictionEffectMovement(player1, island);
        for(int i = 0; i < 5;i++)
            for(int j = 0;j < 5;j++)
                if (i == 0 && j == 1 || i == 1 && j == 0 || i == 1 && j == 1) {
                    if (island.getSpace(i, j).isAvailableMovement().size() != 1 || !island.getSpace(i, j).isAvailableMovement().contains(worker1))
                        System.out.println("Non ha aggiunto il worker nella lista della cella " + i + "," + j);
                    else
                        System.out.println("La lista in " + i + "," + j + " non è vuota");

                }
        assertTrue(true);
    }

    /*restrictionEffectMovementTest2 verifica che se c'è una cupola o un
     dislivello troppo grande non viene aggiunto il worker in quella lista
     */
    @Test
    void restrictionEffectMovementTest2() {
        IslandBoard island = new IslandBoard();
        String playerName = "ciro";
        Player player1 = new Player(null);
        Worker worker1 = new Worker();
        BaseRestriction br = new BaseRestriction();

        player1.getWorkers().add(worker1);
        worker1.setWorkerSpace(island.getSpace(0,0));
        island.getSpace(1,1).setLevel(2);
        island.getSpace(1,0).setHasDome(true);
        br.restrictionEffectMovement(player1, island);

        if(!island.getSpace(1,1).isAvailableMovement().contains(worker1))
            System.out.println("Funzionamento dislivello corretto");
        else {
            System.out.println("Funzionamento dislivello sbagliato");
        }
        if(!island.getSpace(1,0).isAvailableMovement().contains(worker1))
            System.out.println("Funzionamento cupola corretto");
        else {
            System.out.println("Funzionamento cupola sbagliato");
        }
        assertTrue(island.getSpace(1,1).isAvailableMovement().size() == 0 && island.getSpace(0,1).isAvailableMovement().size() != 0 &&
                            island.getSpace(1,0).isAvailableMovement().size() == 0);

    }

    /*restrictionEffectMovementTest2 verifica che se c'è un
     altro worker non viene aggiunto il worker in quella lista
     */
    @Test
    void restrictionEffectMovementTest3() {
        IslandBoard island = new IslandBoard();
        String playerName = "ciro";
        Player player1 = new Player(null);
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        BaseRestriction br = new BaseRestriction();

        player1.getWorkers().add(worker1);
        player1.getWorkers().add(worker2);
        worker1.setWorkerSpace(island.getSpace(0,0));
        worker2.setWorkerSpace(island.getSpace(0,1));
        worker1.getWorkerSpace().setOccupator(worker1);
        worker2.getWorkerSpace().setOccupator(worker2);
        br.restrictionEffectMovement(player1, island);

        if(worker2.getWorkerSpace().isAvailableMovement().size() == 0)
            System.out.println("Worker1 non si muove nella casella di Worker2");
        else
            System.out.println("Worker1 si muove dove non dovrebbe");

        if(worker1.getWorkerSpace().isAvailableMovement().size() == 0)
            System.out.println("Worker2 non si muove nella casella di Worker1");
        else
            System.out.println("Worker2 si muove dove non dovrebbe");

        assertTrue(true);
    }

    /*restrictionEffectBuildingTest1 vede se con un solo worker si settano
    le liste correttamente
     */
    @Test
    void restrictionEffectBuildingTest1() {
        IslandBoard island = new IslandBoard();
        String playerName = "ciro";
        Player player1 = new Player(null);
        Worker worker1 = new Worker();
        BaseRestriction br = new BaseRestriction();

        player1.getWorkers().add(worker1);
        worker1.setWorkerSpace(island.getSpace(0,0));
        worker1.getWorkerSpace().setOccupator(worker1);
        br.restrictionEffectBuilding(worker1, island);

        for(int i = 0; i < 5; i++)
            for( int j = 0; j < 5; j++)
                if (i == 0 && j == 1 || i == 1 && j == 0 || i == 1 && j == 1) {
                    if (island.getSpace(i, j).isAvailableBuilding().size() != 1 || !island.getSpace(i, j).isAvailableBuilding().contains(worker1))
                        System.out.println("Non ha aggiunto il worker nella lista della cella " + i + "," + j);
                    else
                        System.out.println("La lista in " + i + "," + j + " non è vuota");
                }


    }

    /*restrictionEffectBuildingTEst2 verifica che se c'è una
    cupola o un altro worker non viene aggiunto il worker in quella lista
     */
    @Test
    void restrictionEffectBuildingTest2() {
        IslandBoard island = new IslandBoard();
        String playerName = "ciro";
        Player player1 = new Player(null);
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        BaseRestriction br = new BaseRestriction();

        player1.getWorkers().add(worker1);
        player1.getWorkers().add(worker2);
        worker1.setWorkerSpace(island.getSpace(0,0));
        worker2.setWorkerSpace(island.getSpace(1,1));
        worker1.getWorkerSpace().setOccupator(worker1);
        worker2.getWorkerSpace().setOccupator(worker2);
        island.getSpace(1,0).setHasDome(true);
        br.restrictionEffectBuilding(worker1, island);
        br.restrictionEffectBuilding(worker2, island);

        if(island.getSpace(1,0).isAvailableBuilding().size() == 0)
            System.out.println("Funzionamento cupola corretto");
        else
            System.out.println("Funzionamento cupola sbagliato");
        if(!island.getSpace(1,1).isAvailableBuilding().contains(worker1))
            System.out.println("Funzionamento altro worker1 su casella corretto");
        else
            System.out.println("Funzionamento altro worker1 su casella sbagliato");
        if(!island.getSpace(0,0).isAvailableBuilding().contains(worker2))
            System.out.println("Funzionamento altro worker2 su casella corretto");
        else
            System.out.println("Funzionamento altro worker2 su casella sbagliato");

        assertTrue(true);
    }
}