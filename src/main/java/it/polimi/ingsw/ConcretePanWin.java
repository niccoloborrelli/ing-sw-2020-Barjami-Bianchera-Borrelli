package it.polimi.ingsw;

import java.util.List;

public class ConcretePanWin extends PowerWinDecorator {

    public ConcretePanWin(WinConditionAB winConditionAB){
        this.winCondition = winConditionAB;
    }

    /*
    verifica se il giocatore ha vinto salendo al livello 3
    altrimenti verifica se ha vinto con il potere di Pan
     */
    @Override
    public void checkHasWon(Worker worker, int startLevel, IslandBoard islandBoard) {
        winCondition.checkHasWon(worker, startLevel, islandBoard);
        if(!gethasWon())
            checkHasWonPan(worker, startLevel);
    }

    @Override
    public void checkHasWon(List<Player> players){
        winCondition.checkHasWon(players);
    }

    /*
    verifica se il worker è sceso di almeno 2 livelli
     */
    public void checkHasWonPan(Worker worker, int startLevel){ //(VERIFICATA)
        if(startLevel == 2 && worker.getWorkerSpace().getLevel() == 0)
            setHasWon(true);
        else if(startLevel == 3 && (worker.getWorkerSpace().getLevel() == 0 || worker.getWorkerSpace().getLevel() == 1))
            setHasWon((true));
    }

}
