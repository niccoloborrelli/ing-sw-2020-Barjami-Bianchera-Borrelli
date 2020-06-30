package it.polimi.ingsw;

import static it.polimi.ingsw.FinalCommunication.ENDGAME;

public class EndGameState extends State {

    EndGameState(Player player) {
        super(player);
    }

    @Override
    public void onStateTransition() {
        if(player.isHasWon())
            player.notifyWin();
        LastChange uselessInput = player.getLastChange();
        uselessInput.setCode(3);
        uselessInput.setSpecification(ENDGAME);
        player.notifyController();
    }

    public String toString(){
        return "EndGameState";
    }
}
