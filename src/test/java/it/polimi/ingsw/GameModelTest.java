package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {

    /*
    The worker can't move
     */
    /*
    @Test
    void canWorkerDoSomethingTest1() {
        List<String> names = new ArrayList<>();
        names.add("Nico");
        names.add("Simo");
        names.add("Rei");

        GameModel gamemodel = new GameModel(names);
        Player player = gamemodel.getPlayers().get(0);

        player.getWorkers().get(0).setWorkerPlayer(player);
        player.getWorkers().get(1).setWorkerPlayer(player);

        gamemodel.getIslandBoard().getSpace(4,4).setOccupator(player.getWorkers().get(1));
        gamemodel.getIslandBoard().getSpace(0,0).setOccupator(player.getWorkers().get(0));

        player.getWorkers().get(0).setWorkerSpace(gamemodel.getIslandBoard().getSpace(0,0));
        player.getWorkers().get(1).setWorkerSpace(gamemodel.getIslandBoard().getSpace(4,4));

        gamemodel.getIslandBoard().getSpace(0,1).setLevel(2);
        gamemodel.getIslandBoard().getSpace(1,1).setHasDome(true);
        gamemodel.getIslandBoard().getSpace(1,0).setOccupator(gamemodel.getPlayers().get(1).getWorkers().get(0));

        gamemodel.setupConditions();
        player.getRestriction().restrictionEffectMovement(player,gamemodel.getIslandBoard());
        List<Space>[] space = gamemodel.getIslandBoard().checkAvailableMovement(player);
        assertFalse(gamemodel.canWorkerDoSomething(player.getWorkers().get(0), space[0], 0));
    }

    /*
    The worker can move and build
     */
    /*
    @Test
    void canWorkerDoSomethingTest2(){
        List<String> names = new ArrayList<>();
        names.add("Nico");
        names.add("Simo");
        names.add("Rei");

        GameModel gamemodel = new GameModel(names);
        Player player = gamemodel.getPlayers().get(0);

        player.getWorkers().get(0).setWorkerPlayer(player);
        player.getWorkers().get(1).setWorkerPlayer(player);

        gamemodel.getIslandBoard().getSpace(4,4).setOccupator(player.getWorkers().get(1));
        gamemodel.getIslandBoard().getSpace(0,0).setOccupator(player.getWorkers().get(0));

        player.getWorkers().get(0).setWorkerSpace(gamemodel.getIslandBoard().getSpace(0,0));
        player.getWorkers().get(1).setWorkerSpace(gamemodel.getIslandBoard().getSpace(4,4));

        gamemodel.getIslandBoard().getSpace(0,1).setLevel(2);
        gamemodel.getIslandBoard().getSpace(1,1).setHasDome(true);
        gamemodel.getIslandBoard().getSpace(1,0).setOccupator(gamemodel.getPlayers().get(1).getWorkers().get(0));

        gamemodel.setupConditions();
        player.getRestriction().restrictionEffectMovement(player,gamemodel.getIslandBoard());
        List<Space>[] space = gamemodel.getIslandBoard().checkAvailableMovement(player);
        assertTrue(gamemodel.canWorkerDoSomething(player.getWorkers().get(1), space[1], 1));
    }

    /*
    A worker can move, but not build
     */

    /*
    Può accadere solamente quando si scambia con un worker avversario e non può costruire nella casella da cui è partito
    L'escamotage per far calcolare correttamente che non ci sia nessuna mossa disponibile è mettere una cupola nella casella di partenza
    Da ricordare quando si implementaranno poteri di alcune divinità
     */
    /*
    @Test
    void canWorkerDoSomethingTest3(){
        List<String> names = new ArrayList<>();
        names.add("Nico");
        names.add("Simo");
        names.add("Rei");

        GameModel gamemodel = new GameModel(names);
        Player player = gamemodel.getPlayers().get(0);

        player.getWorkers().get(0).setWorkerPlayer(player);
        player.getWorkers().get(1).setWorkerPlayer(player);

        gamemodel.getIslandBoard().getSpace(4,4).setOccupator(player.getWorkers().get(1));
        gamemodel.getIslandBoard().getSpace(0,0).setOccupator(player.getWorkers().get(0));

        player.getWorkers().get(0).setWorkerSpace(gamemodel.getIslandBoard().getSpace(0,0));
        player.getWorkers().get(1).setWorkerSpace(gamemodel.getIslandBoard().getSpace(4,4));

        gamemodel.getIslandBoard().getSpace(0,0).setHasDome(true);
        gamemodel.getIslandBoard().getSpace(0,2).setHasDome(true);
        gamemodel.getIslandBoard().getSpace(1,2).setHasDome(true);
        gamemodel.getIslandBoard().getSpace(1,1).setHasDome(true);
        gamemodel.getIslandBoard().getSpace(1,0).setOccupator(gamemodel.getPlayers().get(1).getWorkers().get(0));

        gamemodel.setupConditions();
        player.getRestriction().restrictionEffectMovement(player,gamemodel.getIslandBoard());
        List<Space>[] space = gamemodel.getIslandBoard().checkAvailableMovement(player);
        assertFalse(gamemodel.canWorkerDoSomething(player.getWorkers().get(0), space[0], 0));
    }

}
*/

}
