package it.polimi.ingsw;

public class StateTest extends State {

    StateTest(Player player) {
        super(player);
    }

    @Override
    public void onStateTransition() {
    }

    public String toString(){
        return "StateTest";
    }
}
