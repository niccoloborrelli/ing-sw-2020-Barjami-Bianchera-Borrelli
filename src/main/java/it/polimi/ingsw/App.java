package  it.polimi.ingsw;

import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static it.polimi.ingsw.FinalCommunication.*;
import static java.lang.Thread.sleep;

/**
 * JavaFX App
 */

public class App extends Application {
    private static Group title;
    private static Group gods;
    private static Group selectionName;
    private static Group selectionColor;
    private static Group selectionGods;
    private static Group exit;
    private Stage stage;
    private Image apollo = loadImage(APOLLOCARD);
    private Image artemis = loadImage(ARTEMISCARD);
    private Image athena = loadImage(ATHENACARD);
    private Image atlas = loadImage(ATLASCARD);
    private Image chronus = loadImage(CHRONUSCARD);
    private Image demeter = loadImage(DEMETERCARD);
    private Image hephaestus = loadImage(HEPHAESTUSCARD);
    private Image hestia = loadImage(HESTIACARD);
    private Image hypnus = loadImage(HYPNUSCARD);
    private Image minotaur = loadImage(MINOTAURCARD);
    private Image pan = loadImage(PANCARD);
    private Image persephone = loadImage(PERSEPHONECARD);
    private Image prometheus = loadImage(PROMETHEUSCARD);
    private Image zeus = loadImage(ZEUSCARD);
    private Image background = loadImage(BACKGROUND);
    private Image askPlayers = loadImage(ASKER);
    private Image quitGame = loadImage(QUIT_GAME);
    private ImageView quit = new ImageView(quitGame);
    private Image colorRed = loadImage(RED_BUTTON);
    private Image colorGrey = loadImage(GREY_BUTTON);
    private Image colorCyan = loadImage(CYAN_BUTTON);
    private Image colorPurple = loadImage(PURPLE_BUTTON);
    private Image colorWhite =  loadImage(WHITE_BUTTON);
    private CommandGUIManager commandGUIManager;
    Socket socket;

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        Socket socket = new Socket("localhost", 60100);
        CommandGUIManager commandGUIManager = new CommandGUIManager(socket);
        DeliveryMessage deliveryMessage = commandGUIManager.getDeliveryMessage();
        new Thread(deliveryMessage::startReading).start();
        commandGUIManager.setApp(this);
        this.setCommandGUIManager(commandGUIManager);
        stage.setTitle(SANTORINI);
        stage.setResizable(true);
        setStageClass(stage);
        Group root = new Group();
        Group root2 = new Group();
        Group root3 = new Group();
        Group root4 = new Group();
        Group root5 = new Group();
        Canvas canvas = new Canvas(1024, 700);
        Canvas godSelection = new Canvas(1024, 700);
        Canvas nameSelection = new Canvas(1024, 700);
        Canvas colorSelection = new Canvas(1024, 700);
        Canvas onExit = new Canvas(1024, 700);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicsContext ggods = godSelection.getGraphicsContext2D();
        GraphicsContext gname = nameSelection.getGraphicsContext2D();
        GraphicsContext gcolor = colorSelection.getGraphicsContext2D();
        GraphicsContext gexit = onExit.getGraphicsContext2D();

        Image titleWater = loadImage(TITLE_WATER);
        Image logo = loadImage(SANTORINI_LOGO);
        Image titleIsland = loadImage(TITLE_ISLAND);
        Image farIsland = loadImage(TITLE_FAR_ISLAND);
        Image titlePoseidon = loadImage(TITLE_POSEIDON);
        Image titleAfrodite = loadImage(TITLE_APHRODITE);
        Image poseidonHand = loadImage(POSEIDON_HAND);
        Image leftTopCloud = loadImage(LEFT_TOP_CLOUD);
        Image rightTopCloud = loadImage(RIGHT_TOP_CLOUD);
        Image titleGrass = loadImage(TITLE_GRASS);
        Image boatRight = loadImage(TITLE_BOAT);

