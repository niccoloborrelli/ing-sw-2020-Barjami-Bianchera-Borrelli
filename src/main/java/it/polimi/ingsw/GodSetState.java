package it.polimi.ingsw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GodSetState extends State {
    private int numberOfGodsReceived;
    private int numberOfGodsRequired;
    private TurnManager turnManager;

    GodSetState(Player player) {
        super(player);
    }

    @Override
    public void onInput(Visitor visitor) throws IOException {
        String input=visitor.visit(this);
        if(allowedInputs.contains(input)){
            turnManager.addGod(input);
            allowedInputs.remove(input);
            numberOfGodsReceived++;
        }
        else
            player.notify(1);

        if(numberOfGodsReceived==numberOfGodsRequired)
            player.getStateManager().setNextState(player);
    }

    @Override
    public void onStateTransition() {
        player.notify(6);
        List allGodsNames=new ArrayList(); //Riempio questa lista con i nomi di tutte le divinita', da finire
        setAllowedInputs(allGodsNames);
        turnManager=player.getStateManager().getTurnManager();
        numberOfGodsReceived=0;
        numberOfGodsRequired=player.getStateManager().getTurnManager().getPlayers().size();
    }

    public String toString(){
        return "GodSetState";
    }
}
