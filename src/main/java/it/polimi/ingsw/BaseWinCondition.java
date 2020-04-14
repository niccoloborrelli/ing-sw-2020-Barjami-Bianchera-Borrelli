package it.polimi.ingsw;

import java.util.List;

public class BaseWinCondition extends WinConditionAB {

    /*
    verifica se il giocatore ha vinto salendo al livello 3
     */
    @Override //VERIFICATO
    public void checkHasWon(Worker worker, int startLevel, IslandBoard islandBoard) {
        if(worker != null)
            if(startLevel == 2 && worker.getWorkerSpace().getLevel() == 3)
                super.setHasWon(true);
    }

    @Override
    public void checkHasWon(List<Player> players){
        int i = 0;
        for(Player p: players){
            if(p.isInGame())
                i++;
        }
        if(i == 1)
            super.setHasWon(true);
    }

}
