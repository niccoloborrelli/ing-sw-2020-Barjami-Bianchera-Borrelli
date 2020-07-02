package it.polimi.ingsw;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Handler extends Thread {

    /**
     * Receives and sends messages to client.
     */

    private Socket sc;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private HandlerHub handlerHub;
    private boolean endGame;

    public void setHandlerHub(HandlerHub handlerHub) {
        this.handlerHub = handlerHub;
    }

    public Handler(Socket sc, HandlerHub handlerHub) throws IOException {
        this.sc = sc;
        dataInputStream = new DataInputStream(sc.getInputStream());
        dataOutputStream = new DataOutputStream(sc.getOutputStream());
        this.handlerHub = handlerHub;
        endGame = false;
    }

    public Socket getSc() {
        return sc;
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    /**
     * Creates and start thread of reading.
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
     * Creates the thread that will read client messages.
     * @return the thread that will read client messages.
     */
    private Thread createReadingThread(){
        return new Thread(() -> {
            while (!endGame){
                try {
                    String message = dataInputStream.readUTF();
                    handlerHub.callController(this, message);
                } catch (IOException e) {
                    if(!endGame) {
                        endGame = true;
                        handlerHub.quitGame(this, false);
                    }
                }
                try {
                    sleep(400);
                } catch (InterruptedException ignored) {
                }
            }
        });
    }

    /**
     * Sends this message to client.
     * @param message is message sent.
     */
    public void communicate(String message){
        try {
            dataOutputStream.writeUTF(message);
        } catch (IOException ignored) {
        }
    }
}
