package it.polimi.ingsw;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Controller implements Observer {

    private ServerSocket ss;
    private VirtualView virtualView;

    public Controller() throws IOException {
        this.ss = new ServerSocket();
        this.virtualView = new VirtualView();
    }

    @Override
    public void updateMovement(List<Socket> socketList, Space startPlace, Space finishPlace, String color) throws IOException {
        int startPlaceRow = startPlace.getRow();
        int startPlaceColumn = startPlace.getColumn();

        int finishSpaceRow = finishPlace.getRow();
        int finishSpaceColumn = finishPlace.getColumn();

        for(Socket sc: socketList)
            virtualView.communicate(sc, "<startSpace>" + "<Row>" + startPlaceRow  + "</Row> " + "<Column>" + startPlaceColumn + "</Column> " + "<finishSpace>" +
                    "<Row>" + finishSpaceRow + "</Row>" + "<Column>" + finishSpaceColumn + "</Column> " + "</finishSpace>" + "<Player>" + "<color>" + color + "</color>" + "</Player>",7);
    }

    @Override
    public void updateBuilding(List<Socket> socketList, Space buildSpace) throws IOException {
        int buildSpaceRow = buildSpace.getRow();
        int buildSpaceColumn = buildSpace.getColumn();
        int buildSpaceLevel = buildSpace.getLevel();
        boolean hasDome = buildSpace.HasDome();

        for(Socket sc: socketList)
        virtualView.communicate(sc, "<buildSpace>" + "<Row>" + buildSpaceRow + "</Row> " + "<Column>" + buildSpaceColumn + "</Column> "
                + "<Level>" + buildSpaceLevel + "</Level> " + "<HasDome>" + hasDome + "</HasDome>" + "</buildSpace>", 8);
    }

    @Override
    public void updateWin(List<Socket> socketList, Socket sc) throws IOException {
        for(Socket socket: socketList) {
            if (socket == sc)
                virtualView.communicate(sc, "Congratulations you won! Others suck", 4);
            else
                virtualView.communicate(sc, "You suck at this game", 4);
        }
    }

    @Override
    public void updateLose(List<Socket> socketList, Socket sc, Space spaceWorker1, Space spaceWorker2) throws IOException {
        for(Socket socket: socketList) {
            if (socket == sc)
                virtualView.communicate(sc, "<message>You lost! Now you can watch others winning</message> " + "<worker1Space>" + "<Row>" + spaceWorker1.getRow() + "</Row>" +
                        "<Column>" + spaceWorker1.getColumn() + "</Column>" + "</worker1Space>" + "<worker2Space>" + "<Row>" + spaceWorker2.getRow() + "</Row> " +
                        "<Column>" + spaceWorker2.getColumn() + "</Column>" + "</worker2Space>", 9);
            else
                virtualView.communicate(sc, "<message>Someone lost, but not you (at the moment)</message>" + "<worker1Space>" + "<Row>" + spaceWorker1.getRow() + "</Row>" +
                        "<Column>" + spaceWorker1.getColumn() + "</Column>" + "</worker1Space>" + "<worker2Space>" + "<Row>" + spaceWorker2.getRow() + "</Row> " +
                        "<Column>" + spaceWorker2.getColumn() + "</Column>" + "</worker2Space>", 9);
        }
    }

    @Override
    public int requiredInt(Socket sc, String message, List<Integer> available) throws IOException {
        int value = 0;
        boolean correct = false;

        while(!correct){
            virtualView.communicate(sc, message, 1);
            value = virtualView.getInt(sc);
            if(available.contains(value))
                correct=true;
        }

        virtualView.communicate(sc, "", 5);

        return value;
    }

    @Override
    public String requiredName(Socket sc, String message, List<String> available) throws IOException {
        String name = null;
        boolean ok =  false;

        while(!ok){
            virtualView.communicate(sc, message, 2);
            name = virtualView.getString(sc);
            if(!available.contains(name))
                ok=true;
        }

        virtualView.communicate(sc, "", 5);

        return name;
    }

    @Override
    public List<Socket> requiredSocket(int numberOfSockets) throws IOException {
        List<Socket> list = new ArrayList<>();
        Socket sc;

        for(int i=1; i<numberOfSockets; i++){
            sc = ss.accept();
            list.add(sc);
        }

        return list;
    }

    @Override
        public int[] requiredPosition(Socket sc, String message, int[][] matrix ) throws IOException {
        String position;
        int row = 0;
        int column = 0;
        int results[] = new int[2];
        boolean notCorrect = true;

        while (!notCorrect) {
            virtualView.communicate(sc, message, 3);
            position = virtualView.getString(sc);
            row = position.charAt(0);
            column = position.charAt(2);
            if (matrix[row][column] == 0)
                notCorrect = false;
        }

        virtualView.communicate(sc, "", 5);
        results[0] = row;
        results[1] = column;

        return results;

    }


}
