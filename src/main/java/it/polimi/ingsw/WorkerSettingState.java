package it.polimi.ingsw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorkerSettingState extends State {
    private final int STARTINGNUMBER=0;
    private final int ROWS=4;
    private final int COLUMNS=4;
    private final int WORKER1INDEX=0;
    private final int WORKER2INDEX=1;
    private boolean worker1Setted;
    private boolean worker2Setted;
    private List<SpaceInput> spaceInputList;


    WorkerSettingState(Player player) {
        super(player);
        spaceInputList=new ArrayList<SpaceInput>();
    }

    public void onInput(Visitor visitor) throws IOException {
        SpaceInput input=visitor.visit(this);
        if(inputAcceptable(input)){
            spaceInputList.remove(input);
            Worker worker=input.getWorker();
            int workerNumber;
            workerNumber=player.getWorkers().indexOf(worker);
            if(workerNumber==WORKER1INDEX){
                worker1Setted=true;
                removeWorkerFromInput(worker); //needed to remove this worker from the available inputs cause it is already setted
            }
            else if (workerNumber==WORKER2INDEX) {
                worker2Setted = true;
                removeWorkerFromInput(worker); //needed to remove this worker from the available inputs cause it is already setted
            }
            Space space=input.getSpace();
            worker.setWorkerSpace(space);
            space.setOccupator(worker);
            player.notify(input,"move");
        }
        else
            player.notify(1);


        if(worker2Setted&&worker1Setted) {
            player.getStateManager().setNextState(player);
        }
    }

    @Override
    public void onStateTransition() throws IOException {
        worker1Setted=false;
        worker2Setted=false;
        for (Worker worker:player.getWorkers()) {
            addWorkerAvailableSpaces(worker);
        }
    }

    public void addWorkerAvailableSpaces(Worker worker){
        for(int i=STARTINGNUMBER;i<ROWS;i++){
            for(int j=STARTINGNUMBER;j<COLUMNS;j++){
                if(player.getIslandBoard().getSpace(i,j).getOccupator()==null){
                    spaceInputList.add(new SpaceInput(worker,player.getIslandBoard().getSpace(i,j)));
                }
            }
        }
    }

    public void removeWorkerFromInput(Worker worker){
        for(int i=STARTINGNUMBER;i<spaceInputList.size();i++){
            if(spaceInputList.get(i).getWorker()==worker)
                spaceInputList.remove(i);
        }
    }

    public String toString(){
        return "WorkerSettingState";
    }

    public boolean inputAcceptable(SpaceInput spaceInput){
        for (SpaceInput temp:spaceInputList) {
            if(temp.getSpace()==spaceInput.getSpace()&&temp.getWorker()==spaceInput.getWorker())
                return true;
        }
        return false;
    }
}
