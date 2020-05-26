package it.polimi.ingsw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorkerSettingState extends State {
    private final int STARTINGNUMBER=0;
    private final int ROWS=4;
    private final int COLUMNS=4;
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
            setSpaceIfPossible(worker, input);
        }
        else
            player.notify(1);

        if(allWorkerOccupied()) {
            player.getStateManager().setNextState(player);
        }else {
            player.notify(8);
        }
    }

    @Override
    public void onStateTransition() {
        for (Worker worker:player.getWorkers()) {
            addWorkerAvailableSpaces(worker);
        }
        player.notify(8);
    }

    private void setSpaceIfPossible(Worker worker, SpaceInput input){
        if(worker.getWorkerSpace()==null){
            settingSpaceWorker(worker, input);
            removeFromInput(worker,input.getSpace());;
            player.notify(input,"move");
        }else
            player.notify(1);
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

    private void settingSpaceWorker(Worker worker, SpaceInput input){
        Space space=input.getSpace();
        worker.setWorkerSpace(space);
        space.setOccupator(worker);
    }

    public void addWorkerAvailableSpaces(Worker worker){
        for(int i=STARTINGNUMBER;i<=ROWS;i++){
            for(int j=STARTINGNUMBER;j<=COLUMNS;j++){
                if(player.getIslandBoard().getSpace(i,j).getOccupator()==null){
                    spaceInputList.add(new SpaceInput(worker,player.getIslandBoard().getSpace(i,j)));
                }
            }
        }
    }

    public void removeSpaceFromInput(Space space){
        for(int i=STARTINGNUMBER;i<spaceInputList.size();i++){
            if(spaceInputList.get(i).getSpace().equals(space)) {
                spaceInputList.remove(i);
                i--;
            }
        }
    }

    public void removeWorkerFromInput(Worker worker){
        for(int i=STARTINGNUMBER;i<spaceInputList.size();i++){
            if(spaceInputList.get(i).getWorker().equals(worker)) {
                spaceInputList.remove(i);
                i--;
            }
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
