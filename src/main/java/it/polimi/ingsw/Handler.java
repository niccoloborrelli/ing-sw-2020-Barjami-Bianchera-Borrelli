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
                    handlerHub.callController(this, message);
                } catch (IOException e) {
                    if(!endGame) {
                        System.out.println("Qualcuno si è disconnesso per sbaglio");
                        endGame = true;
                        handlerHub.quitGame(this, false);
                    }
                }
                try {
                    sleep(400);
                } catch (InterruptedException ignored) {
                }
            }
            System.out.println("Finito l'handler");
        });
    }

    public void communicate(String message){
        try {
            dataOutputStream.writeUTF(message);
        } catch (IOException ignored) {
        }
    }
}
