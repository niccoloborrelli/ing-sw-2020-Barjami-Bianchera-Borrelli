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


public class Controller{

    private static final int STRING_RECEIVING = 0;
    private static final int SPACE_RECEIVING = 1;
    private static final int INT_RECEIVING = 2;

    private static final int UPDATE_TO_PRINT = 0;
    private static final int UPDATE_CHOICE = 1;
    private static final int UPDATE_GAME_FIELD = 2;
    private static final int UPDATE_ENDGAME = 3;

    private static final int FIRST_CHILD = 0;
    private static final int INVALID_VALUE = -1;
    private static final int INVALID_CODE = -1;
    private static final int INT_IF_BOOLEAN_TRUE = 1;
    private static final int INT_IF_BOOLEAN_FALSE = 0;

    private static final int BROADCAST = 0;
    private static final int SINGLE_COMMUNICATION = 1;
    private static final int ALL_NOT_ME = 2;

    private static final String CYAN = "cyan";
    private static final String GREY = "grey";
    private static final String RED = "red";
    private static final String PURPLE = "purple";
    private static final String WHITE="white";
    private static final String INVALID_COLOR = "none";

    private static final String ERROR = "error";
    private static final String ENDTURN = "endTurn";
    private static final String ENDGAME = "endGame";

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
    private static final String COLOR = "color";
    private static final String DOME = "dome";
    private static final String PLAYER = "player";
    private static final String NAME = "name";
    private static final String CHAR = "char";
    private static final String LOSE = "lose";
    private static final String WORKERSETTING = "workerSetting";
    private static final String SPECIFICATION = "specification";
    private static final String PREFIX ="<?xml version=\"1.0\" encoding=\"utf-8\"?>";


    private Player player;
    private HandlerHub handlerHub;
    private HashMap<String,List<String>> godMap;
    private Visitor visitor;

    public Controller() {
        visitor = new Visitor();
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public HashMap<String, List<String>> getGodMap() {
        return godMap;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setHandlerHub(HandlerHub handlerHub) {
        this.handlerHub = handlerHub;
    }

    /**
     * Sets visitor depending of message receveid.
     * Give input to model state.
     *
     * @param message is string receveid.
     */
    public void giveInputToModel(String message) {
        resetAttributeVisitor(visitor);
        int code = findCode(message);

        if (code == STRING_RECEIVING) {
            String operation = findString(message);
            visitor.setStringInput(operation);
        } else if (code == SPACE_RECEIVING) {
            HashMap<Worker, Space> workerSpaceHashMap = convertInSpaceAndWorker(message);
            setSpaceInput(workerSpaceHashMap);
        } else if (code == INT_RECEIVING) {
            int value = parseItemInt(message);
            visitor.setIntInput(value);
        }
        try {
            player.onInput(visitor);
        }catch (IOException ignored){}
    }

    /**
     * Sets the last worker and the last space in a determined attribute of visitor that contains both.
     * @param workerSpaceHashMap contains the worker and space to put into visitor.
     */

    private void setSpaceInput(HashMap<Worker, Space> workerSpaceHashMap){
        Worker temp = null;
        Space spazio = null;

        for(Worker worker: workerSpaceHashMap.keySet())
            temp = worker;

        for(Space space: workerSpaceHashMap.values())
            spazio = space;

        visitor.getSpaceInput().setSpace(spazio);
        visitor.getSpaceInput().setWorker(temp);
    }

    /**
     * Resets with invalid value all visitor's attribute.
     * @param visitor contains attributes to reset.
     */

    private void resetAttributeVisitor(Visitor visitor){
        visitor.setIntInput(INVALID_CODE);
        visitor.setStringInput(null);
        visitor.getSpaceInput().setSpace(null);
        visitor.getSpaceInput().setWorker(null);
    }

    /**
     * Finds and converts information from message to Worker and Space.
     * @param message contains information to find and convert.
     * @return the couple of worker and space chosen in message.
     * If there are no specified in message, it returns an invalid space or invalid worker.
     */

    private HashMap<Worker, Space> convertInSpaceAndWorker(String message) {
        HashMap<Worker, Space> workerSpaceHashMap = new HashMap<>();
        String numberInString;
        Space space;
        Worker worker;

        numberInString = findStringContentPart(message, WORKER);
        worker = parseInWorker(numberInString);

        if (worker != null) {
            space = convertInSpace(message);
            workerSpaceHashMap.put(worker, space);
        } else
            workerSpaceHashMap.put(new Worker(), new Space(INVALID_VALUE, INVALID_VALUE));

        return workerSpaceHashMap;
    }

    /**
     * Finds space information in message and convert it in a existing space (if possibile).
     * Otherwise, it creates an invalid space.
     * @param message is messagge receveid.
     * @return corresponding space from board if coordinates are valid, otherwise an invalid space.
     */

    private Space convertInSpace(String message) {
        Space space = null;
        int row;
        int column;

        Element element = elementInsideMessage(message);
        Element el = throughOneLevelNode(element);

        if (el != null) {
            row = findAndParseToInt(el, ROW);
            column = findAndParseToInt(el, COLUMN);
            space = player.getIslandBoard().getSpace(row, column);
        }

        if (space == null)
            space = new Space(INVALID_VALUE, INVALID_VALUE);

        return space;
    }

    /**
     * Finds target in the element and converts it in an integer number.
     * @param el is element in which method will search.
     * @param target is string to find.
     * @return the integer value of target, otherwise an invalid code.
     */

    private int findAndParseToInt(Element el, String target){
        String targetInString = findTarget(el, target);
        return parseToInt(targetInString);
    }

    /**
     * Returns DOM element inside the string passed.
     * @param message is string in which it will be searched.
     * @return DOM element inside the string passed
     */

    private Element elementInsideMessage(String message){
        Document doc = buildDocument(message);
        Element element = insideMessage(doc);
        return element;
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
                    coord = element.getTextContent();
            }
        }
        return coord;
    }

