package it.polimi.ingsw;

public abstract class RestrictionAB {
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public abstract void restrictionEffectMovement(Player player, IslandBoard islandBoard);
    public abstract void restrictionEffectBuilding(Worker worker, IslandBoard islandBoard);
}
