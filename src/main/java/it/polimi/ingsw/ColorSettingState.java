package it.polimi.ingsw;

import java.io.IOException;
import java.util.List;

import static it.polimi.ingsw.DefinedValues.colorSettingState;

public class ColorSettingState extends State {

    /**
     * In this states a player choose his representative color.
     */

    private static final String COLORMESSAGE="color";
    ColorSettingState(Player player) {
        super(player);
    }


    @Override
    public void onInput(Visitor visitor) throws IOException {
        boolean colorSetted=false;
        String input=visitor.visit(this);
        TurnManager turnManager=player.getStateManager().getTurnManager();
        synchronized (player.getStateManager().getTurnManager()){
            if(turnManager.getAllowedColors().contains(input)) {
                player.setPlayerColor(input);
                player.getStateManager().setNextState(player);
                turnManager.getAllowedColors().remove(input);
                colorSetted=true;
            }
        }
        if(!colorSetted) {
            uselessInputNotify();
            onStateTransition();

        }
    }


    @Override
    public void onStateTransition() {
        TurnManager turnManager=player.getStateManager().getTurnManager();
        LastChange colorsExpected = player.getLastChange();
        colorsExpected.setCode(1);
        colorsExpected.setSpecification(COLORMESSAGE);
        synchronized (player.getStateManager().getTurnManager()) {
            List<String> allowedColors = turnManager.getAllowedColors();
            colorsExpected.setStringList(allowedColors);
            player.notifyController();
        }
    }


    public String toString(){
        return colorSettingState;
    }
}
