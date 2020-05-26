package it.polimi.ingsw;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class AAAClient {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Socket socket = new Socket("localhost",60100);
        DeliveryMessage deliveryMessage = new DeliveryMessage(socket);
        new Thread(deliveryMessage::startReading).start();

        while (true){
            String input = scanner.nextLine();
            if(input.equals("quit"))
                break;
            deliveryMessage.send(input);
        }
    }
}
