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
    private List<Player> players;
    private IslandBoard islandBoard;
    private Challenger gameChallenger;
    private static final int endboard = 4;
    private static final int beginofAll = 0;

    public GameModel(List<String> playersName) { //DA CAMBIARE
        players = new ArrayList<Player>();
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

    public Player getGameChallenger() {
        return gameChallenger;
    }

    /*
    MAIN
     */
    private void game() {
        gameSetting();
        Player activePlayer;
        activePlayer = players.get(beginofAll);
        boolean nobodyHasWon = true;
        while (nobodyHasWon) {
            if(activePlayer.isInGame() && canDoSomething(activePlayer)) {
                round(activePlayer);
                if (activePlayer.getWinCondition().gethasWon()) {
                    nobodyHasWon = false;
                    System.out.println(activePlayer.getPlayerName() + ", you won!!");
                }
            }
            activePlayer = setNextPlayer(players.indexOf(activePlayer));
        }
    }

    public void gameSetting() {

        createPlayers();
        setupWorkers();//posizione iniziale workers
        //scegli divinità
        setupConditions();
    }

    private void createPlayers() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Select the number of players");
        int numberPlayers = sc.nextInt();
        createChallenger(sc);
        for (int i = 1; i < numberPlayers; i++) {
            System.out.println("Player number " + i + 1 + ", please insert your name and your color");
            players.add(new Player(insertName(sc)));
            players.get(i).setPlayerColor(insertColor(sc));
        }
    }

    private String insertName (Scanner sc){
        String name;
        do {
            System.out.println("Please insert your name");
            name = new String(sc.nextLine());
        } while (nameEquals(name));
        return name;

    }

    private boolean nameEquals(String name) {
        for (Player p : players) {
            if (p.getPlayerName().equals(name))
                return true;
        }
        return false;
    }

    private String insertColor(Scanner sc){
        String color;
        do {
            System.out.println("Please insert your color");
            color = new String(sc.nextLine());
        } while (colorEquals(color));
        return color;
    }

    private boolean colorEquals(String color) {
        for (Player p : players) {
            if (p.getPlayerColor().equals(color))
                return true;
        }
        return false;
    }

    private void createChallenger(Scanner sc) {
        System.out.println("Challenger, please insert your name");
        gameChallenger = new Challenger(new String(sc.nextLine()));
        System.out.println("Challenger, please insert your color");
        gameChallenger.setPlayerColor(new String(sc.nextLine()));
        players.add(gameChallenger);
    }

    /*
    Posiziona i worker nelle caselle scelti se libere
     */
    private void setupWorkers() {

        Scanner sc = new Scanner(System.in);
        for (Player p : players) {
            for (Worker w : p.getWorkers()) {
                Space space = spaceFree(sc);
                w.setWorkerSpace(space);
                space.setOccupator(w);
            }
        }
    }

    /*
    Se le coordinate inserite indicano uno spazio esistente e libero,
    altrimenti richiede le coordinate
     */
    private Space spaceFree (Scanner sc){
        int row;
        int column;

        do {
            System.out.println("Select the row for your worker");
            row = checkCorrectCoordinate(sc);
            System.out.println("Select the column for your worker");
            column = checkCorrectCoordinate(sc);
        } while (!emptySpace(row, column));

        return getIslandBoard().getSpace(row, column);
    }

    /*
    Controlla se la coordinata inserita è corretta
     */
    private int checkCorrectCoordinate(Scanner sc){
        int coordinate;
        do {
            System.out.println("Select the coordinate for your worker");
            coordinate = sc.nextInt();
        } while (coordinate < beginofAll || coordinate > endboard);
        return coordinate;

    }

    /*
    Controlla se lo spazio è vuoto
     */
    private boolean emptySpace(int row, int column) {

        return islandBoard.getSpace(row, column).getOccupator() == null;
    }

    private void setupConditions() { //DA CAMBIARE QUANDO INSERIREMO GODS
        for (Player p : players) {
            p.setWinCondition(new BaseWinCondition());
            p.setRestriction(new BaseRestriction());
            p.setMove(new BaseMovement());
            p.setBuild(new BaseBuild());
        }
    }

    private Player setNextPlayer(int index) {
        if (players.size() - 1 != index)
            return players.get(index + 1);
        return players.get(beginofAll);
    }

    private void round(Player player) {
        Worker worker;

        Scanner sc = new Scanner(System.in);
        do {
            worker = player.chooseWorker(sc.nextInt());

        }while(worker.isInGame());

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

    private boolean canWorkerDoSomething(Worker worker, List<Space> liste, int i){
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
    private void deletePlayer(Player player){
            player.setInGame(false);
            player.getWorkers().get(0).getWorkerSpace().setOccupator(null);
            player.getWorkers().get(1).getWorkerSpace().setOccupator(null);
            player.getWorkers().get(0).setWorkerSpace(null);
            player.getWorkers().get(1).setWorkerSpace(null);
            System.out.println(player.getPlayerName() + ", you lost!");
    }
}