package it.polimi.ingsw;

public class BaseWinCondition extends WinConditionAB {

    /*
    verifica se il giocatore ha vinto salendo al livello 3
     */
    @Override //VERIFICATO
    public void checkHasWon(Worker worker, int startLevel) {
        if(worker != null)
            if(startLevel == 2 && worker.getWorkerSpace().getLevel() == 3)
                setHasWon(true);
    }
}
