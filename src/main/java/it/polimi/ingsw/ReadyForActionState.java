package it.polimi.ingsw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.DefinedValues.MINSIZE;
import static it.polimi.ingsw.DefinedValues.readyForActionState;
import static it.polimi.ingsw.FinalCommunication.*;

public class ReadyForActionState extends State {
    private static final String actionType1 = "move";
    private static final String actionType2 = "build";
    private static final int invalidCoordinate=-1;

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

    /**
     * This method calculate all the available spaces for the next action of the player,
     * if there are none the player loses and his game finishes
     * @throws IOException
     */
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

    /**
     * Calculates available spaces that player can do an action on,
     * depending what action is.
     * @return a list of avaiable spaces for each worker
     */
    private List<ArrayList<Space>> calculateAvailableSpace(){
        String action = player.getActionsToPerform().get(MINSIZE);
        clearAvailableSpacePlayer();
        CheckingUtility.calculateValidSpace(player,player.getIslandBoard(),action);
        return CheckingUtility.getLists(player,action);
    }

    /**
     * Clears list of possible movements and buildings of every worker.
     */
    private void clearAvailableSpacePlayer(){
        for(Worker w: player.getWorkers()){
            w.getPossibleMovements().clear();
            w.getPossibleBuilding().clear();
        }
    }

    /**
     * Elaborates lose procedure. Notifies players.
     * @throws IOException if notify goes wrong.
     */
    private void loseProcedure() throws IOException {
        player.setInGame(false);
        deleteWorkersFromBoard();
        LastChange lastChange = player.getLastChange();
        lastChange.setCode(UPDATE_ENDGAME);
        lastChange.setSpecification(LOSE);
        player.notifyController();
        player.getStateManager().setNextState(player);
        player.getStateManager().getTurnManager().setNextPlayer(player);
    }

    /**
     * Deletes workers from board.
     */
    private void deleteWorkersFromBoard(){
        for(Worker w: player.getWorkers()) {
            Space space = w.getWorkerSpace();
            space.setOccupator(null);
            w.setWorkerSpace(new Space(invalidCoordinate,invalidCoordinate));
            notifyDeletedWorker(w, space);
            w.setWorkerSpace(null);
        }

    }

    /**
     * Notifies that worker are deleted.
     * @param worker is worker deleted.
     * @param space is space of worker.
     */
    private void notifyDeletedWorker(Worker worker, Space space){
        LastChange lastChange = new LastChange();
        lastChange.setCode(UPDATE_GAME_FIELD);
        lastChange.setWorker(worker);
        lastChange.setSpecification(DELETED);
        lastChange.setSpace(space);

        player.notify(lastChange);

    }



    /**
     * this method sets the inputs this state of the FSM is allowed to receive and which from this can evolve
     */
    private void setAllowedSpaces() {
        String action=player.getActionsToPerform().get(MINSIZE);
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
            if (spaceList.size()>MINSIZE)
                return false;
        }
        player.setInGame(false);
        return true; //se arrivo qui vuol dire che non ho nessuna action possibile
    }

    @Override
    public String toString() {
        return readyForActionState;
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
        String action=player.getActionsToPerform().get(MINSIZE);
        for(Worker tempWorker: player.getWorkers()){
            LastChange workerActionsNotify=player.getLastChange();
            workerActionsNotify.setWorker(tempWorker);
            workerActionsNotify.setSpecification(action);
            workerActionsNotify.setCode(UPDATE_CHOICE);
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