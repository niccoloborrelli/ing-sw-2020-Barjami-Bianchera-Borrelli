package it.polimi.ingsw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PowerActivationState extends State {
    private static String activatePower="activate";
    private static String dontActivatePower="dontActivate";
    private FlowChanger flowPower;

    /**
     * flow power is the power actionable by this class
     * @param player is the player which may activate flowPower
     * @param flowPower flow power is the power actionable by this class
     */
    PowerActivationState(Player player,FlowChanger flowPower) {
        super(player);
        this.flowPower=flowPower;
    }

    /**
     * if input is activate this method recalls changeFlow, if input equals dontActivate this method does nothing, in both cases at the end of the method setNextState is recalled
     * @param input the input wich produce a change in the state of the fsm
     * @throws IOException
     */
    @Override
    public void onInput(String input){
        if(allowedInputs.contains(input)){
            if (input.equals(activatePower)) {
                flowPower.changeFlow(player);
            }
            player.getStateManager().setNextState(this);
        }
        else
            player.getStateManager().notifyError();
    }

    @Override
    public void onStateTransition() {
        List<String> allowedInputs=new <String>ArrayList();
        boolean usable=flowPower.isUsable(player);
        if(usable){
            allowedInputs.add(activatePower);
            allowedInputs.add(dontActivatePower);
            setAllowedInputs(allowedInputs);
        }
        else
            player.getStateManager().setNextState(this);
    }

    public String toString(){
        return "PowerActivationState";
    }
}