        //title screen setup
        gc.drawImage(titleWater, 0, 150, titleWater.getWidth(), titleWater.getHeight());
        gc.drawImage(logo, 290, 10, logo.getWidth() / 2.5, logo.getHeight() / 2.5);
        gc.drawImage(farIsland, 300, 245, farIsland.getWidth() / 2, farIsland.getHeight() / 2);
        gc.drawImage(titlePoseidon, 60, 120, titlePoseidon.getWidth() / 2, titlePoseidon.getHeight() / 2);
        gc.drawImage(titleAfrodite, 720, 140, titleAfrodite.getWidth() / 2, titleAfrodite.getHeight() / 2);
        gc.drawImage(titleIsland, 250, 180, titleIsland.getWidth() / 1.5, titleIsland.getHeight() / 1.5);
        gc.drawImage(poseidonHand, 265, 330, poseidonHand.getWidth() / 2, poseidonHand.getHeight() / 2);
        gc.drawImage(leftTopCloud, 200, 40, leftTopCloud.getWidth() / 5, leftTopCloud.getHeight() / 5);
        gc.drawImage(rightTopCloud, 745, 40, rightTopCloud.getWidth() / 5, rightTopCloud.getHeight() / 5);
        gc.drawImage(titleGrass, 0, 480, titleGrass.getWidth() / 1.86 - 8, titleGrass.getHeight() / 2);
        gc.drawImage(boatRight, 640, 300, boatRight.getWidth(), boatRight.getHeight());
        gc.drawImage(askPlayers, 380, 570, askPlayers.getWidth() / 2, askPlayers.getHeight() / 2);
        gc.setStroke(Color.WHITE);
        gc.setFont(Font.font("", FontWeight.SEMI_BOLD, 18));
        gc.strokeText(HOW_MANY_PLAYERS, 405, 600);
        gc.setFont(new Font("", 24));
        gc.strokeText(TWOPLAYERS, 100, 600);
        gc.strokeText(THREEPLAYERS, 890, 600);

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
        createCard(apolloImg, 1, 1, cards);
        cardToName.put(apolloImg, APOLLO);

        ImageView artemisImg = new ImageView(artemis);
        createCard(artemisImg, 1, 2, cards);
        cardToName.put(artemisImg, ARTEMIS);

        ImageView athenaImg = new ImageView(athena);
        createCard(athenaImg, 1, 3, cards);
        cardToName.put(athenaImg, ATHENA);

        ImageView atlasImg = new ImageView(atlas);
        createCard(atlasImg, 1, 4, cards);
        cardToName.put(atlasImg, ATLAS);

        ImageView chronusImg = new ImageView(chronus);
        createCard(chronusImg, 1, 5, cards);
        cardToName.put(chronusImg, CHRONUS);

        ImageView demeterImg = new ImageView(demeter);
        createCard(demeterImg, 1, 6, cards);
        cardToName.put(demeterImg, DEMETER);

        ImageView hephaestusImg = new ImageView(hephaestus);
        createCard(hephaestusImg, 1, 7, cards);
        cardToName.put(hephaestusImg, HEPHAESTUS);

        ImageView hestiaImg = new ImageView(hestia);
        createCard(hestiaImg, 2, 1, cards);
        cardToName.put(hestiaImg, HESTIA);

        ImageView hypnusImg = new ImageView(hypnus);
        createCard(hypnusImg, 2, 2, cards);
        cardToName.put(hypnusImg, HYPNUS);

        ImageView minotaurImg = new ImageView(minotaur);
        createCard(minotaurImg, 2, 3, cards);
        cardToName.put(minotaurImg, MINOTAUR);

        ImageView panImg = new ImageView(pan);
        createCard(panImg, 2, 4, cards);
        cardToName.put(panImg, PAN);

        ImageView persephoneImg = new ImageView(persephone);
        createCard(persephoneImg, 2, 5, cards);
        cardToName.put(persephoneImg, PERSEPHONE);

        ImageView prometheusImg = new ImageView(prometheus);
        createCard(prometheusImg, 2, 6, cards);
        cardToName.put(prometheusImg, PROMETHEUS);

