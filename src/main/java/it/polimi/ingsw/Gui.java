package it.polimi.ingsw;

import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.io.File;

    public class Gui extends Application {
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


        @Override
        public void start(Stage primaryStage) {
            root.getChildren().add(world);
            root.setDepthTest(DepthTest.ENABLE);
            buildBodySystem();

            buildCamera();

            BorderPane pane = new BorderPane();
            SubScene subScene=new SubScene(root, 800,600 , true, SceneAntialiasing.BALANCED);
            pane.setCenter(subScene);

            Button button = new Button("Reset");
            CheckBox checkBox = new CheckBox("Line");
            ToolBar toolBar = new ToolBar(button, checkBox);
            toolBar.setOrientation(Orientation.VERTICAL);
            pane.setRight(toolBar);
            pane.autosize();
            pane.setPrefSize(300,300);

            Menu menu = new Menu("File");
            menu.getItems().add(new MenuItem("New"));
            menu.getItems().add(new MenuItem("Save"));
            menu.getItems().add(new SeparatorMenuItem());
            menu.getItems().add(new MenuItem("Exit"));


            //menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
            Scene scene = new Scene(pane,800,600,true);
            subScene.setManaged(false);
            subScene.heightProperty().bind(pane.heightProperty());
            subScene.widthProperty().bind(pane.widthProperty());

            subScene.autosize();
            scene.setFill(javafx.scene.paint.Color.GREY);
            subScene.setCamera(camera);
            subScene.setFill(javafx.scene.paint.Color.GREY);
            handleMouse(scene);
            primaryStage.setTitle("Testerello");
            primaryStage.setScene(scene);
            primaryStage.show();
            mouseFactorX = 180.0 / scene.getWidth();
            mouseFactorY = 180.0 / scene.getHeight();
        }

        private void buildCamera() {
            root.getChildren().add(cameraXform);
            cameraXform.getChildren().add(camera);
            camera.setNearClip(CAMERA_NEAR_CLIP);
            camera.setFarClip(CAMERA_FAR_CLIP);
            camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
        }

        public void buildBoard(){
            //IMPORTS GRAPHICAL RESOURCES
            ObjModelImporter importer = new ObjModelImporter();
            importer.clear();
            importer.read("C:\\Users\\Rei\\IdeaProjects\\hello\\src\\main\\resources\\Cliff.obj");
            MeshView[] tempMesh = importer.getImport();
            MeshView cliff=tempMesh[0];

            importer.clear();
            importer.read("C:\\Users\\Rei\\IdeaProjects\\hello\\src\\main\\resources\\Islands.obj");
            tempMesh = importer.getImport();
            MeshView islands=tempMesh[0];

            importer.clear();
            importer.read("C:\\Users\\Rei\\IdeaProjects\\hello\\src\\main\\resources\\Board.obj");
            tempMesh = importer.getImport();
            MeshView board=tempMesh[0];

            importer.clear();
            importer.read("C:\\Users\\Rei\\IdeaProjects\\hello\\src\\main\\resources\\Sea.obj");
            tempMesh = importer.getImport();
            MeshView sea=tempMesh[0];

            importer.clear();
            importer.read("C:\\Users\\Rei\\IdeaProjects\\hello\\src\\main\\resources\\InnerWalls.obj");
            tempMesh = importer.getImport();
            MeshView innerWalls=tempMesh[0];

            importer.clear();
            importer.read("C:\\Users\\Rei\\IdeaProjects\\hello\\src\\main\\resources\\OuterWall1.obj");
            tempMesh = importer.getImport();
            MeshView outerWalls=tempMesh[0];

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
                    grid[i][j] = new Cell();
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
            File file = new File("C:\\Users\\Rei\\IdeaProjects\\hello\\src\\main\\resources\\MaleBuilder.obj");
            importer.read(file);
            MeshView[] temp = importer.getImport();
            MeshView tempMesh=temp[0];
            correctSize(tempMesh,50,50,50);
            return new Pawn(tempMesh);
        }

        public Pawn createFemaleWorker(){
            ObjModelImporter importer = new ObjModelImporter();
            importer.clear();
            importer.read("C:\\Users\\Rei\\IdeaProjects\\hello\\src\\main\\resources\\FemaleBuilder_Blue.obj");
            MeshView[] temp = importer.getImport();
            MeshView tempMesh=temp[0];
            correctSize(tempMesh,50,50,50);
            return new Pawn(tempMesh);
        }

        public MeshView createBuildingType1(){
            ObjModelImporter importer = new ObjModelImporter();
            importer.clear();
            importer.read("C:\\Users\\Rei\\IdeaProjects\\hello\\src\\main\\resources\\BuildingBlock01.obj");
            MeshView[] temp = importer.getImport();
            MeshView tempMesh=temp[0];
            correctSize(tempMesh,15.5,15.5,15.5);
            PhongMaterial materialBase = new PhongMaterial();
            materialBase.setDiffuseColor(javafx.scene.paint.Color.rgb(240,250,250));
            tempMesh.setMaterial(materialBase);
            return tempMesh;
        }
        public MeshView createDome(){
            ObjModelImporter importer = new ObjModelImporter();
            importer.clear();
            importer.read("C:\\Users\\Rei\\IdeaProjects\\hello\\src\\main\\resources\\Dome.obj");
            MeshView[] temp = importer.getImport();
            MeshView tempMesh=temp[0];
            correctSize(tempMesh,15.5,15.5,15.5);
            PhongMaterial materialBase = new PhongMaterial();
            materialBase.setDiffuseColor(javafx.scene.paint.Color.BLUE);
            tempMesh.setMaterial(materialBase);
            return tempMesh;
        }

        public MeshView createBuildingType2(){
            ObjModelImporter importer = new ObjModelImporter();
            importer.clear();
            importer.read("C:\\Users\\Rei\\IdeaProjects\\hello\\src\\main\\resources\\BuildingBlock02.obj");
            MeshView[] temp = importer.getImport();
            MeshView tempMesh=temp[0];
            correctSize(tempMesh,15.5,15.5,15.5);
            PhongMaterial materialBase = new PhongMaterial();
            materialBase.setDiffuseColor(javafx.scene.paint.Color.rgb(240,250,250));
            tempMesh.setMaterial(materialBase);
            return tempMesh;
        }

        public MeshView createBuildingType3(){
            ObjModelImporter importer = new ObjModelImporter();
            importer.clear();
            importer.read("C:\\Users\\Rei\\IdeaProjects\\hello\\src\\main\\resources\\BuildingBlock03.obj");
            MeshView[] temp = importer.getImport();
            MeshView tempMesh=temp[0];
            correctSize(tempMesh,15.5,15.5,15.5);
            PhongMaterial materialBase = new PhongMaterial();
            materialBase.setDiffuseColor(javafx.scene.paint.Color.rgb(240,250,250));
            tempMesh.setMaterial(materialBase);
            return tempMesh;
        }

        private void buildBodySystem(){
            buildBoard();
            Pawn maleWorker=createMaleWorker();
            Pawn femaleWorker=createFemaleWorker();
            MeshView lvl1Building=createBuildingType1();
            MeshView lvl2Building=createBuildingType2();
            MeshView lvl3Building=createBuildingType3();
            PhongMaterial material = new PhongMaterial();
            material.setDiffuseColor(javafx.scene.paint.Color.CYAN);
            maleWorker.getWorkerMesh().setMaterial(material);

            PhongMaterial material2 = new PhongMaterial();
            material2.setDiffuseColor(Color.RED);
            femaleWorker.getWorkerMesh().setMaterial(material2);
            maleWorker.setIdNumber(5);

            grid[0][0].setWorker(maleWorker);
            grid[2][2].setWorker(femaleWorker);
            grid[1][4].setBuildingLvl1(lvl1Building);
            grid[1][4].setBuildingLvl2(lvl2Building);
            grid[1][4].setBuildingLvl3(lvl3Building);
            grid[1][4].setDome(createDome());
            grid[3][4].setBuildingLvl1(createBuildingType1());
            grid[3][4].setDome(createDome());
            grid[2][0].setBuildingLvl1(createBuildingType1());
            grid[2][0].setBuildingLvl2(createBuildingType2());
            grid[2][0].setDome(createDome());
            System.out.println(maleWorker.getIdNumber());
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

    }
