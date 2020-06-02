package it.polimi.ingsw;

import java.io.IOException;
import java.util.List;

public class GodChoice extends State {
    private TurnManager turnManager;
    private static final String GODSPECIFICATION="godChoice";

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
                e.printStackTrace();
            }
            player.getStateManager().setNextState(player);
        }
        else
            uselessInputNotify();
    }

    @Override
    public void onStateTransition() {

        turnManager = player.getStateManager().getTurnManager();
        if(turnManager.getAvailableGods().size()==1) {
            notifyLeft(turnManager.getAvailableGods());
            player.setPlayerGod(turnManager.getAvailableGods().get(0));
            turnManager.getAvailableGods().remove(player.getPlayerGod());
            try {
                player.getController().decoratePlayer(player);
                player.getStateManager().setNextState(player);
            } catch (IOException | NoSuchMethodException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            notifyLeft(turnManager.getAvailableGods());
            setAllowedInputs(turnManager.getAvailableGods());
        }
    }

    public String toString(){
        return "GodChoiceState";
    }

    private void notifyLeft(List<String> availableGods){
        LastChange inputExpected = new LastChange();
        inputExpected.setCode(1);
        inputExpected.setSpecification(GODSPECIFICATION);
        inputExpected.setStringList(availableGods);
        player.notify(inputExpected);
    }
}
