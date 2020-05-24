package it.polimi.ingsw;

import java.io.IOException;

public class GodChoice extends State {
    private TurnManager turnManager;

    GodChoice(Player player) {
        super(player);
    }

    @Override
    public void onInput(Visitor visitor) throws IOException {
        String input=visitor.visit(this);
        if(getAllowedInputs().contains(input)){
            player.setPlayerGod(input);
            turnManager.removeGod(input);
            try {
                player.getController().decoratePlayer(player);
            }
            catch (Exception e){
                System.out.println("error");
            }
            player.getStateManager().setNextState(player);
        }
    }

    @Override
    public void onStateTransition() throws IOException {
        turnManager=player.getStateManager().getTurnManager();
        setAllowedInputs(turnManager.getAvailableGods());
    }

    public String toString(){
        return "GodChoiceState";
    }
}
