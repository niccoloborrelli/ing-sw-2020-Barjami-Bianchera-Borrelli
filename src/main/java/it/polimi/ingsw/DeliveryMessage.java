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
import static it.polimi.ingsw.Color.*;

public class DeliveryMessage {

    private static final String ACTION = "w";
    private static final String SPECIAL_CHAR1 = "+";
    private static final String SPECIAL_CHAR2 = "-";
    private static final String PREFIX = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
    private static final int FIRST_INDEX = 0;
    private static final int SECOND_INDEX = 1;
    private static final int MINIMUM_LENGTH_INT = 1;
    private static final int INVALID_VALUE = -1;
    private static final int INVALID_CODE = -1;
    private static final String MESSAGE = "message";
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
    private static final String DATA = "data";
    private static final String LEVEL = "level";
    private static final String COLOR = "color";
    private static final String DOME = "dome";
    private static final String SPECIFICATION = "specification";
    private static final String MOVE = "move";
    private static final String BUILD = "build";
    private static final String PLAYER = "player";
    private static final String NAME = "name";
    private static final String ENDGAME = "endgame";
    private static final int FIRST_CHILD = 0;


    private boolean graphicInterface;
    private Field field;
    private NetHandler netHandler;

    public DeliveryMessage(Socket sc) throws IOException {
        field = new Field();
        netHandler = new NetHandler(this, sc);
    }

    /**
     * Traduces input e sends it to net manager.
     *
     * @param input is input received.
     */

    public void send(String input) {
        int code = decodeInput(input);
        String cd = generateField(String.valueOf(code), CODE);
        String message;

        if (code == 1) {
            message = buildActionMessage(input);
        } else if (code == 2)
            message = buildIntMessage(input);
        else
            message = buildStringMessage(input);

        String coveredMessage = generateField(message, MESSAGE);
        String data = generateField(cd + coveredMessage, DATA);

        netHandler.sendMessage(PREFIX + data);
    }


    /**
     * Decodes type of message.
     *
     * @param input is input received.
     * @return code that represents type of message.
     */
    private int decodeInput(String input) {
        int code = 0;

        if (isItWorkerAction(input))
            code = 1;
        else if (isItInt(input))
            code = 2;

        return code;

    }

    /**
     * Determines if input represents a worker's action.
     *
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
     *
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
     *
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
     * Build space's message according to rules of communication.
     *
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
     *
     * @param input is input received.
     * @return int's string codified.
     */

    private String buildIntMessage(String input) {

        return generateField(input, INT);
    }

    /**
     * Builds string's message according to rules of communication.
     *
     * @param input is input received.
     * @return string codified.
     */

    private String buildStringMessage(String input) {
        return generateField(input, STRING);
    }

    /**
     * Covers content string according to XML rules creating a field passed.
     *
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
     * Parse message received and send it to client interface.
     * @param message is message received.
     */

