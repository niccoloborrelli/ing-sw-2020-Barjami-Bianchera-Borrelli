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

    /**
     * creates the command for command pattern
     */
    private void createCommand(){
        SelectPawnRequestCommand selectPawnRequestCommand=null;
        if(worker!=null) {
            selectPawnRequestCommand = new SelectPawnRequestCommand(worker.getIdNumber());
        }
        SelectCellRequestCommand selectCellRequestCommand=new SelectCellRequestCommand(row,column);
        commandGUIManager.selectAction(selectPawnRequestCommand,selectCellRequestCommand);
    }


    /**
     * creates the base box of the cell
     */
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

    /**
     * sets a pawn over this cell
     * @param pawn the pawn to be setted
     */
    public void setWorker(Pawn pawn) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                if (pawn != null) {
                    worker = pawn;
                    MeshView meshWorker;
                    meshWorker = pawn.getWorkerMesh();
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

    public VBox getvBox() {
        return vBox;
    }

    /**
     * removes the pawn, if it exists, from the cell
     * @return the pawn removed
     */
    public Pawn removeWorker(){
        Pawn temp=worker;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                if(worker!=null)
                    vBox.getChildren().remove(worker.getWorkerMesh());
                worker=null;
            }
        });
        Platform.runLater(t);
        return temp;
    }


    /**
     * sets a building of type 1
     * @param base the base over which
     */
    public void setBuildingLvl1(MeshView base){
        this.buildingLvl1=base;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().addAll(base);
            }
        });
    }

    /**
     * sets a building of type 2
     * @param buildingLvl2 the building to be set
     */
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

    /**
     * sets a building of type 3
     * @param buildingLvl3 the building to be set
     */
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

    /**
     * sets a dome on the cell
     * @param dome the dome to be set
     */
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


    public MeshView getDome() {
        return dome;
    }

    public void resetPawn(){
        setWorker(removeWorker());
    }

    /**
     * colors the entire cell with the phong material
     * @param lightenedUpMaterial the pongMaterial decoring the cell
     */
    public void lightenUp(PhongMaterial lightenedUpMaterial){
        base.setMaterial(lightenedUpMaterial);
        if(buildingLvl1!=null)
            buildingLvl1.setMaterial(lightenedUpMaterial);
        if(buildingLvl2!=null)
            buildingLvl2.setMaterial(lightenedUpMaterial);
        if(buildingLvl3!=null)
            buildingLvl3.setMaterial(lightenedUpMaterial);
    }

    /**
     * sets the phonMatiral of the cell to his original PhongMaterial
     * @param materialBase the orignal Phongmaterial
     */
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
