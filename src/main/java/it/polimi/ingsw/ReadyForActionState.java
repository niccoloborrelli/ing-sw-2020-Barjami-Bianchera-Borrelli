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
    private List <SpaceInput> allowedSpaces;

    ReadyForActionState(Player player) {
        super(player);
    }

    /**
     * This state permits to perform a change in the state of the game when the player hits it with an input
     * @param visitor the visitor used to get the input
     * @throws IOException
     */
    @Override
    public void onInput(Visitor visitor) throws IOException {
        SpaceInput input=visitor.visit(this);
        if(inputAcceptable(input)){
            player.setLastReceivedInput(input);
            player.getStateManager().setNextState(player);
        }
        else
            player.notify(1);
    }

    @Override
    public void onStateTransition() throws IOException {
        boolean hasLost = false;
        player.setLastReceivedInput(null);
        List<ArrayList<Space>> possibleAction = new ArrayList<ArrayList<Space>>();
        String action = player.getActionsToPerform().get(0);
        CheckingUtility.calculateValidSpace(player,player.getIslandBoard(),action);
        possibleAction=CheckingUtility.getLists(player,action);
        hasLost = checkForLosing(possibleAction);
        if(hasLost) {
            player.setInGame(false);
            player.notify(3);
            player.getStateManager().setNextState(player); //se ho perso richiamo il stateManager perche' cambi lo stato
        }//fine della parte della fu-RestrictionState
        else {
            setAllowedSpaces();
            player.notify(allowedSpaces);
        }
    }

    /**
     * this method sets the inputs this state of the FSM is allowed to receive and which from this can evolve
     */
    private void setAllowedSpaces() {
        String action=player.getActionsToPerform().get(0);
        List<SpaceInput> allowed=new ArrayList<SpaceInput>();
        for(Worker tempWorker:player.getWorkers()){
            if(action.equals(actionType1)){
                for(Space sp:tempWorker.getPossibleMovements())
                    allowed.add(new SpaceInput(tempWorker,sp));
            }
            else if(action.equals(actionType2)){
                for(Space sp:tempWorker.getPossibleBuilding())
                    allowed.add(new SpaceInput(tempWorker,sp));
            }
        }
        allowedSpaces=allowed;

        for(SpaceInput spaceInput: allowedSpaces){
            System.out.println(spaceInput.getWorker());
            System.out.println("Row: " + spaceInput.getSpace().getRow() + " Column: " + spaceInput.getSpace().getColumn());
        }
    }

    /**
     * method wich returns true if the player isn't able of doing any action
     * @param possibleActions are the possible actions a player can make, if this is 0 the method actually return true
     * @return true: if the player has lost, false: if the player hasn't lost (has at least one possible action to perform)
     */
    private boolean checkForLosing(List<ArrayList<Space>> possibleActions) {
        for (ArrayList<Space> spaceList : possibleActions) {
            if (spaceList.size()>0)
                return false;
        }
        player.setInGame(false);
        return true; //se arrivo qui vuol dire che non ho nessuna action possibile
    }

    @Override
    public String toString() {
        return "ReadyForActionState";
    }


    public boolean inputAcceptable(SpaceInput spaceInput){
        for (SpaceInput temp:allowedSpaces) {
            if(temp.getSpace()==spaceInput.getSpace()&&temp.getWorker()==spaceInput.getWorker())
                return true;
        }
        return false;
    }

    public List<SpaceInput> getAllowedSpaces() {
        return allowedSpaces;
    }
}