    public void receive(String message) {
        Document doc = buildDocument(message);

        int code = findCode(doc);
        String specification = findSpecification(doc);
        String playerName = findPlayerAttribute(doc, NAME);
        String playerColor = getCodeColor(findPlayerAttribute(doc, COLOR));


        if (code == 0) {
            sendToInterface(specification, playerName, playerColor);
        } else if (code == 1) {
            decodePossibleChoice(doc, specification, playerName, playerColor);
        } else if (code == 2) {
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
        if(specification.equals("power") || specification.equals("preLobby"))
            search = INT;
        else
            search = STRING;
        List<String> stringList = findListToPrint(doc, search);
        List<HashMap<String, String>> hashMapList = findHashMapToPrint(doc);
        String worker = findInsideWorker(doc);

        if(stringList.size()!=0)
            sendToInterface(stringList, specification, playerName, playerColor);
        else
            sendToInterface(worker, hashMapList, specification, playerName, playerColor);
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
        Node node = nodeList.item(0);

        if(node!=null && node.getNodeType()==Node.ELEMENT_NODE){
            content = node.getTextContent();
        }
        worker = content;

        return worker;
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
                if (node.getNodeType() == Node.ELEMENT_NODE)
                    stringList.add(node.getTextContent());
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
     *
     * @param node
     * @param hashMapList
     * @param parameters
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

    private String findFirstTextContent(Element element, String parameters){
        NodeList nodeList1 = element.getElementsByTagName(parameters);
        Node node1 = nodeList1.item(FIRST_CHILD);
        if (node1!=null && node1.getNodeType() == Node.ELEMENT_NODE) {
            return node1.getTextContent();
        }
        return null;
    }


    private void decodeUpdate(Document document, String specification,  String playerName, String playerColor){
        List<Integer> integerList = parseUpdate(document, specification);
        sendToInterface( specification, integerList, playerName, playerColor);

    }


    private List<Integer> parseUpdate(Document document, String specification){
        List<Integer> integerList = new ArrayList<>();

        if(specification.equals(MOVE)){
            parseMovement(integerList, document);
        }else if(specification.equals(BUILD))
            parseBuilding(integerList, document);

        return integerList;

    }

    private void parseMovement(List<Integer> integerList, Document document){
        Element el = insideField(document, MESSAGE);

        addTargetCoordinate(integerList, el, SPACE, ROW);
        addTargetCoordinate(integerList, el, SPACE, COLUMN);
        addTargetCoordinate(integerList, el, WORKER, ROW);
        addTargetCoordinate(integerList, el, WORKER, COLUMN);
    }

    private void parseBuilding(List<Integer> integerList, Document document){
        Element el = insideField(document, MESSAGE);

        addTargetCoordinate(integerList, el, SPACE, ROW);
        addTargetCoordinate(integerList, el, SPACE, COLUMN);
        addTargetCoordinate(integerList, el, SPACE, LEVEL);
        addTargetCoordinate(integerList, el, SPACE, DOME);
    }

    private void addTargetCoordinate(List<Integer> integerList, Element first, String target, String coordinate){
        Element element = findTarget(first, target);
        Element el = findTarget(element, coordinate);
        if(el!=null){
            String rowString = el.getTextContent();
            try{
               int row = Integer.parseInt(rowString);
               integerList.add(row);
            }catch (NumberFormatException e){
                integerList.add(INVALID_VALUE);
            }
        }
    }


    private Element findTarget(Element element, String target){
        NodeList nodeList = element.getElementsByTagName(target);
        if(nodeList!=null){
            Node node = nodeList.item(0);
            if(node!=null && node.getNodeType() == Node.ELEMENT_NODE){
                return (Element) node;
            }
        }
        return null;
    }

    private String getCodeColor(String color){
        if(color!=null) {
            switch (color) {
                case "red":
                    return ANSI_RED.escape();
                case "purple":
                    return ANSI_PURPLE.escape();
                case "white":
                    return ANSI_WHITE.escape();
                case "cyan":
                    return ANSI_CYAN.escape();
                case "grey":
                    return ANSI_GREY.escape();
            }
            return "invalidColor";
        }
        return "invalidColor";
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

    public void quitGame() {
        try {
            netHandler.getSocket().close();
            netHandler.setEndGame(true);
            if(!graphicInterface)
                sendToInterface(ENDGAME, null, null);
        }catch(IOException e) {
            e.printStackTrace();
        }

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

    private int findCode(Document doc) {
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

    private void sendToInterface(String specification, String playerName, String playerColor){
        if(!graphicInterface)
            field.printParticularSentence(specification, playerName, playerColor);
    }

    private void sendToInterface(List<String> stringList, String specification, String playerName, String playerColor){
        if(!graphicInterface)
            field.printChoices(stringList, specification, playerName, playerColor);
    }

    private void sendToInterface(String worker, List<HashMap<String, String>> hashMapList, String specification, String playerName, String playerColor){
        if(!graphicInterface)
            field.printChoices(worker, hashMapList, specification, playerName, playerColor);
    }

    public void sendToInterface(String specification, List<Integer> integerList, String playerName, String playerColor){
        if(!graphicInterface)
            field.updateGameField(integerList, specification, playerName, playerColor);
    }

    public void startReading(){
        netHandler.handle();
    }


}
