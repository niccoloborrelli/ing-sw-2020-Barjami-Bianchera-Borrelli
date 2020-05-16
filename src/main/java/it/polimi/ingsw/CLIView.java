package it.polimi.ingsw;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class CLIView {

    private String ip;
    private int port;
    private Field field;
    private int messageFormat;
    private boolean game;
    /*
    0: stampa a schermo e basta
    1: inviami un intero
    2: inviami una stringa
    3: richiesta di 2 interi sotto forma di stringa (es 1/1)
    4. partita finita
    5: conferma valore valido
    6: invio posizioni usabili
    7: viewMove
    8: viewBuild
    9: viewRemove
    10: viewSetup
     */

    public CLIView(String string, int port){
        this.ip = string;
        this.port = port;
        //this.field = new Field();
    }

    public void clientCLIView() throws IOException, InterruptedException {
        game = true;
        Socket socket = new Socket(ip, port);
        DataInputStream socketIn = new DataInputStream(socket.getInputStream());
        DataOutputStream socketOut = new DataOutputStream(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);

        Thread sender = new Thread(new Runnable(){
            public void run(){
                String inputKeyboard;
                String messageToSend;
                do{
                    inputKeyboard=stdin.next();
                    if(messageFormat==1){
                        messageToSend="<data><code>1</code><body><int>"+inputKeyboard+"</int></body></data>";
                        messageFormat=0;

                    }
                    else if(messageFormat==2){
                        messageToSend="<data><code>2</code><body><string>"+inputKeyboard+"</string></body></data>";
                        messageFormat=0;
                    }
                    else if(messageFormat==3){
                        char row=inputKeyboard.charAt(0);
                        char column=inputKeyboard.charAt(2);
                        messageToSend="<data><code>3</code><body><row>"+row+"</row>"+"<column>"+column+"</column></body></data>";
                        messageFormat=0;
                    }
                    else{
                        messageToSend="<data><code>0</code><body><message>"+inputKeyboard+"</message></body></data>";
                    }
                    try {
                        socketOut.writeUTF(messageToSend);
                        socketOut.flush();
                    }
                    catch (Exception e){
                        System.out.println("socket closed");
                        game=false;
                        break;
                    }
                }while(!inputKeyboard.equals("exit")&&game==true);
                game=false;
                System.out.println("ending the game...");
            }
        },"sender");



        Thread receiver = new Thread(new Runnable() {
            @Override
            public void run() {
                //RICEVO IL MESSAGGIO E LO PARSO
                while (game) {
                    try {
                        String message = socketIn.readUTF();
                        //qui ho il messaggio, devo parsarlo ora
                        try {
                            Document builder = loadXMLFromString(message);
                            Node nodo = builder.getElementsByTagName("code").item(0);
                            int code = Integer.parseInt(nodo.getTextContent());
                            messageFormat = code;
                            if (code == 1 || code == 2 || code == 3 || code == 4) {
                                System.out.println(builder.getElementsByTagName("message").item(0).getTextContent());
                                if (code == 4)
                                    game = false;
                                if (code == 3)
                                    System.out.println("write the position in the format X-Y where x is the row and Y the column : \n");
                            } else if (code == 5)
                                System.out.println("correct message received");

                            else if (code == 7) {
                                int oldRow = Integer.parseInt(builder.getElementsByTagName("row").item(0).getTextContent());
                                int oldColumn = Integer.parseInt(builder.getElementsByTagName("column").item(0).getTextContent());
                                int newRow = Integer.parseInt(builder.getElementsByTagName("newRow").item(0).getTextContent());
                                int newColumn = Integer.parseInt(builder.getElementsByTagName("newColumn").item(0).getTextContent());
                                String color = builder.getElementsByTagName("color").item(0).getTextContent();

                                field.viewMove(oldRow, oldColumn, newRow, newColumn, color);
                            } else if (code == 8) {
                                int row = Integer.parseInt(builder.getElementsByTagName("row").item(0).getTextContent());
                                int column = Integer.parseInt(builder.getElementsByTagName("column").item(0).getTextContent());
                                int level = Integer.parseInt(builder.getElementsByTagName("level").item(0).getTextContent());
                                int hasDome = Integer.parseInt(builder.getElementsByTagName("hasDome").item(0).getTextContent());
                                field.viewBuild(row, column, level, hasDome);
                            } else if (code == 9) {
                                int row = Integer.parseInt(builder.getElementsByTagName("row").item(0).getTextContent());
                                int column = Integer.parseInt(builder.getElementsByTagName("column").item(0).getTextContent());
                                field.viewRemoveWorker(row, column);
                            } else if (code == 10) {
                                //setup
                                int row = Integer.parseInt(builder.getElementsByTagName("row").item(0).getTextContent());
                                int column = Integer.parseInt(builder.getElementsByTagName("column").item(0).getTextContent());
                                String color = builder.getElementsByTagName("color").item(0).getTextContent();
                                field.viewSetup(row, column, color);
                            } else if (code == 0) {
                                System.out.println(builder.getElementsByTagName("message").item(0).getTextContent());
                            }

                        } catch (Exception e) {
                            System.out.println("SERVER DISCONNECTED PRESS ANY KEY AND ENTER");
                            game=false;
                        }
                    }
                    catch (IOException e) {
                        System.out.println("SERVER DISCONNECTED PRESS ANY KEY AND ENTER");
                        game=false;
                    }
                }
            }
        }, "Receiver");


        Thread ping = new Thread(new Runnable(){
            public void run(){
                    InetAddress serverAddress=socket.getInetAddress();
                    while(game){
                        try {
                            boolean reachable=serverAddress.isReachable(100);
                            if(reachable==false) {
                                game = false;
                                System.out.println("connection with the server lost");
                                if(sender.isAlive())
                                    sender.interrupt();
                                if(receiver.isAlive())
                                    receiver.interrupt();
                                socket.close();
                            }
                            else
                                sleep(20);
                        } catch (IOException | InterruptedException e) {
                            System.out.println("SERVER DISCONNECTED PRESS ANY KEY AND ENTER");
                            game=false;
                            break;
                        }
                    }
            }
        },"ping");



        receiver.start();
        sender.start();
        ping.start();

        receiver.join();
        ping.join();


        socketOut.close();
        socketIn.close();
        socket.close();
    }

    public Document loadXMLFromString(String xml) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }
}
