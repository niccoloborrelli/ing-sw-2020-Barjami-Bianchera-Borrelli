
package  it.polimi.ingsw;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * JavaFX App
 */

public class App extends Application {

    private static Scene title;
    private static Scene gods;
    private static Scene selectionName;
    private static Scene selectionColor;
    private static Scene selectionGods;
    private Stage stage;
    private Image apollo = new Image("card_apollo.png");
    private Image artemis = new Image("card_artemis.png");
    private Image athena = new Image("card_athena.png");
    private Image atlas = new Image("card_atlas.png");
    private Image chronus = new Image("card_chronus.png");
    private Image demeter = new Image("card_demeter.png");
    private Image hephaestus = new Image("card_hephaestus.png");
    private Image hestia = new Image("card_hestia.png");
    private Image hypnus = new Image("card_hypnus.png");
    private Image minotaur = new Image("card_minotaur.png");
    private Image pan = new Image("card_pan.png");
    private Image persephone = new Image("card_persephone.png");
    private Image prometheus = new Image("card_prometheus.png");
    private Image zeus = new Image("card_zeus.png");
    private Image background = new Image("bg_modeselect.png");
    private Image askPlayers = new Image("clp_textbar.png");
    private CommandGUIManager commandGUIManager;

    @Override
    public void start(Stage stage){
        //commandGUIManager.setApp(this);
        stage.setTitle("Santorini");
        stage.setResizable(true);
        setStageClass(stage);
        Group root = new Group();
        Group root2 = new Group();
        Group root3 = new Group();
        Group root4 = new Group();
        Canvas canvas = new Canvas(1024, 700);
        Canvas godSelection = new Canvas(1024, 700);
        Canvas nameSelection = new Canvas(1024,700);
        Canvas colorSelection = new Canvas(1024,700);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicsContext ggods = godSelection.getGraphicsContext2D();
        GraphicsContext gname = nameSelection.getGraphicsContext2D();
        GraphicsContext gcolor = colorSelection.getGraphicsContext2D();

        Image titleWater = new Image("title_water.png");
        Image logo = new Image("santorini-logo.png");
        Image titleIsland = new Image("title_island.png");
        Image farIsland = new Image("title_far_island.png");
        Image titlePoseidon = new Image("title_poseidon.png");
        Image titleAfrodite = new Image("title_aphrodite.png");
        Image poseidonHand = new Image("title_poseidon_hand.png");
        Image leftTopCloud = new Image("title_FG_cloud.png");
        Image rightTopCloud = new Image("title_FG_cloud2.png");
        Image titleGrass = new Image("title_FG_grass.png");
        Image boatRight = new Image("title_boat_right.png");

        //title screen setup
        gc.drawImage(titleWater, 0, 150, titleWater.getWidth(), titleWater.getHeight());
        gc.drawImage(logo, 290, 10, logo.getWidth()/2.5, logo.getHeight()/2.5);
        gc.drawImage(farIsland, 300, 245, farIsland.getWidth()/2, farIsland.getHeight()/2);
        gc.drawImage(titlePoseidon, 60, 120, titlePoseidon.getWidth()/2, titlePoseidon.getHeight()/2);
        gc.drawImage(titleAfrodite, 720, 140, titleAfrodite.getWidth()/2, titleAfrodite.getHeight()/2);
        gc.drawImage(titleIsland, 250, 180, titleIsland.getWidth()/1.5, titleIsland.getHeight()/1.5);
        gc.drawImage(poseidonHand, 265, 330, poseidonHand.getWidth()/2, poseidonHand.getHeight()/2);
        gc.drawImage(leftTopCloud, 200, 40, leftTopCloud.getWidth()/5, leftTopCloud.getHeight()/5);
        gc.drawImage(rightTopCloud, 745, 40, rightTopCloud.getWidth()/5, rightTopCloud.getHeight()/5);
        gc.drawImage(titleGrass, 0, 480, titleGrass.getWidth()/1.86 - 8,titleGrass.getHeight()/2);
        gc.drawImage(boatRight, 640, 300, boatRight.getWidth(), boatRight.getHeight());
        gc.drawImage(askPlayers, 380, 570, askPlayers.getWidth()/2, askPlayers.getHeight()/2);
        gc.setStroke(Color.WHITE);
        gc.setFont(Font.font("", FontWeight.SEMI_BOLD, 18));
        gc.strokeText("How many players to play?", 405, 600);
        gc.setFont(new Font("", 24));
        gc.strokeText("2", 100, 600);
        gc.strokeText("3", 890, 600);

        HashMap<ImageView, Integer> numberPlayers = new HashMap<>();
        ImageView players2 = new ImageView(askPlayers);
        createButton(players2, 2, numberPlayers);
        ImageView players3 = new ImageView(askPlayers);
        createButton(players3, 3, numberPlayers);
        List<ImageView> buttons = new ArrayList<>();
        buttons.add(players2);
        buttons.add(players3);


        //god selection setup
        List<ImageView> cards = new ArrayList<>();
        HashMap<ImageView, String> cardToName = new HashMap<>();
        ggods.drawImage(background, 0, 0, 1024, 700);

        ImageView apolloImg = new ImageView(apollo);
        createCard(apolloImg, 1,1, cards);
        cardToName.put(apolloImg, "Apollo");

        ImageView artemisImg = new ImageView(artemis);
        createCard(artemisImg, 1,2, cards);
        cardToName.put(artemisImg, "Artemis");

        ImageView athenaImg = new ImageView(athena);
        createCard(athenaImg, 1,3, cards);
        cardToName.put(athenaImg, "Athena");

        ImageView atlasImg = new ImageView(atlas);
        createCard(atlasImg, 1,4, cards);
        cardToName.put(atlasImg, "Atlas");

        ImageView chronusImg = new ImageView(chronus);
        createCard(chronusImg, 1,5, cards);
        cardToName.put(chronusImg, "Chronus");

        ImageView demeterImg = new ImageView(demeter);
        createCard(demeterImg, 1,6, cards);
        cardToName.put(demeterImg, "Demeter");

        ImageView hephaestusImg = new ImageView(hephaestus);
        createCard(hephaestusImg, 1,7, cards);
        cardToName.put(hephaestusImg, "Hephaestus");

        ImageView hestiaImg = new ImageView(hestia);
        createCard(hestiaImg, 2,1, cards);
        cardToName.put(hestiaImg, "Hestia");

        ImageView hypnusImg = new ImageView(hypnus);
        createCard(hypnusImg, 2,2, cards);
        cardToName.put(hypnusImg, "Hypnus");

        ImageView minotaurImg = new ImageView(minotaur);
        createCard(minotaurImg, 2,3, cards);
        cardToName.put(minotaurImg, "Minotaur");

        ImageView panImg = new ImageView(pan);
        createCard(panImg, 2,4, cards);
        cardToName.put(panImg, "Pan");

        ImageView persephoneImg = new ImageView(persephone);
        createCard(persephoneImg, 2,5, cards);
        cardToName.put(persephoneImg, "Persephone");

        ImageView prometheusImg = new ImageView(prometheus);
        createCard(prometheusImg, 2,6, cards);
        cardToName.put(prometheusImg, "Prometheus");

        ImageView zeusImg = new ImageView(zeus);
        createCard(zeusImg, 2,7, cards);
        cardToName.put(zeusImg, "Zeus");

        ggods.drawImage(askPlayers, 335, 320, askPlayers.getWidth()/1.5, askPlayers.getHeight()/2);
        ggods.setStroke(Color.WHITE);
        ggods.setFont(new Font("", 18));
        ggods.strokeText("Choose the gods to use in the match", 363, 349);

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(-1.0);

        //name&color selection setup
        ImageView textName = insertQuestion(gname, "name", background, askPlayers);
        ImageView textColor = insertQuestion(gcolor, "color", background, askPlayers);
        TextField textFieldName = new TextField();
        TextField textFieldColor = new TextField();
        insertTextField(textFieldName);
        insertTextField(textFieldColor);


        root.getChildren().addAll(canvas, players2, players3);
        root2.getChildren().addAll(godSelection, apolloImg, artemisImg, athenaImg, atlasImg, chronusImg, demeterImg,
                hephaestusImg, hestiaImg, hypnusImg, minotaurImg, panImg, persephoneImg, prometheusImg, zeusImg);
        root3.getChildren().addAll(nameSelection, textName, textFieldName);
        root4.getChildren().addAll(colorSelection, textColor, textFieldColor);
        title = new Scene(root);
        gods = new Scene(root2);
        selectionName = new Scene(root3);
        selectionColor = new Scene(root4);
        stage.setScene(title);
        stage.show();

        for (ImageView img: buttons) {
            img.setOnMouseEntered(mouseEvent -> title.setCursor(Cursor.HAND));

            img.setOnMouseExited(mouseEvent -> title.setCursor(Cursor.DEFAULT));

            img.setOnMouseClicked(mouseEvent -> {
                set3DGui();
                System.out.println(numberPlayers.get(img));
                GeneralStringRequestCommand generalStringRequestCommand = new GeneralStringRequestCommand(String.valueOf(numberPlayers.get(img)));
                commandGUIManager.manageCommand(generalStringRequestCommand);
            });
        }

        textFieldName.setOnAction((EventHandler) event -> {
            GeneralStringRequestCommand generalStringRequestCommand = new GeneralStringRequestCommand(textFieldName.getText());
            commandGUIManager.manageCommand(generalStringRequestCommand);
        });

        textFieldColor.setOnAction((EventHandler) event -> {
            GeneralStringRequestCommand generalStringRequestCommand = new GeneralStringRequestCommand(textFieldColor.getText());
            commandGUIManager.manageCommand(generalStringRequestCommand);
        });

        for (ImageView img: cards) {
            img.setOnMouseEntered(mouseEvent -> gods.setCursor(Cursor.HAND));

            img.setOnMouseExited(mouseEvent -> gods.setCursor(Cursor.DEFAULT));

            img.setOnMouseClicked(mouseEvent-> {
                img.setEffect(colorAdjust);
                System.out.println(cardToName.get(img));
                GeneralStringRequestCommand generalStringRequestCommand = new GeneralStringRequestCommand(cardToName.get(img));
                commandGUIManager.manageCommand(generalStringRequestCommand);
                //StringCommand stringCommand = new StringCommand(cardToName.get(img));
            });
        }
    }

