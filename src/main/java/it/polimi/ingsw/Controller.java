package it.polimi.ingsw;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Thread.sleep;

public class Controller implements Observer {

    private ServerSocket ss;
    private VirtualView virtualView;
    private HashMap<Socket, DataOutputStream> socketCommunication;
    private HashMap<Socket,ClientHandler> socketStreamMap;
    private HashMap<ClientHandler,DataInputStream> messageReceveing;

    public Controller() throws IOException {
        this.ss = new ServerSocket(60100);
        this.virtualView = new VirtualView();
        this.socketStreamMap = new HashMap<Socket,ClientHandler>();  //decidere se mettere anche dataOutputStream in modo da averne sempre e solo una (altrimenti se ne continuano a creare)
        messageReceveing = new HashMap<ClientHandler, DataInputStream>();
        socketCommunication = new HashMap<Socket, DataOutputStream>();
    }

    public ServerSocket getSs() {
        return ss;
    }

    public VirtualView getVirtualView() {
        return virtualView;
    }

    public HashMap<Socket, ClientHandler> getSocketStreamMap() {
        return socketStreamMap;
    }

    public List<God> createGodset(){
        //ricorda cambiare path del file
        Parser parser=new Parser(new File("C:\\Users\\Rei\\Desktop\\Gods.txt"));
        HashMap<String,List<String>> godMap=parser.createHashRepresentation();
        GodFactory godFactory=new GodFactory();
        return godFactory.godList(godMap);
    }

