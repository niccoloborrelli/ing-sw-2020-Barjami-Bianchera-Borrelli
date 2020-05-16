package it.polimi.ingsw;

public abstract class FlowChanger {

    /**
     * This method change the flow of the action to perform by a player
     */
    public abstract void changeFlow(Player player);

    /**
     * This method check if a power is usable by a player
     * @return true if usable, false otherwise
     */
    public abstract boolean isUsable(Player player);
}
