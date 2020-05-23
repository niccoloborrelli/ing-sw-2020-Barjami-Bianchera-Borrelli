package it.polimi.ingsw;

public class Visitor {
    private SpaceInput spaceInput;
    private String stringInput;
    public SpaceInput visit(ReadyForActionState readyForAtion){
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
}
