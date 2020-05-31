package it.polimi.ingsw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PreLobbyState extends State{
    private List<Integer> allowedInts;
    private LobbyManager lobbyManager;
    private boolean enteredGame;
    private static final int MINNUMBEROFPLAYERS=2;
    private static final int MAXNUMBEROFPLAYERS=3;
    PreLobbyState(Player player, LobbyManager lobbyManager) {
        super(player);
        this.lobbyManager = lobbyManager;
        allowedInts=new ArrayList<Integer>();
        enteredGame=false;
    }

    public void onInput(Visitor visitor){
        int input=visitor.visit(this);
        if(enteredGame==false&&allowedInts.contains(input)){
            lobbyManager.addPlayer(player,input);
            enteredGame=true;
        }
        else {
            uselessInputNotify();
            }
    }

    @Override
    public void onStateTransition() throws IOException {
        for(int i=MINNUMBEROFPLAYERS;i<=MAXNUMBEROFPLAYERS;i++)
            allowedInts.add(i);

        notifyAcceptableInputs();
    }

    /**
     * method that notifies the input from which this state can evolve
     */
    private void notifyAcceptableInputs(){
        LastChange powerAllowedInputs = new LastChange();
        powerAllowedInputs.setCode(1);
        powerAllowedInputs.setSpecification("preLobby");
        powerAllowedInputs.setIntegerList(allowedInts);
        player.notify(powerAllowedInputs);
    }
}
