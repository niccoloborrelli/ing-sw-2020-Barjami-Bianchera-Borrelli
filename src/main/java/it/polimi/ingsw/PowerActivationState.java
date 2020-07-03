package it.polimi.ingsw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.DefinedValues.powerActivationState;
import static it.polimi.ingsw.FinalCommunication.UPDATE_CHOICE;

public class PowerActivationState extends State {

    private FlowChanger flowPower;
    private static final String POWERACTIVATION="power";
    private static final int usePower=1;
    private static final int noPower=0;

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
     * @param visitor the Visitor used for receiving the input
     * @throws IOException
     **/
    @Override
    public void onInput(Visitor visitor) throws IOException {
        int input=visitor.visit(this);
        if(input==usePower||input==noPower){
            player.setPowerUsed(true);
            if (input==usePower) {
                player.setPowerActivated(true);
                flowPower.changeFlow(player);
            }
            player.getStateManager().setNextState(player);
        }
        else {
            uselessInputNotify();
        }
    }

    @Override
    public void onStateTransition() throws IOException {
        boolean usable=flowPower.isUsable(player);
        if(!usable){
            player.setPowerNotUsable(true);
            player.getStateManager().setNextState(player);
        }
        else
            notifyAcceptableInputs();
    }

    public String toString(){
        return powerActivationState;
    }

    /**
     * method that notifies the input from which this state can evolve
     */
    private void notifyAcceptableInputs(){
        List<Integer> allowedInt=new ArrayList<Integer>();
        allowedInt.add(usePower);
        allowedInt.add(noPower);
        LastChange powerAllowedInputs = player.getLastChange();
        powerAllowedInputs.setCode(UPDATE_CHOICE);
        powerAllowedInputs.setSpecification(POWERACTIVATION);
        powerAllowedInputs.setIntegerList(allowedInt);
        player.notifyController();
    }
}
