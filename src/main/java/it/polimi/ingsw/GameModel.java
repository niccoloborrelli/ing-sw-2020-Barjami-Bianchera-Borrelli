package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private List<Player> players;
    private IslandBoard islandBoard;
    private Round round;
    private WinConditionAB winCondition;
    private Player gameChallenger;

    /*
    il challenger è il primo giocatore
     */
    public GameModel(List<String> playersName) { //colori??
        players = new ArrayList<Player>();
        for (int i = 0; i < playersName.size(); i++) {
            if (i == 0){
                gameChallenger = new Challenger(playersName.get(i));
                players.add(gameChallenger);
            }
            else
                players.add(new Player(playersName.get(i)));
        }

        this.islandBoard = new IslandBoard();

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

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public WinConditionAB getWinCondition() {
        return winCondition;
    }

    public void setWinCondition(WinConditionAB winCondition) {
        this.winCondition = winCondition;
    }

    public Player getGameChallenger() {
        return gameChallenger;
    }

    public void setGameChallenger(Player gameChallenger) {
        this.gameChallenger = gameChallenger;
    }
}
