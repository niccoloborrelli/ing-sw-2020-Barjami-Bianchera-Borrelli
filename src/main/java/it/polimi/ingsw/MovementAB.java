package it.polimi.ingsw;

public abstract class MovementAB {

    private Player player;
    private Worker worker;
    private GameModel game;
    private Space startSpace;

    public abstract boolean move(Worker worker, Space finishSpace);

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public GameModel getGame() {
        return game;
    }

    public void setGame(GameModel game) {
        this.game = game;
    }

    public Space getStartSpace() {
        return startSpace;
    }

    public void setStartSpace(Space startSpace) {
        this.startSpace = worker.getWorkerSpace();
    }
}
