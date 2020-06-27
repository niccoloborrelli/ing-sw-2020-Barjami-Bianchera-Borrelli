package it.polimi.ingsw;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class AAAClient1 {

    public static boolean inGame;

    public static void main(String[] args) throws IOException {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            Socket socket = new Socket("localhost", 60100);
            CommandCLIManager commandCLIManager = new CommandCLIManager(socket);
            DeliveryMessage deliveryMessage = commandCLIManager.getDeliveryMessage();

            new Thread(deliveryMessage::startReading).start();

            while (true) {
                String input = scanner.nextLine();
                GeneralStringRequestCommand generalStringRequestCommand = new GeneralStringRequestCommand(input);
                commandCLIManager.manageCommand(generalStringRequestCommand);
                if (socket.isClosed())
                    break;
            }
        }
    }
}