        ImageView zeusImg = new ImageView(zeus);
        createCard(zeusImg, 2, 7, cards);
        cardToName.put(zeusImg, ZEUS);

        ggods.drawImage(askPlayers, 335, 320, askPlayers.getWidth() / 1.5, askPlayers.getHeight() / 2);
        ggods.setStroke(Color.WHITE);
        ggods.setFont(new Font("", 18));
        ggods.strokeText(CHOOSE_GODS, 363, 349);

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(-1.0);

        //name&color selection setup
        ImageView textName = insertQuestion(gname, NAME, background, askPlayers);
        ImageView textColor = insertQuestion(gcolor, COLOR, background, askPlayers);
        TextField textFieldName = new TextField();
        insertTextField(textFieldName);


        //quit
        quitButton(quit);

        //exit
        gexit.drawImage(background, 0, 0, 1024, 700);
        gexit.drawImage(askPlayers, 335, 320, askPlayers.getWidth() / 1.5, askPlayers.getHeight() / 2);
        gexit.setStroke(Color.WHITE);
        gexit.setFont(new Font("", 18));
        gexit.strokeText(DISCONNECTED, 363, 349);

        root.getChildren().addAll(canvas, players2, players3, quit);
        root2.getChildren().addAll(godSelection, apolloImg, artemisImg, athenaImg, atlasImg, chronusImg, demeterImg,
                hephaestusImg, hestiaImg, hypnusImg, minotaurImg, panImg, persephoneImg, prometheusImg, zeusImg);
        root3.getChildren().addAll(nameSelection, textName, textFieldName);
        root4.getChildren().add(colorSelection);
        root5.getChildren().addAll(onExit);
        title = root;
        gods = root2;
        selectionName = root3;
        selectionColor = root4;
        exit = root5;
        Scene scene = new Scene(title);
        stage.setScene(scene);
        stage.show();

        for (ImageView img : buttons) {
            img.setOnMouseEntered(mouseEvent -> title.setCursor(Cursor.HAND));

            img.setOnMouseExited(mouseEvent -> title.setCursor(Cursor.DEFAULT));

            img.setOnMouseClicked(mouseEvent -> {
                GeneralStringRequestCommand generalStringRequestCommand = new GeneralStringRequestCommand(String.valueOf(numberPlayers.get(img)));
                commandGUIManager.manageCommand(generalStringRequestCommand);
            });
        }

        textFieldName.setOnAction((EventHandler) event -> {
            GeneralStringRequestCommand generalStringRequestCommand = new GeneralStringRequestCommand(textFieldName.getText());
            commandGUIManager.manageCommand(generalStringRequestCommand);
        });

        for (ImageView img : cards) {
            img.setOnMouseEntered(mouseEvent -> gods.setCursor(Cursor.HAND));

            img.setOnMouseExited(mouseEvent -> gods.setCursor(Cursor.DEFAULT));

            img.setOnMouseClicked(mouseEvent -> {
                img.setEffect(colorAdjust);
                GeneralStringRequestCommand generalStringRequestCommand = new GeneralStringRequestCommand(cardToName.get(img));
                commandGUIManager.manageCommand(generalStringRequestCommand);
            });
        }

        quit.setOnMouseEntered(mouseEvent -> title.setCursor(Cursor.HAND));

        quit.setOnMouseExited(mouseEvent -> title.setCursor(Cursor.DEFAULT));

        quit.setOnMouseClicked(mouseEvent -> {
            GeneralStringRequestCommand generalStringRequestCommand = new GeneralStringRequestCommand(QUIT);
            commandGUIManager.manageCommand(generalStringRequestCommand);
        });

