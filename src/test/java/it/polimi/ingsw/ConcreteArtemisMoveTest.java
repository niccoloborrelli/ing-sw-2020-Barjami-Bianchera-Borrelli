package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ConcreteArtemisMoveTest {

    /*
    Questo test verifica che se il giocatore non può
    muoversi di nuovo resti nella posiione della prima move
     */
    @Test
    void moveAgainNoStartSpaceTest1() throws IOException {
        ServerSocket serverSocket = new ServerSocket(60010);
        Socket socket = new Socket("127.0.0.1", 60010);
        serverSocket.accept();

        OutputStream outputStream = socket.getOutputStream();
        outputStream.flush();
        outputStream.write(1);
        outputStream.flush();
        outputStream.write(0);
        outputStream.flush();

        IslandBoard islandBoard = new IslandBoard();
        Player player = new Player(socket);
        player.setMove(new ConcreteArtemisMove(new BaseMovement()));
        player.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(1,1));
        islandBoard.getSpace(1,1).setOccupator(player.getWorkers().get(0));

        islandBoard.getSpace(1,0).setHasDome(true);
        islandBoard.getSpace(0,1).setHasDome(true);
        player.getMove().move(player.getWorkers().get(0), islandBoard.getSpace(0,0), islandBoard);

        assertSame(player.getWorkers().get(0).getWorkerSpace(), islandBoard.getSpace(0, 0));
    }

    /*
    Questo test verifica che il worker si muova di nuovo
    ma non nella casella di partenza
     */
    @Test
    void moveAgainNoStartSpaceTest2() throws IOException {
        ServerSocket serverSocket = new ServerSocket(60012);
        Socket socket = new Socket("127.0.0.1", 60012);
        serverSocket.accept();

        OutputStream outputStream = socket.getOutputStream();
        outputStream.flush();
        outputStream.write(1);
        outputStream.flush();
        outputStream.write(0);
        outputStream.flush();

        IslandBoard islandBoard = new IslandBoard();
        Player player = new Player(socket);
        player.setMove(new ConcreteArtemisMove(new BaseMovement()));
        player.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(1,1));
        islandBoard.getSpace(1,1).setOccupator(player.getWorkers().get(0));
        player.getWorkers().get(1).setWorkerSpace(islandBoard.getSpace(4,4));
        islandBoard.getSpace(4,4).setOccupator(player.getWorkers().get(1));

        islandBoard.getSpace(1,0).setHasDome(true);

        player.getRestriction().restrictionEffectMovement(player, islandBoard);
        player.getMove().move(player.getWorkers().get(0), islandBoard.getSpace(0,0), islandBoard);

        assertSame(player.getWorkers().get(0).getWorkerSpace(), islandBoard.getSpace(0, 1));
    }
}