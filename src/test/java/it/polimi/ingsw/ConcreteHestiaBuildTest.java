package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ConcreteHestiaBuildTest {

    /*
    Questo test controlla che se la seconda
    costruzione è avvenuta (in questo caso
    sulla stessa casella)
     */
    @Test
    void buildNoPerimeterTest1() throws IOException {
        ServerSocket serverSocket = new ServerSocket(60011);
        Socket socket = new Socket("127.0.0.1", 60011);
        serverSocket.accept();

        OutputStream outputStream = socket.getOutputStream();
        outputStream.flush();
        outputStream.write(1);
        outputStream.flush();
        outputStream.write(0);
        outputStream.flush();

        IslandBoard islandBoard = new IslandBoard();
        Player player = new Player(socket);
        player.setBuild(new ConcreteHestiaBuild(new BaseBuild()));
        player.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(0,1));
        islandBoard.getSpace(0,1).setOccupator(player.getWorkers().get(0));
        islandBoard.getSpace(1,1).setHasDome(true);

        int startLevel = islandBoard.getSpace(1,2).getLevel();
        player.getBuild().build(player.getWorkers().get(0), islandBoard.getSpace(1,2), islandBoard);

        assertEquals(islandBoard.getSpace(1, 2).getLevel(), startLevel + 2);

    }

    /*
    Questo test controlla che se non è possibile
    la seconda costruzione, allora gli space
    perimetrali rimangono allo stesso livello
     */
    @Test
    void buildNoPerimeterTest2() throws IOException {
        ServerSocket serverSocket = new ServerSocket(60013);
        Socket socket = new Socket("127.0.0.1", 60013);
        serverSocket.accept();

        OutputStream outputStream = socket.getOutputStream();
        outputStream.flush();
        outputStream.write(1);
        outputStream.flush();
        outputStream.write(0);
        outputStream.flush();

        IslandBoard islandBoard = new IslandBoard();
        Player player = new Player(socket);
        player.setBuild(new ConcreteHestiaBuild(new BaseBuild()));

        islandBoard.getSpace(1,1).setLevel(3);
        player.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(0,0));
        islandBoard.getSpace(0,0).setOccupator(player.getWorkers().get(0));

        int startLevel01 = islandBoard.getSpace(0,1).getLevel();
        int startLevel10 = islandBoard.getSpace(1,0).getLevel();

        player.getRestriction().restrictionEffectBuilding(player.getWorkers().get(0), islandBoard);
        player.getBuild().build(player.getWorkers().get(0), islandBoard.getSpace(1,1), islandBoard);

        assertTrue(islandBoard.getSpace(0,1).getLevel() == startLevel01
                            && islandBoard.getSpace(1,0).getLevel() == startLevel10);
    }
}