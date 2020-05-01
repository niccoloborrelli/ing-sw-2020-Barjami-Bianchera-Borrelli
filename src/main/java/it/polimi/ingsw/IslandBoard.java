package it.polimi.ingsw;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/*
Attenzione con areWorkersInGame perchè va settato a true ogni inizio turno
 */
public class IslandBoard {
    private Space[][] spaces;
    private boolean restrictionPower;
    private List<Controller> observers;

    /**
     * IslandBoard constructor that initialize a 5x5 array of space
     */
    public IslandBoard(){
        observers = new ArrayList<>();
        spaces = new Space[5][5];
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 5; j++)
                spaces[i][j]= new Space(i,j);
    }

    /**
     * @param row is the coordinate of the row
     * @param column is the coordinate of the column
     * @return the space with coordinates row and column
     */
    public Space getSpace(int row, int column){
        return spaces[row][column];
    }

    /**
     * This methods calculate all the possible movements for a player
     * @param player is the player to analyze
     * @return an array of list of dimension 2:
     * in position 0 the movements for the first worker
     * in position 1 the movements for the second worker
     */
    public List<Space>[] checkAvailableMovement(Player player){
        List<Space> availableSpaces1 = new ArrayList<>();
        List<Space> availableSpaces2 = new ArrayList<>();
        Worker firstWorker = player.getWorkers().get(0);
        Worker secondWorker = player.getWorkers().get(1);

        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                controlIfWorkersInList(spaces[i][j].isAvailableMovement(), firstWorker, secondWorker, i, j, availableSpaces1, availableSpaces2);

        List[] returnList = new ArrayList[2];
        returnList[0] = availableSpaces1;
        returnList[1] = availableSpaces2;
        areWorkersInGame(player, availableSpaces1, availableSpaces2);
        return returnList;
    }

    /**
     * This methods calculate all the possible buildings for a player
     * @param player is the player to analyze
     * @return an array of list of dimension 2:
     * in position 0 the buildings for the first worker
     * in position 1 the buildings for the second worker
     */
    public List<Space>[] checkAvailableBuilding(Player player){
        List<Space> availableSpaces1 = new ArrayList<>();
        List<Space> availableSpaces2 = new ArrayList<>();
        Worker firstWorker = player.getWorkers().get(0);
        Worker secondWorker = player.getWorkers().get(1);

        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                controlIfWorkersInList(spaces[i][j].isAvailableBuilding(), firstWorker, secondWorker, i, j, availableSpaces1, availableSpaces2);

        List[] returnList = new ArrayList[2];
        returnList[0] = availableSpaces1;
        returnList[1] = availableSpaces2;
        return returnList;
    }

    /**
     * This method checks if the workers belongs to listToCheck and if they do then the method add the space in the list availableSpace
     * @param i is the coordinate of the row
     * @param j is the coordinate of the column
     * @param availableSpaces1 is the list of the possible movements or buildings for worker 1
     * @param availableSpaces2 is the list of the possible movements or buildings for worker 2
     */
    private void controlIfWorkersInList(List <Worker> listToCheck, Worker firstWorker, Worker secondWorker, int i, int j, List<Space> availableSpaces1, List<Space> availableSpaces2){
        if (listToCheck.contains(firstWorker))
            availableSpaces1.add(spaces[i][j]);
        if (listToCheck.contains(secondWorker))
            availableSpaces2.add(spaces[i][j]);
    }

    /**
     * This method empties for every space the list
     * of worker that can build or move on that space
     */
    public void resetBoard(){
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 5; j++){
                spaces[i][j].resetAvailableBuilding();
                spaces[i][j].resetAvailableMovement();
            }
    }

    /**
     * This methods checks if a worker can move somewhere, if it can't
     * then the boolean attribute "inGame" is set as false
     * @param player is the player to analyze
     * @param spaces1 is the list of the possible movements of worker 1
     * @param spaces2 is the list of the possible movements of worker 2
     */
    private void areWorkersInGame(Player player, List<Space> spaces1,  List<Space> spaces2){
        if(spaces1.size() == 0)
            player.getWorkers().get(0).setInGame(false);
        if(spaces2.size() == 0)
            player.getWorkers().get(1).setInGame(false);
    }

    public boolean isRestrictionPower() {
        return restrictionPower;
    }

    public void setRestrictionPower(boolean restrictionPower) {
        this.restrictionPower = restrictionPower;
    }


    public List<Controller> getObservers() {
        return observers;
    }

    public void removeObservers(Controller observer) {
        observers.remove(observer);
    }

    public void addObservers(Controller observer) {
       observers.add(observer);
    }

    public void notifyMovement(List<Socket> socketList, Space startPlace, Space finishPlace, String color) throws IOException {
        for(Observer o: observers)
            o.updateMovement(socketList,startPlace,finishPlace,color);
    }

    public void notifyBuilding(List<Socket> socketList, Space buildSpace) throws IOException {
        for(Controller o: observers)
            o.updateBuilding(socketList, buildSpace);
    }

    public void notifyWin(List<Socket> socketList, Socket sc) throws IOException {
        for(Controller o:observers)
            o.updateWin(socketList,sc);
    }

    public void notifySomeoneLose(List<Socket> socketList, Socket sc, Space spaceWorker1, Space spaceWorker2) throws IOException{
        for(Controller o: observers)
            o.updateLose(socketList,sc,spaceWorker1,spaceWorker2);
    }

    public int requiredInt(Socket sc, String message, List<Integer> available) throws IOException {
        int i = observers.get(0).requiredInt(sc,message,available);
        return i;
    }

    public Space requiredSpace(Socket sc, String message, int matrix[][]){ //DA CAMBIARE
        return  new Space(1,1);
    }

}

