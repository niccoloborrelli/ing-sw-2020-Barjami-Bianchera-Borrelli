package it.polimi.ingsw;

public class FinalCommunication {

    /**
     * This class contains most of static final used to parse and deparse message in communication classes.
     */

    public static final String DISCONNECTION = "disconnection";
    public static final String MESSAGE = "message";
    public static final String SPACE_STRING = " ";
    public static final String TWO_POINTS = ": ";
    public static final String BEGIN_FIELD = "<";
    public static final String END = ">";
    public static final String END_FIELD = "</";
    public static final String WORKER = "worker";
    public static final String ROW = "row";
    public static final String COLUMN = "column";
    public static final String SPACE = "space";
    public static final String INT = "int";
    public static final String STRING = "string";
    public static final String CODE = "code";
    public static final String DATA = "data";
    public static final String LEVEL = "level";
    public static final String COLOR = "color";
    public static final String DOME = "dome";
    public static final String SPECIFICATION = "specification";
    public static final String MOVE = "move";
    public static final String BUILD = "build";
    public static final String PLAYER = "player";
    public static final String NAME = "name";
    public static final String ENDGAME = "endGame";
    public static final String CHAR = "char";
    public static final String SET_UP = "setUp";
    public static final String GAME = "game";
    public static final String endMessage = "<data><code>3</code><player></player><specification>disconnection</specification><message></message></data>";
    public static final String PREFIX = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";

    public static final String ERROR = "error";
    public static final String ENDTURN = "endTurn";
    public static final String WORKERSETTING = "workerSetting";
    public static final String POWER = "power";
    public static final String PRE_LOBBY = "preLobby";
    public static final String GODSET = "godSet";
    public static final String GODCHOICE = "godChoice";
    public static final String LOSE = "lose";
    public static final String LOST = "lost";
    public static final String _GOD = "-god";
    public static final String _HELP = "-help";
    public static final String HELP = "help";
    public static final String WHAT_TO_DO = "-whatToDo";
    public static final String GOD = "god";
    public static final String WIN = "win";
    public static final String DELETED = "deleted";
    public static final String QUIT = "quit";
    public static final String WAITING_COLOR = "waitingColor";
    public static final String WAITING_GAME = "waitingGame";
    public static final String WAITING_PLAYER = "waitingPlayer";

    public static final String SANTORINI = "Santorini";
    public static final String HOW_MANY_PLAYERS = "How many players to play?";
    public static final String CHOOSE_GODS = "Choose the gods to use in the match";
    public static final String CHOOSE_GOD = "Choose the god to use in this match";
    public static final String INSERT_NAME = "Insert your name";
    public static final String INSERT_COLOR = "Insert your color";
    public static final String DISCONNECTED = "Somebody disconnected from the game";

    public static final String WAIT_PLAYER = "Waiting for players";
    public static final String WAIT_COLOR = "Waiting for players to choose color";
    public static final String WAIT_GAME = "Waiting for the game to start";

    public static final String FIRST_WORKER = "A";
    public static final String SECOND_WORKER = "B";
    public static final String INVALID_CHAR = "C";

    public static final int STRING_CODE = 0;
    public static final int ACTION_CODE = 1;
    public static final int INT_CODE = 2;

    public static final int INVALID_VALUE = -1;
    public static final int INVALID_CODE = -1;

    public static final int FIRST_CHILD = 0;
    public static final int FIRST_INDEX = 0;
    public static final int SECOND_INDEX = 1;

    public static final int UPDATE_TO_PRINT = 0;
    public static final int UPDATE_CHOICE = 1;
    public static final int UPDATE_GAME_FIELD = 2;
    public static final int UPDATE_ENDGAME = 3;

    public static final int BROADCAST = 0;
    public static final int SINGLE_COMMUNICATION = 1;
    public static final int ALL_NOT_ME = 2;

    public static final String RED_COLOR = "red";
    public static final String WHITE_COLOR = "white";
    public static final String CYAN_COLOR = "cyan";
    public static final String GREY_COLOR = "grey";
    public static final String PURPLE_COLOR = "purple";

    public static final String TWOPLAYERS = "2";
    public static final String THREEPLAYERS = "3";

    public static final String APOLLO = "Apollo";
    public static final String ARTEMIS = "Artemis";
    public static final String ATHENA = "Athena";
    public static final String ATLAS = "Atlas";
    public static final String DEMETER = "Demeter";
    public static final String CHRONUS = "Chronus";
    public static final String HEPHAESTUS = "Hephaestus";
    public static final String HESTIA = "Hestia";
    public static final String HYPNUS = "Hypnus";
    public static final String MINOTAUR = "Minotaur";
    public static final String PAN = "Pan";
    public static final String PERSEPHONE = "Persephone";
    public static final String PROMETHEUS = "Prometheus";
    public static final String ZEUS = "Zeus";

    public static final String APOLLOCARD = "card_apollo.png";
    public static final String ARTEMISCARD = "card_artemis.png";
    public static final String ATHENACARD = "card_athena.png";
    public static final String ATLASCARD = "card_atlas.png";
    public static final String DEMETERCARD = "card_demeter.png";
    public static final String CHRONUSCARD = "card_chronus.png";
    public static final String HEPHAESTUSCARD = "card_hephaestus.png";
    public static final String HESTIACARD = "card_hestia.png";
    public static final String HYPNUSCARD = "card_hypnus.png";
    public static final String MINOTAURCARD = "card_minotaur.png";
    public static final String PANCARD = "card_pan.png";
    public static final String PERSEPHONECARD = "card_persephone.png";
    public static final String PROMETHEUSCARD = "card_prometheus.png";
    public static final String ZEUSCARD = "card_zeus.png";

    public static final String BACKGROUND = "bg_modeselect.png";
    public static final String ASKER = "clp_textbar.png";
    public static final String QUIT_GAME = "Destroy.png";

    public static final String TITLE_WATER = "title_water.png";
    public static final String SANTORINI_LOGO = "santorini-logo.png";
    public static final String TITLE_ISLAND = "title_island.png";
    public static final String TITLE_FAR_ISLAND = "title_far_island.png";
    public static final String TITLE_POSEIDON = "title_poseidon.png";
    public static final String TITLE_APHRODITE = "title_aphrodite.png";
    public static final String POSEIDON_HAND = "title_poseidon_hand.png";
    public static final String LEFT_TOP_CLOUD = "title_FG_cloud.png";
    public static final String RIGHT_TOP_CLOUD = "title_FG_cloud2.png";
    public static final String TITLE_GRASS = "title_FG_grass.png";
    public static final String TITLE_BOAT = "title_boat_right.png";

    public static final String RED_BUTTON = "btn_coral.png";
    public static final String GREY_BUTTON = "btn_small_gray.png";
    public static final String CYAN_BUTTON = "btn_blue.png";
    public static final String PURPLE_BUTTON = "btn_purple.png";
    public static final String WHITE_BUTTON = "btn_white.png";

}