        stage.setOnCloseRequest(we -> {
            ExitCommand exitCommand = new ExitCommand();
            commandGUIManager.manageCommand(exitCommand);
            Platform.exit();
        });
    }

    public void quitApplication(){
        Platform.exit();
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

    private void quitButton(ImageView imageView){
        imageView.setFitHeight(imageView.getImage().getHeight()/8);
        imageView.setFitWidth(imageView.getImage().getWidth()/8);
        imageView.fitHeightProperty();
        imageView.fitWidthProperty();
        imageView.setSmooth(true);
        imageView.setCache(true);

        imageView.setX(900);
        imageView.setY(30);
    }

    private ImageView insertQuestion(GraphicsContext graphicsContext, String string, Image background, Image askPlayers){
        graphicsContext.drawImage(background, 0 , 0, 1024, 700);
        graphicsContext.drawImage(askPlayers, 365, 430, askPlayers.getWidth()/2, askPlayers.getHeight()/2);
        graphicsContext.setStroke(Color.WHITE);
        graphicsContext.setFont(new Font("", 18));
        if(string.equals(NAME))
            graphicsContext.strokeText(INSERT_NAME, 430, 460);
        else
            graphicsContext.strokeText(INSERT_COLOR, 430, 460);

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
        Platform.runLater(()  -> {
                    if (!selectionName.getChildren().contains(quit))
                        selectionName.getChildren().add(quit);
                });

        stage.getScene().setRoot(selectionName);

        quit.setOnMouseEntered(mouseEvent ->  selectionName.setCursor(Cursor.HAND));

        quit.setOnMouseExited(mouseEvent -> selectionName.setCursor(Cursor.DEFAULT));

        quit.setOnMouseClicked(mouseEvent -> {
            GeneralStringRequestCommand generalStringRequestCommand = new GeneralStringRequestCommand(QUIT);
            commandGUIManager.manageCommand(generalStringRequestCommand);
        });
    }

    public void setColorStage(List<String> colorList){
        Object objectCanvas = selectionColor.getChildren().get(0);
        selectionColor.getChildren().removeIf(s-> !s.equals(objectCanvas));
        List<ImageView> colors = new ArrayList<>();
        HashMap<ImageView, String> buttonToColor = new HashMap<>();

        for (String s: colorList) {
            switch (s) {
                case RED_COLOR:
                    ImageView buttonRed = new ImageView(colorRed);
                    insertColorButton(1, buttonRed, colors);
                    buttonToColor.put(buttonRed, RED_COLOR);
                    Platform.runLater(() -> {
                        if (!selectionColor.getChildren().contains(buttonRed))
                                selectionColor.getChildren().add(buttonRed);
                    });
                    break;
                case GREY_COLOR:
                    ImageView buttonGrey = new ImageView(colorGrey);
                    insertColorButton(2, buttonGrey, colors);
                    buttonToColor.put(buttonGrey, GREY_COLOR);
                    Platform.runLater(() -> {
                        if (!selectionColor.getChildren().contains(buttonGrey))
                                selectionColor.getChildren().add(buttonGrey);
                    });
                    break;
                case PURPLE_COLOR:
                    ImageView buttonPurple = new ImageView(colorPurple);
                    insertColorButton(3, buttonPurple, colors);
                    buttonToColor.put(buttonPurple, PURPLE_COLOR);
                    Platform.runLater(() -> {
                        if(!selectionColor.getChildren().contains(buttonPurple))
                                selectionColor.getChildren().add(buttonPurple);
                    });
                    break;
                case CYAN_COLOR:
                    ImageView buttonCyan = new ImageView(colorCyan);
                    insertColorButton(4, buttonCyan, colors);
                    buttonToColor.put(buttonCyan, CYAN_COLOR);
                    Platform.runLater(() -> {
                        if (!selectionColor.getChildren().contains(buttonCyan))
                                selectionColor.getChildren().add(buttonCyan);
                    });
                    break;
                case WHITE_COLOR:
                    ImageView buttonWhite = new ImageView(colorWhite);
                    insertColorButton(5, buttonWhite, colors);
                    buttonToColor.put(buttonWhite, WHITE_COLOR);
                    Platform.runLater(() ->{
                        if(!selectionColor.getChildren().contains(buttonWhite))
                                selectionColor.getChildren().add(buttonWhite);
                    });
            }
        }

        for (ImageView img: colors){
            img.setOnMouseEntered(mouseEvent ->  selectionColor.setCursor(Cursor.HAND));

            img.setOnMouseExited(mouseEvent -> selectionColor.setCursor(Cursor.DEFAULT));

            img.setOnMouseClicked(mouseEvent -> {
                GeneralStringRequestCommand generalStringRequestCommand = new GeneralStringRequestCommand(buttonToColor.get(img));
                commandGUIManager.manageCommand(generalStringRequestCommand);
            });
        }

        Platform.runLater(() -> {
            if(!selectionColor.getChildren().contains(quit))
                selectionColor.getChildren().add(quit);
                });
        stage.getScene().setRoot(selectionColor);

        quit.setOnMouseEntered(mouseEvent ->  selectionColor.setCursor(Cursor.HAND));

        quit.setOnMouseExited(mouseEvent -> selectionColor.setCursor(Cursor.DEFAULT));

        quit.setOnMouseClicked(mouseEvent -> {
            GeneralStringRequestCommand generalStringRequestCommand = new GeneralStringRequestCommand(QUIT);
            commandGUIManager.manageCommand(generalStringRequestCommand);
        });
    }

    public void setGodsStage(){
        quit.setY(310);
        Platform.runLater(() -> gods.getChildren().add(quit));
        stage.getScene().setRoot(gods);

        quit.setOnMouseEntered(mouseEvent ->  gods.setCursor(Cursor.HAND));

        quit.setOnMouseExited(mouseEvent -> gods.setCursor(Cursor.DEFAULT));

        quit.setOnMouseClicked(mouseEvent -> {
            GeneralStringRequestCommand generalStringRequestCommand = new GeneralStringRequestCommand(QUIT);
            commandGUIManager.manageCommand(generalStringRequestCommand);
        });
    }

    public void setOnExitStage(){
        quit.setY(30);
        Platform.runLater(() -> exit.getChildren().add(quit));
        stage.getScene().setRoot(exit);

        quit.setOnMouseEntered(mouseEvent ->  exit.setCursor(Cursor.HAND));

        quit.setOnMouseExited(mouseEvent -> exit.setCursor(Cursor.DEFAULT));

        quit.setOnMouseClicked(mouseEvent -> {
            GeneralStringRequestCommand generalStringRequestCommand = new GeneralStringRequestCommand(QUIT);
            commandGUIManager.manageCommand(generalStringRequestCommand);
        });

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
                case APOLLO:
                    ImageView apolloImg = new ImageView(apollo);
                    createCard(apolloImg, 1,1, cards);
                    cardToName.put(apolloImg, APOLLO);
                    root.getChildren().add(apolloImg);
                    break;
                case ARTEMIS:
                    ImageView artemisImg = new ImageView(artemis);
                    createCard(artemisImg, 1,2, cards);
                    cardToName.put(artemisImg, ARTEMIS);
                    root.getChildren().add(artemisImg);
                    break;
                case ATHENA:
                    ImageView athenaImg = new ImageView(athena);
                    createCard(athenaImg, 1,3, cards);
                    cardToName.put(athenaImg, ATHENA);
                    root.getChildren().add(athenaImg);
                    break;
                case ATLAS:
                    ImageView atlasImg = new ImageView(atlas);
                    createCard(atlasImg, 1,4, cards);
                    cardToName.put(atlasImg, ATLAS);
                    root.getChildren().add(atlasImg);
                    break;
                case CHRONUS:
                    ImageView chronusImg = new ImageView(chronus);
                    createCard(chronusImg, 1,5, cards);
                    cardToName.put(chronusImg, CHRONUS);
                    root.getChildren().add(chronusImg);
                    break;
                case DEMETER:
                    ImageView demeterImg = new ImageView(demeter);
                    createCard(demeterImg, 1,6, cards);
                    cardToName.put(demeterImg, DEMETER);
                    root.getChildren().add(demeterImg);
                    break;
                case HEPHAESTUS:
                    ImageView hephaestusImg = new ImageView(hephaestus);
                    createCard(hephaestusImg, 1,7, cards);
                    cardToName.put(hephaestusImg, HEPHAESTUS);
                    root.getChildren().add(hephaestusImg);
                    break;
                case HESTIA:
                    ImageView hestiaImg = new ImageView(hestia);
                    createCard(hestiaImg, 2,1, cards);
                    cardToName.put(hestiaImg, HESTIA);
                    root.getChildren().add(hestiaImg);
                    break;
                case HYPNUS:
                    ImageView hypnusImg = new ImageView(hypnus);
                    createCard(hypnusImg, 2,2, cards);
                    cardToName.put(hypnusImg, HYPNUS);
                    root.getChildren().add(hypnusImg);
                    break;
                case MINOTAUR:
                    ImageView minotaurImg = new ImageView(minotaur);
                    createCard(minotaurImg, 2,3, cards);
                    cardToName.put(minotaurImg, MINOTAUR);
                    root.getChildren().add(minotaurImg);
                    break;
                case PAN:
                    ImageView panImg = new ImageView(pan);
                    createCard(panImg, 2,4, cards);
                    cardToName.put(panImg, PAN);
                    root.getChildren().add(panImg);
                    break;
                case PERSEPHONE:
                    ImageView persephoneImg = new ImageView(persephone);
                    createCard(persephoneImg, 2,5, cards);
                    cardToName.put(persephoneImg, PERSEPHONE);
                    root.getChildren().add(persephoneImg);
                    break;
                case PROMETHEUS:
                    ImageView prometheusImg = new ImageView(prometheus);
                    createCard(prometheusImg, 2,6, cards);
                    cardToName.put(prometheusImg, PROMETHEUS);
                    root.getChildren().add(prometheusImg);
                    break;
                case ZEUS:
                    ImageView zeusImg = new ImageView(zeus);
                    createCard(zeusImg, 2,7, cards);
                    cardToName.put(zeusImg, ZEUS);
                    root.getChildren().add(zeusImg);
                    break;
            }
        }

        gc.drawImage(askPlayers, 335, 320, askPlayers.getWidth()/1.5, askPlayers.getHeight()/2);
        gc.setStroke(Color.WHITE);
        gc.setFont(new Font("", 18));
        gc.strokeText(CHOOSE_GOD, 363, 349);

        quit.setY(310);

        selectionGods = root;
        Platform.runLater(() -> selectionGods.getChildren().add(quit));
        stage.getScene().setRoot(selectionGods);

        for (ImageView img: cards) {
            img.setOnMouseEntered(mouseEvent -> selectionGods.setCursor(Cursor.HAND));

            img.setOnMouseExited(mouseEvent -> selectionGods.setCursor(Cursor.DEFAULT));

            img.setOnMouseClicked(mouseEvent-> {
                GeneralStringRequestCommand generalStringRequestCommand = new GeneralStringRequestCommand(cardToName.get(img));
                commandGUIManager.manageCommand(generalStringRequestCommand);
            });
        }

        quit.setOnMouseEntered(mouseEvent ->  selectionGods.setCursor(Cursor.HAND));

        quit.setOnMouseExited(mouseEvent -> selectionGods.setCursor(Cursor.DEFAULT));

        quit.setOnMouseClicked(mouseEvent -> {
            GeneralStringRequestCommand generalStringRequestCommand = new GeneralStringRequestCommand(QUIT);
            commandGUIManager.manageCommand(generalStringRequestCommand);
        });
    }

    public void setWaitingColor(){
        Group root = new Group();
        Canvas waitingColor = new Canvas(1024, 700);
        GraphicsContext gwaitcolor = waitingColor.getGraphicsContext2D();
        createWaitScene(COLOR, gwaitcolor);
        root.getChildren().add(waitingColor);
        Platform.runLater(() -> root.getChildren().add(quit));

        stage.getScene().setRoot(root);

        quit.setOnMouseEntered(mouseEvent ->  root.setCursor(Cursor.HAND));

        quit.setOnMouseExited(mouseEvent -> root.setCursor(Cursor.DEFAULT));

        quit.setOnMouseClicked(mouseEvent -> {
            GeneralStringRequestCommand generalStringRequestCommand = new GeneralStringRequestCommand(QUIT);
            commandGUIManager.manageCommand(generalStringRequestCommand);
        });
    }

    public void setWaitingPlayer(){
        Group root = new Group();
        Canvas waitingPlayer = new Canvas(1024, 700);
        GraphicsContext gwaitplayer = waitingPlayer.getGraphicsContext2D();
        createWaitScene(PLAYER, gwaitplayer);
        root.getChildren().add(waitingPlayer);
        Platform.runLater(() -> root.getChildren().add(quit));

        stage.getScene().setRoot(root);

        quit.setOnMouseEntered(mouseEvent ->  root.setCursor(Cursor.HAND));

        quit.setOnMouseExited(mouseEvent -> root.setCursor(Cursor.DEFAULT));

        quit.setOnMouseClicked(mouseEvent -> {
            GeneralStringRequestCommand generalStringRequestCommand = new GeneralStringRequestCommand(QUIT);
            commandGUIManager.manageCommand(generalStringRequestCommand);
        });
    }

    public void setWaitingGame(){
        Group root = new Group();
        Canvas waitingGame = new Canvas(1024, 700);
        GraphicsContext gwaitgame = waitingGame.getGraphicsContext2D();
        createWaitScene(PLAYER, gwaitgame);
        root.getChildren().add(waitingGame);
        Platform.runLater(() -> root.getChildren().add(quit));

        stage.getScene().setRoot(root);

        quit.setOnMouseEntered(mouseEvent ->  root.setCursor(Cursor.HAND));

        quit.setOnMouseExited(mouseEvent -> root.setCursor(Cursor.DEFAULT));

        quit.setOnMouseClicked(mouseEvent -> {
            GeneralStringRequestCommand generalStringRequestCommand = new GeneralStringRequestCommand(QUIT);
            commandGUIManager.manageCommand(generalStringRequestCommand);
        });
    }

    public void createWaitScene(String string, GraphicsContext context){
        context.drawImage(background, 0, 0, 1024, 700);
        context.drawImage(askPlayers, 335, 320, askPlayers.getWidth()/1.5, askPlayers.getHeight()/2);
        context.setStroke(Color.WHITE);
        context.setFont(new Font("", 18));
        switch (string) {
            case PLAYER:
                context.strokeText(WAIT_PLAYER, 363, 349);
                break;
            case COLOR:
                context.strokeText(WAIT_COLOR, 390, 349);
                break;
            case GAME:
                context.strokeText(WAIT_GAME, 363, 349);
                break;
        }
    }

    public void setCommandGUIManager(CommandGUIManager commandGUIManager) {
        this.commandGUIManager = commandGUIManager;
    }

    private Image loadImage(String name){
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(name);
        return new Image(inputStream);
    }

    private void insertColorButton(int number, ImageView imageView, List<ImageView> list){
        imageView.setFitWidth(imageView.getImage().getWidth()/4);
        imageView.setFitHeight(imageView.getImage().getHeight()/2);
        imageView.fitWidthProperty();
        imageView.fitHeightProperty();
        imageView.setSmooth(true);
        imageView.setCache(true);
        imageView.setY(540);
        switch (number){
            case 1:
                imageView.setX(130);
                break;
            case 2:
                imageView.setX(290);
                break;
            case 3:
                imageView.setX(450);
                break;
            case 4:
                imageView.setX(610);
                break;
            case 5:
                imageView.setX(770);
        }
        list.add(imageView);
    }


    public void set3DGui(){
        GraphicInterface gui=new GraphicInterface();
        commandGUIManager.setGraphicInterface(gui);
        gui.setCommandGUIManager(commandGUIManager);
        gui.start(stage);
    }
}
