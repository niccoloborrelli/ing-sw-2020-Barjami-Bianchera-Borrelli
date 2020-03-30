package it.polimi.ingsw;

public abstract class AbstractBuild {

    private Player player;
    private Worker worker;

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

    public abstract boolean build(Worker worker, Space buildSpace);
}
