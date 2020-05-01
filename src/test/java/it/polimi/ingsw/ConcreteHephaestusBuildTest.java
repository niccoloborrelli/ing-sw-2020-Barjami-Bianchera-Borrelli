package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

import static org.junit.jupiter.api.Assertions.*;

//bisogna controllare se funziona server e client su stesso metodo (altrimenti usare thread)

class ConcreteHephaestusBuildTest {

    /*
    Controlla se il livello dello spazio in cui si costruisce aumenta di 2
     */
    @Test
    void buildTest1() throws IOException {
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
        ConcreteHephaestusBuild concreteHephaestusBuild = new ConcreteHephaestusBuild(new BaseBuild());
        Space space = islandBoard.getSpace(0,0);
        space.isAvailableBuilding().add(worker);

        concreteHephaestusBuild.build(worker,space,islandBoard);
        assertTrue(space.getLevel()==2);
    }

    /*
    Controlla se lo space rimane a 1 nel caso in cui il client non usi il potere
     */
    @Test
    void buildTest2() throws IOException {
        ServerSocket serverSocket = new ServerSocket(60010);
        Socket sc = new Socket("localhost",60010);

        serverSocket.accept();
        SocketAddress socketAddress = serverSocket.getLocalSocketAddress();
        sc.connect(socketAddress);

        OutputStream outputStream = sc.getOutputStream();
        outputStream.write(0);

        Player player = new Player(sc);
        IslandBoard islandBoard = new IslandBoard();
        Worker worker = player.getWorkers().get(0);
        ConcreteHephaestusBuild concreteHephaestusBuild = new ConcreteHephaestusBuild(new BaseBuild());
        Space space = islandBoard.getSpace(0,0);
        space.isAvailableBuilding().add(worker);

        concreteHephaestusBuild.build(worker,space,islandBoard);
        assertTrue(space.getLevel()==1);
    }


    /*
    Controlla se accade qualcosa nel caso l'utente voglia ancora costruire, ma non ci sia la possibilità di farlo
     */
    @Test
    void buildTest3() throws IOException {
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
        ConcreteHephaestusBuild concreteHephaestusBuild = new ConcreteHephaestusBuild(new BaseBuild());
        Space space = islandBoard.getSpace(0,0);
        space.setLevel(2);
        space.isAvailableBuilding().add(worker);

        concreteHephaestusBuild.build(worker,space,islandBoard);
        assertTrue(space.HasDome() && space.getLevel()==4);
    }
}