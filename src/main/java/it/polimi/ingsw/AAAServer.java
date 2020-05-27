package it.polimi.ingsw;

import java.io.IOException;

public class AAAServer {

    public static void main(String[] args) throws IOException {
        Accepter accepter = new Accepter();
        accepter.accept();
    }
}
