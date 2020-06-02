package it.polimi.ingsw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        System.out.println("Sono dentro Worker Setting State");
        WorkerSpaceCouple input = visitor.visit(this);
        System.out.println(inputAcceptable(input));
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

    private void setSpaceIfPossible(Worker worker, WorkerSpaceCouple input){
        if(worker.getWorkerSpace()==null){
            settingSpaceWorker(worker, input);
            removeFromInput(worker,input.getSpace());;
            LastChange workerPlacedChange = player.getLastChange();
            workerPlacedChange.setCode(2);
            workerPlacedChange.setSpecification("move");
            workerPlacedChange.setWorker(input.getWorker());
            workerPlacedChange.setSpace(input.getSpace());
            player.notifyController();
        }else{
            uselessInputNotify();
        }
    }

    private boolean allWorkerOccupied(){
        for(Worker worker: player.getWorkers()){
            if(worker.getWorkerSpace()==null)
                return false;
        }
        return true;
    }

    private void removeFromInput(Worker worker, Space space){
        removeWorkerFromInput(worker); //needed to remove this worker from the available inputs cause it is already setted
        removeSpaceFromInput(space);
    }

    private void settingSpaceWorker(Worker worker, WorkerSpaceCouple input){
        Space space=input.getSpace();
        worker.setWorkerSpace(space);
        space.setOccupator(worker);
    }

    public void addWorkerAvailableSpaces(Worker worker){
        for(int i=STARTINGNUMBER;i<=ROWS;i++){
            for(int j=STARTINGNUMBER;j<=COLUMNS;j++){
                if(player.getIslandBoard().getSpace(i,j).getOccupator()==null){
                    workerSpaceCoupleList.add(new WorkerSpaceCouple(worker,player.getIslandBoard().getSpace(i,j)));
                }
            }
        }
    }

    public void removeSpaceFromInput(Space space){
        for(int i = STARTINGNUMBER; i< workerSpaceCoupleList.size(); i++){
            if(workerSpaceCoupleList.get(i).getSpace().equals(space)) {
                workerSpaceCoupleList.remove(i);
                i--;
            }
        }
    }

    public void removeWorkerFromInput(Worker worker){
        for(int i = STARTINGNUMBER; i< workerSpaceCoupleList.size(); i++){
            if(workerSpaceCoupleList.get(i).getWorker().equals(worker)) {
                workerSpaceCoupleList.remove(i);
                i--;
            }
        }
    }

    public String toString(){
        return "WorkerSettingState";
    }


    public boolean inputAcceptable(WorkerSpaceCouple workerSpaceCouple){
        for (WorkerSpaceCouple temp: workerSpaceCoupleList) {
            if(temp.getSpace()== workerSpaceCouple.getSpace()&&temp.getWorker()== workerSpaceCouple.getWorker())
                return true;
        }
        return false;
    }

    private void notifySetUp(){
        LastChange settingWorker = player.getLastChange();
        settingWorker.setCode(0);
        settingWorker.setSpecification("workerSetting");
        player.notifyController();
    }
}
