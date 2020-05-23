package it.polimi.ingsw;

public class NameSettingState extends State {
    NameSettingState(Player player) {
        super(player);
    }

    @Override
    public void onInput(Visitor visitor){
        boolean nameSetted=false;
        String input=visitor.visit(this);
        TurnManager turnManager=player.getStateManager().getTurnManager();
        synchronized (turnManager){
            if(!turnManager.getNotAllowedNames().contains(input)) {
                turnManager.addName(input);
                player.setPlayerName(input);
                player.getStateManager().setNextState(player);
                nameSetted=true;
            }
        }
        if(nameSetted==false)
            notifyString();
    }

    @Override
    public void onStateTransition() {
        // player.getStateManager().notifyState();
    }
}
