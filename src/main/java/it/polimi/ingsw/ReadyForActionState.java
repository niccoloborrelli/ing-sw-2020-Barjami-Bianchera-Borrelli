package it.polimi.ingsw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadyForActionState extends State {
    private static final String actionType1 = "move";
    private static final String actionType2 = "build";
    private List <WorkerSpaceCouple> allowedSpaces;

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
        WorkerSpaceCouple input=visitor.visit(this);
        if(inputAcceptable(input)){
            player.setLastReceivedInput(input);
            player.getStateManager().setNextState(player);
        }
        else {
            uselessInputNotify();
        }
    }

    @Override
    public void onStateTransition() throws IOException {
        boolean hasLost = false;
        player.setLastReceivedInput(null);
        List<ArrayList<Space>> possibleAction = calculateAvailableSpace();
        hasLost = checkForLosing(possibleAction);
        if(hasLost)
            loseProcedure();
        else {
            notifyInputs();
            setAllowedSpaces();
        }
    }

    private List<ArrayList<Space>> calculateAvailableSpace(){
        String action = player.getActionsToPerform().get(0);
        clearAvailableSpacePlayer();
        CheckingUtility.calculateValidSpace(player,player.getIslandBoard(),action);
        return CheckingUtility.getLists(player,action);
    }

    private void clearAvailableSpacePlayer(){
        for(Worker w: player.getWorkers()){
            w.getPossibleBuilding().clear();
            w.getPossibleBuilding().clear();
        }
    }

    private void loseProcedure() throws IOException {
        player.setInGame(false);
        LastChange lastChange = player.getLastChange();
        lastChange.setCode(3);
        lastChange.setSpecification("lose");
        player.notifyController();
        player.getStateManager().setNextState(player); //se ho perso richiamo il stateManager perche' cambi lo stato
    }

    /**
     * this method sets the inputs this state of the FSM is allowed to receive and which from this can evolve
     */
    private void setAllowedSpaces() {
        String action=player.getActionsToPerform().get(0);
        List<WorkerSpaceCouple> allowed=new ArrayList<WorkerSpaceCouple>();
        for(Worker tempWorker:player.getWorkers()){
            if(action.equals(actionType1)){
                for(Space sp:tempWorker.getPossibleMovements())
                    allowed.add(new WorkerSpaceCouple(tempWorker,sp));
            }
            else if(action.equals(actionType2)){
                for(Space sp:tempWorker.getPossibleBuilding())
                    allowed.add(new WorkerSpaceCouple(tempWorker,sp));
            }
        }
        allowedSpaces=allowed;
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


    /**
     * controls if the input received from the visitor is acceptable
     * @param workerSpaceCouple is the input received from the visitor
     * @return
     */
    public boolean inputAcceptable(WorkerSpaceCouple workerSpaceCouple){
        for (WorkerSpaceCouple temp:allowedSpaces) {
            if(temp.getSpace()== workerSpaceCouple.getSpace() && temp.getWorker()== workerSpaceCouple.getWorker())
                return true;
        }
        return false;
    }

    /**
     * this methods notifies the controller for the input this state expects
     */
    private void notifyInputs(){
        String action=player.getActionsToPerform().get(0);
        for(Worker tempWorker: player.getWorkers()){
            LastChange workerActionsNotify=player.getLastChange();
            workerActionsNotify.setWorker(tempWorker);
            workerActionsNotify.setSpecification(action);
            workerActionsNotify.setCode(1);
            if(action.equals(actionType1)){
                workerActionsNotify.setListSpace(tempWorker.getPossibleMovements());
            }
            else if(action.equals(actionType2)){
                workerActionsNotify.setListSpace(tempWorker.getPossibleBuilding());
            }
            player.notifyController();
        }
    }
}