    public void createClientHandler(Socket sc) throws InterruptedException {
        final Socket[] handler = new Socket[1];
        handler[0] = sc;
        Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                        try {
                            handler[0] = ss.accept();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            }, "threadA");

            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ClientHandler clientHandler = new ClientHandler(sc);
                        socketStreamMap.put(sc, clientHandler);
                        sleep(20);
                        messageReceveing.put(clientHandler, new DataInputStream(handler[0].getInputStream()));
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            },"threabB");

            t1.start();
            t2.start();
            t1.join();
            t2.join();;
        }

    @Override
    public Socket requiredChallengerSocket() throws IOException {
        Socket socket = virtualView.getSocket(ss);
        socketCommunication.put(socket,new DataOutputStream(socket.getOutputStream()));
        return socket;
    }

    @Override
    public List<Socket> requiredSockets(int numberOfPlayers) throws IOException, InterruptedException {
        List<Socket> socketList = new ArrayList<>();
        Socket newSocket;
        for(int i=1; i<numberOfPlayers; i++) {
            newSocket = virtualView.getSocket(ss);
            socketList.add(newSocket);
            createClientHandler(newSocket);
        }
        return socketList;
    }
    @Override
    public void printMessage(Socket sc, String message) throws IOException {
        DataOutputStream dataOutputStream = socketCommunication.get(sc);
        virtualView.communicate(dataOutputStream,message,0);
    }



    @Override
    public int requiredInt(Socket sc, String message, List<Integer> available) throws IOException {
        String handlerResult = null;
        int value = 0;
        boolean correct = false;

        while(!correct){
            virtualView.communicate(socketCommunication.get(sc), message, 1);
            socketStreamMap.get(sc).setCanSpeak(true);
            ClientHandler clientHandler = socketStreamMap.get(sc);
            handlerResult = virtualView.getString(messageReceveing.get(clientHandler));
            value = Integer.parseInt(handlerResult);
            if(available.contains(value)){
                correct=true;
            }

        }

        socketStreamMap.get(sc).setCanSpeak(false);
        virtualView.communicate(socketCommunication.get(sc), "", 5);

        return value;
    }

    @Override
    public String requiredName(Socket sc, String message, List<String> available) throws IOException {
        String name = null;
        boolean wrongName =  true;
        ClientHandler clientHandler = socketStreamMap.get(sc);
        DataOutputStream dataOutputStream = socketCommunication.get(sc);

        while(wrongName){
            virtualView.communicate(dataOutputStream, message, 2);
            socketStreamMap.get(sc).setCanSpeak(true);
            name = virtualView.getString(messageReceveing.get(clientHandler));
            if(!available.contains(name))
                wrongName=false;
        }

        socketStreamMap.get(sc).setCanSpeak(false);
        virtualView.communicate(dataOutputStream, "", 5);

        return name;
    }

    public Space requiredSpace(Socket sc, String message, List<Space> available) throws IOException {
        String position = null;
        boolean correctSpace = false;
        int choice = 0;
        ClientHandler clientHandler = socketStreamMap.get(sc);
        DataOutputStream dataOutputStream = socketCommunication.get(sc);

        while(!correctSpace){
            virtualView.communicate(dataOutputStream,message,1);
            socketStreamMap.get(sc).setCanSpeak(true);
            position = virtualView.getString(messageReceveing.get(clientHandler));
            choice = Integer.parseInt(position);
            if(choice >= 0 && choice < available.size())
                correctSpace = true;
        }

        socketStreamMap.get(sc).setCanSpeak(false);
        virtualView.communicate(dataOutputStream, "", 5);

        return available.get(choice);

    }

    public String requiredString(Socket sc, String message, List<String> available) throws IOException {
        String string = null;
        boolean correctString =  false;
        DataOutputStream dataOutputStream = socketCommunication.get(sc);

        while(!correctString){
            virtualView.communicate(dataOutputStream, message, 2);
            socketStreamMap.get(sc).setCanSpeak(true);
            ClientHandler clientHandler = socketStreamMap.get(sc);
            string = virtualView.getString(messageReceveing.get(clientHandler));
            if(available.contains(string))
                correctString=true;
        }

        virtualView.communicate(dataOutputStream, "", 5);

        return string;
    }



    @Override
    public int[] requiredPosition(Socket sc, String message, int[][] matrix ) throws IOException {
        String position = null;
        ClientHandler clientHandler = socketStreamMap.get(sc);
        int row = 0;
        int column = 0;
        int[] results = new int[2];
        boolean notCorrect = true;
        DataOutputStream dataOutputStream = socketCommunication.get(sc);

        while (!notCorrect) {
            virtualView.communicate(dataOutputStream, message, 3);
            clientHandler.setCanSpeak(true);
            position = virtualView.getString(messageReceveing.get(clientHandler));
            row = pickFirstInt(position);
            column = pickSecondInt(position);
            if (matrix[row][column] == 0)
                notCorrect = false;
        }

        clientHandler.setCanSpeak(false);
        virtualView.communicate(dataOutputStream, "", 5);
        results[0] = row;
        results[1] = column;

        return results;

    }

    private int pickFirstInt(String position){
        int lastIndex = position.indexOf("-");
        String firstPart = position.substring(0,lastIndex-1);
         return Integer.parseInt(firstPart);
    }

    private int pickSecondInt(String position){
        int firstIndex = position.indexOf("-");
        String secondPart = position.substring(firstIndex);
        return Integer.parseInt(secondPart);
    }

    public void updateSetUp(Space space, String color) throws IOException {
        DataOutputStream dataOutputStream;
        for(Socket sc: socketStreamMap.keySet()){
            dataOutputStream = socketCommunication.get(sc);
            virtualView.communicate(dataOutputStream,"<message>Updating position of worker</message><Space>" +
                    "<Row>" + space.getRow() + "</Row>" + "<Column>" + space.getColumn() + "</Column></Space>" +
                    "<color>" + color + "</color>", 0);
        }
    }

    @Override
    public void updateMovement(Space startPlace, Space finishPlace, String color) throws IOException {
        DataOutputStream dataOutputStream;

        int startPlaceRow = startPlace.getRow();
        int startPlaceColumn = startPlace.getColumn();

        int finishSpaceRow = finishPlace.getRow();
        int finishSpaceColumn = finishPlace.getColumn();

        for(Socket sc: socketStreamMap.keySet()) {
            dataOutputStream = socketCommunication.get(sc);
            virtualView.communicate(dataOutputStream, "<startSpace>" + "<row>" + startPlaceRow + "</row> " + "<column>" + startPlaceColumn + "</column>" + "</startSpace>" + "<finishSpace>" +
                    "<row>" + finishSpaceRow + "</row>" + "<column>" + finishSpaceColumn + "</column> " + "</finishSpace>" + "<player>" + "<color>" + color + "</color>" + "</player>", 7);
        }
    }

    @Override
    public void updateBuilding(Space buildSpace) throws IOException {
        DataOutputStream dataOutputStream;
        int buildSpaceRow = buildSpace.getRow();
        int buildSpaceColumn = buildSpace.getColumn();
        int buildSpaceLevel = buildSpace.getLevel();
        boolean hasDome = buildSpace.HasDome();

        for(Socket sc: socketStreamMap.keySet()) {
            dataOutputStream = socketCommunication.get(sc);
            virtualView.communicate(dataOutputStream, "<buildSpace>" + "<row>" + buildSpaceRow + "</row> " + "<column>" + buildSpaceColumn + "</column> "
                    + "<level>" + buildSpaceLevel + "</level> " + "<hasDome>" + hasDome + "</hasDome>" + "</buildSpace>", 8);
        }
    }

    @Override
    public void updateWin(Socket sc) throws IOException {
        DataOutputStream dataOutputStream;

        for(Socket socket: socketStreamMap.keySet()) {
            dataOutputStream = socketCommunication.get(socket);
            if (socket == sc)
                virtualView.communicate(dataOutputStream, "<message>Congratulations you won! Others suck</message>", 4);
            else
                virtualView.communicate(dataOutputStream, "<message>You suck at this game</message>", 4);
        }
    }

    @Override
    public void updateLose(Socket sc, Space spaceWorker1, Space spaceWorker2) throws IOException {
        DataOutputStream dataOutputStream;

        for(Socket socket: socketStreamMap.keySet()) {
            dataOutputStream = socketCommunication.get(socket);
            if (socket == sc)
                virtualView.communicate(dataOutputStream, "<message>You lost! Now you can watch others winning</message> " + "<worker1Space>" + "<Row>" + spaceWorker1.getRow() + "</Row>" +
                        "<Column>" + spaceWorker1.getColumn() + "</Column>" + "</worker1Space>" + "<worker2Space>" + "<Row>" + spaceWorker2.getRow() + "</Row> " +
                        "<Column>" + spaceWorker2.getColumn() + "</Column>" + "</worker2Space>", 9);
            else
                virtualView.communicate(dataOutputStream, "<message>Someone lost, but not you (at the moment)</message>" + "<worker1Space>" + "<Row>" + spaceWorker1.getRow() + "</Row>" +
                        "<Column>" + spaceWorker1.getColumn() + "</Column>" + "</worker1Space>" + "<worker2Space>" + "<Row>" + spaceWorker2.getRow() + "</Row> " +
                        "<Column>" + spaceWorker2.getColumn() + "</Column>" + "</worker2Space>", 9);
        }
    }
}

