package it.polimi.ingsw;

public class Visitor {
    private SpaceInput spaceInput;
    private String stringInput;

    public Visitor(){
        spaceInput = new SpaceInput();
    }

    public SpaceInput visit(ReadyForActionState readyForAction){
        return this.spaceInput;
    }
    public String visit(PowerActivationState powerActivationState){
        return this.stringInput;
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

    public SpaceInput getSpaceInput() {
        return spaceInput;
    }

    public void setSpaceInput(SpaceInput spaceInput) {
        this.spaceInput = spaceInput;
    }

    public String getStringInput() {
        return stringInput;
    }

    public void setStringInput(String stringInput) {
        this.stringInput = stringInput;
    }
}

