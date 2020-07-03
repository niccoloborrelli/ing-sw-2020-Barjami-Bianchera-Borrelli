package it.polimi.ingsw;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class NetHandler {

    /**
     * Receives and sends message to server.
     */

    private static final int timeToSleep=400;
    private DeliveryMessage deliveryMessage;
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private boolean endGame;



    public NetHandler(DeliveryMessage deliveryMessage, Socket socket) throws IOException {
        this.deliveryMessage = deliveryMessage;
        endGame = false;
        this.socket = socket;
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
    };

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public boolean isEndGame() {
        return endGame;
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    /**
     * Creates and starts reading thread.
     */

    public void handle(){
        Thread reading = createReadingThread();

        reading.start();

        try {
            reading.join();
        } catch (InterruptedException ignored) {
        }
    }

    /**
     * Creates the thread that will receive messages from server.
     * @return the thread that will receive messages from server
     */

    private Thread createReadingThread(){
        return new Thread(() -> {
            while (!endGame){
                try {
                    String message = dataInputStream.readUTF();
                    deliveryMessage.receive(message);
                } catch (IOException e) {
                    deliveryMessage.quitGame(false);
                }
                try {
                    sleep(timeToSleep);
                } catch (InterruptedException ignored) {
                }
            }
        });
    }

    /**
     * Sends the message to server. If socket is closed, quit the game.
     * @param message is message to send.
     */

    public void sendMessage(String message){
        try {
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
        } catch (IOException e) {
            deliveryMessage.quitGame(false);
        }
    }
}
