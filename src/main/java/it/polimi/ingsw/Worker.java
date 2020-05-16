package it.polimi.ingsw;

import java.util.List;

public class Worker {
    private boolean cantMoveUp;
    private boolean cantMove;
    private boolean cantMoveDown;
    private boolean chosen;
    private boolean cantBuild;
    private Player workerPlayer;
    private Space workerSpace;
    private boolean inGame;
    private boolean movedThisTurn;
    private Space lastSpaceOccupied;
    private Space lastSpaceBuilt;
    private List<Space> possibleMovements;
    private List<Space> possibleBuilding;

    public Worker() {
        cantMoveUp=false;
        cantMove=false;
        cantMoveDown=false;
        chosen=false;
        cantBuild=true;
        inGame=true;
        movedThisTurn=false;
    }

    //metodo da richiamare alla fine di ogni turno su worker
    public void resetWorker(){
        cantMoveUp=false;
        cantMove=false;
        cantMoveDown=false;
        chosen=false;
        cantBuild=true;
        inGame=true;
        movedThisTurn=false;
    }

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

    public boolean isCantMoveUp() {
        return cantMoveUp;
    }

    public void setCantMoveUp(boolean cantMoveUp) {
        this.cantMoveUp = cantMoveUp;
    }

    public boolean isCantMove() {
        return cantMove;
    }

    public void setCantMove(boolean cantMove) {
        this.cantMove = cantMove;
    }

    public boolean isCantMoveDown() {
        return cantMoveDown;
    }

    public void setCantMoveDown(boolean cantMoveDown) {
        this.cantMoveDown = cantMoveDown;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }

    public boolean isCantBuild() {
        return cantBuild;
    }

    public void setCantBuild(boolean canBuild) {
        this.cantBuild = canBuild;
    }

    public boolean isMovedThisTurn() {
        return movedThisTurn;
    }

    public void setMovedThisTurn(boolean movedThisTurn) {
        this.movedThisTurn = movedThisTurn;
    }

    public Space getLastSpaceOccupied() {
        return lastSpaceOccupied;
    }

    public void setLastSpaceOccupied(Space lastSpaceOccupied) {
        this.lastSpaceOccupied = lastSpaceOccupied;
    }

    public Space getLastSpaceBuilt() {
        return lastSpaceBuilt;
    }

    public void setLastSpaceBuilt(Space lastSpaceBuilt) {
        this.lastSpaceBuilt = lastSpaceBuilt;
    }

    public List<Space> getPossibleMovements() {
        return possibleMovements;
    }

    public void setPossibleMovements(List<Space> possibleMovements) {
        this.possibleMovements = possibleMovements;
    }

    public List<Space> getPossibleBuilding() {
        return possibleBuilding;
    }

    public void setPossibleBuilding(List<Space> possibleBuilding) {
        this.possibleBuilding = possibleBuilding;
    }
}

