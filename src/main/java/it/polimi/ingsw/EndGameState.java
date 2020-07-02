package it.polimi.ingsw;

import static it.polimi.ingsw.FinalCommunication.ENDGAME;
import static it.polimi.ingsw.FinalCommunication.UPDATE_ENDGAME;

public class EndGameState extends State {

    /**
     * Represents the end of the game.
     * In this state no player can do something.
     * @param player
     */

    public EndGameState(Player player) {
        super(player);
    }

    @Override
    public void onStateTransition() {
        if(player.isHasWon())
            player.notifyWin();
        LastChange uselessInput = player.getLastChange();
        uselessInput.setCode(UPDATE_ENDGAME);
        uselessInput.setSpecification(ENDGAME);
        player.notifyController();
    }

    public String toString(){
        return "EndGameState";
    }
}
