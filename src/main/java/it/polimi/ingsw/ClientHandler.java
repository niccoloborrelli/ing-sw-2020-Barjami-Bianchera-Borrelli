package it.polimi.ingsw;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.StandardSocketOptions;

public class ClientHandler extends Thread {

    private DataInputStream dataInputStreamClient;  //non posso settarle FINAL perchè non è gestibile nella creazione multiThread
    private DataOutputStream dataOutputStreamClient;
    private Socket sc;
    private final DataOutputStream dataOutputStreamServer;
    private final DataInputStream dataInputStreamServer;
    private final Socket ss;
    boolean canSpeak;
    boolean endGame;


    public ClientHandler() throws IOException {
        ss = new Socket("localhost", 60100);   //da capire come farlo collegare al server dopo la accept del client
        dataOutputStreamServer = new DataOutputStream(ss.getOutputStream());
        dataInputStreamServer = new DataInputStream(ss.getInputStream());
        canSpeak = false;
        endGame = false;
    }

    public void setClientHandler(Socket sc) throws IOException {
        this.sc = sc;
        dataInputStreamClient = new DataInputStream(sc.getInputStream());
        dataOutputStreamClient = new DataOutputStream(sc.getOutputStream());
    }


    public Socket getSs() {
        return ss;
    }

    public boolean isEndGame() {
        return endGame;
    }

    public void ping() throws IOException, InterruptedException {
        InetAddress inetAddress = sc.getInetAddress();
        while(!endGame) {
            while (inetAddress.isReachable(2000))
                sleep(200);
        }
    }

    public void setCanSpeak(boolean canSpeak) {
        this.canSpeak = canSpeak;
    }

    public void run() {

        String received;
        String message;

        while(sc==null){}

        while (!endGame) {

            try {
                while (!canSpeak) {
                    if(dataInputStreamClient.available()>0) {
                        if(!canSpeak) {
                            received = dataInputStreamClient.readUTF();
                            System.out.println("Non doveva scrivere: " + received);
                            communicate(dataOutputStreamClient,"<message>It isn't your turn</message>",0); //l'ho copiato sotto, vedere se utilizzare proprio la virtual view
                            sleep(10);
                        }else
                            break;
                    }else
                        sleep(10);
                }
                while (canSpeak) { //si può implementare che il server mandi il codice del messaggio che si aspetta
                    // in modo da controllare se quello in ricezione è corretto

                    received = dataInputStreamClient.readUTF();
                    System.out.println("L'handler ha ricevuto: " + received);
                    canSpeak = false;
                    message = formatMessage(received);
                    if (message != null) {
                        dataOutputStreamServer.writeUTF(message);
                        dataOutputStreamServer.flush();
                    } else {
                        communicate(dataOutputStreamClient,"<message>Wrong message</message>",0);
                        dataOutputStreamClient.writeUTF("");
                        dataOutputStreamServer.flush();
                    }
                }
            } catch (IOException | ParserConfigurationException | SAXException | InterruptedException ignored) {

            }


        }

    }

    public String formatMessage(String received) throws ParserConfigurationException, IOException, SAXException {   //Gestire qui eccezione
        String message = null;
        int result;

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(received));

        Document doc = db.parse(is);
        NodeList nodes = doc.getElementsByTagName("code");
        Element element = (Element) nodes.item(0);


        int code =0;
        try {
            code = Integer.parseInt(getCharacterDataFromElement(element));
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        if (code == 1) {
            nodes = doc.getElementsByTagName("int");
            element = (Element) nodes.item(0);
            message = getCharacterDataFromElement(element);
            System.out.println("C'è scritto: " + message);
            try {
                result = Integer.parseInt(message);
            } catch (NumberFormatException e) {
                message = null;
            }
        } else if (code == 2) {
            nodes = doc.getElementsByTagName("string");
            element = (Element) nodes.item(0);
            message = getCharacterDataFromElement(element);
            System.out.println("C'è scritto: " + message);
        } else if (code == 3) {
            nodes = doc.getElementsByTagName("row");
            element = (Element) nodes.item(0);
            message = getCharacterDataFromElement(element);
            try {
                result = Integer.parseInt(message);
            } catch (NumberFormatException e) {
                message = null;
            }
            if (message != null) {
                nodes = doc.getElementsByTagName("column");
                element = (Element) nodes.item(0);
                String part2 = getCharacterDataFromElement(element);
                try {
                    result = Integer.parseInt(part2);
                    message = message.concat("-" + part2);
                } catch (NumberFormatException e) {
                    message = null;
                }
            }
        }
        return message;
    }

    public String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return null;
    }

    public void communicate(DataOutputStream dataOutputStream, String message, int typeMessage) throws IOException {
        String data = "<data>" + "<code>" + typeMessage + "</code>" + "<body>" + message + "</body>" + "</data>";
        dataOutputStream.writeUTF(data);
        dataOutputStream.flush();

    }

}