    /**
     * Parse value in integer value if contains only numbers.
     * @param value is value to parser
     * @return this integer value (if possible), otherwise an invalid value;.
     */

    private int parseToInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException | NullPointerException e) {
            return INVALID_VALUE;
        }
    }

    /**
     * Go through one level of node.
     * @param element is start element.
     * @return element in the next level of node.
     */
    private Element throughOneLevelNode(Element element) {
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
     * @param numberInString is information to convert.
     * @return worker of game if exists, otherwise a new worker.
     */

    private Worker parseInWorker(String numberInString) {
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
     * Finds content of string with the information required.
     * @param message    is message to analyze.
     * @param researched is information required.
     * @return part of string with information required if it exists, otherwise null.
     */

    private String findStringContentPart(String message, String researched) {
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
     * Finds and converts information from message to a integer value.
     * @param message
     * @return
     */

    private int parseItemInt(String message) {
        int value = -1;
        String valueInString;

        valueInString = findStringContentPart(message, INT);

        value = parseToInt(valueInString);

        return value;
    }

    /**
     * Finds string information from message.
     *
     * @param message is message containing information.
     * @return information.
     */

    private String findString(String message) {

        return findStringContentPart(message, STRING);
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

    /**
     * Converts int and covers it with the field.
     * @param content is what it will be converted.
     * @param field is field that covers string.
     * @return string that represents int, covered with field.
     */

    private String intToCoveredString(int content, String field){
        String contentString = String.valueOf(content);
        return generateField(contentString, field);
    }

    /**
     * Converts a boolean in a int.
     * @param value is boolean converted.
     * @return value that represents the true of boolean, otherwise another boolean that represents boolean false.
     */
    private int convertBooleanToInt(boolean value){
        return value ? INT_IF_BOOLEAN_TRUE : INT_IF_BOOLEAN_FALSE;
    }


    public void update(LastChange lastChange){
        String data = buildUpdate(lastChange);
        int codeCommunication = howToCommunicate(lastChange.getCode(), lastChange);
        handlerHub.sendData(PREFIX + data, this, codeCommunication);
    }

    public int howToCommunicate(int code, LastChange lastChange){
        int codeCommunication = INVALID_VALUE;

        switch (code) {
            case UPDATE_TO_PRINT:
                codeCommunication = findCommunication(lastChange.specification);
                break;
            case UPDATE_CHOICE:
            case UPDATE_ENDGAME:
                codeCommunication = SINGLE_COMMUNICATION;
                break;
            case UPDATE_GAME_FIELD:
                codeCommunication = BROADCAST;
                break; 
        }

        return codeCommunication;
    }


    public String buildUpdate(LastChange lastChange){
        String message = "";
        int code = lastChange.getCode();
        String codeInString = insertCode(code);
        String specification = generateField(lastChange.getSpecification(), SPECIFICATION);
        String playerString = createPlayerString(player);

        switch (code) {
            case UPDATE_TO_PRINT:
                message = buildUpdateToPrintMessage();
                break;
            case UPDATE_CHOICE:
                message = buildUpdateChoiceMessage(lastChange);
                break;
            case UPDATE_GAME_FIELD:
                message = buildUpdateGameFieldMessage(lastChange);
                break;
            case UPDATE_ENDGAME:
                sendLostToOthers(codeInString, playerString);
        }

        return generateField(codeInString+ playerString + specification+message, DATA);

    }

    /**
     * Creates a XML string that contains player's name and color.
     * @param player is the player that makes the change.
     * @return XML string that contains player's name and color.
     */


    private String createPlayerString(Player player){
        String color = player.getPlayerColor();
        String name = player.getPlayerName();

        String colorCovered = generateField(color, COLOR);
        String nameCovered = generateField(name, NAME);

        return generateField(nameCovered+colorCovered, PLAYER);
    }

    /**
     * Creates a XMl empty message.
     * @return a XML empty message.
     */

    private String buildUpdateToPrintMessage(){
        return generateField("", MESSAGE);
    }

    /**
     * Finds code of communication that need string.
     * @param string contains information about type of connection.
     * @return code of communication.
     */

    private int findCommunication(String string){
        if(string.equals(ERROR) || string.equals(ENDTURN) || string.equals(NAME) || string.equals(WORKERSETTING))
                return SINGLE_COMMUNICATION;
        else if(string.equals(ENDGAME))
                return BROADCAST;
        return INVALID_CODE;
    }

    /**
     * Builds a XML string that contains available inputs accepted by model.
     * @param dataOutput contains lists of acceptable parameters.
     * @return a XML string that contains available inputs accepted by model.
     */

    private String buildUpdateChoiceMessage(LastChange dataOutput){
        StringBuilder message = new StringBuilder();
        message.append(generateField(generateWorker(dataOutput.getWorker()), WORKER));
        StringBuilder insideMessage = generateIntList(message, dataOutput.getIntegerList());
        StringBuilder spaceList = generateSpaceList(insideMessage, dataOutput.getListSpace());
        StringBuilder stringList = generateStringList(spaceList, dataOutput.getStringList());

        return generateField(stringList.toString(), MESSAGE);
    }

    private String generateWorker(Worker worker){
        int numberOfWorker = player.getWorkers().indexOf(worker);
        String letterOfWorker = convertIntToLetter(numberOfWorker);

        return generateField(letterOfWorker, CHAR);
    }

    private String convertIntToLetter(int number){
        if(number==0)
            return "A";
        else if(number==1)
            return "B";
        return "C";
    }

    /**
     * Generates a string that represents an integer list.
     * @param message is StringBuilder that would build string.
     * @param integerList is integer list.
     * @return StringBuilder that contains list.
     */

    private StringBuilder generateIntList(StringBuilder message, List<Integer> integerList){
        if(integerList!=null) {
            for (Integer i : integerList)
                message.append(generateField(String.valueOf(i), INT));
        }
        return message;
    }

    private StringBuilder generateStringList(StringBuilder message, List<String> stringList){
        if(stringList != null){
            for(String s: stringList)
                message.append(generateField(s, STRING));
        }
        return message;
    }

    /**
     * Generates a string that represents a space list.
     * @param message is StringBuilder that would build string.
     * @param spaceList is space list.
     * @return StringBuilder that contains list.
     */

    private StringBuilder generateSpaceList(StringBuilder message, List<Space> spaceList){
        if(spaceList!=null) {
            for (Space space : spaceList)
                message.append(generateStringSpace("",space, SPACE));
        }
        return message;
    }

    /**
     * Builds a XML string that represents change according to an action.
     * @param dataOutput contains information about change and action.
     * @return a XML string that represents change according to an action.
     */

    private String buildUpdateGameFieldMessage(LastChange dataOutput){
        String data = null;
        String causeOfChange = dataOutput.getSpecification();

        switch (causeOfChange){
            case MOVE:
                data = updateMovement(dataOutput.getWorker(), dataOutput.getSpace());
                break;
            case BUILD:
                data = updateBuilding(dataOutput.getSpace());
        }
        return data;
    }

    /**
     * Builds a XML string that represent change due to a move action.
     * @param worker is worker that moved.
     * @param oldSpace is space in which worker is before movement.
     * @return a XML string that represent change due to a move action.
     */

    private String updateMovement(Worker worker, Space oldSpace) {
        String charOfWorker = convertIntToLetter(player.getWorkers().indexOf(worker));
        String workerTranslation = generateStringSpace(generateField(charOfWorker, CHAR), worker.getWorkerSpace(), WORKER);
        String spaceTranslation = generateStringSpace("", oldSpace, SPACE);

        return generateField(workerTranslation + spaceTranslation, MESSAGE);
    }

    /**
     * Builds a XML string that represent change due to a build action.
     * @param space is space in which a worker built.
     * @return a XML string that represent change due to a build action.
     */

    private String updateBuilding(Space space) {
        String rowPart = intToCoveredString(space.getRow(), ROW);
        String columnPart = intToCoveredString(space.getColumn(), COLUMN);
        String levelPart = intToCoveredString(space.getLevel(), LEVEL);
        String domePart = intToCoveredString(convertBooleanToInt(space.HasDome()), DOME);

        String spacePart = generateField(rowPart + columnPart + levelPart + domePart, SPACE);
        return generateField(spacePart, MESSAGE);
    }

    /**
     * Generates a string that represents a space.
     * @param space is space that will be represented.
     * @param field is XML field that contains space.
     * @return a string that represents a space.
     */

    private String generateStringSpace(String otherParameters, Space space, String field) {
        String row = String.valueOf(space.getRow());
        String column = String.valueOf(space.getColumn());

        String rowPart = generateField(row, ROW);
        String columnPart = generateField(column, COLUMN);

        return generateField(otherParameters + rowPart + columnPart, field);
    }

    private void sendLostToOthers(String code, String player){
        String message = generateField("", MESSAGE);
        String specification = generateField(LOSE, SPECIFICATION);
        String data = generateField(code + player + specification + message, DATA);
        handlerHub.sendData(data, this, ALL_NOT_ME);
    }

    /**
     * Covers a content with XMl field.
     * @param content is content to cover.
     * @param field is field that cover content.
     * @return content covered with field according to XML.
     */

    private String generateField(String content, String field) {
        String begin = BEGIN_FIELD + field + END;
        String end = END_FIELD + field + END;

        return begin + content + end;
    }

    /**
     * Converts to a string and creates a XMl string that contains a code.
     * @param code is code to convert and cover.
     * @return a XML string that represent code.
     */

    private String insertCode(int code) {
        String codeString = String.valueOf(code);
        return generateField(codeString, CODE);
    }


    /**
     * Parses and creates god map.
     */
    public void createGodMap(){
        Parser parser=new Parser(new File("C:\\Users\\Yoshi\\Desktop\\Gods.txt"));
        this.godMap=parser.createHashRepresentation();
    }

    /**
     * Decorates player  according to god map.
     * @param playerToDecorate is player to decorate.
     * @throws NoSuchMethodException
     * @throws ClassNotFoundException
     */

    public void decoratePlayer(Player playerToDecorate) throws NoSuchMethodException, ClassNotFoundException {
        GodFactory godFactory=new GodFactory();
        createGodMap();
        godFactory.decoratePlayer(godMap,playerToDecorate);
    }

    /**
     * Create the base flux table.
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */

    public void createFluxTable() throws IOException, SAXException, ParserConfigurationException {
        TableXML tableXML = new TableXML(new File("C:\\Users\\Yoshi\\Desktop\\table.txt"),player);
        HashMap<State, List<Line>> table = tableXML.readXML(player.getStateManager().getStateHashMap());
        player.getStateManager().setTable(table);
        player.getStateManager().sortAllTable();

    }

}