package it.polimi.ingsw;

import javafx.application.Platform;
import javafx.scene.layout.VBox;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;

import static java.lang.Thread.sleep;

public class Cell {
    private VBox vBox;
    private Pawn worker;
    private MeshView buildingLvl1;
    private MeshView buildingLvl2;
    private MeshView buildingLvl3;
    private MeshView dome;
    private Box base;
    private PhongMaterial standardMaterial;
    private int row;
    private int column;
    private CommandGUIManager commandGUIManager;

    public Cell(int row,int column,CommandGUIManager commandGUIManager){
        this.commandGUIManager=commandGUIManager;
        this.row=row;
        this.column=column;
        vBox=new VBox();
        createBase();
        vBox.setSpacing(-45);
        vBox.setOnMouseClicked(mouseEvent -> {
            createCommand();
        });
    }

    private void createCommand(){
        SelectPawnRequestCommand selectPawnRequestCommand=null;
        if(worker!=null) {
            System.out.print("Hai toccato una cell con un worker sopra");
            selectPawnRequestCommand = new SelectPawnRequestCommand(worker.getIdNumber());
        }
        SelectCellRequestCommand selectCellRequestCommand=new SelectCellRequestCommand(row,column);
        commandGUIManager.selectAction(selectPawnRequestCommand,selectCellRequestCommand);
    }


    private void createBase(){
        base=new Box(100,1,100);
        standardMaterial=new PhongMaterial();
        standardMaterial.setDiffuseColor(javafx.scene.paint.Color.rgb(218,248,255));
        vBox.setTranslateY(-2);
        base.setMaterial(standardMaterial);
        vBox.getChildren().add(base);
        vBox.getChildren().get(0).setTranslateX(-50);
        vBox.getChildren().get(0);
    }

    public Pawn getWorker(){
        return worker;
    }

    public void setWorker(Pawn worker){
        this.worker=worker;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                if(worker!=null) {
                    MeshView meshWorker;
                    meshWorker = worker.getWorkerMesh();
                    vBox.getChildren().addAll(meshWorker);
                    if (buildingLvl3 != null) {
                        meshWorker.setTranslateY(-55);
                    } else if (buildingLvl2 != null) {
                        meshWorker.setTranslateY(-55);
                    } else if (buildingLvl1 != null) {
                        meshWorker.setTranslateY(-37);
                    } else if (buildingLvl1 == null) {
                        meshWorker.setTranslateY(-3);
                    }
                }
            }
        });
        Platform.runLater(t);

    }

    public void setBuildingLvl1(MeshView base){
        this.buildingLvl1=base;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().addAll(base);
            }
        });
    }

    public VBox getvBox() {
        return vBox;
    }

    public Pawn removeWorker(){
        Pawn temp=worker;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Dentro il thread di Remove Worker");
                if(worker!=null)
                    vBox.getChildren().remove(worker.getWorkerMesh());
                worker=null;
            }
        });
        Platform.runLater(t);
        return temp;
    }

    public void setBuildingLvl2(MeshView buildingLvl2) {
        this.buildingLvl2 = buildingLvl2;
        buildingLvl2.setTranslateY(-20);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().addAll(buildingLvl2);
            }
        });
    }

    public void setBuildingLvl3(MeshView buildingLvl3) {
        this.buildingLvl3 = buildingLvl3;
        buildingLvl3.setTranslateY(-30);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().addAll(buildingLvl3);
            }
        });
    }

    public void setDome(MeshView dome) {
        this.dome = dome;
        if(buildingLvl3!=null)
            dome.setTranslateY(-27);
        else if(buildingLvl2!=null)
            dome.setTranslateY(-28);
        else if(buildingLvl1!=null)
            dome.setTranslateY(-10);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().addAll(dome);
            }
        });
    }

    public MeshView getBuildingLvl1() {
        return buildingLvl1;
    }

    public MeshView getBuildingLvl2() {
        return buildingLvl2;
    }

    public MeshView getBuildingLvl3() {
        return buildingLvl3;
    }

    public MeshView getDome() {
        return dome;
    }

    public void resetPawn(){
        setWorker(removeWorker());
    }

    public Box getBase() {
        return base;
    }

    public void lightenUp(PhongMaterial lightenedUpMaterial){
        System.out.println("Sto accendendo la casella\n");
        base.setMaterial(lightenedUpMaterial);
        if(buildingLvl1!=null)
            buildingLvl1.setMaterial(lightenedUpMaterial);
        if(buildingLvl2!=null)
            buildingLvl2.setMaterial(lightenedUpMaterial);
        if(buildingLvl3!=null)
            buildingLvl3.setMaterial(lightenedUpMaterial);
    }

    public void switchOff(PhongMaterial materialBase){
        base.setMaterial(standardMaterial);
        if(buildingLvl1!=null)
            buildingLvl1.setMaterial(materialBase);
        if(buildingLvl2!=null)
            buildingLvl2.setMaterial(materialBase);
        if(buildingLvl3!=null)
            buildingLvl3.setMaterial(materialBase);
    }
}
