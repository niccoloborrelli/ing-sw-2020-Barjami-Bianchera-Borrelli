package it.polimi.ingsw;

import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.commons.io.FileUtils;



import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class GraphicInterface {
        private static final String sceneName="Santorini";
        private static final String cliffMeshString="Cliff.obj";
        private static final String islandsMeshString="Islands.obj";
        private static final String boardMeshString="Board.obj";
        private static final String seaMeshString="Sea.obj";
        private static final String innerWallsMeshString="InnerWalls.obj";
        private static final String outerWallsMeshString="OuterWall1.obj";
        private static final String maleBuilderMeshString="MaleBuilder.obj";
        private static final String femaleBuilderMeshString="FemaleBuilder_Blue.obj";
        private static final String domeMeshString="Dome.obj";
        private static final String buildingLevel2MeshString="BuildingBlock02.obj";
        private static final String buildingLevel1MeshString="BuildingBlock01.obj";
        private static final String buildingLevel3MeshString="BuildingBlock03.obj";
        private static final int CLIFFDIM=375;
        private static final int DECORATIONSDIM=50;
        private static final int ROWS=5;
        private static final int COLUMNS=5;
        private static final int FIRSTROWPOS=-250;
        private static final int FIRSTCOLUMNPOS=-250;
        private static final int XDISTANCE=122;
        private static final int ZDISTANCE=122;
        private static final double CAMERA_INITIAL_DISTANCE = -2000;
        private static final double CAMERA_NEAR_CLIP = 0.1;
        private static final double CAMERA_FAR_CLIP = 10000.0;
        public static MeshView tolto;
        final Group root = new Group();
        final XformWorld world = new XformWorld();
        final PerspectiveCamera camera = new PerspectiveCamera(true);
        final XformCamera cameraXform = new XformCamera();
        double mousePosX, mousePosY, mouseOldX, mouseOldY, mouseDeltaX, mouseDeltaY;
        double mouseFactorX, mouseFactorY;
        private Cell[][] grid;
        private BorderPane pane = new BorderPane();
        private SubScene mainSubScene;
        private SubScene bottomSubScene;
        private SubScene popUpSubScene;
        private SubScene sideSubScene;
        private Label popUpLabel;
        private Label bottomLabel;
        private boolean valuableId;
        private boolean firstHasAlreadyCome;
        private boolean setAllsWorker;
        private List <Pawn> pawns;
        private ToolBar powerToolbar;



    public void setCommandGUIManager(CommandGUIManager commandGUIManager) {
        this.commandGUIManager = commandGUIManager;
    }

    public boolean isSetAllsWorker() {
        return setAllsWorker;
    }

    private CommandGUIManager commandGUIManager;

    /**
     * Create the dynamic scenes with images and buttons
     */
    public void start(Stage primaryStage) {
        root.getChildren().add(world);
        root.setDepthTest(DepthTest.ENABLE);
        buildBodySystem();
        buildCamera();
        Scene scene = primaryStage.getScene();
        scene.setFill(javafx.scene.paint.Color.GREY);
        handleMouse(scene);
        primaryStage.setTitle(sceneName);
        primaryStage.getScene().setRoot(pane);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                //GeneralStringRequestCommand quitCommand=new GeneralStringRequestCommand("quit");
                //commandGUIManager.manageCommand(quitCommand);
                ExitCommand exitCommand = new ExitCommand();
                commandGUIManager.manageCommand(exitCommand);
                Platform.exit();
            }
        });
        mouseFactorX = 180.0 / scene.getWidth();
        mouseFactorY = 180.0 / scene.getHeight();
    }

    public void quitApplication(){
        Platform.exit();
    }

    public void printBottom(String bottomString){
        Platform.runLater(() -> {
            if(bottomLabel!=null)
                bottomLabel.setText(bottomString);
        });
    }

    public void clearBottomLabel(){
        bottomLabel.setText("");
    }

    /**
     * creates the main part of the scene( the 3d part, effectively the board)
     */
    private void createMainSubscene(){
                mainSubScene=new SubScene(root, 800,600 , true, SceneAntialiasing.BALANCED);
                pane.setTop(mainSubScene);
                mainSubScene.setManaged(false);
                mainSubScene.heightProperty().bind(pane.heightProperty());
                mainSubScene.widthProperty().bind(pane.widthProperty());
                mainSubScene.autosize();
                mainSubScene.setCamera(camera);
                mainSubScene.setFill(javafx.scene.paint.Color.GREY);
            }

    /**
     * creates the side subscuene (utility buttons and power activation)
     */
    private void createSideSubscene(){
            Button deselect=new Button("Deselect");
            deselect.setOnMouseClicked(e -> {
                DeselectWorkerRequestCommand deseselectCommnad=new DeselectWorkerRequestCommand();
                commandGUIManager.manageCommand(deseselectCommnad);
            });

            Button quit=new Button("QUIT");
            quit.setOnMouseClicked(e -> {
                GeneralStringRequestCommand quitCommand=new GeneralStringRequestCommand("quit");
                commandGUIManager.manageCommand(quitCommand);
            });

            Image image=new Image("AAA.png");
            ImageView imageView=new ImageView(image);
            Image image2=new Image("BBB.png");
            ImageView imageView2=new ImageView(image2);
            imageView.setFitHeight(image.getHeight()/3.5);
            imageView.setFitWidth(image.getWidth()/3.5);

            imageView2.setFitHeight(image2.getHeight()/3.5);
            imageView2.setFitWidth(image2.getWidth()/3.5);

            imageView.setOnMouseClicked(e -> {
                UsePowerRequestCommand powerUse=new UsePowerRequestCommand(1);
                commandGUIManager.manageCommand(powerUse);
                powerToolbar.setVisible(false);

            });
            imageView2.setOnMouseClicked(e -> {
                UsePowerRequestCommand powerUse=new UsePowerRequestCommand(0);
                commandGUIManager.manageCommand(powerUse);
                powerToolbar.setVisible(false);
            });


            powerToolbar=new ToolBar(imageView,imageView2);
            powerToolbar.setOrientation(Orientation.HORIZONTAL);
            ToolBar toolBar=new ToolBar(quit,deselect,powerToolbar);
            toolBar.setOrientation(Orientation.VERTICAL);
            powerToolbar.setVisible(false);
            sideSubScene=new SubScene(toolBar,100,100,true,SceneAntialiasing.BALANCED);
            pane.setRight(sideSubScene);

            sideSubScene.heightProperty().bind(pane.heightProperty().divide(1.12));
        }


    /**
     * creates the bottom subscene, for printing message from the server
     */
    private void createBottomSubscene(){
            bottomLabel=new Label();
            ToolBar toolBar2 = new ToolBar(bottomLabel);
            bottomSubScene=new SubScene(toolBar2,100,100,true,SceneAntialiasing.BALANCED);
            pane.setBottom(bottomSubScene);
            bottomSubScene.heightProperty().bind(pane.heightProperty().divide(9));
            bottomSubScene.widthProperty().bind(pane.widthProperty());
            bottomSubScene.setOpacity(0.4);
        }

    /**
     * creates a message popup
     */
    private void createPopUp(){
            Button button = new Button("OK");
            button.setTranslateX(35);
            popUpLabel=new Label("");
            ToolBar toolBar = new ToolBar(button, popUpLabel);
            toolBar.setOrientation(Orientation.VERTICAL);
            popUpSubScene=new SubScene(toolBar,100,100,true,SceneAntialiasing.BALANCED);
            popUpSubScene.setVisible(false);
            button.setOnMouseClicked(e -> {
                popUpSubScene.setVisible(false);
                mainSubScene.setOpacity(1);
                mainSubScene.setCamera(camera);
            });
            pane.setCenter(popUpSubScene);
            popUpSubScene.heightProperty().bind(pane.heightProperty().divide(7));
            popUpSubScene.widthProperty().bind(pane.widthProperty().divide(7));
        }

    /**
     * permits the set a string in the popoup
     * @param popUpString the string to be printed in the popup
     */
    public void printPopUp(String popUpString){
            mainSubScene.setOpacity(0.5);
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    popUpLabel.setText(popUpString);
                    popUpLabel.setCenterShape(true);
                    popUpSubScene.setVisible(true);
                }
            });
            Platform.runLater(t);
        }

    /**
     * builds the camera for the gui
     */
    private void buildCamera() {
            root.getChildren().add(cameraXform);
            cameraXform.getChildren().add(camera);
            camera.setNearClip(CAMERA_NEAR_CLIP);
            camera.setFarClip(CAMERA_FAR_CLIP);
            camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
        }

    /**
     * methon for the creation of a pawn
     */
    public void workerCreation(){
            Thread t =  new Thread((new Runnable() {
                @Override
                public void run() {
                    valuableId=true;
                    Pawn pawn;
                    if(!firstHasAlreadyCome){
                        pawns=new ArrayList<Pawn>();
                        pawn = createMaleWorker();
                        pawn.setIdNumber(0);
                        firstHasAlreadyCome=true;
                        positionMesh(750,-58,300,pawn.getWorkerMesh());
                    }
                    else {
                        pawn = createFemaleWorker();
                        pawn.setIdNumber(1);
                        positionMesh(-450,-80,750,pawn.getWorkerMesh());
                        setAllsWorker = true;
                    }
                    pawn.getWorkerMesh().setOnMouseClicked(mouseEvent -> {
                        SelectPawnRequestCommand selectPawnRequestCommand = new SelectPawnRequestCommand(pawn.getIdNumber());
                        SelectCellRequestCommand selectCellRequestCommand = new SelectCellRequestCommand(100,100);
                        commandGUIManager.selectAction(selectPawnRequestCommand,selectCellRequestCommand);
                    });
                    pawns.add(pawn);
                    world.getChildren().add(pawn.getWorkerMesh());
                }
            }));
        Platform.runLater(t);
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * method that permits to set the position of a mesh
     * @param x x coordinate position
     * @param y y coordinate position
     * @param z z coordinate position
     * @param meshView the mesh to be moved
     */
    private void positionMesh(int x,int y,int z,MeshView meshView){
        root.getChildren().add(meshView);
        meshView.setTranslateY(y);
        meshView.setTranslateX(x);
        meshView.setTranslateZ(z);
    }

    /**
     * shows the power toolbar for power activation
     */
    public void showPower(){
            powerToolbar.setVisible(true);
        }

    /**
     * permits to create a mesh using InteractiveMesh library object
     * @param importer the importer(interactiveMesh) for the .obj file
     * @param s the string for specifying wich object
     * @return
     */
    public MeshView createMesh(ObjModelImporter importer,String s){
        ClassLoader classLoader=ClassLoader.getSystemClassLoader();
        InputStream inputStream;
        File file1;
        MeshView[] tempMesh =null;
        importer.clear();
        inputStream = classLoader.getResourceAsStream(s);
        try {
            file1 = File.createTempFile("xxx", ".tmp");
            FileUtils.copyInputStreamToFile(inputStream, file1);
            importer.read(file1);
            tempMesh = importer.getImport();
            file1.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempMesh[0];
    }

    /**
     * creates the base game board without pawns in it
     */
    public void buildBoard(){
            //IMPORTS GRAPHICAL RESOURCES

            ObjModelImporter importer = new ObjModelImporter();

            MeshView cliff=createMesh(importer,cliffMeshString);

            MeshView islands=createMesh(importer,islandsMeshString);

            MeshView board=createMesh(importer,boardMeshString);

            MeshView sea=createMesh(importer,seaMeshString);;

            MeshView innerWalls=createMesh(importer,innerWallsMeshString);;

            MeshView outerWalls=createMesh(importer,outerWallsMeshString);;

            //ADJUST THE DIMENSION OF THE RESOURCES
            correctSize(outerWalls,DECORATIONSDIM,DECORATIONSDIM,DECORATIONSDIM);
            correctSize(innerWalls,DECORATIONSDIM,DECORATIONSDIM,DECORATIONSDIM);
            correctSize(sea,DECORATIONSDIM/2,DECORATIONSDIM/2,DECORATIONSDIM/2);
            correctSize(islands,DECORATIONSDIM,DECORATIONSDIM,DECORATIONSDIM);
            correctSize(cliff,CLIFFDIM,CLIFFDIM,CLIFFDIM);
            correctSize(board,DECORATIONSDIM,DECORATIONSDIM,DECORATIONSDIM);

            //SETS EVERYTHING IN POSITION
            outerWalls.setTranslateY(6);
            innerWalls.setTranslateY(6);
            sea.setTranslateY(130);
            islands.setTranslateY(30);
            cliff.setTranslateY(82);
            board.setTranslateY(3);

            //SETS MATERIALS FOR THE RESOURCES
            PhongMaterial materialOuterWall = new PhongMaterial();
            materialOuterWall.setDiffuseColor(javafx.scene.paint.Color.BEIGE);
            outerWalls.setMaterial(materialOuterWall);

            PhongMaterial materialInner = new PhongMaterial();
            materialInner.setDiffuseColor(javafx.scene.paint.Color.BEIGE);
            innerWalls.setMaterial(materialInner);

            PhongMaterial materialSea = new PhongMaterial();
            materialSea.setDiffuseColor(javafx.scene.paint.Color.rgb(99,190,255));
            sea.setMaterial(materialSea);

            PhongMaterial materialCliff = new PhongMaterial();
            materialCliff.setDiffuseColor(javafx.scene.paint.Color.SANDYBROWN);
            cliff.setMaterial(materialCliff);

            PhongMaterial materialBoard = new PhongMaterial();
            materialBoard.setDiffuseColor(javafx.scene.paint.Color.rgb(218,248,255));
            board.setMaterial(materialBoard);

            islands.setMaterial(materialCliff);

            //CREATES THE GRID
            buildGrid();

            //ADDS EVERYTHING TO TE ROOT
            world.getChildren().addAll(board);
            world.getChildren().add(islands);
            world.getChildren().add(cliff);
            world.getChildren().add(sea);
            world.getChildren().add(innerWalls);
            world.getChildren().add(outerWalls);
        }

    /**
     * permits to modify the dimension of a mesh
     * @param mesh the mesh to be resized
     * @param xScale x scale factor
     * @param yScale y scale factor
     * @param zScale z scale factor
     */
        private void correctSize(MeshView mesh,double xScale,double yScale,double zScale) {
            mesh.setScaleX(xScale);
            mesh.setScaleY(yScale);
            mesh.setScaleZ(zScale);
        }

    /**
     * build the grid, it is the effective board game with clickable objects
     */
    private void buildGrid(){
            grid=new Cell[ROWS][COLUMNS];
            for(int i=0;i<ROWS;i++){
                for(int j=0;j<COLUMNS;j++) {
                    grid[i][j] = new Cell(i,j,commandGUIManager);
                    correctCellPosition(grid[i][j], i, j);
                    world.getChildren().add(grid[i][j].getvBox());
                }
            }
        }

    /**
     * permits to reposition a cell in the grid
     * @param cell the cell to be positioned
     * @param row his row  in the grid
     * @param column his column in the grid
     */
        private void correctCellPosition(Cell cell,int row,int column){
            int totXPos=FIRSTROWPOS+row*XDISTANCE;
            int totZPos=FIRSTCOLUMNPOS+column*ZDISTANCE;
            cell.getvBox().setTranslateX(totXPos);
            cell.getvBox().setTranslateZ(totZPos);
        }

    /**
     * creates a pawn male mesh
     * @return  the pawn mesh
     */
    public Pawn createMaleWorker(){
            ObjModelImporter importer = new ObjModelImporter();
            importer.clear();
            MeshView tempMesh=createMesh(importer,maleBuilderMeshString);;
            correctSize(tempMesh,50,50,50);
            return new Pawn(tempMesh);
        }
    /**
     * creates a pawn male mesh
     * @return  the pawn mesh
     */
    public Pawn createFemaleWorker(){
        ObjModelImporter importer = new ObjModelImporter();
        importer.clear();
        MeshView tempMesh=createMesh(importer,femaleBuilderMeshString);;
        correctSize(tempMesh,50,50,50);
        return new Pawn(tempMesh);
    }

    /**
     * creates a building mesh for building type 1
     * @return the mesh created
     */
        public MeshView createBuildingType1(){
            ObjModelImporter importer = new ObjModelImporter();
            importer.clear();
            MeshView tempMesh=createMesh(importer,buildingLevel1MeshString);;
            correctSize(tempMesh,15.5,15.5,15.5);
            PhongMaterial materialBase = new PhongMaterial();
            materialBase.setDiffuseColor(javafx.scene.paint.Color.rgb(240,250,250));
            tempMesh.setMaterial(materialBase);
            return tempMesh;
        }
    /**
     * creates a dome mesh
     * @return the mesh created
     */
    public MeshView createDome(){
        ObjModelImporter importer = new ObjModelImporter();
        importer.clear();
        MeshView tempMesh=createMesh(importer,domeMeshString);;
        correctSize(tempMesh,15.5,15.5,15.5);
        PhongMaterial materialBase = new PhongMaterial();
        materialBase.setDiffuseColor(javafx.scene.paint.Color.BLUE);
        tempMesh.setMaterial(materialBase);
        return tempMesh;
    }

    /**
     * creates a building mesh for building type 2
     * @return the mesh created
     */
    public MeshView createBuildingType2(){
        ObjModelImporter importer = new ObjModelImporter();
        importer.clear();
        MeshView tempMesh=createMesh(importer,buildingLevel2MeshString);;
        correctSize(tempMesh,15.5,15.5,15.5);
        PhongMaterial materialBase = new PhongMaterial();
        materialBase.setDiffuseColor(javafx.scene.paint.Color.rgb(240,250,250));
        tempMesh.setMaterial(materialBase);
        return tempMesh;
    }

    /**
     * creates a building mesh for building type 2
     * @return the mesh created
     */
    public MeshView createBuildingType3(){
        ObjModelImporter importer = new ObjModelImporter();
        importer.clear();
        MeshView tempMesh=createMesh(importer,buildingLevel3MeshString);
        correctSize(tempMesh,15.5,15.5,15.5);
        PhongMaterial materialBase = new PhongMaterial();
        materialBase.setDiffuseColor(javafx.scene.paint.Color.rgb(240,250,250));
        tempMesh.setMaterial(materialBase);
        return tempMesh;
    }

    /**
     * build the scene in his totality
     */
    private void buildBodySystem(){
        buildBoard();
        createMainSubscene();
        createPopUp();
        createBottomSubscene();
        createSideSubscene();
    }

    /**
     * sets the mousehandling for the camera
     * @param scene scene in which we want to set the hanlding
     */
    private void handleMouse(Scene scene) {
        scene.setOnMousePressed((MouseEvent me) -> {
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseOldX = me.getSceneX();
            mouseOldY = me.getSceneY();
        });
        scene.setOnMouseDragged((MouseEvent me) -> {
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseDeltaX = (mousePosX - mouseOldX);
            mouseDeltaY = (mousePosY - mouseOldY);
            if (me.isPrimaryButtonDown()) {
                cameraXform.ry(mouseDeltaX * 180.0 / scene.getWidth());
                cameraXform.rx(-mouseDeltaY * 180.0 / scene.getHeight());
            } else if (me.isSecondaryButtonDown()) {
                camera.setTranslateZ(camera.getTranslateZ() + mouseDeltaY);
            }
        });
    }

    /**
     * permits to set a building type mesh in the grid
     * @param row row in the grid
     * @param column column in the grid
     * @param lvl level of the building
     * @param dome if has dome is 1
     */
    public void build(int row,int column,int lvl,int dome){
        Cell toBuild=grid[row][column];
        MeshView building;
        if(dome==1){
            building=createDome();
            toBuild.setDome(building);
        }
        else if(lvl==1){
            building=createBuildingType1();
            toBuild.setBuildingLvl1(building);
        }
        else if(lvl==2){
            building=createBuildingType2();
            toBuild.setBuildingLvl2(building);
        }
        else if(lvl==3){
            building=createBuildingType3();
            toBuild.setBuildingLvl3(building);
        }
        toBuild.resetPawn();
    }

    /**
     * permits to move a pawn mesh from an old position of the grid to a new
     * @param oldRow row of the grid of the old pos
     * @param oldColumn column of the grid of the old pos
     * @param newRow row of the grid in the new pos
     * @param newColumn column of the grid of the new pos
     * @param gender
     */
        public void move(int oldRow,int oldColumn,int newRow,int newColumn,String gender){
            Pawn pawn;
            Pawn pawn2;
            Cell oldPosition=grid[oldRow][oldColumn];
            Cell newPosition=grid[newRow][newColumn];
            pawn=oldPosition.removeWorker();
            pawn2=newPosition.removeWorker();
            newPosition.setWorker(pawn);
            oldPosition.setWorker(pawn2);

        }

    public void remove(int row, int column){
        grid[row][column].removeWorker();
    }

    /**
     * permits to set a still not existing worker in the board
     * @param row row to set the worker
     * @param column column to set the worker
     * @param color color for the worker
     * @param gender gender of the worker
     */
    public void setPawn(int row,int column,String color,String gender){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Pawn pawn=null;
                if(!valuableId) {
                    if (gender.equals("A"))
                        pawn = createFemaleWorker();
                    else if (gender.equals("B"))
                        pawn = createMaleWorker();
                    else return;
                    pawn.setIdNumber(100);
                }
                else {
                    if(gender.equals("A")) {
                        pawn = pawns.get(0);
                        pawns.remove(0);
                    }
                    else if(gender.equals("B")) {

                        if(pawns.size()>1) {
                            pawn = pawns.get(1);
                            pawns.remove(1);
                        }
                        else {
                            pawn = pawns.get(0);
                            pawns.remove(0);
                        }
                    }
                    //pawns.remove(pawn);
                    if(pawns.size()==0)
                        valuableId=false;
                }

                    positionMesh(0,0,0,pawn.getWorkerMesh());
                    pawn.getWorkerMesh().setMaterial(colorCreation(color));
                    Cell cell=grid[row][column];
                    cell.setWorker(pawn);
                }
            });
        }

    /**
     * creates a PhongMaterial with a specific color
     * @param color string of the color we want the PhongMaterial
     * @return the PhonMaterial with specified color
     */
    public PhongMaterial colorCreation(String color){
            PhongMaterial phongMaterial=new PhongMaterial();
            if(color.equals("green")){
                phongMaterial.setDiffuseColor(Color.LIGHTGREEN);
                return phongMaterial;
            }
            else if(color.equals(ColorConverter.ANSI_CYAN.escape())){
                phongMaterial.setDiffuseColor(Color.CYAN);
                return phongMaterial;
            }
            else if(color.equals(ColorConverter.ANSI_RED.escape())){
                phongMaterial.setDiffuseColor(Color.RED);
                return phongMaterial;
            }
            else if(color.equals(ColorConverter.ANSI_GREY.escape())){
                phongMaterial.setDiffuseColor(Color.GREY);
                return phongMaterial;
            }
            else if(color.equals(ColorConverter.ANSI_PURPLE.escape())){
                phongMaterial.setDiffuseColor(Color.PURPLE);
                return phongMaterial;
            }
            else if(color.equals(ColorConverter.ANSI_WHITE.escape())){
                phongMaterial.setDiffuseColor(Color.WHITE);
                return phongMaterial;
            }
            return phongMaterial;
        }

    /**
     * permits to color with green a specific cell
     * @param row the row of the cell in the grid
     * @param column the column of the cell in the grid
     */
    public void cellLightenUp(int row,int column){
            PhongMaterial phongMaterial=colorCreation("green");
            grid[row][column].lightenUp(phongMaterial);
        }

    /**
     * permits to switch off the green color from a cell and return it to his normal color
     * @param row row of the cell
     * @param column column of the cell
     */
    public void cellSwitchOff(int row,int column){
            PhongMaterial materialBase = new PhongMaterial();
            materialBase.setDiffuseColor(javafx.scene.paint.Color.rgb(240,250,250));
            grid[row][column].switchOff(materialBase);
        }

    /**
     * permits to reset the color of every cell in the board to his original color
     */
    public void resetColorBoard(){
            for(int i=0;i<ROWS;i++){
                for(int j=0;j<COLUMNS;j++)
                    cellSwitchOff(i,j);
            }
        }
}