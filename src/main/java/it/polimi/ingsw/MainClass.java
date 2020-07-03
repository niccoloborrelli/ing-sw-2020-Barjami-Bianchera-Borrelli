
package it.polimi.ingsw;

import java.io.IOException;

public class MainClass {
    private static final String CLIClientArg="CLIClient";
    private static final String GUIClientArg="GUIClient";
    private static final String serverArg="server";
    private static final int argIndex = 0;

    public static void main(String[] args){
        if(args[argIndex].equals(CLIClientArg)){
            new Thread() {
                @Override
                public void run() {
                    AAAClient CLIclient=new AAAClient();
                    CLIclient.runCLI();
                }
            }.start();
        }

        else if(args[argIndex].equals(GUIClientArg)){
            new Thread() {
                @Override
                public void run() {
                    javafx.application.Application.launch(App.class);
                }
            }.start();
        }

        else if(args[argIndex].equals(serverArg)){
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
