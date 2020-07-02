
package it.polimi.ingsw;

import java.io.IOException;

public class MainClass {
    public static void main(String[] args){
        if(args[0].equals("CLIClient")){
            new Thread() {
                @Override
                public void run() {
                    AAAClient CLIclient=new AAAClient();
                    CLIclient.runCLI();
                }
            }.start();
        }

        else if(args[0].equals("GUIClient")){
            new Thread() {
                @Override
                public void run() {
                    javafx.application.Application.launch(App.class);
                }
            }.start();
        }

        else if(args[0].equals("server")){
            new Thread() {
                @Override
                public void run() {
                    AAAServer server=new AAAServer();
                    server.runServer();
                }
            }.start();
        }
    }
}
