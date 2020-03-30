package it.polimi.ingsw;

public class God {
    private String godName;
    private boolean powerUsed;
    private String godPower;

    public String getGodName() {
        return godName;
    }

    public boolean isPowerUsed() {
        return powerUsed;
    }

    public String getGodPower() {
        return godPower;
    }

    public void setGodName(String godName) {
        this.godName = godName;
    }

    public void setPowerUsed(boolean powerUsed) {
        this.powerUsed = powerUsed;
    }

    public void setGodPower(String godPower) {
        this.godPower = godPower;
    }
}
