package it.polimi.ingsw;


import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class AAAClient {

    /**
     * Creates and runs the CLI interface.
     */
    public void runCLI() {
        try {
            Scanner scanner = new Scanner(System.in);
            Socket socket = new Socket("localhost", 60100);
            manageCLI(socket, scanner);
        }catch (IOException e){
            return;
        }
    }

    /**
     * Creates the CLI interface set-up and starts it
     * @param socket is the connection with server.
     * @param scanner is used to receive input from user.
     * @throws IOException if socket isn't valid or it's closed.
     */
    private void manageCLI(Socket socket,Scanner scanner) throws IOException {
        CommandCLIManager commandCLIManager = new CommandCLIManager(socket);
        DeliveryMessage deliveryMessage = commandCLIManager.getDeliveryMessage();
        new Thread(deliveryMessage::startReading).start();
        do {
            String input = scanner.nextLine();
            GeneralStringRequestCommand generalStringRequestCommand = new GeneralStringRequestCommand(input);
            commandCLIManager.manageCommand(generalStringRequestCommand);
        } while (!socket.isClosed());
    }
}