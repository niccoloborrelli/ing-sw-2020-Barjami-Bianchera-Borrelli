package it.polimi.ingsw;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Handler extends Thread {

    private Socket sc;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private HandlerHub handlerHub;
    private boolean endGame;

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

    public void handle(){
        InetAddress inetAddress = sc.getInetAddress();
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
                    handlerHub.callController(this, message);
                } catch (IOException e) {
                    handlerHub.quitGame(this);
                }
                try {
                    sleep(400);
                } catch (InterruptedException ignored) {
                }
            }
        });
    }

    public void communicate(String message){
        try {
            dataOutputStream.writeUTF(message);
        } catch (IOException ignored) {
        }
    }
}
