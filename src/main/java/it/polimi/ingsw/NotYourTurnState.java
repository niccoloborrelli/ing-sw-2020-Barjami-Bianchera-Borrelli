package it.polimi.ingsw;

import java.io.IOException;

public class NotYourTurnState extends State {
    private static String spectateAction1;
    private static String spectateAction2; //........

    NotYourTurnState(Player player) {
        super(player);
    }

    @Override
    public void onInput(String input) throws IOException {
        if(input.equals(spectateAction1))
            doSomething();
        else if(input.equals(spectateAction2))
            doSomethingElse();
    }

    @Override
    public void onStateTransition() {

    }

    public String toString(){
        return "NotYourTurnState";
    }
}

