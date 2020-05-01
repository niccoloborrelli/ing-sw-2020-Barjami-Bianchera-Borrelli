package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

import static org.junit.jupiter.api.Assertions.*;

class ConcretePrometheusMoveTest {

    @Test
    public void moveTest1() throws IOException {
        ServerSocket serverSocket = new ServerSocket(60010);
        Socket sc = new Socket("localhost",60010);

        serverSocket.accept();
        SocketAddress socketAddress = serverSocket.getLocalSocketAddress();
        sc.connect(socketAddress);

        OutputStream outputStream = sc.getOutputStream();
        outputStream.write(1);

        Player player = new Player(sc);
        IslandBoard islandBoard = new IslandBoard();
        Worker worker = player.getWorkers().get(0);

        
    }

}