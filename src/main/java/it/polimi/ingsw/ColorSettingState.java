package it.polimi.ingsw;

import java.io.IOException;

public class ColorSettingState extends State {
    ColorSettingState(Player player) {
        super(player);
    }

    public void onInput(Visitor visitor) throws IOException {
        boolean colorSetted=false;
        String input=visitor.visit(this);
        TurnManager turnManager=player.getStateManager().getTurnManager();
        synchronized (turnManager){
            if(turnManager.getAllowedColors().contains(input)) {
                turnManager.setColor(player, input);
                player.getStateManager().setNextState(player);
                colorSetted=true;
            }
        }
        if(!colorSetted)
            player.notify(1);
    }


    @Override
    public void onStateTransition() {
       // player.getStateManager().notifyState();
    }

    public String toString(){
        return "ColorSettingState";
    }
}
