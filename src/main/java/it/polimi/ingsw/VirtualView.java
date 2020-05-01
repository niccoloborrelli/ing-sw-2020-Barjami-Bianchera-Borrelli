package it.polimi.ingsw;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class VirtualView{

    public VirtualView() {
    }

    public Socket getSocket(ServerSocket ss) throws IOException {
        return ss.accept();
    }

    public void communicate(Socket sc, String message, int typeMessage) throws IOException {
        OutputStream outputStream = sc.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeUTF(message);
        dataOutputStream.flush();
        dataOutputStream.write(typeMessage);
        dataOutputStream.flush();
        dataOutputStream.close();
        outputStream.close();
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

    public String getString(Socket sc) throws IOException {
        InputStream inputStream = sc.getInputStream();
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        String message = dataInputStream.readUTF();
        dataInputStream.close();
        return message;
    }

    public int getInt(Socket sc) throws IOException {
        InputStream inputStream = sc.getInputStream();
        int number = inputStream.read();
        inputStream.close();
        return number;
    }

    /**
     * This method asks the player which space he wants to move or build on
     * @param possibleSpace is the list of the possible movements or buildings
     * @param player is the player that chooses
     * @return the selected space
     */
    public Space selectPos(List<Space> possibleSpace, Player player) throws IOException {
        String message = "Choose the space to execute the action:\n";
        int row, column, index, selected;

        for (Space s: possibleSpace) {
            row = s.getRow();
            column = s.getColumn();
            index = possibleSpace.indexOf(s);
            message = message.concat("[" + row + "][" + column + "] = " + index + ";\n");
        }
        do {
            communicate(player.getSocket(), message, 1);
            selected = getInt(player.getSocket());
        } while(selected > possibleSpace.size());
        communicate(player.getSocket(), "", 5);

        return possibleSpace.get(selected);
    }

}
