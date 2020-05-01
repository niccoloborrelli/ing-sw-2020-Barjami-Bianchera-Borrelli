package it.polimi.ingsw;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public interface Observer {

    void updateMovement(List<Socket> socketList, Space startPlace, Space finishPlace, String color) throws IOException;
    void updateBuilding(List<Socket> socketList, Space buildSpace) throws IOException;
    void updateWin(List<Socket> socketList, Socket sc) throws IOException;
    void updateLose(List<Socket> socketList, Socket sc, Space spaceWorker1, Space spaceWorker2) throws IOException;

    int requiredInt(Socket sc, String message, List<Integer> available) throws IOException;
    String requiredName(Socket sc, String message, List<String> available) throws IOException;
    List<Socket> requiredSocket(int numberOfSockets) throws IOException;

    int[] requiredPosition(Socket sc, String message, int[][] matrix) throws IOException;
}