    private void createCard(ImageView imageView, int row, int column, List<ImageView> list){
        imageView.setFitWidth(imageView.getImage().getWidth()/6);
        imageView.setFitHeight(imageView.getImage().getHeight()/6);
        imageView.fitWidthProperty();
        imageView.fitHeightProperty();
        imageView.setSmooth(true);
        imageView.setCache(true);
        if(row == 1)
            imageView.setY(50);
        else
            imageView.setY(400);
        switch (column){
            case 1:
                imageView.setX(10);
                break;
            case 2:
                imageView.setX(155);
                break;
            case 3:
                imageView.setX(300);
                break;
            case 4:
                imageView.setX(445);
                break;
            case 5:
                imageView.setX(590);
                break;
            case 6:
                imageView.setX(735);
                break;
            case 7:
                imageView.setX(880);
                break;
        }
        list.add(imageView);
    }

    private void createButton(ImageView imageView, int players, HashMap<ImageView, Integer> hashMap){
        imageView.setFitHeight(imageView.getImage().getHeight()/2);
        imageView.setFitWidth(imageView.getImage().getWidth()/6);
        imageView.fitHeightProperty();
        imageView.fitWidthProperty();
        imageView.setSmooth(true);
        imageView.setCache(true);
        imageView.setY(600);
        if(players == 2)
            imageView.setX(60);
        else
            imageView.setX(850);
        hashMap.put(imageView, players);
    }

