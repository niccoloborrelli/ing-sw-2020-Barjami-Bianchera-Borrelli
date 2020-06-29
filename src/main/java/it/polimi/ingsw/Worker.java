package it.polimi.ingsw;

import java.util.ArrayList;
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
    private boolean cantMoveFirstSpace;
    private boolean cantBuildFirstSpace;
    private boolean cantBuildPerimeter;
    private boolean cantBuildDome;
    private boolean cantBuildUnder;
    private boolean mustBuildDome;
    private boolean canBuildOnlyInTheFirstPlace;

    public Worker() {
        cantMoveUp=false;
        cantMove=false;
        cantMoveDown=false;
        chosen=false;
        cantBuild=true;
        inGame=true;
        movedThisTurn=false;
        cantBuildUnder=true;
        cantBuildDome=false;
        cantBuildPerimeter=false;
        possibleBuilding = new ArrayList<>();
        possibleMovements = new ArrayList<>();
        workerSpace = null;
        canBuildOnlyInTheFirstPlace = false;
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
        cantBuildDome=false;
        cantBuildPerimeter=false;
        cantBuildFirstSpace=false;
        cantMoveFirstSpace=false;
        mustBuildDome=false;
        canBuildOnlyInTheFirstPlace = false;
        clearLists();
    }

    public void clearLists(){
        possibleBuilding.clear();
        possibleMovements.clear();
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

    public boolean isCantMoveFirstSpace() {
        return cantMoveFirstSpace;
    }

    public void setCantMoveFirstSpace(boolean cantMoveFirstSpace) {
        this.cantMoveFirstSpace = cantMoveFirstSpace;
    }

    public boolean isCantBuildFirstSpace() {
        return cantBuildFirstSpace;
    }

    public void setCantBuildFirstSpace(boolean cantBuildFirstSpace) {
        this.cantBuildFirstSpace = cantBuildFirstSpace;
    }

    public boolean isCantBuildPerimeter() {
        return cantBuildPerimeter;
    }

    public void setCantBuildPerimeter(boolean cantBuildPerimeter) {
        this.cantBuildPerimeter = cantBuildPerimeter;
    }

    public boolean isCantBuildDome() {
        return cantBuildDome;
    }

    public void setCantBuildDome(boolean cantBuildDome) {
        this.cantBuildDome = cantBuildDome;
    }

    public boolean isCantBuildUnder() {
        return cantBuildUnder;
    }

    public void setCantBuildUnder(boolean cantBuildUnder) {
        this.cantBuildUnder = cantBuildUnder;
    }

    public boolean isCantPush(){
        return workerPlayer.isCantPush();
    }
    public boolean isCantSwap(){
        return workerPlayer.isCantSwap();
    }

    public String toString(){
        return workerPlayer.getPlayerName() + workerPlayer.getWorkers().indexOf(this);
    }

    public boolean isMustBuildDome() {
        return mustBuildDome;
    }

    public void setMustBuildDome(boolean mustBuildDome) {
        this.mustBuildDome = mustBuildDome;
    }

    public boolean isCanBuildOnlyInTheFirstPlace() {
        return canBuildOnlyInTheFirstPlace;
    }

    public void setCanBuildOnlyInTheFirstPlace(boolean canBuildOnlyInTheFirstPlace) {
        this.canBuildOnlyInTheFirstPlace = canBuildOnlyInTheFirstPlace;
    }
}

