package it.polimi.ingsw;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ControllerModel {

    private GameModel gameModel;
    private ServerSocket serverSocket;

    public ControllerModel(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    public void game() throws IOException {
        gameSetting();
        ServerSocket sc = new ServerSocket(5050);
        Socket cl = sc.accept();
        Player activePlayer;
        activePlayer = gameModel.getPlayers().get(0);
        while (gameModel.isNobodyHasWon()) {

            if(activePlayer.isInGame()) {
                activePlayer.getWinCondition().checkHasWon(gameModel.getPlayers());
                if (!activePlayer.getWinCondition().gethasWon() && gameModel.canDoSomething(activePlayer)) {
                    round(activePlayer);
                }
                else if(activePlayer.getWinCondition().gethasWon())
                    gameModel.setNobodyHasWon(false);
            }
            activePlayer = setNextPlayer(gameModel.getPlayers().indexOf(activePlayer));

        }
        for (Player p: gameModel.getPlayers()) {
            ControllerUtility.communicate(p.getSocket(), "GAME OVER", 6);
        }
    }

    private void gameSetting() throws IOException {
        createPlayers();
        for (Player p: gameModel.getPlayers()) {
            gameModel.setupWorkers(p);
        }
        gameModel.setupConditions();
        //scegli divinità
    }

    private void createPlayers() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Select the number of players");
        int numberPlayers = sc.nextInt();
        createChallenger(sc);
        for (int i = 1; i < numberPlayers; i++) {
            System.out.println("Player number " + i + 1);
            gameModel.getPlayers().add(new Player(insertName()));
            gameModel.getPlayers().get(i).setPlayerColor(insertColor(sc));
        }
    }

    /**
     * This method asks the client his name, if it's equals to someone else's
     * then it repeats the question
     * @return the name after the control
     */
    private String insertName(/*Player player*/){
        String name;
        do {
            ControllerUtility.communicate(player.getSocket(), "Please insert your name", 2);
            name = ControllerUtility.getString(player.getSocket());
        } while (nameEquals(name));
        ControllerUtility(player.getSocket(), "", 5);
        return name;

    }

    /**
     * This method controls if the name send by the client is
     * already occupied
     * @param name is the name send by the client
     * @return true if occupied, otherwise false
     */
    private boolean nameEquals(String name) {
        for (Player p : gameModel.getPlayers()) {
            if (p.getPlayerName().equals(name))
                return true;
        }
        return false;
    }

    /**
     * This method asks the client his color, if it's equals to someone else's
     * then it repeats the question
     * @return the name after the control
     */
    private String insertColor(/*Player player*/){
        String color;
        do {
            ControllerUtility.communicate(player.getSocket(), "Please insert your color", 2);
            color = ControllerUtility.getString(player.getSocket());
        } while (colorEquals(color));
        ControllerUtility(player.getSocket(), "", 5);
        return color;
    }

    /**
     * This method controls if the color send by the client is
     * already occupied
     * @param color is the color send by the client
     * @return true if occupied, otherwise false
     */
    private boolean colorEquals(String color) {
        for (Player p : gameModel.getPlayers()) {
            if (p.getPlayerColor().equals(color))
                return true;
        }
        return false;
    }

    private void createChallenger(Scanner sc) {
        System.out.println("Challenger, please insert your name");
        Challenger gameChallenger = new Challenger((sc.nextLine()));
        System.out.println("Challenger, please insert your color");
        gameChallenger.setPlayerColor((sc.nextLine()));
        gameModel.getPlayers().add(gameChallenger);
    }


    private Player setNextPlayer(int index) {
        if (gameModel.getPlayers().size() - 1 != index)
            return gameModel.getPlayers().get(index + 1);
        return gameModel.getPlayers().get(0);
    }

    //check e restriction prima di richiesta worker??
    private void round(Player player) throws IOException {
        Worker worker;
        Space selectedPos;
        int startingPosLevel;

        do {
            ControllerUtility.communicate(player.getSocket(),
                        "Choose which worker you're going to move: (0 for the first, 1 for the second)", 4);
            worker = player.chooseWorker(ControllerUtility.getInt(player.getSocket()));
        } while(!worker.isInGame());
        ControllerUtility.communicate(player.getSocket(),"", 5);

        startingPosLevel = worker.getWorkerSpace().getLevel();
        // estrai la lista dei possibili movimenti dei worker
        player.getRestriction().restrictionEffectMovement(player, gameModel.getIslandBoard());
        List<Space> possibleMovements = gameModel.getIslandBoard().checkAvailableMovement(player)[player.getWorkers().indexOf(worker)];

        ControllerUtility.communicate(player.getSocket(), "MOVEMENT: ", 0);
        selectedPos = ControllerUtility.selectPos(possibleMovements, player);
        player.getMove().move(worker, selectedPos, gameModel.getIslandBoard());
        player.getWinCondition().checkHasWon(worker, startingPosLevel, gameModel.getIslandBoard());

        if (player.getWinCondition().gethasWon()) {
            gameModel.setNobodyHasWon(false);
            ControllerUtility.communicate(player.getSocket(), player.getPlayerName() + ", you WON!!", 0);
        }

        else {
            player.getRestriction().restrictionEffectBuilding(worker, gameModel.getIslandBoard());
            List<Space> possibleBuilding = gameModel.getIslandBoard().checkAvailableBuilding(player)[player.getWorkers().indexOf(worker)];
            if(possibleBuilding.size() == 0)
                gameModel.deletePlayer(player);
            else {
                ControllerUtility.communicate(player.getSocket(), "BUILDING: ", 0);
                selectedPos = ControllerUtility.selectPos(possibleBuilding, player);
                player.getBuild().build(worker, selectedPos, gameModel.getIslandBoard());
            }
        }
    }




}