    private ImageView insertQuestion(GraphicsContext graphicsContext, String string, Image background, Image askPlayers){
        graphicsContext.drawImage(background, 0 , 0, 1024, 700);
        graphicsContext.drawImage(askPlayers, 365, 430, askPlayers.getWidth()/2, askPlayers.getHeight()/2);
        graphicsContext.setStroke(Color.WHITE);
        graphicsContext.setFont(new Font("", 18));
        if(string.equals("name"))
            graphicsContext.strokeText("Insert your name", 430, 460);
        else
            graphicsContext.strokeText("Insert your color", 430, 460);

        ImageView text = new ImageView(askPlayers);
        text.setFitWidth(askPlayers.getWidth()/2);
        text.setFitHeight(askPlayers.getHeight()/2);
        text.setX(365);
        text.setY(550);

        return text;
    }

    private void insertTextField(TextField textField){
        textField.setAlignment(Pos.CENTER);
        textField.setTranslateX(382);
        textField.setPrefWidth(230);
        textField.setTranslateY(558);
        textField.setFont(new Font("", 15));
    }

    private void setStageClass(Stage stage){
        this.stage = stage;
    }

    public void setNameStage(){
        stage.setScene(selectionName);
        stage.show();
    }

    public void setColorStage(){
        stage.setScene(selectionColor);
        stage.show();
    }

    public void setGodsStage(){
        stage.setScene(gods);
        stage.show();
    }

