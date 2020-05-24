package it.polimi.ingsw;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;

public class Controller implements Observer {

    private static final int STRING_RECEIVING = 0;
    private static final int SPACE_RECEIVING = 1;
    private static final int INT_RECEIVING = 2;
    private static final int FIRST_CHILD = 0;
    private static final int INVALID_COORDINATE = -1;
    private static final int INVALID_CODE = -1;
    private static final int PRINT = 0;
    private static final int UPDATE_MOVEMENT = 3;
    private static final int UPDATE_BUILDING = 4;
    private static final int BROADCAST = 0;
    private static final int SINGLE_COMMUNICATION = 1;
    private static final int ALL_NOT_ME = 2;
    private static final String BEGIN_FIELD = "<";
    private static final String END = ">";
    private static final String END_FIELD = "</";
    private static final String WORKER = "worker";
    private static final String ROW = "row";
    private static final String COLUMN = "column";
    private static final String SPACE = "space";
    private static final String INT = "int";
    private static final String STRING = "string";
    private static final String CODE = "code";
    private static final String MESSAGE = "message";
    private static final String DATA = "data";
    private static final String LEVEL = "level";
    private static final String MOVE = "move";
    private static final String BUILD = "build";
    private static final String ENDTURN = "Turn completed. Good job";
    private static final String ENDGAME = "Game is finished. Press quit to exit.";
    private static final String WINNER = "Congratulations! You won.";
    private static final String LOSER = "I'm sorry, you lost.";
    private static final String POWER_ACTIVATION = "Do you want to use yor God power?";


    private Player player;
    private HandlerHub handlerHub;
    private HashMap<String,List<String>> godMap;
    private Visitor visitor;

    public Controller() {
        visitor = new Visitor();
    }

    /**
     * Sets visitor depending of message receveid.
     * Give input to model state.
     *
     * @param message is string receveid.
     */
    public void giveInput(String message) {
        int code = findCode(message);

        if (code == STRING_RECEIVING) {
            String operation = parseString(message);
            visitor.setStringInput(operation);
        } else if (code == SPACE_RECEIVING) {
            HashMap<Worker, Space> workerSpaceHashMap = parseSpace(message);
            setSpaceInput(workerSpaceHashMap);
        } else if (code == INT_RECEIVING) {
            int value = parseItemInt(message);
            visitor.getSpaceInput().setAnInt(value);
        }
    }

    private void setSpaceInput(HashMap<Worker, Space> workerSpaceHashMap){
        for(Worker worker: workerSpaceHashMap.keySet())
            visitor.getSpaceInput().setWorker(worker);

        for(Space space: workerSpaceHashMap.values())
            visitor.getSpaceInput().setSpace(space);
    }

    /**
     * Find and convert information from message to Worker and Space.
     *
     * @param message
     * @return the couple of worker and space chosen in message.
     * If there are no specified in message, it returns an invalid space or invalid worker.
     */

    private HashMap<Worker, Space> parseSpace(String message) {
        HashMap<Worker, Space> workerSpaceHashMap = new HashMap<>();
        String numberInString;
        Space space;
        Worker worker;

        numberInString = parseOnSingular(message, WORKER);
        worker = parseWorker(numberInString);

        if (worker != null) {
            space = parseOnSpace(message);
            workerSpaceHashMap.put(worker, space);
        } else
            workerSpaceHashMap.put(new Worker(), new Space(INVALID_COORDINATE, INVALID_COORDINATE));

        return workerSpaceHashMap;
    }

    /**
     * Finds space information in message and convert it in a existing space (if possibile).
     * Otherwise, it creates an invalid space.
     *
     * @param message is messagge receveid.
     * @return corresponding space from board if coordinates are valid, otherwise an invalid space.
     */

    private Space parseOnSpace(String message) {
        Space space = null;
        String rowInString;
        String columnInString;
        int row;
        int column;

        Document doc = buildDocument(message);
        Element element = insideMessage(doc);
        Element el = multipleAttributes(element);

        if (el != null) {
            rowInString = findTarget(el, ROW);
            columnInString = findTarget(el, COLUMN);

            row = parseValue(rowInString);
            column = parseValue(columnInString);

            space = player.getIslandBoard().getSpace(row, column);
        }

        if (space == null)
            space = new Space(INVALID_COORDINATE, INVALID_COORDINATE);

        return space;


    }

