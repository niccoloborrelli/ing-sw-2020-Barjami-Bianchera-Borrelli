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
     * @param input is input received.
     */
    
    public void send(String input){
        int code = decodeInput(input);
        String cd = generateField(String.valueOf(code), CODE);
        String message;

        if(code==1)
            message = buildActionMessage(input);
        else if (code==2)
            message = buildIntMessage(input);
        else
            message = buildStringMessage(input);

        String coveredMessage = generateField(message, MESSAGE);
        String data =  generateField(cd + coveredMessage, DATA);

        netHandler.sendMessage(PREFIX + data);
    }


    /**
     * Decodes type of message.
     * @param input is input received.
     * @return code that represents type of message.
     */
    private int decodeInput(String input){
        int code = 0;

        if(isItWorkerAction(input))
            code =1;
        else if(isItInt(input))
            code = 2;

        return code;

    }

    /**
     * Determines if input represents a worker's action.
     * @param input is input received.
     * @return true if it is, otherwise false.
     */

    private boolean isItWorkerAction(String input){
        if(input.contains(ACTION) && input.contains(SPECIAL_CHAR1) && input.contains(SPECIAL_CHAR2)){
            if(input.substring(FIRST_INDEX,SECOND_INDEX).equals(ACTION) && input.indexOf(ACTION) < input.indexOf(SPECIAL_CHAR1) && input.indexOf(ACTION) <  input.indexOf(SPECIAL_CHAR2)){
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

    private boolean isItInt(String input){
        try{
            Integer.parseInt(input);
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }

    /**
     * Builds action's message according to rules of communication.
     * @param input is input received.
     * @return message codified.
     */

    private String buildActionMessage(String input){
        String workerNumb = input.substring(input.indexOf(ACTION), input.indexOf(SPECIAL_CHAR1));
        String worker = generateField(workerNumb, WORKER);

        return buildSpace(input.substring(input.indexOf(SPECIAL_CHAR1) +1));

    }

    /**
     * Build space's message according to rules of communication.
     * @param input is input received.
     * @return message codified.
     */

    private String buildSpace(String input){
        String row = input.substring(FIRST_INDEX, input.indexOf(SPECIAL_CHAR2));
        String column = input.substring(input.indexOf(SPECIAL_CHAR2) + 1);

        String coverRow = generateField(row, ROW);
        String coverColumn = generateField(column, COLUMN);

        return generateField(coverRow+coverColumn, SPACE);
    }

    /**
     * Builds int's message according to rules of communication.
     * @param input is input received.
     * @return int's string codified.
     */

    private String buildIntMessage(String input){

        return generateField(input, INT);
    }

    /**
     * Builds string's message according to rules of communication.
     * @param input is input received.
     * @return string codified.
     */

    private String buildStringMessage(String input){
        return generateField(input, STRING);
    }

    /**
     * Covers content string according to XML rules creating a field passed.
     * @param content is string covered.
     * @param field is field that cover content.
     * @return content covered with field.
     */

    private String generateField(String content, String field) {
        String begin = BEGIN_FIELD + field + END;
        String end = END_FIELD + field + END;

        return begin + content + end;
    }


    public void receive(String message){
        List<Integer> listOfParameters = null;
        Document doc = buildDocument(message);

        int code = findCode(message, doc);

        if(code ==0) { //da stampare
            String data = parseMessToPrint(message);
            sendToInterface(data);
        }else if(code==2){
            HashMap<String, String> data = parseListSpace(message);
            String worker = parseWorker(message);
            sendToInterface(data, worker);
        }else if(code==3) { //si potrebbero unire le update
            listOfParameters = parseUpdate(message, true);
            String color = parseColor(message);
            sendToInterface(listOfParameters, color);
        }else if (code==4){
            listOfParameters = parseUpdate(message, false);
            sendToInterface(listOfParameters);
        }

    }

    private void sendToInterface(String message){
        if(!graphicInterface)
            field.printData(message);
    }

    private void sendToInterface(List<Integer> integerList){
        if(!graphicInterface){
            field.viewBuild(integerList.get(0), integerList.get(1), integerList.get(2), integerList.get(3));
        }
    }

    private void sendToInterface(List<Integer> integerList, String color){
        if(!graphicInterface){
            field.viewMove(integerList.get(0), integerList.get(1), integerList.get(2), integerList.get(3), color);
        }
    }

    private void sendToInterface(HashMap<String, String> hashMap, String worker){
        if(!graphicInterface){
            field.printData(hashMap, worker);
        }
    }

    private List<Integer> parseUpdate(String message, boolean type){
        List<Integer> integerList = new ArrayList<>();

        if(type){
            parseMovement(integerList,message);
        }else
            parseBuilding(integerList, message);

        return integerList;

    }

    private void parseMovement(List<Integer> integerList, String message){
        Document doc = buildDocument(message);
        Element el = insideMessage(doc);

        addTargetCoordinate(integerList, el, SPACE, ROW);
        addTargetCoordinate(integerList, el, SPACE, COLUMN);
        addTargetCoordinate(integerList, el, WORKER, ROW);
        addTargetCoordinate(integerList, el, WORKER, COLUMN);

    }

    private void parseBuilding(List<Integer> integerList, String message){
        Document doc = buildDocument(message);
        Element el = insideMessage(doc);

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

    private String parseMessToPrint(String message){
        Document doc = buildDocument(message);
        Element el = insideMessage(doc);

        return parseMessage(el);
    }

    private String parseMessage(Element element){

        return element.getTextContent();
    }

    private String parseColor(String message){
        String color = null;
        Document doc = buildDocument(message);
        Element el = insideMessage(doc);

        Element element = findTarget(el, COLOR);

        if(element!=null)
            color = element.getTextContent();
        return color;
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

    public void quitGame(){
        System.out.println("Figa, si è scollegato");
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

    /**
     * Finds and traduces code in message.
     *
     * @param message is message containing code.
     * @return code if valid, otherwise an invalid integer.
     */

    private int findCode(String message, Document doc) {
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

    private HashMap<String, String> parseListSpace(String message){
        HashMap<String, String> spaces = new HashMap<>();
        Document doc = buildDocument(message);
        Element el = insideMessage(doc);

        parseSpaces(spaces, el);

        return spaces;
    }

    private void parseSpaces(HashMap<String, String> spaceList, Element el){
        Element element;
        NodeList nodeList = el.getElementsByTagName(SPACE);
        for(int i=0; i<nodeList.getLength(); i++){
            if(nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                element = (Element) nodeList.item(i);
                String row = parseCoordinate(element, ROW);
                String column = parseCoordinate(element, COLUMN);
                if(row!=null && column!=null)
                    spaceList.put(row, column);
            }
        }
    }

    private String parseCoordinate(Element element, String target){
        NodeList nodeList = element.getElementsByTagName(target);
        Node node = nodeList.item(0);
        if(node!=null && node.getNodeType()==Node.ELEMENT_NODE)
            return node.getTextContent();
        return  null;
    }

    private String parseWorker(String message){
        Document document = buildDocument(message);
        Element el = insideMessage(document);
        String worker = null;
        String content = null;
        NodeList nodeList = el.getElementsByTagName(WORKER);
        Node node = nodeList.item(0);

        if(node!=null && node.getNodeType()==Node.ELEMENT_NODE){
            content = node.getTextContent();
        }

        worker = content;
        //if(content!=null)
          //  worker = addOneToString(content);
        return worker;
    }

    private String addOneToString(String content){
        int number = Integer.parseInt(content);
        number++;
        return String.valueOf(number);
    }

    public void startReading(){
        netHandler.handle();
    }


}
