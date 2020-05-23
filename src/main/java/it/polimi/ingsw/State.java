package it.polimi.ingsw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class State {
    private static final String move="move";
    private static final String build="build";

    Player player;
    /**
     * Context passes itself through the state constructor. This may help a
     * state to fetch some useful context data if needed.
     */
    List<String> allowedInputs;

    State(Player player) {
        this.player = player;
        allowedInputs=new ArrayList<String>();
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * a method invoked when the state gets an input to change and the evolve the FSM state
     * @param input the input wich produce a change in the state of the fsm
     * @throws IOException
     */
    public void onInput(Visitor visitor){
        //inserisco on input base
    }

    /**
     * a method to be invoked everytime the player enters this state
     */
    public abstract void onStateTransition() throws IOException;

    /**
     * @return a list of the inputs that may modify the state of the FSM
     */
    public List<String> getAllowedInputs(){
        return this.allowedInputs;
    }

    public void setAllowedInputs(List<String> allowedInputs) {
        this.allowedInputs = allowedInputs;
    }
}