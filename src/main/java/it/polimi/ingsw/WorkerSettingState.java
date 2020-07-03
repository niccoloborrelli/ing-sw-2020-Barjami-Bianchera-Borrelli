package it.polimi.ingsw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.DefinedValues.workerSettingState;
import static it.polimi.ingsw.FinalCommunication.*;

public class WorkerSettingState extends State {
    private final int STARTINGNUMBER=0;
    private final int ROWS=4;
    private final int COLUMNS=4;
    private List<WorkerSpaceCouple> workerSpaceCoupleList;


    WorkerSettingState(Player player) {
        super(player);
        workerSpaceCoupleList =new ArrayList<WorkerSpaceCouple>();
    }

    public void onInput(Visitor visitor) throws IOException {
        WorkerSpaceCouple input = visitor.visit(this);
        if(inputAcceptable(input)){
            workerSpaceCoupleList.remove(input);
            Worker worker=input.getWorker();
            setSpaceIfPossible(worker, input);
        }
        else{
            uselessInputNotify();
        }

        if(allWorkerOccupied()) {
            player.getStateManager().setNextState(player);
        }else {
            notifySetUp();
        }
    }

    @Override
    public void onStateTransition() {
        for (Worker worker:player.getWorkers()) {
            addWorkerAvailableSpaces(worker);
        }
        notifySetUp();
    }

    /**
     * Sets a worker in a space and sends the notify to players.
     * @param worker is worker to set.
     * @param input contains information of setting
     */
    private void setSpaceIfPossible(Worker worker, WorkerSpaceCouple input){
        if(worker.getWorkerSpace()==null){
            settingSpaceWorker(worker, input);
            removeFromInput(worker,input.getSpace());;
            LastChange workerPlacedChange = player.getLastChange();
            workerPlacedChange.setCode(UPDATE_GAME_FIELD);
            workerPlacedChange.setSpecification(WORKERSETTING);
            workerPlacedChange.setWorker(input.getWorker());
            workerPlacedChange.setSpace(input.getSpace());
            player.notifyController();
        }else{
            uselessInputNotify();
        }
    }

    /**
     * Checks if all worker are occupied.
     * @return true if they are, otherwise false.
     */
    private boolean allWorkerOccupied(){
        for(Worker worker: player.getWorkers()){
            if(worker.getWorkerSpace()==null)
                return false;
        }
        return true;
    }


    /**
     * Removes a worker and a space from acceptable inputs.
     * @param worker is worker to remove.
     * @param space is space to remove.
     */
    private void removeFromInput(Worker worker, Space space){
        removeWorkerFromInput(worker); //needed to remove this worker from the available inputs cause it is already setted
        removeSpaceFromInput(space);
    }


    /**
     * Sets the space of worker.
     * @param worker is worker to set.
     * @param input contains information of setting.
     */
    private void settingSpaceWorker(Worker worker, WorkerSpaceCouple input){
        Space space=input.getSpace();
        worker.setWorkerSpace(space);
        space.setOccupator(worker);
    }

    /**
     * Adds worker and avaiable spaces as possible inputs.
     * @param worker is worker to add.
     */
    public void addWorkerAvailableSpaces(Worker worker){
        for(int i=STARTINGNUMBER;i<=ROWS;i++){
            for(int j=STARTINGNUMBER;j<=COLUMNS;j++){
                if(player.getIslandBoard().getSpace(i,j).getOccupator()==null){
                    workerSpaceCoupleList.add(new WorkerSpaceCouple(worker,player.getIslandBoard().getSpace(i,j)));
                }
            }
        }
    }

    /**
     * Removes a determined space from input.
     * @param space is space to remove.
     */
    public void removeSpaceFromInput(Space space){
        for(int i = STARTINGNUMBER; i< workerSpaceCoupleList.size(); i++){
            if(workerSpaceCoupleList.get(i).getSpace().equals(space)) {
                workerSpaceCoupleList.remove(i);
                i--;
            }
        }
    }

    /**
     * Removes a determined worker from possible inputs.
     * @param worker is worker to remove.
     */
    public void removeWorkerFromInput(Worker worker){
        for(int i = STARTINGNUMBER; i< workerSpaceCoupleList.size(); i++){
            if(workerSpaceCoupleList.get(i).getWorker().equals(worker)) {
                workerSpaceCoupleList.remove(i);
                i--;
            }
        }
    }

    public String toString(){
        return workerSettingState;
    }


    /**
     * Controls if input is acceptable.
     * @param workerSpaceCouple is input.
     * @return true if it is, otherwise false.
     */
    public boolean inputAcceptable(WorkerSpaceCouple workerSpaceCouple){
        for (WorkerSpaceCouple temp: workerSpaceCoupleList) {
            if(temp.getSpace()== workerSpaceCouple.getSpace()&&temp.getWorker()== workerSpaceCouple.getWorker())
                return true;
        }
        return false;
    }


    /**
     * Notifies that player is in worker setting state.
     */
    private void notifySetUp(){
        LastChange settingWorker = player.getLastChange();
        settingWorker.setCode(UPDATE_TO_PRINT);
        settingWorker.setSpecification(SET_UP);
        player.notifyController();

    }
}
