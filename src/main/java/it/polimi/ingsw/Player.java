package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String playerName;
    private String playerColor;
    private God playerGod;
    private List<Worker> workers;
    private boolean inGame;
    
    //costruttore con argomento nome
    public Player(String playerName){
        this.playerName = playerName;
        this.playerColor = null;
        this.playerGod = null;
        this.workers = new ArrayList<Worker>();
        this.inGame = true;
    }

    public void chooseYourGod(List <God> godList){

    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setPlayerColor(String playerColor) {
        this.playerColor = playerColor;
    }

    public void setPlayerGod(God playerGod) {
        this.playerGod = playerGod;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPlayerColor() {
        return playerColor;
    }

    public God getPlayerGod() {
        return playerGod;
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public boolean isInGame() {
        return inGame;
    }
}
