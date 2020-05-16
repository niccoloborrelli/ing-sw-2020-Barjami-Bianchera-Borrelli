package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

class CLIViewTest {

    @Test
    public void CLIViewTest1() throws InterruptedException, IOException {
        CLIView cl=new CLIView("localhost",60100);
        String input = "uno due tre quattro cinque";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        cl.clientCLIView();

    }
    @Test
    public void CLIViewTest2() throws InterruptedException, IOException {
        ServerSocket ss=new ServerSocket(60100);
        Socket sc=ss.accept();
        DataInputStream dt=new DataInputStream(sc.getInputStream());
        DataOutputStream dd=new DataOutputStream(sc.getOutputStream());
        dd.writeUTF("<data><code>0</code><message>primissimissimissmo</message></data>");
        int i=0;
        while(true){
            try {
                i++;
                String s = dt.readUTF();
                System.out.println(s);
                dd.writeUTF(s);
                if (i == 4)
                    dd.writeUTF("<data><code>4</code><message>finiscimi tutto</message></data>");
            }
            catch (IOException e) {
                break;
            }
        }
    }

}