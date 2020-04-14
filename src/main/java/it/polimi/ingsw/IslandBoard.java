package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

public class IslandBoard {
    private Space[][] spaces;
    private RestrictionAB restriction;

    public IslandBoard(){
        spaces = new Space[5][5];
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 5; j++)
                spaces[i][j]= new Space(i,j);
    }

    public Space[][] getSpaces() {
        return spaces;
    }

    public Space getSpace(int row, int column){
        return spaces[row][column];
    }

    public void setSpaces(Space[][] spaces) {
        this.spaces = spaces;
    }

    public RestrictionAB getRestriction() {
        return restriction;
    }

    public void setRestriction(RestrictionAB restriction) {
        this.restriction = restriction;
    }

    /*
    restituisce un array che contiene 2 liste: la prima sono le celle in cui si
    può muovere il worker1, la seconda la corrispettiva del worker2
     */
    public List<Space>[] checkAvailableMovement(Player player){
        List<Space> availableSpaces1 = new ArrayList<Space>();
        List<Space> availableSpaces2 = new ArrayList<Space>();
        Worker firstWorker = player.getWorkers().get(0);
        Worker secondWorker = player.getWorkers().get(1);

        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                controlIfWorkersInList(spaces[i][j].isAvailableMovement(), firstWorker, secondWorker, i, j, availableSpaces1, availableSpaces2);

        List[] returnList = new ArrayList[2];
        returnList[0] = availableSpaces1;
        returnList[1] = availableSpaces2;
        areWorkersInGame(player,availableSpaces1, availableSpaces2);
        return returnList;
    }

    /*
    restituisce un array che contiene 2 liste: la prima sono le celle in cui
    può costruire il worker1, la seconda la corrispettiva del worker2
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

    //controlla se i 2 worker appartengono alla lista listToCheck e se lo sono inserisce lo spazio i,j dentro availablespaces1 e 2
    private void controlIfWorkersInList(List <Worker> listToCheck, Worker firstWorker, Worker secondWorker, int i, int j, List<Space> availableSpaces1, List<Space> availableSpaces2){
        if (listToCheck.contains(firstWorker))
            availableSpaces1.add(spaces[i][j]);
        if (listToCheck.contains(secondWorker))
            availableSpaces2.add(spaces[i][j]);
    }

    /*
    svuota le liste di worker che possono costruire
    o muoversi in ogni cella della board
     */
    public void resetBoard(){
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 5; j++){
                spaces[i][j].resetAvailableBuilding();
                spaces[i][j].resetAvailableMovement();
            }
    }

    private void areWorkersInGame(Player player, List<Space> spaces1,  List<Space> spaces2){
        if(spaces1.size() == 0)
            player.getWorkers().get(0).setInGame(false);
        if(spaces2.size() == 0)
            player.getWorkers().get(1).setInGame(false);
    }

}
