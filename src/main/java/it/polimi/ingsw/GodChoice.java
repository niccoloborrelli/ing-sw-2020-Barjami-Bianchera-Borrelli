package it.polimi.ingsw;

import java.io.IOException;
import java.util.List;

import static it.polimi.ingsw.DefinedValues.MINSIZE;
import static it.polimi.ingsw.DefinedValues.godChoice;
import static it.polimi.ingsw.FinalCommunication.UPDATE_CHOICE;

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
                e.printStackTrace();}

            player.getStateManager().setNextState(player);
        }
        else
            uselessInputNotify();
    }

    @Override
    public void onStateTransition() {
        turnManager = player.getStateManager().getTurnManager();
        if(turnManager.getAvailableGods().size()==MINSIZE+1) {
            notifyLeft(turnManager.getAvailableGods());
            player.setPlayerGod(turnManager.getAvailableGods().get(MINSIZE));
            turnManager.getAvailableGods().remove(player.getPlayerGod());
            try {
                player.getController().decoratePlayer(player);
                player.getStateManager().setNextState(player);
            } catch (IOException | NoSuchMethodException | ClassNotFoundException e) {
                e.printStackTrace(); }
        }else{
            notifyLeft(turnManager.getAvailableGods());
            setAllowedInputs(turnManager.getAvailableGods());
        }
    }

    public String toString(){
        return godChoice;
    }

    /**
     * notifies the gods still available to be chosen
     * @param availableGods a list of the gods available
     */
    private void notifyLeft(List<String> availableGods){
        LastChange inputExpected = player.getLastChange();
        inputExpected.setCode(UPDATE_CHOICE);
        inputExpected.setSpecification(GODSPECIFICATION);
        inputExpected.setStringList(availableGods);
        player.notifyController();
    }
}
