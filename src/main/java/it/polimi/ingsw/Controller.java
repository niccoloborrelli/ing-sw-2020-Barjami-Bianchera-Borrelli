package it.polimi.ingsw;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.InetAddress;
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


    public HashMap<Socket, DataOutputStream> getSocketCommunication() {
        return socketCommunication;
    }


    public ServerSocket getSs() {
        return ss;
    }

    public VirtualView getVirtualView() {
        return virtualView;
    }

    public void ping(Socket sc) throws InterruptedException, IOException {
        ClientHandler clientHandler = socketStreamMap.get(sc);
        System.out.println("Sono dentro Ping");
        Thread tPing = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    clientHandler.ping();
                }catch (IOException | InterruptedException ignored) {
                }
            }
        },"ping");

        tPing.start();
        tPing.join();
        if(!clientHandler.isEndGame()){
            notifyDisconnectionClient(sc);
        }
        closeEverySocket();

    }

    public void notifyDisconnectionClient(Socket sc) throws IOException {
        for(Socket socket: socketStreamMap.keySet()) {
            if (!socket.equals(sc)) {
                if (!socket.isClosed())
                    virtualView.communicate(socketCommunication.get(sc), "I'm sorry! A player cut off the connection", 4);
            }
        }
    }

    public void closeEverySocket() throws IOException {
        ClientHandler clientHandler;
        for(Socket socket: socketStreamMap.keySet()) {
            clientHandler = socketStreamMap.get(socket);
            socket.close();
            clientHandler.getSs().close();
            messageReceveing.remove(clientHandler);
            socketCommunication.remove(socket);
            socketStreamMap.remove(socket);
        }

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


    public ClientHandler createClientHandler() throws InterruptedException {
        final Socket[] handler = new Socket[1];
        final ClientHandler[] clientHandler = new ClientHandler[1];
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
                        clientHandler[0] = new ClientHandler();
                        sleep(20);
                        messageReceveing.put(clientHandler[0], new DataInputStream(handler[0].getInputStream()));
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            },"threabB");

            t1.start();
            t2.start();
            t1.join();
            t2.join();

            System.out.println("Ho terminato la creazione del clientHandler");

            return clientHandler[0];

        }

     public List<Socket> createSession() throws InterruptedException, IOException {
        List<ClientHandler> clientHandlerList = new ArrayList<>();
        List<Socket> socketList  = new ArrayList<>();
        List<Integer> available = new ArrayList<>();
        available.add(0);
        available.add(1);
        boolean validAnswer = false;
        int value;

         for(int i=0; i<3; i++)
             clientHandlerList.add(createClientHandler());

        Thread t1 = createThread(socketList, clientHandlerList.get(0));
        System.out.println("Ho creato il primo thread");
        Thread t2 = createThread(socketList, clientHandlerList.get(1));
        System.out.println("Ho creato il secondo thread");
        Thread t3 = createThread(socketList, clientHandlerList.get(2));
        System.out.println("Ho creato il terzo thread");




        t1.start();
        t2.start();
        t3.start();

        System.out.println("Li ho fatti partire tutti e 3");

        t1.join();
        t2.join();

        /*
        Serve per gestire il caso in cui sia t3 ad essere il challenger
         */

        if(socketCommunication.get(socketList.get(0))==null){
            System.out.println("Il terzo thread è il challenger");
            t3.join();
        }

        int i=0;


        new Thread(()-> {
            runAllClientHandler();  //NON ANDREBBE FATTO QUA, MA MI SERVE PER IL TEST -- PUò ESSERE FATTO QUA E POI FATTO RIPARTIRE UNA VOLTA FINITA CREATE SESSION
        }).start();
        Socket challenger = socketList.get(0);

        while(!validAnswer) {
            value = requiredInt(challenger,"Insert 0 if you want to play a 1 vs 1, otherwise insert 1 to play against 2 players", available);
            System.out.println("value è:" + value);
            if (value == 0) {
                if (t3.isAlive()){
                    t3.join();
                    System.out.println("Ora anche t3 è terminato");
                }
                removeExtraSocket(socketList);
                validAnswer = true;
            } else if (value == 1) {
                if (t3.isAlive())
                    t3.join();
                validAnswer = true;
            }
        }

        for(DataOutputStream data : socketCommunication.values())
            virtualView.communicate(data, "You're enlisted! You have to conquer Santorini", 0);
        return socketList;
     }

     public void runAllClientHandler(){
        for(ClientHandler h: socketStreamMap.values())
            new Thread(()->{h.run();}).start();
     }


     public Thread createThread(List<Socket> socketList, ClientHandler clientHandler){
         Thread t = new Thread(()->{
             Socket sc;
             try {
                 sc = virtualView.getSocket(ss);
                 synchronized (socketList) {
                     socketList.add(sc);
                 }
                 clientHandler.setClientHandler(sc);
                 socketStreamMap.put(sc, clientHandler);
                 socketCommunication.put(sc, new DataOutputStream(sc.getOutputStream()));
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }, "t2");

         return t;
     }

     public void removeExtraSocket(List<Socket> socketList) throws IOException {

         Socket lastPossiblePlayer = socketList.get(2);
         ClientHandler lastHandler = socketStreamMap.get(lastPossiblePlayer);
         socketStreamMap.remove(lastPossiblePlayer);
         virtualView.communicate(socketCommunication.get(lastPossiblePlayer),"I'm sorry, a game is already started",4);
         socketCommunication.remove(lastPossiblePlayer);
         messageReceveing.remove(lastHandler);
         socketList.remove(lastPossiblePlayer);
         if(lastHandler.isAlive() && !lastHandler.getSs().isClosed()) {
             lastHandler.endGame=true;
             lastHandler.getSs().close();
         }
         lastPossiblePlayer.close();
     }

    @Override
    public void printMessage(Socket sc, String message) throws IOException {
        DataOutputStream dataOutputStream = socketCommunication.get(sc);
        virtualView.communicate(dataOutputStream,message,0);
    }



    @Override
    public int requiredInt(Socket sc, String message, List<Integer> available) throws IOException {
        String handlerResult;
        int value = 0;
        boolean correct = false;
        ClientHandler clientHandler = socketStreamMap.get(sc);

        while(!correct){
            virtualView.communicate(socketCommunication.get(sc), message, 1);
            socketStreamMap.get(sc).setCanSpeak(true);
            handlerResult = virtualView.getString(messageReceveing.get(clientHandler));
            System.out.println("La required ha preso: " + handlerResult);
            if(!handlerResult.equals("")) {
                value = Integer.parseInt(handlerResult);
                if (available.contains(value)) {
                    System.out.println("Valore valido");
                    correct = true;
                }
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

