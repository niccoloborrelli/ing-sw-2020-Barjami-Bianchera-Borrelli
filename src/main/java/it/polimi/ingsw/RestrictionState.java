package it.polimi.ingsw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RestrictionState extends State {
    private static final String actionType1="move";
    private static final String actionType2="build";

    RestrictionState(Player player) {
        super(player);
    }

    @Override
    public void onInput(String input) throws IOException {

    }

    /**
     * in this method i set the lists of available building or movement of worker and if these empty i set player.haslost at true
     */
    @Override
    public void onStateTransiction() {
        boolean hasLost = false;
        List<Space> []possibleAction=new ArrayList[player.getWorkers().size()];
        String action = player.getActionsToPerform().get(0);
        CheckingUtility.restriction(player,action);
        possibleAction=CheckingUtility.getLists(player,action);
        hasLost = checkForLosing(possibleAction);

        if(hasLost)
            player.setInGame(false);

        player.getStateManager().setNextState(); //se ho perso richiamo il stateManager perche' cambi lo stato
    }

    /**
     * method wich returns true if the player isn't able of doing any action
     * @param possibleActions are the possible actions a player can make, if this is 0 the method actually return true
     * @return true: if the player has lost, false: if the player hasn't lost (has at least one possible action to perform)
     */
    private boolean checkForLosing(List[] possibleActions) {
        for (int i = 0; i < possibleActions.length; i++) {
            if (possibleActions[i].size() != 0)
                return false;
        }
        player.setInGame(false);
        return true; //se arrivo qui vuol dire che non ho nessuna action possibile
    }

    @Override
    public String toString() {
        return "RestrictionState";
    }

}
