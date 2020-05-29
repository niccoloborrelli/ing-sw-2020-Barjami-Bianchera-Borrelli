package it.polimi.ingsw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PowerActivationState extends State {

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
     * @param visitor the Visitor used for receiving the input
     * @throws IOException
     **/
    @Override
    public void onInput(Visitor visitor) throws IOException {
        int input=visitor.visit(this);
        if(input==1||input==0){
            System.out.println("potere:" + input);
            player.setPowerUsed(true);
            if (input==1) {
                player.setPowerActivated(true);
                flowPower.changeFlow(player);
            }
            player.getStateManager().setNextState(player);
        }
        else
            player.notify(1);
    }

    @Override
    public void onStateTransition() throws IOException {
        boolean usable=flowPower.isUsable(player);
        if(!usable){
            player.setPowerNotUsable(true);
            player.getStateManager().setNextState(player);
        }
        player.notify(0);
    }

    public String toString(){
        return "PowerActivationState";
    }
}
