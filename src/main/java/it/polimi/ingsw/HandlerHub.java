package it.polimi.ingsw;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

import static it.polimi.ingsw.FinalCommunication.*;

public class HandlerHub {

    /**
     * Represents an hub which sends messages, depending of parameters of their headers.
     */

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

    /**
     * Creates and links handler to controller and vice versa.
     * @param socket is socket of the new handler.
     * @param controller is linked with handler.
     * @throws IOException if socket aren't valid.
     */

    public void addHandlerForSocket(Socket socket, Controller controller) throws IOException {
        handlerControllerHashMap.put(controller, new Handler(socket,this));
        handlerControllerHashMap.get(controller).setHandlerHub(this);
        controller.setHandlerHub(this);
    }

    /**
     * If it isn't a global hub, deletes every link and closed every socket. It could
     * send a disconnection message.
     * @param handler is handler who invokes this method.
     * @param awareness indicates if it's aware or not.
     */

    public void quitGame(Handler handler, boolean awareness){
        if(!general){
            synchronized (handlerControllerHashMap) {
                Iterator<Handler> iterator = handlerControllerHashMap.values().iterator();
                while (iterator.hasNext()) {
                    Handler leftHandler = iterator.next();
                    if (!leftHandler.equals(handler)) {
                        if (!awareness) {
                            findControllerFromHandler(handler).communicateDisconnectionMessage();
                        }
                    }
                    stopHandler(leftHandler);
                }
                handlerControllerHashMap.clear();
            }
        }else{
            stopHandler(handler);
            handlerControllerHashMap.remove(findControllerFromHandler(handler));
            }
    }

    /**
     * Stops handler and closes his sockets.
     * @param handlerToStop is handler to stop.
     */

    private void stopHandler(Handler handlerToStop){
        try{
            handlerToStop.setEndGame(true);
            handlerToStop.getSc().close();
        }catch (IOException ignored){}
    }

    /**
     * Passes a message from handler to controller linked to its.
     * @param handler is handler that receives message.
     * @param message is message to pass.
     */
    public void callController(Handler handler, String message){
        Controller controller = findControllerFromHandler(handler);
        controller.giveInputToModel(message);
    }

    /**
     * Sends message depending of type of communication.
     * @param message is message to send.
     * @param controller generates message.
     * @param typeCommunication is type of communication.
     */

    public void sendData(String message, Controller controller, int typeCommunication){
        if(handlerControllerHashMap.get(controller)!=null) {
            if (typeCommunication == BROADCAST) {
                for (Handler handler : handlerControllerHashMap.values()) {
                    handler.communicate(message);
                }
            } else if (typeCommunication == SINGLE_COMMUNICATION) {
                Handler handler = handlerControllerHashMap.get(controller);
                handler.communicate(message);
            } else if (typeCommunication == ALL_NOT_ME) {
                for (Handler handler : handlerControllerHashMap.values()) {
                    if (!handler.equals(handlerControllerHashMap.get(controller)))
                        handler.communicate(message);
                }
            }
        }
    }

    /**
     * Finds controller from handler passed.
     * @param handler is handler linked to controller to search.
     * @return controller linked with handler passed if it exists, otherwise null.
     */

    private Controller findControllerFromHandler(Handler handler){
        for(Controller controller: handlerControllerHashMap.keySet()){
            if(handler.equals(handlerControllerHashMap.get(controller)))
                return controller;
        }

        return null;
    }

    /**
     * Creates the correct thread of handler passed.
     * @param handler is handler passed.
     * @return the correct thread of handler passed.
     */

    public Thread createThreadHandle(Handler handler){
        return new Thread(handler::handle);
    }
}
