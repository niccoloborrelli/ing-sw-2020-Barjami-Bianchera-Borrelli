package it.polimi.ingsw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PreLobbyState extends State{
    private List<Integer> allowedInts;
    private LobbyManager lobbyManager;
    private boolean enteredGame;

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
        else
            player.notify(1);
    }

    @Override
    public void onStateTransition() throws IOException {
        allowedInts.add(2);
        allowedInts.add(3);
        for (Integer i:allowedInts) {
            allowedInputs.add(""+i);
        }
        player.notify(7);
    }

}
