package it.polimi.ingsw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static it.polimi.ingsw.DefinedValues.*;

import static it.polimi.ingsw.Color.*;

public class TurnManager {

    private List<Player> players;
    private List<String> notAllowedNames;
    private List<String> allowedColors;
    private List<String> availableGods;

    public TurnManager(){
        allowedColors = new ArrayList<>();
        allowedColors.add("red");
        allowedColors.add("purple");
        allowedColors.add("white");
        allowedColors.add("gray");
        allowedColors.add("cyan");
        notAllowedNames = new ArrayList<>();
        players = new ArrayList<>();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<String> getNotAllowedNames() {
        return notAllowedNames;
    }

    public List<String> getAllowedColors() {
        return allowedColors;
    }

    public void addName(String name){
        notAllowedNames.add(name);
    }

    public void removeColor(String color){
        allowedColors.remove(color);
    }

    public void setColor(Player player, String color){
        switch (color){
            case "red":
                player.setPlayerColor(ANSI_RED.escape());
                break;
            case "purple":
                player.setPlayerColor(ANSI_PURPLE.escape());
                break;
            case "white":
                player.setPlayerColor(ANSI_WHITE.escape());
                break;
            case "cyan":
                player.setPlayerColor(ANSI_CYAN.escape());
                break;
            case "gray":
                player.setPlayerColor(ANSI_GRAY.escape());
                break;
        }
    }

    /**
     * This method check if someone has won after an action
     */
    public void checkWin() throws IOException {
        if(!oneRemain()) {
            for (Player p : players)
                if (p.isInGame())
                    p.getWinCondition().checkHasWon(p);
        }
    }

    /**
     * @param player is the player who has just finished hir turn
     * @return the next player to play
     */
    public Player setNextPlayer(Player player){
        int pos = players.indexOf(player);
        if(pos == players.size() - 1)
            return players.get(0);
        else
            return players.get(++pos);
    }

    private boolean oneRemain(){
        int alive = 0;
        Player winner = null;
        for (Player p: players) {
            if(p.isInGame()){
                winner = p;
                alive++;
            }
        }
        if(alive == 1) {
            winner.getWinCondition().setHasWon(true);
            //notifyWin();
            return true;
        }
        return false;
    }

    public void setNotAllowedNames(List<String> notAllowedNames) {
        this.notAllowedNames = notAllowedNames;
    }

    public void setAllowedColors(List<String> allowedColors) {
        this.allowedColors = allowedColors;
    }

    public List<String> getAvailableGods() {
        return availableGods;
    }

    public void setAvailableGods(List<String> availableGods) {
        this.availableGods = availableGods;
    }

    public void addGod(String godName){
        availableGods.add(godName);
    }

    public void removeGod(String godName){
        if(availableGods.contains(godName)){
            availableGods.remove(godName);
        }
    }
}
