package it.polimi.ingsw;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ControllerModel {

    private GameModel gameModel;

    public void game() throws IOException {
        gameSetting();
        ServerSocket sc = new ServerSocket(5050);
        Socket cl = sc.accept();
        Player activePlayer;
        activePlayer = players.get(0);
        while (nobodyHasWon) {

            if(activePlayer.isInGame()) {
                activePlayer.getWinCondition().checkHasWon(players);
                if (!activePlayer.getWinCondition().gethasWon() && canDoSomething(activePlayer)) {
                    round(activePlayer);
                }
                else if(activePlayer.getWinCondition().gethasWon())
                    nobodyHasWon = false;
            }
            activePlayer = setNextPlayer(players.indexOf(activePlayer));

        }
    }

    private void gameSetting() {
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
    private Player setNextPlayer(int index) {
        if (players.size() - 1 != index)
            return players.get(index + 1);
        return players.get(beginofAll);
    }

    private void round(Player player) {
        Worker worker;
        int numWorker;
        Space selectedPos;
        int startingPosLevel;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("scegli quale lavoratore vuoi muovere: (0 per il primo 1 per il secondo)");
            worker = player.chooseWorker(sc.nextInt());
        }while(!worker.isInGame());

        startingPosLevel=worker.getWorkerSpace().getLevel();
        // estrai la lista dei possibili movimenti del worker
        List<Space> possibleMovements = islandBoard.checkAvailableMovement(player)[player.getWorkers().indexOf(worker)];

        System.out.println("MOVIMENTO: ");
        selectedPos = selectPos(possibleMovements);
        player.getMove().move(worker,selectedPos); // A questo punto ho fatto i movimenti con tutti i controlli dovuti
        player.getWinCondition().checkHasWon(worker,startingPosLevel); //setto il booleano della cond di vittoria

        if (player.getWinCondition().gethasWon()) {
            nobodyHasWon = false;
            System.out.println(player.getPlayerName() + ", you won!!");
        }

        else {
            player.getRestriction().restrictionEffectBuilding(worker, islandBoard);
            List<Space> possibleBuilding = islandBoard.checkAvailableBuilding(player)[player.getWorkers().indexOf(worker)];
            if(possibleBuilding.size() == 0)
                deletePlayer(player);
            else{
                System.out.println("COSTRUZIONE: ");
                selectedPos = selectPos(possibleBuilding);
                player.getBuild().build(worker,selectedPos);
            }
        }
    }

    //seleziona una posizione possibile tra quelle date
    private Space selectPos(List <Space> possibleSpace){
        int indexOfSelected;
        int i = 0;
        Scanner sc = new Scanner(System.in);
        for(Space pos: possibleSpace){
            System.out.println("inserire il numero "+ i +" per compiere l'azione nella posizione: " + pos.getRow() + "-" + pos.getColumn());
            i++;
        }
        indexOfSelected = sc.nextInt();
        while(indexOfSelected < 0 || indexOfSelected > i){
            System.out.println("e' stata inserita una pos errata, inserire una posizione valida");
            indexOfSelected = sc.nextInt();
        }
        return possibleSpace.get(indexOfSelected);
    }

}