    public void changeGods(List<String> godsList){
        Group root = new Group();
        Canvas canvas = new Canvas(1024, 700);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        List<ImageView> cards = new ArrayList<>();
        HashMap<ImageView, String> cardToName = new HashMap<>();
        gc.drawImage(background, 0, 0, 1024, 700);
        root.getChildren().add(canvas);

        for (String s: godsList) {
            switch (s) {
                case "Apollo":
                    ImageView apolloImg = new ImageView(apollo);
                    createCard(apolloImg, 1,1, cards);
                    cardToName.put(apolloImg, "Apollo");
                    root.getChildren().add(apolloImg);
                    break;
                case "Artemis":
                    ImageView artemisImg = new ImageView(artemis);
                    createCard(artemisImg, 1,2, cards);
                    cardToName.put(artemisImg, "Artemis");
                    root.getChildren().add(artemisImg);
                    break;
                case "Athena":
                    ImageView athenaImg = new ImageView(athena);
                    createCard(athenaImg, 1,3, cards);
                    cardToName.put(athenaImg, "Athena");
                    root.getChildren().add(athenaImg);
                    break;
                case "Atlas":
                    ImageView atlasImg = new ImageView(atlas);
                    createCard(atlasImg, 1,4, cards);
                    cardToName.put(atlasImg, "Atlas");
                    root.getChildren().add(atlasImg);
                    break;
                case "Chronus":
                    ImageView chronusImg = new ImageView(chronus);
                    createCard(chronusImg, 1,5, cards);
                    cardToName.put(chronusImg, "Chronus");
                    root.getChildren().add(chronusImg);
                    break;
                case "Demeter":
                    ImageView demeterImg = new ImageView(demeter);
                    createCard(demeterImg, 1,6, cards);
                    cardToName.put(demeterImg, "Demeter");
                    root.getChildren().add(demeterImg);
                    break;
                case "Hephaestus":
                    ImageView hephaestusImg = new ImageView(hephaestus);
                    createCard(hephaestusImg, 1,7, cards);
                    cardToName.put(hephaestusImg, "Hephaestus");
                    root.getChildren().add(hephaestusImg);
                    break;
                case "Hestia":
                    ImageView hestiaImg = new ImageView(hestia);
                    createCard(hestiaImg, 2,1, cards);
                    cardToName.put(hestiaImg, "Hestia");
                    root.getChildren().add(hestiaImg);
                    break;
                case "Hypnus":
                    ImageView hypnusImg = new ImageView(hypnus);
                    createCard(hypnusImg, 2,2, cards);
                    cardToName.put(hypnusImg, "Hypnus");
                    root.getChildren().add(hypnusImg);
                    break;
                case "Minotaur":
                    ImageView minotaurImg = new ImageView(minotaur);
                    createCard(minotaurImg, 2,3, cards);
                    cardToName.put(minotaurImg, "Minotaur");
                    root.getChildren().add(minotaurImg);
                    break;
                case "Pan":
                    ImageView panImg = new ImageView(pan);
                    createCard(panImg, 2,4, cards);
                    cardToName.put(panImg, "Pan");
                    root.getChildren().add(panImg);
                    break;
                case "Persephone":
                    ImageView persephoneImg = new ImageView(persephone);
                    createCard(persephoneImg, 2,5, cards);
                    cardToName.put(persephoneImg, "Persephone");
                    root.getChildren().add(persephoneImg);
                    break;
                case "Prometheus":
                    ImageView prometheusImg = new ImageView(prometheus);
                    createCard(prometheusImg, 2,6, cards);
                    cardToName.put(prometheusImg, "Prometheus");
                    root.getChildren().add(prometheusImg);
                    break;
                case "Zeus":
                    ImageView zeusImg = new ImageView(zeus);
                    createCard(zeusImg, 2,7, cards);
                    cardToName.put(zeusImg, "Zeus");
                    root.getChildren().add(zeusImg);
                    break;
            }
        }

        gc.drawImage(askPlayers, 335, 320, askPlayers.getWidth()/1.5, askPlayers.getHeight()/2);
        gc.setStroke(Color.WHITE);
        gc.setFont(new Font("", 18));
        gc.strokeText("Choose the god to use in this match", 363, 349);

        selectionGods = new Scene(root);
        stage.setScene(selectionGods);
        stage.show();

        for (ImageView img: cards) {
            img.setOnMouseEntered(mouseEvent -> selectionGods.setCursor(Cursor.HAND));

            img.setOnMouseExited(mouseEvent -> selectionGods.setCursor(Cursor.DEFAULT));

            img.setOnMouseClicked(mouseEvent-> {
                GeneralStringRequestCommand generalStringRequestCommand = new GeneralStringRequestCommand(cardToName.get(img));
                commandGUIManager.manageCommand(generalStringRequestCommand);
                //StringCommand stringCommand = new StringCommand(cardToName.get(img));
            });
        }
    }

    public void setCommandGUIManager(CommandGUIManager commandGUIManager) {
        this.commandGUIManager = commandGUIManager;
    }

    public static void main(String[] args) throws IOException {
        //Socket socket = new Socket("localhost", 60100);
        //CommandGUIManager commandGUIManager = new CommandGUIManager(socket);
        //DeliveryMessage deliveryMessage = commandGUIManager.getDeliveryMessage();
       // new Thread(deliveryMessage::startReading).start();
        launch();
    }

    public void set3DGui(){
        GraphicInterface gui=new GraphicInterface();
       // commandGUIManager.setGraphicInterface(gui);
        gui.start(stage);
    }
}
