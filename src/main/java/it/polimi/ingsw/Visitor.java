package it.polimi.ingsw;

public class Visitor {
    private WorkerSpaceCouple workerSpaceCouple;
    private String stringInput;
    private int intInput;

    public Visitor(){
        workerSpaceCouple = new WorkerSpaceCouple();
    }

    public WorkerSpaceCouple visit(ReadyForActionState readyForAction){
        return this.workerSpaceCouple;
    }
    public int visit(PowerActivationState powerActivationState){
        return this.intInput;
    }
    public String visit(GodSetState godSetState){
        return this.stringInput;
    }
    public String visit(NameSettingState nameSettingState){
        return this.stringInput;
    }
    public String visit(ColorSettingState colorSettingState){
        return this.stringInput;
    }
    public String visit(GodChoice godChoice){
        return this.stringInput;
    }
    public int visit(EndTurnState endTurnState) { return 0; }

    public int getIntInput() {
        return intInput;
    }

    public void setIntInput(int intInput) {
        this.intInput = intInput;
    }

    public WorkerSpaceCouple getSpaceInput() {
        return workerSpaceCouple;
    }

    public void setWorkerSpaceCouple(WorkerSpaceCouple workerSpaceCouple) {
        this.workerSpaceCouple = workerSpaceCouple;
    }

    public String getStringInput() {
        return stringInput;
    }

    public void setStringInput(String stringInput) {
        this.stringInput = stringInput;
    }

    public int visit(PreLobbyState preLobbyState){
        return this.intInput;
    }

    public WorkerSpaceCouple visit(WorkerSettingState workerSettingState){
        return this.workerSpaceCouple;
    }

}

