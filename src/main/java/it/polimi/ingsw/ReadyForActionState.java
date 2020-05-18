package it.polimi.ingsw;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ReadyForActionState extends State {
    private static final String actionType1 = "move";
    private static final String actionType2 = "build";
    private static final int workerInInput = 1;
    private static final int rowInInput = 2;
    private static final int columnInInput = 3;
    ReadyForActionState(Player player) {
        super(player);
    }

    /**
     * This state permits to perform a change in the state of the game when the player hits it with an input
     * @param input the input wich produce a change in the state of the fsm
     * @throws IOException
     */
    @Override
    public void onInput(String input){
        if(allowedInputs.contains(input)) {
            player.setLastReceivedInput(input);
            player.getStateManager().setNextState(this);
        }
        else
            player.getStateManager().notifyError();
    }

    @Override
    public void onStateTransition() {
        List<ArrayList<Space>> possibleAction = new ArrayList<ArrayList<Space>>();
        String action = player.getActionsToPerform().get(0);
        possibleAction = CheckingUtility.getLists(player,action);
        setAllowed(possibleAction);
    }

    /**
     * this method sets the inputs this state of the FSM is allowed to receive and which from this can evolve
     * @param possibleAction list of the spaces in which the can player can act, these spaces has to be converted in strings
     */
    private void setAllowed(List<ArrayList<Space>> possibleAction) {
        List<String> inputs = new ArrayList<String>();
        String action=player.getActionsToPerform().get(0);
        int i = 0;
        for(ArrayList<Space> spaceList : possibleAction){
            for (Space s: spaceList) {
                if(action.equals(actionType1)){
                    inputs.add("M"+"-"+i+"-"+s.getRow()+"-"+s.getColumn()+"-");
                }
                else if(action.equals(actionType2)){
                    inputs.add("B"+"-"+i+"-"+s.getRow()+"-"+s.getColumn()+"-");
                }
            }
            i++;
        }
        super.setAllowedInputs(inputs);
    }

    @Override
    public String toString() {
        return "ReadyForAction";
    }
}