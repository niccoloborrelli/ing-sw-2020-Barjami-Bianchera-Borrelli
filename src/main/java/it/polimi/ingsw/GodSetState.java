package it.polimi.ingsw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static it.polimi.ingsw.DefinedValues.MINSIZE;
import static it.polimi.ingsw.DefinedValues.godSetState;
import static it.polimi.ingsw.FinalCommunication.UPDATE_CHOICE;

public class GodSetState extends State {
    private int numberOfGodsReceived;
    private int numberOfGodsRequired;
    private TurnManager turnManager;
    private static final String GODSETSPECIFICATION="godSet";

    GodSetState(Player player) {
        super(player);
        numberOfGodsReceived=MINSIZE;
    }

    @Override
    public void onInput(Visitor visitor) throws IOException {
        String input=visitor.visit(this);
        if(allowedInputs.contains(input)){
            turnManager.addGod(input);
            allowedInputs.remove(input);
            numberOfGodsReceived++;
        }
        else {
            uselessInputNotify();
        }

        if(numberOfGodsReceived==numberOfGodsRequired) {
            player.getStateManager().setNextState(player);
        }
    }

    @Override
    public void onStateTransition() {
        player.getController().createGodMap();
        List<String> allGodsNames= new ArrayList<String>(player.getController().getGodMap().keySet());
        setAllowedInputs(allGodsNames);
        notifyAllowedInputs(allGodsNames);
        turnManager=player.getStateManager().getTurnManager();
        numberOfGodsReceived=MINSIZE;
        numberOfGodsRequired=player.getStateManager().getTurnManager().getPlayers().size();
    }

    public String toString(){
        return godSetState;
    }

    /**
     * Notifies all the gods to the challenger
     * @param allGodsNames is the list of all gods
     */
    private void notifyAllowedInputs(List <String> allGodsNames){
        LastChange godSetExpectedInput = player.getLastChange();
        godSetExpectedInput.setCode(UPDATE_CHOICE);
        godSetExpectedInput.setStringList(allGodsNames);
        godSetExpectedInput.setSpecification(GODSETSPECIFICATION);
        player.notifyController();
    }
}
