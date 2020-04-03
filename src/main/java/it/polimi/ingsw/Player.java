package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String playerName;
    private String playerColor;
    private God playerGod;
    private List<Worker> workers;
    private boolean inGame;
    private WinConditionAB winCondition;
    private RestrictionAB restriction;
    private BuildAB build;
    private MovementAB move;

    public Player(String playerName){
        this.playerName = playerName;
        this.workers = new ArrayList<Worker>();
        this.inGame = true;
        workers.add(new Worker());
        workers.add(new Worker());
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

    public WinConditionAB getWinCondition() {
        return winCondition;
    }

    public void setWinCondition(WinConditionAB winCondition) {
        this.winCondition = winCondition;
    }

    public Worker chooseWorker(int input) {
        return workers.get(input);
    }

    public RestrictionAB getRestriction() {
        return restriction;
    }

    public void setRestriction(RestrictionAB restriction) {
        this.restriction = restriction;
    }

    public BuildAB getBuild() {
        return build;
    }

    public void setBuild(BuildAB build) {
        this.build = build;
    }

    public MovementAB getMove() {
        return move;
    }

    public void setMove(MovementAB move) {
        this.move = move;
    }
}
