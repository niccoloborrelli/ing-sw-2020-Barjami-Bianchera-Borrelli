package it.polimi.ingsw;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HandlerHub {

    private HashMap<Controller, Handler> handlerControllerHashMap;
    private static final String endMessage = "<data><code>0</code><message>Somebody disconnected from server! Game is nullified</message></data>"; // DA CAMBIARE
    private static final String PREFIX ="<?xml version=\"1.0\" encoding=\"utf-8\"?>";


    public HandlerHub() {
        handlerControllerHashMap = new HashMap<>();
    }

    public HashMap<Controller, Handler> getHandlerControllerHashMap() {
        return handlerControllerHashMap;
    }

    public void addHandlerForSocket(Socket socket, Controller controller) throws IOException {
        handlerControllerHashMap.put(controller, new Handler(socket,this));
        handlerControllerHashMap.get(controller).setHandlerHub(this);
        controller.setHandlerHub(this);
    }

    public void quitGame(Handler handler){

        for(Handler leftHandler: handlerControllerHashMap.values()){
            try {
                if(!leftHandler.equals(handler))
                    leftHandler.communicate(PREFIX + endMessage);
                handler.setEndGame(true);
                leftHandler.getSc().close(); //oppure attendere il messaggio di quit?
            } catch (IOException ignored) {
            }

            //handlerControllerHashMap.remove(findControllerFromHandler(leftHandler));
        }
    }

    public void callController(Handler handler, String message){
        Controller controller = findControllerFromHandler(handler);
        controller.giveInputToModel(message);
    }

    public void updateEndGame(String message){
        for(Handler handler: handlerControllerHashMap.values()) {
            handler.communicate(message);
            try {
                handler.setEndGame(true);
                Socket sc = handler.getSc();
                handler.join();
                sc.close();
            } catch (IOException | InterruptedException ignored) {
            }
        }
    }

    public void sendData(String message, Controller controller, int typeCommunication){
        if(typeCommunication == 0) {
            for (Handler handler : handlerControllerHashMap.values()) {
                handler.communicate(message);
            }
        }else if(typeCommunication == 1) {
            Handler handler = handlerControllerHashMap.get(controller);
            handler.communicate(message);
        }else if(typeCommunication == 2){
            for (Handler handler : handlerControllerHashMap.values()) {
                if (!handler.equals(handlerControllerHashMap.get(controller)))
                    handler.communicate(message);
            }
        }
    }

    private Controller findControllerFromHandler(Handler handler){
        for(Controller controller: handlerControllerHashMap.keySet()){
            if(handler.equals(handlerControllerHashMap.get(controller)))
                return controller;
        }

        return null;
    }
/*
    public void actionateEveryHandler(){
        List<Thread> threadList;
        threadList = createThreadList();
        for(Thread t: threadList){
            t.start();
        }
        joinEveryHandle(threadList);
    }
*/
/*
    private List<Thread> createThreadList(){
        List<Thread> threadList = new ArrayList<>();

        for(Handler handler: handlerControllerHashMap.values()){
            threadList.add(createThreadHandle(handler));
        }

        return threadList;
    }
*/
    /*
    private void joinEveryHandle(List<Thread> threadList){
        for(Thread t: threadList){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace(); //non dovrebbe capitare, ma capire come gestirla
            }
        }
    }
     */

    public Thread createThreadHandle(Handler handler){
        return new Thread(handler::handle);
    }
}
