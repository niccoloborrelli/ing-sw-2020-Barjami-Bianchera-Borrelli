package it.polimi.ingsw;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class VirtualView{


    public Socket getSocket(ServerSocket ss) throws IOException {
        return ss.accept();
    }

    public void communicate(DataOutputStream dataOutputStream, String message, int typeMessage) throws IOException {
        String data = "<data>" + "<code>" + typeMessage + "</code>" + "<body>" + message + "</body>" + "</data>";
        dataOutputStream.writeUTF(data);
        dataOutputStream.flush();

    }
    /*
    0: stampa a schermo
    1: inviami un intero
    2: inviami una stringa
    3: inviami due interi
    4. partita finita
    5: conferma valore valido
    6: invio posizioni usabili
    7: aggiornamento posizione
    8: aggiornamento build
    9: rimozione worker
    10: inserimetnp worker in board (setup)
     */

    public String getString(DataInputStream dataInputStream) throws IOException {
        String message = dataInputStream.readUTF();
        return message;
    }




}
