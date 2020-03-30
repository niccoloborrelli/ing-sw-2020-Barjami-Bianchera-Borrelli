package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

public class Challenger extends  Player {

    public Challenger(String playerName){
        super(playerName);
    }

    public Player chooseFirstPlayer(List<Player> players){
        return new Player(new String("ciro"));
    }

    public List<God> drawGods(){
        List<God> gods = new ArrayList<>();
        return gods;
    }

}
