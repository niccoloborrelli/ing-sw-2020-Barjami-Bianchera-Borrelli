
package it.polimi.ingsw;

import java.io.IOException;

public class MainClass {
    public static void main(String[] args){
        if(args[0].equals("CLIClient")){
            new Thread() {
                @Override
                public void run() {
                    AAAClient CLIclient=new AAAClient();
                    try {
                        CLIclient.runCLI();
                    } catch (IOException e) {
                       // e.printStackTrace();
                    }
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
                    try {
                        server.runServer();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}
