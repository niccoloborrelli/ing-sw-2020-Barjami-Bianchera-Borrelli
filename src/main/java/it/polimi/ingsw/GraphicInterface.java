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
        public static MeshView tolto;
        final Group root = new Group();
        final XformWorld world = new XformWorld();
        final PerspectiveCamera camera = new PerspectiveCamera(true);
        final XformCamera cameraXform = new XformCamera();
        private static final double CAMERA_INITIAL_DISTANCE = -2000;
        private static final double CAMERA_NEAR_CLIP = 0.1;
        private static final double CAMERA_FAR_CLIP = 10000.0;
        double mousePosX, mousePosY, mouseOldX, mouseOldY, mouseDeltaX, mouseDeltaY;
        double mouseFactorX, mouseFactorY;
        private static final int CLIFFDIM=375;
        private static final int DECORATIONSDIM=50;
        private static final int ROWS=5;
        private static final int COLUMNS=5;
        private static final int FIRSTROWPOS=-250;
        private static final int FIRSTCOLUMNPOS=-250;
        private static final int XDISTANCE=122;
        private static final int ZDISTANCE=122;
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

        public void start(Stage primaryStage) {
            root.getChildren().add(world);
            root.setDepthTest(DepthTest.ENABLE);
            buildBodySystem();
            buildCamera();
            Scene scene = primaryStage.getScene();
            scene.setFill(javafx.scene.paint.Color.GREY);
            handleMouse(scene);
            primaryStage.setTitle("Santorini");
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

        private void createBottomSubscene(){
            bottomLabel=new Label();
            ToolBar toolBar2 = new ToolBar(bottomLabel);
            bottomSubScene=new SubScene(toolBar2,100,100,true,SceneAntialiasing.BALANCED);
            pane.setBottom(bottomSubScene);
            bottomSubScene.heightProperty().bind(pane.heightProperty().divide(9));
            bottomSubScene.widthProperty().bind(pane.widthProperty());
            bottomSubScene.setOpacity(0.4);
        }

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

        private void buildCamera() {
            root.getChildren().add(cameraXform);
            cameraXform.getChildren().add(camera);
            camera.setNearClip(CAMERA_NEAR_CLIP);
            camera.setFarClip(CAMERA_FAR_CLIP);
            camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
        }

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
    private void positionMesh(int x,int y,int z,MeshView meshView){
        root.getChildren().add(meshView);
        meshView.setTranslateY(y);
        meshView.setTranslateX(x);
        meshView.setTranslateZ(z);
    }
    public void showPower(){
            powerToolbar.setVisible(true);
        }

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

    public void buildBoard(){
            //IMPORTS GRAPHICAL RESOURCES


            ObjModelImporter importer = new ObjModelImporter();

            MeshView cliff=createMesh(importer,"Cliff.obj");

            MeshView islands=createMesh(importer,"Islands.obj");

            MeshView board=createMesh(importer,"Board.obj");

            MeshView sea=createMesh(importer,"Sea.obj");;

            MeshView innerWalls=createMesh(importer,"InnerWalls.obj");;

            MeshView outerWalls=createMesh(importer,"OuterWall1.obj");;

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

        private void correctSize(MeshView mesh,double xScale,double yScale,double zScale) {
            mesh.setScaleX(xScale);
            mesh.setScaleY(yScale);
            mesh.setScaleZ(zScale);
        }

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

        private void correctCellPosition(Cell cell,int row,int column){
            int totXPos=FIRSTROWPOS+row*XDISTANCE;
            int totZPos=FIRSTCOLUMNPOS+column*ZDISTANCE;
            cell.getvBox().setTranslateX(totXPos);
            cell.getvBox().setTranslateZ(totZPos);
        }

        public Pawn createMaleWorker(){
            ObjModelImporter importer = new ObjModelImporter();
            importer.clear();
            MeshView tempMesh=createMesh(importer,"MaleBuilder.obj");;
            correctSize(tempMesh,50,50,50);
            return new Pawn(tempMesh);
        }

        public Pawn createFemaleWorker(){
            ObjModelImporter importer = new ObjModelImporter();
            importer.clear();
            MeshView tempMesh=createMesh(importer,"FemaleBuilder_Blue.obj");;
            correctSize(tempMesh,50,50,50);
            return new Pawn(tempMesh);
        }

        public MeshView createBuildingType1(){
            ObjModelImporter importer = new ObjModelImporter();
            importer.clear();
            MeshView tempMesh=createMesh(importer,"BuildingBlock01.obj");;
            correctSize(tempMesh,15.5,15.5,15.5);
            PhongMaterial materialBase = new PhongMaterial();
            materialBase.setDiffuseColor(javafx.scene.paint.Color.rgb(240,250,250));
            tempMesh.setMaterial(materialBase);
            return tempMesh;
        }
        public MeshView createDome(){
            ObjModelImporter importer = new ObjModelImporter();
            importer.clear();
            MeshView tempMesh=createMesh(importer,"Dome.obj");;
            correctSize(tempMesh,15.5,15.5,15.5);
            PhongMaterial materialBase = new PhongMaterial();
            materialBase.setDiffuseColor(javafx.scene.paint.Color.BLUE);
            tempMesh.setMaterial(materialBase);
            return tempMesh;
        }

        public MeshView createBuildingType2(){
            ObjModelImporter importer = new ObjModelImporter();
            importer.clear();
            MeshView tempMesh=createMesh(importer,"BuildingBlock02.obj");;
            correctSize(tempMesh,15.5,15.5,15.5);
            PhongMaterial materialBase = new PhongMaterial();
            materialBase.setDiffuseColor(javafx.scene.paint.Color.rgb(240,250,250));
            tempMesh.setMaterial(materialBase);
            return tempMesh;
        }

        public MeshView createBuildingType3(){
            ObjModelImporter importer = new ObjModelImporter();
            importer.clear();
            MeshView tempMesh=createMesh(importer,"BuildingBlock03.obj");
            correctSize(tempMesh,15.5,15.5,15.5);
            PhongMaterial materialBase = new PhongMaterial();
            materialBase.setDiffuseColor(javafx.scene.paint.Color.rgb(240,250,250));
            tempMesh.setMaterial(materialBase);
            return tempMesh;
        }

        private void buildBodySystem(){
            buildBoard();
            createMainSubscene();
            createPopUp();
            createBottomSubscene();
            createSideSubscene();
        }

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

        public void setPawn(int row,int column,String color,String gender){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Pawn pawn;
                    if(!valuableId) {
                        if (gender.equals("A"))
                            pawn = createFemaleWorker();
                        else if (gender.equals("B"))
                            pawn = createMaleWorker();
                        else return;
                        pawn.setIdNumber(100);
                    }
                    else {
                        pawn = pawns.get(0);
                        pawns.remove(0);
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

        public void cellLightenUp(int row,int column){
            PhongMaterial phongMaterial=colorCreation("green");
            grid[row][column].lightenUp(phongMaterial);
        }

        public void cellSwitchOff(int row,int column){
            PhongMaterial materialBase = new PhongMaterial();
            materialBase.setDiffuseColor(javafx.scene.paint.Color.rgb(240,250,250));
            grid[row][column].switchOff(materialBase);
        }

        public void resetColorBoard(){
            for(int i=0;i<ROWS;i++){
                for(int j=0;j<COLUMNS;j++)
                    cellSwitchOff(i,j);
            }
        }
}
