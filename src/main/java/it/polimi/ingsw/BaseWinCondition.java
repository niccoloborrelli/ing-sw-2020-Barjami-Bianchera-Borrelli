package it.polimi.ingsw;

import java.io.IOException;
import java.util.List;

public class BaseWinCondition extends WinConditionAB {

    /**
     * this method permits to set a WinConditionAb hasWon attribute to true if the worker has moved from a Space at level 2 to a
     * Space at level 3
     * @param worker is the Worker who's movement has to be controlled for a possible win
     * @param startLevel is the level of the worker's starting position
     */
    @Override //VERIFICATO
    public void checkHasWon(Worker worker, int startLevel, IslandBoard islandBoard) throws IOException {
        if(worker != null)
            if(startLevel == 2 && worker.getWorkerSpace().getLevel() == 3){
                setHasWon(true);
            }
    }

    /**
     * this method sets the attribute hasWon of a WinConditionAb to true if there is only a player still active in the game
     * @param players is the list of players playing in a game
     */
    @Override
    public void checkHasWon(List<Player> players){
        int i = 0;
        for(Player p: players){
            if(p.isInGame())
                i++;
        }
        if(i == 1) {
            setHasWon(true);
        }
    }


}
