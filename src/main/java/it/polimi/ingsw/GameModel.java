package it.polimi.ingsw;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.Socket;
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
    private int numberOfPlayers;
    private static final int beginofAll = 0;
    private static final int rows = 5;
    private static final int columns = 5;
    private int numberPlayingPlayers; //quanti giocatori stanno ancora giocando = giocatori totali - giocatori eliminati

    public GameModel(List<String> playersName,Controller controller) {
        players = new ArrayList<Player>();
        this.islandBoard = new IslandBoard(controller);
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

    /*
    MAIN
     */
    public void game() throws IOException {
        createPlayers(); //creo giocatori: sockets,nomi e colori
        godSetting(); // assegno le divinita ai giocatori con i rispettivi decorator
        boardSetting(); //inizializzo posizione iniziale degli worker (partendo dal challenger)
        numberPlayingPlayers=numberOfPlayers;

        Player activePlayer=players.get(0); // il primo turno e' quello del giocatore iniziale (challenger)
        while (nobodyHasWon&&numberPlayingPlayers>1){  // la partita continua finche' c'e' piu' di un giocatore che sta ancora giocando
            if(activePlayer.isInGame())
                round(activePlayer);
            activePlayer=setNextPlayer(players.indexOf(activePlayer)); //seleziono il prossimo giocatore che deve giocare
        }

        if(numberOfPlayers==1){ //se entro qui' e' perche' la partita e' finita essendo rimasto un unico giocatore in gioco, lo cerco e gli comunico che ha vinto
            nobodyHasWon=false;
            for(Player p:players)
                if(p.isInGame())
                    p.getWinCondition().setHasWon(true);
        }

        for (Player player:players) { //notifico vincitori e perdenti alla fine 
            if(player.getWinCondition().gethasWon()==true)
                islandBoard.getController().notifyWin(player.getSocket());
            else
                islandBoard.getController().notifyLost(player.getSocket());
        }
    }



    public void createPlayers(){
        List <String> unavailableNames=new ArrayList<String>();
        List <String> availableColors;
        Socket challengerSocket = islandBoard.getController().requiredChallengerSocket();
        String challengerName= islandBoard.getController().requiredName(challengerSocket,"Challenger, insert your name",new ArrayList<String>());
        gameChallenger=new Challenger(challengerSocket);
        gameChallenger.setPlayerName(challengerName);
        unavailableNames.add(challengerName);
        players.add(gameChallenger);

        List <Integer> possibleNumOfPlayers=new ArrayList<Integer>();
        // il numero di giocatori puo' essere di 2 o 3 o 4
        possibleNumOfPlayers.add(2);
        possibleNumOfPlayers.add(3);
        possibleNumOfPlayers.add(4);
        numberOfPlayers=islandBoard.getController().requiredInt(challengerSocket,"Insert the number of players",possibleNumOfPlayers);
        // a questo punto ho creato il socket del challenger col suo nome e ho selezionato il numero di giocatori per la partita
        List <Socket> sockets=islandBoard.getController().requiredSocket(numberOfPlayers);
        for(Socket s:sockets){
            players.add(new Player(s));
        }
        setPlayersNames(unavailableNames);
        setPlayersColors();
    }

    private void setPlayersNames(List <String> unavailableNames){
        for(int i=1;i<numberOfPlayers;i++){
            String name=islandBoard.getController().requiredName(players.get(i).getSocket(),"Insert your name",unavailableNames):
            unavailableNames.add(name);
            players.get(i).setPlayerName(name);
        }
    }

    private void setPlayersColors(){
        List <String> colors=new ArrayList<String>();
        colors.add("color1");
        colors.add("color2");
        colors.add("color3");
        colors.add("color4");
        int i;
        for (Player p: players) {
            String message=new String("Chose one of these still available colors: ");
            i=0;
            for (String s: colors) {
                message.concat(s+" - ");
            }
            String chosenColor=islandBoard.getController().requiredString(p.getSocket(),message,colors);
            colors.remove(chosenColor);
            p.setPlayerColor(chosenColor);
        }
    }


    public void godSetting(){
        boolean existsAthena=false;
        List <String> availableGods=godSetBuild();
        if(availableGods.contains("athena"))
            existsAthena=true;
        //playerGod in player e' una stringa
        godAssignation(availableGods); //assegno i god(stringhe) a ogni giocatore
        decoratePlayers(existsAthena); //qui creo effettivamente i decorator
    }

    // CREO IL GOD SET, VA FINITO, NON RICORDO COME AVEVAMO SCELTO DI GESTIRLO
    private List <String> godSetBuild(){

    }

    private void godAssignation(List <String> availableGods){
        for(int i=1;i<numberOfPlayers;i++){
            String message=new String("Choose one of these gods:");
            for (String s:availableGods) {
                message.concat(" "+s+" -");
            }
            String god=islandBoard.getController().requiredString(players.get(i).getSocket(),message,availableGods);
            players.get(i).setPlayerGod(god);
            availableGods.remove(god);
        }
        //DO A CHALLENGER IL GOD RIMASTO
        gameChallenger.setPlayerGod(availableGods.get(0));
        //Notifica al challenger quale god gli e' stato assegnato
        islandBoard.getController().notifyRemainedGod(gameChallenger,"You have been assigned "+availableGods.get(0));
    }

    private void decoratePlayers(boolean existsAthena){
        //Se c'e' athena decoro la restriction piu' internamente con essa
        if(existsAthena==true){
            for (Player p:players)
                if(!p.getPlayerGod().equals("athena"))
                    p.setRestriction(new ConcreteAthenaRestriction(p.getRestriction()));
        }

        for (Player p: players) {
            try {
                Method metodo = GodFactory.class.getMethod(p.getPlayerGod(), Player.class);
                //richiamo il metodo di GodFactory con il nome della divinita' di p
                metodo.invoke(new GodFactory(),p);
            }
            catch (Exception e) {
                System.out.println("error");
            }
        }
    }



    public void boardSetting() {
        for (Player activePlayer: players) {
            setupWorkers(activePlayer);
        }
    }

    /*
    Posiziona i worker inizialmente
     */
    private void setupWorkers(Player activePlayer) {
        for (Worker worker:activePlayer.getWorkers()) {
            int pos[]=islandBoard.getController().requiredPosition(activePlayer.getSocket(),"Choose the setting position for the worker number "+activePlayer.getWorkers().indexOf(worker)+1,getBoardRepresentetion());
            islandBoard.getSpace(pos[0],pos[1]).setOccupator(worker);  //inserisco nella posizione ricevuta worker
            worker.setWorkerSpace(islandBoard.getSpace(pos[0],pos[1]));  //attribuisco a worker il spazio in posizione ricevuta
        }
    }

    //restituisce una rappresentazione matriciale della board mettendo a 0 gli spazi occupati e a 1 quelli non occupati
    public int[][] getBoardRepresentetion(){
        int [][]matrixBoard=new int[rows][columns];
        for(int i=0;i<rows;i++){
            for(int j=0;j<columns;j++)
                if(islandBoard.getSpace(i,j).getOccupator()==null)
                    matrixBoard[i][j]=0;
                else
                    matrixBoard[i][j]=1;
        }
        return matrixBoard;
    }




    private Player setNextPlayer(int index) {
        if (players.size() - 1 != index)
            return players.get(index + 1);
        return players.get(beginofAll);
    }

    private void round(Player activePlayer) throws IOException {
        Worker movedWorker=movementPhase(activePlayer);
        if(movedWorker!=null&&!activePlayer.getWinCondition().gethasWon()){//se il giocatore non ha perso oppure non ha vinto
            //eseguo la buildphase sul moved worker
            buildingPhase(movedWorker);
        }
    }

    //permette il movimento di un worker, ritorna il worker mosso, ritorna null se non ci sono piu' possibili movimenti
    public Worker movementPhase(Player activePlayer) throws IOException {
        int indexOfWorker;
        activePlayer.getRestriction().restrictionEffectMovement(activePlayer,islandBoard);
        List <Space> [] possibleMovements=islandBoard.checkAvailableMovement(activePlayer);
        if(possibleMovements[0].size()==0&&possibleMovements[1].size()==0){ //se entra qua il player non ha movimenti percio' ha perso
            deletePlayer(activePlayer);
            return null;
        }
        indexOfWorker=workerToMove(activePlayer,possibleMovements);
        Worker movingWorker=activePlayer.getWorkers().get(indexOfWorker);
        Space finishPosition=getMovementPosition(movingWorker,possibleMovements[indexOfWorker]);
        Space startPosition=movingWorker.getWorkerSpace();
        activePlayer.getMove().move(movingWorker,finishPosition,islandBoard); //eseguo effettiva move
        islandBoard.getController().notifyMovement(startPosition,finishPosition); //notifico l'avvenuto movimento, non so se serve
        activePlayer.getWinCondition().checkHasWon(movingWorker,startPosition.getLevel());
        if(activePlayer.getWinCondition().gethasWon())
            nobodyHasWon=false;
        return movingWorker;
    }

    public int workerToMove(Player activePlayer,List <Space> []possibleMovements){ //ritorna l'indice del worker che si vuole muovere
        if(possibleMovements[0].size()==0) { //se entro qui posso usare solo il worker 2
            islandBoard.getController().notifyString(activePlayer.getSocket(), "the only movable worker is the 2, you have to use it");
            return 0;
        }
        else if (possibleMovements[1].size() == 0) {//se entro qui posso usare solo il worker 1
            islandBoard.getController().notifyString(activePlayer.getSocket(), "the only movable worker is the 1, you have to use it");
            return 1;
        }
        else{ //se entro qui posso usare entrambi i worker
            List <Integer> possibleWorkers=new ArrayList<Integer>();
            possibleWorkers.add(1);
            possibleWorkers.add(2);
            return islandBoard.getController().requiredInt(activePlayer.getSocket(),"Both you workers are movable, choose wich one you want to move",possibleWorkers);
        }
    }

    public Space getMovementPosition(Worker movingWorker,List <Space> possibleMovements){
        String messaggio=new String("Write: \n");
        int i=0;
        List <Integer> possibleInt=new ArrayList<Integer>();
        for (Space space:possibleMovements) {
            messaggio.concat(i+" to go in position "+space.getRow()+"/"+space.getColumn()+"\n");
            possibleInt.add(i);
            i++;
        }
        int choosenIndex=islandBoard.getController().requiredInt(movingWorker.getWorkerPlayer().getSocket(),messaggio,possibleInt);
        return possibleMovements.get(choosenIndex);
    }


    public void buildingPhase(Worker movedWorker){ //ritorna lo spazio in cui si ha costruito, null altrimenti
        movedWorker.getWorkerPlayer().getRestriction().restrictionEffectBuilding(movedWorker,islandBoard);
        //ho la lista delle possibili costruzioni del worker mosso in precedenza
        List <Space> possibleBuilding=islandBoard.checkAvailableBuilding(movedWorker.getWorkerPlayer())[movedWorker.getWorkerPlayer().getWorkers().indexOf(movedWorker)];
        if(possibleBuilding.size()==0){ //se qui dentro sono andato in una posizione che fa perdere il player
            deletePlayer(movedWorker.getWorkerPlayer());
            return ;
        }
        Space spaceToBuild=getBuildingPosition(movedWorker,possibleBuilding);
        movedWorker.getWorkerPlayer().getBuild().build(movedWorker,spaceToBuild);
        islandBoard.getController().notifyBuilding(spaceToBuild); //notifico l'avvenuta costruzione, non so se serve
        return ;
    }

    public Space getBuildingPosition(Worker movedWorker,List <Space> possibleBuilding){
        String messaggio=new String("Write: \n");
        int i=0;
        List <Integer> possibleInt=new ArrayList<Integer>();
        for (Space space:possibleBuilding) {
            messaggio.concat(i+" to build in position "+space.getRow()+"/"+space.getColumn()+"\n");
            possibleInt.add(i);
            i++;
        }
        int choosenIndex=islandBoard.getController().requiredInt(movedWorker.getWorkerPlayer().getSocket(),messaggio,possibleInt);
        return possibleBuilding.get(choosenIndex);
    }

    /*
    setta le posizioni dei workers a null
     */
    private void deletePlayer(Player player){
            player.setInGame(false);
            numberPlayingPlayers--;
            player.getWorkers().get(0).getWorkerSpace().setOccupator(null);
            player.getWorkers().get(1).getWorkerSpace().setOccupator(null);
            player.getWorkers().get(0).setWorkerSpace(null);
            player.getWorkers().get(1).setWorkerSpace(null);
            islandBoard.getController().notifyLost(player.getSocket());
    }
}