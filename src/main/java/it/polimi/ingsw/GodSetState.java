package it.polimi.ingsw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GodSetState extends State {
    private int numberOfGodsReceived;
    private int numberOfGodsRequired;
    private TurnManager turnManager;
    private static final String GODSETSPECIFICATION="godSet";

    GodSetState(Player player) {
        super(player);
        numberOfGodsReceived=0;
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
        numberOfGodsReceived=0;
        numberOfGodsRequired=player.getStateManager().getTurnManager().getPlayers().size();
    }

    public String toString(){
        return "GodSetState";
    }

    private void notifyAllowedInputs(List <String> allGodsNames){
        LastChange godSetExpectedInput = new LastChange();
        godSetExpectedInput.setCode(1);
        godSetExpectedInput.setStringList(allGodsNames);
        godSetExpectedInput.setSpecification(GODSETSPECIFICATION);
        player.notify(godSetExpectedInput);
    }
}
