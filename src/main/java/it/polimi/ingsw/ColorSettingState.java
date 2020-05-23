package it.polimi.ingsw;

public class ColorSettingState extends State {
    ColorSettingState(Player player) {
        super(player);
    }

    public void onInput(Visitor visitor){
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
        if(colorSetted==false)
            notifyString();
    }


    @Override
    public void onStateTransition() {
       // player.getStateManager().notifyState();
    }
}
