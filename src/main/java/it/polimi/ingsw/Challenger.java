package it.polimi.ingsw;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Challenger extends  Player {

    public Challenger(Socket sc){
        super(sc);
    }

    public List<God> drawGods(){
        List<God> gods = new ArrayList<>();
        return gods;
    }

}
