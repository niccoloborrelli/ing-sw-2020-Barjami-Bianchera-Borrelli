package it.polimi.ingsw;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class NetHandler {

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

    public void handle(){
        Thread reading = createReadingThread();

        reading.start();

        try {
            reading.join();
        } catch (InterruptedException ignored) {
        }
    }

    private Thread createReadingThread(){
        return new Thread(() -> {
            while (!endGame){
                try {
                    String message = dataInputStream.readUTF();
                    deliveryMessage.receive(message);
                } catch (IOException e) {
                    deliveryMessage.quitGame();
                }
                try {
                    sleep(400);
                } catch (InterruptedException ignored) {
                }
            }
        });
    }

    public void sendMessage(String message){
        try {
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
        } catch (IOException e) {
            deliveryMessage.quitGame();
        }
    }
}
