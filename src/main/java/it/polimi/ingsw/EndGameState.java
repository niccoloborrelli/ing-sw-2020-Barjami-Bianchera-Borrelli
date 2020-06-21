package it.polimi.ingsw;

public class EndGameState extends State {

    EndGameState(Player player) {
        super(player);
    }

    @Override
    public void onStateTransition() {
        LastChange uselessInput = player.getLastChange();
        uselessInput.setCode(3);
        uselessInput.setSpecification("endGame");
        player.notifyController();
    }

    public String toString(){
        return "EndGameState";
    }
}
