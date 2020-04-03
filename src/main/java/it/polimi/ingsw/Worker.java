package it.polimi.ingsw;

public class Worker {
    private Player workerPlayer;
    private Space workerSpace;
    private boolean inGame; //solo per divinità con possibilità di eliminare workers

    public Player getWorkerPlayer() {
        return workerPlayer;
    }

    public Space getWorkerSpace() {
        return workerSpace;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setWorkerPlayer(Player workerPlayer) {
        this.workerPlayer = workerPlayer;
    }

    public void setWorkerSpace(Space workerSpace) {
        this.workerSpace = workerSpace;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

}
