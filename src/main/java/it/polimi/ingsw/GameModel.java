package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
Metodi da specificare in Javadoc:
- game
- gamesetting
- setnextplayer (?)
- setupworkers
- setupconditions
- spacefree
 */

public class GameModel {
    private boolean nobodyHasWon;
    private List<Player> players;
    private IslandBoard islandBoard;
    private Challenger gameChallenger;
    private static final int endboard = 4;
    private static final int beginofAll = 0;

    public GameModel(List<String> playersName) { //DA CAMBIARE
        players = new ArrayList<Player>();
        this.islandBoard = new IslandBoard();
        nobodyHasWon=true;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public IslandBoard getIslandBoard() {
        return islandBoard;
    }

    public void setIslandBoard(IslandBoard islandBoard) {
        this.islandBoard = islandBoard;
    }

    public Player getGameChallenger() {
        return gameChallenger;
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    /*
    Posiziona i worker nelle caselle scelti se libere
     */
    public boolean setupWorker(Worker worker, int row, int column) {
                Space space = islandBoard.getSpace(row,column);
                if(spaceFree(space)) {
                    worker.setWorkerSpace(space);
                    space.setOccupator(worker);
                    return true;
                }else
                    return false;
    }

    public boolean spaceFree (Space space){
        return space.getOccupator() == null;
    }

    public boolean isNobodyHasWon() {
        return nobodyHasWon;
    }

    public void setNobodyHasWon(boolean nobodyHasWon) {
        this.nobodyHasWon = nobodyHasWon;
    }

    public void setupConditions() { //setta le classi base dei player
        for (Player p : players) {
            p.setWinCondition(new BaseWinCondition());
            p.setRestriction(new BaseRestriction());
            p.setMove(new BaseMovement());
            p.setBuild(new BaseBuild());
        }
    }

    public boolean canDoSomething(Player player){
        player.getRestriction().restrictionEffectMovement(player, islandBoard);
        List<Space>[] liste = islandBoard.checkAvailableMovement(player);
        boolean can1 = canWorkerDoSomething(player.getWorkers().get(0), liste[0], 0);
        if(can1)
            return true;
        boolean can2 = canWorkerDoSomething(player.getWorkers().get(1), liste[1], 1);
        if(can2)
            return true;
        deletePlayer(player);
        return false;
    }

    public boolean canWorkerDoSomething(Worker worker, List<Space> liste, int i){
        Player player = worker.getWorkerPlayer();
        Space startSpace = worker.getWorkerSpace();
        startSpace.setOccupator(null);
        for (Space s:liste) {
            s.setOccupator(worker);
            worker.setWorkerSpace(s);
            player.getRestriction().restrictionEffectBuilding(worker, islandBoard);
            List<Space>[] costruzioni = islandBoard.checkAvailableBuilding(player);
            if(costruzioni[i].size() > 0) {
                s.setOccupator(null);
                worker.setWorkerSpace(startSpace);
                startSpace.setOccupator(worker);
                return true;
            }
            s.setOccupator(null);
        }
        worker.setWorkerSpace(startSpace);
        startSpace.setOccupator(worker);
        return false;
    }

    /*
    setta le posizioni dei workers a null
     */
    public void deletePlayer(Player player){
            player.setInGame(false);
            player.getWorkers().get(0).getWorkerSpace().setOccupator(null);
            player.getWorkers().get(1).getWorkerSpace().setOccupator(null);
            player.getWorkers().get(0).setWorkerSpace(null);
            player.getWorkers().get(1).setWorkerSpace(null);
            System.out.println(player.getPlayerName() + ", you lost!");
    }
}