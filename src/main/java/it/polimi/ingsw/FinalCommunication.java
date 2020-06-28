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
}
