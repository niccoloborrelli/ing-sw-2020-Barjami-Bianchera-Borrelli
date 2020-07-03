package it.polimi.ingsw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.FinalCommunication.UPDATE_CHOICE;

public class PreLobbyState extends State{
    private static final String PRELOBBY="preLobby";
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
        LastChange powerAllowedInputs =player.getLastChange();
        powerAllowedInputs.setCode(UPDATE_CHOICE);
        powerAllowedInputs.setSpecification(PRELOBBY);
        powerAllowedInputs.setIntegerList(allowedInts);
        player.notifyController();
    }
}
