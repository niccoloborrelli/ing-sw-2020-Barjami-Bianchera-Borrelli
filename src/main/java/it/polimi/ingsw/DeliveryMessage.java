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
import java.io.IOException;
import java.io.StringReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static it.polimi.ingsw.ColorConverter.*;
import static it.polimi.ingsw.FinalCommunication.*;

public class DeliveryMessage {

    /**
     * This class permits the correct switching of incoming packets between interfaces with a previous de-parsing.
     * It permits also the correct formalization of messages sent by client.
     */

    private static final String ACTION = "w";
    private static final String SPECIAL_CHAR1 = "+";
    private static final String SPECIAL_CHAR2 = "-";
    private static final int MINIMUM_LENGTH_INT = 1;
    private static final int EMPTY = 0;

    private Command command;
    private NetHandler netHandler;


    public DeliveryMessage(Socket sc) throws IOException {
        netHandler = new NetHandler(this, sc);
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    /**
     * Traduces input e sends it to net manager.
     * @param input is input received.
     */

    public void send(String input) {
        int code = decodeInput(input);
        String cd = generateField(String.valueOf(code), CODE);
        String message;

        if (code == ACTION_CODE) {
            message = buildActionMessage(input);
        } else if (code == INT_CODE) {
            message = buildIntMessage(input);
        }else {
            message = buildStringMessage(input);
        }

        String coveredMessage = generateField(message, MESSAGE);
        String data = generateField(cd + coveredMessage, DATA);

        netHandler.sendMessage(PREFIX + data);
    }


    /**
     * Decodes type of message.
     * @param input is input received.
     * @return code that represents type of message.
     */
    private int decodeInput(String input) {
        int code = STRING_CODE;

        if (isItWorkerAction(input)) {
            code = ACTION_CODE;
        }else if (isItInt(input)) {
            code = INT_CODE;
        }

        return code;

    }

    /**
     * Determines if input represents a worker's action.
     * @param input is input received.
     * @return true if it is, otherwise false.
     */

    private boolean isItWorkerAction(String input) {
        if (input.contains(ACTION) && input.contains(SPECIAL_CHAR1) && input.contains(SPECIAL_CHAR2)) {
            if (input.substring(FIRST_INDEX, SECOND_INDEX).equals(ACTION) && input.indexOf(ACTION) < input.indexOf(SPECIAL_CHAR1) && input.indexOf(ACTION) < input.indexOf(SPECIAL_CHAR2)) {
                return input.indexOf(SPECIAL_CHAR1) < input.indexOf(SPECIAL_CHAR2) - MINIMUM_LENGTH_INT && input.indexOf(SPECIAL_CHAR2) < input.length() - MINIMUM_LENGTH_INT;
            }
        }
        return false;
    }

    /**
     * Determines if input represents an int.
     * @param input is input received.
     * @return true if it is, otherwise false.
     */

    private boolean isItInt(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Builds action's message according to rules of communication.
     * @param input is input received.
     * @return message codified.
     */

    private String buildActionMessage(String input) {
        String workerNumb = input.substring(input.indexOf(ACTION) + 1, input.indexOf(SPECIAL_CHAR1));
        String worker = generateField(workerNumb, WORKER);
        String space = buildSpace(input.substring(input.indexOf(SPECIAL_CHAR1) + 1));

        return generateField(worker + space, MESSAGE);

    }

    /**
     * Builds space's message according to rules of communication.
     * @param input is input received.
     * @return message codified.
     */

    private String buildSpace(String input) {
        String row = input.substring(FIRST_INDEX, input.indexOf(SPECIAL_CHAR2));
        String column = input.substring(input.indexOf(SPECIAL_CHAR2) + 1);

        String coverRow = generateField(row, ROW);
        String coverColumn = generateField(column, COLUMN);

        return generateField(coverRow + coverColumn, SPACE);
    }

    /**
     * Builds int's message according to rules of communication.
     * @param input is input received.
     * @return int's string codified.
     */

    private String buildIntMessage(String input) {
        return generateField(input, INT);
    }

    /**
     * Builds string's message according to rules of communication.
     * @param input is input received.
     * @return string codified.
     */

    private String buildStringMessage(String input) {
        return generateField(input, STRING);
    }

    /**
     * Covers content string according to XML rules creating a field passed.
     * @param content is string covered.
     * @param field   is field that cover content.
     * @return content covered with field.
     */

    private String generateField(String content, String field) {
        String begin = BEGIN_FIELD + field + END;
        String end = END_FIELD + field + END;

        return begin + content + end;
    }


    /**
     * Parses message received and send it to client interface.
     * @param message is message received.
     */

    public void receive(String message) {
        Document doc = buildDocument(message);

        int code = findCode(doc);
        String specification = findSpecification(doc);
        String playerName = findPlayerAttribute(doc, NAME);
        String playerColor = getCodeColor(findPlayerAttribute(doc, COLOR));

        if (code == UPDATE_TO_PRINT || code == UPDATE_ENDGAME) {
            sendToInterface(specification, playerName, playerColor);
        } else if (code == UPDATE_CHOICE) {
            decodePossibleChoice(doc, specification, playerName, playerColor);
        } else if (code == UPDATE_GAME_FIELD) {
            decodeUpdate(doc, specification, playerName, playerColor);
        }

    }

    /**
     * Finds field inside player's part of string.
     * @param doc is document containing information.
     * @param field is field searched
     * @return content of field in player's part.
     */

    private String findPlayerAttribute(Document doc, String field) {
        Element el = insideField(doc, PLAYER);
        if (el != null) {
            NodeList nodeList = el.getElementsByTagName(field);
            Node node = nodeList.item(FIRST_CHILD);
            if(node!=null && node.getNodeType() == Node.ELEMENT_NODE){
                return node.getTextContent();
            }
        }
        return null;
    }

    /**
     * Finds specification part in document.
     * @param doc is document containing specification.
     * @return specification's content.
     */

    private String findSpecification(Document doc) {
        String specification = null;
        Element el = insideField(doc, SPECIFICATION);
        if (el != null) {
            specification = el.getTextContent();
        }

        return specification;
    }

    /**
     * Decodes and sends to interface string containing possible choices.
     * @param doc contains information.
     * @param specification specifies type of message.
     * @param playerName is player's name.
     * @param playerColor is player's color.
     */

    private void decodePossibleChoice(Document doc, String specification, String playerName, String playerColor){
        String search;
        if(specification.equals(POWER) || specification.equals(PRE_LOBBY)) {
            search = INT;
        }else {
            search = STRING;
        }
        List<String> stringList = findListToPrint(doc, search);
        List<HashMap<String, String>> hashMapList = findHashMapToPrint(doc);
        String worker = findInsideWorker(doc);

        if(stringList.size()!=EMPTY) {
            sendToInterface(stringList, specification, playerName, playerColor);
        }else {
            sendToInterface(worker, hashMapList, specification, playerName, playerColor);
        }
    }

    public NetHandler getNetHandler() {
        return netHandler;
    }

    /**
     * Finds information inside worker's field.
     * @param document contains information.
     * @return content of worker's field.
     */

    private String findInsideWorker(Document document){
        Element el = insideField(document, MESSAGE);
        String worker = null;
        String content = null;
        NodeList nodeList = el.getElementsByTagName(WORKER);
        Node node = nodeList.item(FIRST_CHILD);


        if(node!=null && node.getNodeType()==Node.ELEMENT_NODE){
            Element elem = (Element) node;
            Element element =  findTarget(elem, CHAR);
            content = contentElement(element);
        }
        worker = content;

        return worker;
    }

    /**
     * Returns text content of element.
     * @param el is element that contains text.
     * @return text content of element.
     */

    private String contentElement(Element el){
        return el.getTextContent();
    }

    /**
     * Finds string list to send to interface.
     * @param document contains list.
     * @return string list found.
     */

    private List<String> findListToPrint(Document document, String type) {
        List<String> stringList = new ArrayList<>();
        Element el = insideField(document, MESSAGE);
        addMultipleContentToList(stringList, el, type);
        return stringList;
    }

    /**
     * Finds list of hash map containing space's information.
     * @param doc contains information.
     * @return list of hash map found.
     */

    private List<HashMap<String, String>> findHashMapToPrint(Document doc){
        List<String> parameters = new ArrayList<>();
        List<HashMap<String, String>> hashMapList = new ArrayList<>();
        Element el = insideField(doc, MESSAGE);
        parameters.add(ROW);
        parameters.add(COLUMN);
        addMultipleDoubleParametersToList(hashMapList, el, SPACE, parameters);

        return hashMapList;
    }

    /**
     * Adds to list information found in a node list.
     * @param stringList is list in which will be added information.
     * @param element contains field's name searched.
     * @param name is field's name searched.
     */

    private void addMultipleContentToList(List<String> stringList, Element element, String name) {
        NodeList nodeList = element.getElementsByTagName(name);
        if (nodeList != null) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    stringList.add(node.getTextContent());
                }
            }
        }
    }

    /**
     * Adds information to list of hash map from a node list containing double information for each node.
     * @param hashMapList is list of hash map to fill.
     * @param el is element containing information.
     * @param name is name of field.
     * @param parameters is parameters searched.
     */

    private void addMultipleDoubleParametersToList(List<HashMap<String, String>> hashMapList, Element el, String name, List<String> parameters) {
        NodeList nodeList = el.getElementsByTagName(name);
        if (nodeList != null) {
            for (int i = FIRST_INDEX; i < nodeList.getLength(); i++) {
                addParametersToHashMap(nodeList.item(i), hashMapList, parameters);
            }
        }
    }

    /**
     * Searches determined parameters in a node and adds them to a hash map.
     * @param node contains information.
     * @param hashMapList is hash map to fill.
     * @param parameters contains what fields will be searched in.
     */

    private void addParametersToHashMap(Node node, List<HashMap<String, String>> hashMapList, List<String> parameters){
        String firstParameters;
        String secondParameters;
        HashMap<String, String> stringHashMap = new HashMap<>();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            firstParameters = findFirstTextContent(element, parameters.get(FIRST_INDEX));
            secondParameters = findFirstTextContent(element, parameters.get(SECOND_INDEX));
            stringHashMap.put(firstParameters, secondParameters);
            hashMapList.add(stringHashMap);
        }
    }

    /**
     * Finds text content of first node of list.
     * @param element is element that contains node list.
     * @param parameters is field searched.
     * @return text content of first node.
     */

    private String findFirstTextContent(Element element, String parameters){
        String textContent = null;
        NodeList nodeList1 = element.getElementsByTagName(parameters);
        Node node1 = nodeList1.item(FIRST_CHILD);
        if (node1!=null && node1.getNodeType() == Node.ELEMENT_NODE) {
            textContent = node1.getTextContent();
        }
        return textContent;
    }

    /**
     * Decodes update from document.
     * @param document is document that contains information.
     * @param specification contains type of update.
     * @param playerName is name of player.
     * @param playerColor is color of player.
     */

    private void decodeUpdate(Document document, String specification,  String playerName, String playerColor){
        List<Integer> integerList = parseUpdate(document, specification);
        String worker = parseWorker(document);
        sendToInterface(worker, specification, integerList, playerName, playerColor);

    }

    /**
     * Translates part of information in a letter that represents worker from document.
     * @param document is document that contains information.
     * @return a string that represents worker.
     */

    private String parseWorker(Document document){
        String textContent = null;
        Element el = insideField(document, MESSAGE);
        Element work = findTarget(el, WORKER);
        if(work!=null) {
            Element charWorker = findTarget(work, CHAR);
            textContent =  charWorker.getTextContent();
        }
        return textContent;
    }

    /**
     * Translates document in a action.
     * @param document contains information.
     * @param specification specifies action type.
     * @return a list of parameters that represent action.
     */

    private List<Integer> parseUpdate(Document document, String specification){
        List<Integer> integerList = new ArrayList<>();

        if(specification.equals(MOVE) || specification.equals(DELETED) || specification.equals(WORKERSETTING)){
            parseMovement(integerList, document);
        }else if(specification.equals(BUILD)) {
            parseBuilding(integerList, document);
        }

        return integerList;
    }

    /**
     * Translates a document in a list of parameters that represents a movement.
     * @param integerList is list to fill with parameters.
     * @param document contains information.
     */

    private void parseMovement(List<Integer> integerList, Document document){
        Element el = insideField(document, MESSAGE);

        addIntNestedField(integerList, el, SPACE, ROW);
        addIntNestedField(integerList, el, SPACE, COLUMN);
        addIntNestedField(integerList, el, WORKER, ROW);
        addIntNestedField(integerList, el, WORKER, COLUMN);
    }

    /**
     * Translates a document in a list of parameters that represents a build.
     * @param integerList is list to fill with parameters.
     * @param document contains information.
     */
    private void parseBuilding(List<Integer> integerList, Document document){
        Element el = insideField(document, MESSAGE);

        addIntNestedField(integerList, el, SPACE, ROW);
        addIntNestedField(integerList, el, SPACE, COLUMN);
        addIntNestedField(integerList, el, SPACE, LEVEL);
        addIntNestedField(integerList, el, SPACE, DOME);
    }

    /**
     * Finds, converts to integer and adds to a list value contained in a nested field inside another field.
     * @param integerList is list in which value will be added.
     * @param targetElement is element that contains field which contains other field.
     * @param fieldNest is field to look into to find the nested field.
     * @param nestedField contains string to convert.
     */
    private void addIntNestedField(List<Integer> integerList, Element targetElement, String fieldNest, String nestedField){
        Element element = findTarget(targetElement, fieldNest);
        if(element!=null) {
            Element el = findTarget(element, nestedField);
            if (el != null) {
                String coordinateString = el.getTextContent();
                try {
                    int coordinateValue = Integer.parseInt(coordinateString);
                    integerList.add(coordinateValue);
                } catch (NumberFormatException e) {
                    integerList.add(INVALID_VALUE);
                }
            }
        }
    }

    /**
     * Finds first element contained in element passed.
     * @param element is element in which search.
     * @param target is field to look into.
     * @return element search if it exists, otherwise null.
     */
    private Element findTarget(Element element, String target){
        Element elTarget = null;
        NodeList nodeList = element.getElementsByTagName(target);
        if(nodeList!=null){
            Node node = nodeList.item(FIRST_CHILD);
            if(node!=null && node.getNodeType() == Node.ELEMENT_NODE){
                elTarget =  (Element) node;
            }
        }
        return elTarget;
    }

    /**
     * Traduces a string that means color in the correct color string.
     * @param color is the string to traduce.
     * @return the string that's the correct color, otherwise an invalid string.
     */

    private String getCodeColor(String color){
        if(color!=null) {
            switch (color) {
                case RED:
                    return ANSI_RED.escape();
                case PURPLE:
                    return ANSI_PURPLE.escape();
                case WHITE:
                    return ANSI_WHITE.escape();
                case CYAN:
                    return ANSI_CYAN.escape();
                case GREY:
                    return ANSI_GREY.escape();
            }
            return INVALID_COLOR;
        }
        return INVALID_COLOR;
    }

    /**
     * Find the informations in this first wrapper.
     *
     * @param document is document in which search information.
     * @return element containing lists of information.
     */

    private Element insideField(Document document, String field) {
        Element element = null;

        if (document != null) {
            NodeList nodeList = document.getElementsByTagName(field);
            Node node = nodeList.item(FIRST_CHILD);
            if (node!=null && node.getNodeType() == Node.ELEMENT_NODE) {
                element = (Element) node;

            }
        }

        return element;
    }

    /**
     * Closed every socket and sends a message that indicates the end of game.
     */

    public void quitGame(boolean awareness) {
        try {
            netHandler.getSocket().close();
            netHandler.setEndGame(true);
            if(!awareness)
                sendToInterface(DISCONNECTION, null, null);
        }catch(IOException ignored) {}

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
        Document doc = null;
        try {
            db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(message));
            doc = db.parse(is);
            doc.normalize();
        } catch (ParserConfigurationException | IOException | SAXException e) { //bisogna vedere come gestirle
            quitGame(false);
        }
        return doc;
    }

    /**
     * Finds code in message and converts it in a integer.
     * @param doc contains message.
     * @return an integer that represents code, otherwise an invalid integer.
     */
    private int findCode(Document doc) {
        int code = INVALID_CODE;
        if (doc != null) {
            NodeList nodeList = doc.getElementsByTagName(CODE);
            Node node = nodeList.item(FIRST_CHILD);
            if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
                String value = nodeList.item(FIRST_CHILD).getTextContent();
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
     * Creates command depending of event represented and sends it to the command manager.
     * @param specification specifies what type of message to send is.
     * @param playerName is player name.
     * @param playerColor is player color.
     */

    private void sendToInterface(String specification, String playerName, String playerColor){
        if(command!=null) {
            switch (specification) {
                case NAME:
                    TransitionSceneCommand transitionSceneCommand = new TransitionSceneCommand(playerName, playerColor, specification);
                    command.manageCommand(transitionSceneCommand);
                    break;
                case SET_UP:
                    command.manageCommand(new SetUpCommand(specification, playerName, playerColor));
                    break;
                case LOSE:
                case LOST:
                case WIN:
                case ENDGAME:
                    command.manageCommand(new PopUpCommand(playerName, playerColor, specification));
                    break;
                case ERROR:
                case ENDTURN:
                    SentenceBottomRequestCommand sentenceBottomRequestCommand = new SentenceBottomRequestCommand(playerColor, playerName, specification);
                    command.manageCommand(sentenceBottomRequestCommand);
                    break;
                case DISCONNECTION:
                    command.manageCommand(new QuitCommand());
                    quitGame(true);
                    break;
            }
        }
    }

    /**
     * Creates command with a limited options and sends it to the command manager.
     * @param stringList contains choices.
     * @param specification specifies what type of message to send is.
     * @param playerName is player name.
     * @param playerColor is player color.
     */

    private void sendToInterface(List<String> stringList, String specification, String playerName, String playerColor){
        if(command!=null) {
            LimitedOptionsCommand limitedOptionsCommand = new LimitedOptionsCommand(stringList, specification, playerName, playerColor);
            command.manageCommand(limitedOptionsCommand);
        }
    }

    /**
     * Creates command with a list of cell available and sends it to the command manager..
     * @param worker is worker that could do these choice.
     * @param hashMapList contains choices.
     * @param specification specifies what type of message to send is.
     * @param playerName is player name.
     * @param playerColor is player color.
     */

    private void sendToInterface(String worker, List<HashMap<String, String>> hashMapList, String specification, String playerName, String playerColor){
        if(command!=null) {
            if(specification.equals(MOVE)) {
                SentenceBottomRequestCommand sentenceBottomRequestCommand = new SentenceBottomRequestCommand(playerColor, playerName, WAITING_MOVE);
                command.manageCommand(sentenceBottomRequestCommand);
            }else if(specification.equals(BUILD)){
                SentenceBottomRequestCommand sentenceBottomRequestCommand = new SentenceBottomRequestCommand(playerColor, playerName, WAITING_BUILD);
                command.manageCommand(sentenceBottomRequestCommand);
            }
            ShowAvCells showAvCells = new ShowAvCells(specification, playerName, playerColor, worker, hashMapList);
            command.manageCommand(showAvCells);
        }
    }

    /**
     * Creates an update command depending of event.
     * @param worker is worker that causes this update.
     * @param integerList contains information of updating.
     * @param specification specifies what type of message to send is.
     * @param playerName is player name.
     * @param playerColor is player color.
     */

    public void sendToInterface(String worker, String specification, List<Integer> integerList, String playerName, String playerColor){
        if(command!=null){
        switch (specification) {
            case MOVE:
                createMoveUpdateCommand(integerList, worker, playerName, playerColor);
                break;
            case BUILD:
                createBuildUpdateCommand(integerList, playerName, playerColor);
                break;
            case WORKERSETTING:
                createSetUpCommand(integerList, worker, playerColor);
                break;
            case DELETED:
                createRemoveWorkerCommand(integerList);
                break;

        }
        }
    }

    /**
     * Creates an update command due to a movement.
     * @param integerList contains parameters of movement.
     * @param worker is worker that caused this update.
     * @param playerName is player name.
     * @param playerColor is player color.
     */

    private void createMoveUpdateCommand(List<Integer> integerList, String worker, String playerName, String playerColor){
        MoveUpdateCommand moveUpdateCommand = new MoveUpdateCommand(integerList, worker, playerName, playerColor);
        command.manageCommand(moveUpdateCommand);
    }

    /**
     * Creates an update command due to a building.
     * @param integerList contains parameters of building.
     * @param playerName is player name.
     * @param playerColor is player color.
     */

    private void createBuildUpdateCommand(List<Integer> integerList, String playerName, String playerColor){
        BuildUpdateCommand buildUpdateCommand = new BuildUpdateCommand(integerList, playerName, playerColor);
        command.manageCommand(buildUpdateCommand);
    }

    /**
     * Creates a command that represents a worker setting.
     * @param integerList is parameters of setting.
     * @param worker is worker that has to set.
     * @param playerColor is player color.
     */

    private void createSetUpCommand(List<Integer> integerList, String worker, String playerColor){
        SettingPawnCommand settingPawnCommand = new SettingPawnCommand(integerList, worker, playerColor);
        command.manageCommand(settingPawnCommand);
    }

    /**
     * Creates a command that represents a removing action.
     * @param integerList contains parameters of removing.
     */


    private void createRemoveWorkerCommand(List<Integer> integerList){
        RemovingCommand removingCommand = new RemovingCommand(integerList);
        command.manageCommand(removingCommand);
    }

    /**
     * Starts reading messages.
     */
    public void startReading(){
        netHandler.handle();
    }

}
