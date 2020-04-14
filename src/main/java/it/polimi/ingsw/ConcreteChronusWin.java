package it.polimi.ingsw;

import java.util.List;

public class ConcreteChronusWin extends PowerWinDecorator {

    public ConcreteChronusWin(WinConditionAB winConditionAB){
        this.winCondition = winConditionAB;
    }

    @Override
    public void checkHasWon(Worker worker, int startLevel, IslandBoard islandBoard) {
        winCondition.checkHasWon(worker, startLevel, islandBoard);
        if(!gethasWon())
            checkHasWonChronus(islandBoard);
    }

    @Override
    public void checkHasWon(List<Player> players) {
        winCondition.checkHasWon(players);
    }

    /*
    conta il numero di torri complete
    (celle aventi livello 4), se
    sono almeno 5 il giocatore vince
     */
    public void checkHasWonChronus(IslandBoard islandBoard){ //(VERIFICATA)
        int completeTower = 0;
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 5; j++)
                if(islandBoard.getSpace(i,j).getLevel() == 4)
                    completeTower++;
        if(completeTower > 4)
            setHasWon(true);
    }
}
