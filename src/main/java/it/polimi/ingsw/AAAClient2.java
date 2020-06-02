package it.polimi.ingsw;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class AAAClient2 {

    public static boolean inGame;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Socket socket = new Socket("localhost", 60100);
        DeliveryMessage deliveryMessage = new DeliveryMessage(socket);
        new Thread(deliveryMessage::startReading).start();
        inGame = true;

        while (inGame) {
            String input = scanner.nextLine();
            if (socket.isClosed())
                break;
            deliveryMessage.send(input);
        }
    }
}