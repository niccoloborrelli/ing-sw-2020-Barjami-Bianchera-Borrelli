package it.polimi.ingsw;

import javafx.scene.shape.MeshView;

public class Pawn{
    int idNumber;
    int playerNumber;
    MeshView workerMesh;

    public Pawn(MeshView workerMesh){
        this.workerMesh=workerMesh;
    }

    public int getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public MeshView getWorkerMesh() {
        return workerMesh;
    }

    public void setWorkerMesh(MeshView workerMesh) {
        this.workerMesh = workerMesh;
    }
}