    /**
     * Finds target and gets its text content.
     *
     * @param el     is element in which researching.
     * @param target is parameter to find.
     * @return target text content if it exists, otherwise null.
     */

    private String findTarget(Element el, String target) {
        String coord = null;
        NodeList nodeList = el.getElementsByTagName(target);
        if (nodeList != null) {
            Node node = nodeList.item(FIRST_CHILD);
            if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (!node.hasChildNodes()) {
                    coord = element.getTextContent();
                }
            }
        }

        return coord;

    }

    /**
     * Parse value in integer value if contains only numbers.
     *
     * @param value is value to parser
     * @return this integer value (if possible), otherwise null.
     */

    private int parseValue(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException | NullPointerException e) {
            return INVALID_COORDINATE;
        }
    }

    /**
     * Go through one level of node.
     *
     * @param element is start element.
     * @return element in the next level of node.
     */
    private Element multipleAttributes(Element element) {
        Element el = null;
        NodeList nodeSpaces = element.getElementsByTagName(SPACE);
        if (nodeSpaces != null) {
            Node spaceNode = nodeSpaces.item(FIRST_CHILD);
            if (spaceNode != null && spaceNode.getNodeType() == Node.ELEMENT_NODE) {
                el = (Element) spaceNode;
            }
        }

        return el;
    }

    /**
     * Converts information of string in a object Worker.
     *
     * @param numberInString is information to convert.
     * @return worker of game if exists, otherwise a new worker.
     */

    private Worker parseWorker(String numberInString) {
        Worker worker = null;
        int number;

        if (numberInString != null) {
            try {
                number = Integer.parseInt(numberInString);
                if (number < player.getWorkers().size())
                    worker = player.getWorkers().get(number);
                else
                    worker = new Worker();
            } catch (NumberFormatException e) {
                worker = new Worker();
            }
        }

        return worker;
    }

    /**
     * Finds part of string with of information required.
     *
     * @param message    is message to analyze.
     * @param researched is information required.
     * @return part of string with information required if it exists, otherwise null.
     */

    private String parseOnSingular(String message, String researched) {
        Document doc = buildDocument(message);
        String text = null;

        Element element = insideMessage(doc);
        if (element != null) {
            text = findTarget(element, researched);
        }

        return text;
    }

    /**
     * Find the informations in this first wrapper.
     *
     * @param document is document in which search information.
     * @return element containing lists of information.
     */

    private Element insideMessage(Document document) {
        Element element = null;

        if (document != null) {
            NodeList nodeList = document.getElementsByTagName(MESSAGE);
            Node node = nodeList.item(FIRST_CHILD);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                element = (Element) node;

            }
        }

        return element;
    }

    /**
     * Finds and converts information from message to a integer value
     *
     * @param message
     * @return
     */

    private int parseItemInt(String message) {
        int value = -1;
        String valueInString;

        valueInString = parseOnSingular(message, INT);

        value = parseValue(valueInString);

        return value;
    }

    /**
     * Finds string information from message.
     *
     * @param message is message containing information.
     * @return information.
     */

    private String parseString(String message) {

        return parseOnSingular(message, STRING);
    }

    /**
     * Finds and traduces code in message.
     *
     * @param message is message containing code.
     * @return code if valid, otherwise an invalid integer.
     */

    private int findCode(String message) {
        Document doc = buildDocument(message);
        int code = INVALID_CODE;
        if (doc != null) {
            NodeList nodeList = doc.getElementsByTagName(CODE);
            Node node = nodeList.item(FIRST_CHILD);
            if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
                String value = nodeList.item(0).getTextContent();
                try {
                    code = Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    return INVALID_CODE;
                }
            }
        }

        return code;
    }

    /**
     * Build document DOM from a string.
     *
     * @param message is message on which this document is built.
     * @return document created.
     */
    private Document buildDocument(String message) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(message));
            Document doc = db.parse(is);
            doc.normalize();
            return doc;
        } catch (ParserConfigurationException | IOException | SAXException e) { //bisogna vedere come gestirle
            e.printStackTrace();
        }
        return null;
    }

    public void update(SpaceInput spaceInput, String action){
        if(action.equals(MOVE))
            updateMovement(spaceInput.getWorker(), spaceInput.getSpace());
        else if(action.equals(BUILD))
            updateBuilding(spaceInput.getSpace());
    }

    private void updateMovement(Worker worker, Space space) {
        String workerTranslation = generateStringWorker(worker);
        String spaceTranslation = generateStringSpace(space);

        String code = insertCode(UPDATE_MOVEMENT);

        String message = generateField(workerTranslation + spaceTranslation, MESSAGE);

        String data = generateField(code + message, DATA);

        handlerHub.sendData(data, this, BROADCAST);
    }

    private void updateBuilding(Space space) {
        String code = insertCode(UPDATE_BUILDING);

        String row = String.valueOf(space.getRow());
        String column = String.valueOf(space.getColumn());
        String level = String.valueOf(space.getLevel());

        String rowPart = generateField(row, ROW);
        String columnPart = generateField(column, COLUMN);
        String levelPart = generateField(level, LEVEL);

        String message = generateField(rowPart + columnPart + levelPart, MESSAGE);
        String data = generateField(code + message, DATA);

        handlerHub.sendData(data, this, BROADCAST);

    }

    public void update(int code){
        if(code==0)
            updateActivationPower();
        else if(code==1)
            updateEndTurn();
        else if(code==2)
            updateEndTurn();
        else if(code==3)
            updateLost();
        else if(code==4)
            updateWon();
        else if(code==5)
            updateEndGame();
    }



    private void updateEndTurn() {
        String data = dataOnlyToPrint(ENDTURN);

        handlerHub.sendData(data, this, SINGLE_COMMUNICATION);
    }

    private void updateEndGame() {
        String data = dataOnlyToPrint(ENDGAME);

        handlerHub.sendData(data, this, BROADCAST);
    }

    private void updateActivationPower() {
        String data = dataOnlyToPrint(POWER_ACTIVATION);

        handlerHub.sendData(data, this, SINGLE_COMMUNICATION);
    }

    private void updateWon() {
        String dataWin = dataOnlyToPrint(WINNER);
        String dataLose = dataOnlyToPrint(LOSER);

        handlerHub.sendData(dataWin, this, SINGLE_COMMUNICATION);
        handlerHub.sendData(dataLose, this, ALL_NOT_ME);
    }

    private void updateLost() {
        String dataLose = dataOnlyToPrint(LOSER);

        handlerHub.sendData(dataLose, this, SINGLE_COMMUNICATION);
    }

    public void update(List<Space> spaceList) {
        StringBuilder mess = new StringBuilder();
        for (Space space : spaceList) {
            mess.append(generateStringSpace(space));
        }

        dataOnlyToPrint(mess.toString());
    }

    private String generateStringWorker(Worker worker) {
        String workerSpace = generateStringSpace(worker.getWorkerSpace());

        return generateField(workerSpace, WORKER);
    }

    private String generateStringSpace(Space space) {
        String row = String.valueOf(space.getRow());
        String column = String.valueOf(space.getColumn());

        String rowPart = generateField(row, ROW);
        String columnPart = generateField(column, COLUMN);

        return generateField(rowPart + columnPart, SPACE);
    }

    private String generateField(String content, String field) {
        String begin = BEGIN_FIELD + field + END;
        String end = END_FIELD + field + END;

        return begin + content + end;
    }

    private String insertCode(int code) {
        String codeString = String.valueOf(code);

        return generateField(codeString, CODE);
    }

    private String dataOnlyToPrint(String mess) {
        String code = insertCode(PRINT);
        String message = generateField(mess, MESSAGE);

        return generateField(code + message, DATA);
    }

    public void createGodMap(){
        Parser parser=new Parser(new File("C:\\Users\\Yoshi\\Desktop\\Gods.txt"));
        this.godMap=parser.createHashRepresentation();
    }

    public void decoratePlayer(Player playerToDecorate) throws NoSuchMethodException, ClassNotFoundException {
        GodFactory godFactory=new GodFactory();
        godFactory.decoratePlayer(godMap,playerToDecorate);
    }

    public void createFluxTable() throws IOException, SAXException, ParserConfigurationException {
        TableXML tableXML = new TableXML(new File("C:\\Users\\Yoshi\\Desktop\\table.txt"),player);
        player.getStateManager().createBaseStates(player);
        HashMap<State, List<Line>> table = tableXML.readXML(player.getStateManager().getStateHashMap());
        player.getStateManager().setTable(table);
    }

}