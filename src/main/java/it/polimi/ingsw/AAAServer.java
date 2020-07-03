package it.polimi.ingsw;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class AAAServer {

    /**
     * Starts and runs the server.
     */
    public void runServer(){
        try {
            Accepter accepter = new Accepter();
            System.out.println("SERVER STARTED\n");
            accepter.accept();
        }catch (IOException e){
            return;
        }
    }

}
