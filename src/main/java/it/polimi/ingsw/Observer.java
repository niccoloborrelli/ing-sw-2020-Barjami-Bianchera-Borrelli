package it.polimi.ingsw;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public interface Observer {

    Socket requiredChallengerSocket() throws IOException;
    List<Socket> requiredSockets(int numberOfPlayers) throws IOException, InterruptedException;
    String requiredString(Socket sc, String message, List<String> available) throws IOException;

    void printMessage(Socket sc, String message) throws IOException;
    void updateMovement(Space startPlace, Space finishPlace, String color) throws IOException;
    void updateBuilding(Space buildSpace) throws IOException;
    void updateWin(Socket sc) throws IOException;
    void updateLose(Socket sc, Space spaceWorker1, Space spaceWorker2) throws IOException;

    int requiredInt(Socket sc, String message, List<Integer> available) throws IOException;
    String requiredName(Socket sc, String message, List<String> available) throws IOException;

    int[] requiredPosition(Socket sc, String message, int[][] matrix) throws IOException;
}
