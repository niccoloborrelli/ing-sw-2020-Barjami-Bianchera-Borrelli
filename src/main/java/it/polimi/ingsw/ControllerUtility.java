package it.polimi.ingsw;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ControllerUtility {

    public static Socket getSocket(ServerSocket ss) throws IOException {
        return ss.accept();
    }

    public static void communicate(Socket sc, String message, int typeMessage) throws IOException {
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
    0: stampa a schermo e basta
    1: inviami un intero
    2: inviami una stringa
    3: inviami due interi
    4. scegli worker o utilizzo potere
    5: conferma valore valido
    6: partita finita
     */

    public static String getString(Socket sc) throws IOException {
        InputStream inputStream = sc.getInputStream();
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        String message = dataInputStream.readUTF();
        dataInputStream.close();
        return message;
    }

    public static int getInt(Socket sc) throws IOException {
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
    public static Space selectPos(List<Space> possibleSpace, Player player) throws IOException {
        String message = "Choose the space to execute the action:\n";
        int row, column, index, selected;

        for (Space s: possibleSpace) {
            row = s.getRow();
            column = s.getColumn();
            index = possibleSpace.indexOf(s);
            message = message.concat("[" + row + "][" + column + "] = " + index + ";\n");
        }
        do {
            ControllerUtility.communicate(player.getSocket(), message, 1);
            selected = ControllerUtility.getInt(player.getSocket());
        } while(selected > possibleSpace.size());
        ControllerUtility.communicate(player.getSocket(), "", 5);

        return possibleSpace.get(selected);
    }
}
