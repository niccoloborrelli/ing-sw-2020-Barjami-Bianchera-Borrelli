package it.polimi.ingsw;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class AAAServer {

    public void runServer(){
        try {
            Accepter accepter = new Accepter();
            accepter.accept();
        }catch (IOException e){
            return;
        }
    }

}
