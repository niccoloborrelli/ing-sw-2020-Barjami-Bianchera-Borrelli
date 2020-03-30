package it.polimi.ingsw;

public class Worker {
    private Player workerPlayer;
    private Space workerSpace;
    private boolean inGame;
    private boolean chosen;
    private boolean usable;

    public Player getWorkerPlayer() {
        return workerPlayer;
    }

    public Space getWorkerSpace() {
        return workerSpace;
    }

    public boolean isInGame() {
        return inGame;
    }

    public boolean isChosen() {
        return chosen;
    }

    public boolean isUsable() {
        return usable;
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

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }

    public void setUsable(boolean usable) {
        this.usable = usable;
    }
}
