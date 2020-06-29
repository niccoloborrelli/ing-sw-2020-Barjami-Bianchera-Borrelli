package it.polimi.ingsw;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import static it.polimi.ingsw.FinalCommunication.*;

public class HandlerHub {

    private HashMap<Controller, Handler> handlerControllerHashMap;
    boolean general;


    public HandlerHub() {
        handlerControllerHashMap = new HashMap<>();
        general = true;
    }

    public boolean isGeneral() {
        return general;
    }

    public void setGeneral(boolean general) {
        this.general = general;
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
        if(!general){
            for(Handler leftHandler: handlerControllerHashMap.values()) {
                if(!leftHandler.equals(handler))
                    findControllerFromHandler(handler).communicateDisconnectionMessage();
                try {
                    leftHandler.setEndGame(true);
                    leftHandler.getSc().close();
                } catch (IOException ignored) {
                }
            }
            handlerControllerHashMap.clear();
        }else{
            try {
                handler.setEndGame(true);
                handler.getSc().close();
            } catch (IOException ignored) {
            }
            handlerControllerHashMap.remove(findControllerFromHandler(handler));
            }

    }

    public void callController(Handler handler, String message){
        Controller controller = findControllerFromHandler(handler);
        controller.giveInputToModel(message);
    }

    public void sendData(String message, Controller controller, int typeCommunication){
        if(handlerControllerHashMap.get(controller)!=null) {
            if (typeCommunication == 0) {
                for (Handler handler : handlerControllerHashMap.values()) {
                    handler.communicate(message);
                }
            } else if (typeCommunication == 1) {
                Handler handler = handlerControllerHashMap.get(controller);
                handler.communicate(message);
            } else if (typeCommunication == 2) {
                for (Handler handler : handlerControllerHashMap.values()) {
                    if (!handler.equals(handlerControllerHashMap.get(controller)))
                        handler.communicate(message);
                }
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
