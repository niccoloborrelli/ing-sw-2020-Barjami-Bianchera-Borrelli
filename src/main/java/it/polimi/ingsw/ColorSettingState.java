package it.polimi.ingsw;

import java.io.IOException;
import java.util.List;

public class ColorSettingState extends State {
    private static final String COLORMESSAGE="color";
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
                turnManager.getAllowedColors().remove(input);
                colorSetted=true;
            }
        }
        if(!colorSetted) {
            System.out.println("Inviato da color");
            uselessInputNotify();
        }
    }


    @Override
    public void onStateTransition() {
        TurnManager turnManager=player.getStateManager().getTurnManager();
        LastChange colorsExpected = new LastChange();
        colorsExpected.setCode(1);
        colorsExpected.setSpecification(COLORMESSAGE);
        synchronized (turnManager) {
            List<String> allowedColors = turnManager.getAllowedColors();
            colorsExpected.setStringList(allowedColors);
            player.notify(colorsExpected);
        }
    }

    public String toString(){
        return "ColorSettingState";
    }
}
