package it.polimi.ingsw;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TableXML {

    /**
     * Permits to traduce a file XML containing info of basic game flow (without use of Gods).
     * This file writing has to follow some rules: startState and finishState have an ID (it's the result of toString() of that classes,
     * if priority missed, then it would automatically sets at lowest_priority indicated.
     * Condition could be absent, in this case the relative structure will be empty.
     * Flag (that are considered) are always boolean. (It could be expanded in a int way in Line class).
     * If  flag doesn't exist in this class, finish state would be considered wrong and not added to table.
     */

    private File inputFile;
    private Object flagClass;  // VEDERE SE FARE UNA LISTA OPPURE NO
    private static final int INVALID_PRIORITY = -50;
    private static final int INDEX_BEGIN = 0;
    private static final int NUMBER_OF_TEXT_CONTENT = 0;
    private static final int LOWEST_PRIORITY = 0;
    private static final int MINIMUM_SIZE = 0;
    private static final String STARTSTATE = "startState";
    private static final String STARTSTATE_ATTRIBUTE = "id";
    private static final String FINISHSTATE = "finishState";
    private static final String FINISHSTATE_ATTRIBUTE = "id";
    private static final String FLAG = "flag";
    private static final String PREFIX_METHOD = "is";
    private static final String EXPECTED_VALUE = "expectedValue";
    private static final String CONDITION = "condition";
    private static final String PRIORITY = "priority";
    private static final String PACKAGE_NAME = "it.polimi.ingsw.";

    public TableXML(File inputFile, Object flagClass) {
        this.flagClass = flagClass;
        this.inputFile = inputFile;
    }


    /**
     * Creates an hash map containing start state and lines containing finish states (with conditions and priority) from a XML file.
     * @return hash map created.
     * @throws ParserConfigurationException when file begins with invalid characters.
     * @throws IOException --> DA AGGIUNGERE
     * @throws SAXException --> DA AGGIUNGERE
     */

    public HashMap<State, List<Line>> readXML(HashMap<String, State> hashNameState) throws ParserConfigurationException, IOException, SAXException {

        HashMap<State, List<Line>> table = new HashMap<State, List<Line>>();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;

        documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document document = null;

        document = documentBuilder.parse(inputFile);


        document.normalize();

        NodeList nodeList = document.getElementsByTagName(STARTSTATE);

        findStartStates(table,nodeList, hashNameState);

        return table;
    }

    /**
     * Analyzes every node with a start state.
     * @param table is hash map in which it traduces XML file.
     * @param nodeList is list of node containing start states.
     */
    private void findStartStates(HashMap<State, List<Line>> table, NodeList nodeList, HashMap<String, State> hashNameState) {
        for (int index = INDEX_BEGIN; index < nodeList.getLength(); index++) {
            Node node = nodeList.item(index);
            getStartStates(node, table, hashNameState);
        }
    }

    /**
     * Takes name of start state, convert it in the corresponded state.
     * Finds nodes (related with this start state) with finish state.
     * @param node contains this start state.
     * @param table is hash map in which it traduces XML file.
     */

    private void getStartStates(Node node, HashMap<State,List<Line>> table, HashMap<String, State> hashNameState){
        State startState;
        String nameState;
        List<Line> lineList;

        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element e = (Element) node;
            nameState = e.getAttribute(STARTSTATE_ATTRIBUTE);
            startState = hashNameState.get(nameState);
            if (startState != null) {
                NodeList nodeList1 = e.getElementsByTagName(FINISHSTATE);
                lineList = analyzeFinishState(nodeList1, hashNameState);
                if(lineList.size()>MINIMUM_SIZE) {
                    table.put(startState, lineList);
                }
            }
        }
    }

    /**
     * Analyzes every finish state node.
     * @param nodeList1 is node list of finish state.
     * @return line list (containing finish states, conditions and priorities) related with previous start state.
     */

    private List<Line> analyzeFinishState(NodeList nodeList1, HashMap<String, State> hashNameState) {
        List<Line> lines = new ArrayList<Line>();

        for (int temp = INDEX_BEGIN; temp < nodeList1.getLength(); temp++) {
            Node node1 = nodeList1.item(temp);
            buildLines(lines, node1, hashNameState);
        }
        return lines;
    }

    /**
     * Creates a line with conditions and priorities related to a finish state.
     * @param lines is list containing lines related to start state.
     * @param node1 is node of a determined finish state.
     */

    private void buildLines(List<Line> lines, Node node1,HashMap<String, State> hashNameState) {
        String nextState;
        Line newLine;
        int priority = LOWEST_PRIORITY;

        if (node1.getNodeType() == Node.ELEMENT_NODE) {
            Element f = (Element) node1;
            nextState = f.getAttribute(FINISHSTATE_ATTRIBUTE);
            if (nextState != null) {
                HashMap<Method, Boolean> conditions = new HashMap<>();
                priority = findPriority(f);
                if(priority!=INVALID_PRIORITY) {
                    try {
                        findConditions(f, conditions);
                        newLine = createLine(nextState, conditions, priority, hashNameState);
                        lines.add(newLine);
                    } catch (NoSuchMethodException | ClassNotFoundException ignored) {
                    }
                }
            }
        }
    }

    /**
     * Creates a line with conditions, priority related to a finish state.
     * @param nextState is name of this finish state.
     * @param conditions represents conditions to pass to finish state.
     * @param priority is the level of priority.
     * @return
     */

    private Line createLine(String nextState, HashMap<Method, Boolean> conditions, int priority, HashMap<String, State> hashNameState){
        Line line = new Line(hashNameState.get(nextState));
        line.setConditions(conditions);
        line.setPriority(priority);

        return line;
    }

    /**
     * Finds priority of this specific element and converts it in a int data.
     * @param element is element analyzed.
     * @return value of priority.
     */

    private int findPriority(Element element){
        String value = null;
        int priority = LOWEST_PRIORITY;

        if(element.getElementsByTagName(PRIORITY).item(NUMBER_OF_TEXT_CONTENT)!=null) {
            value = element.getElementsByTagName(PRIORITY).item(NUMBER_OF_TEXT_CONTENT).getTextContent();
        }

        if(value!=null) {
            try {
                priority = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                priority = INVALID_PRIORITY;
            }
        }

        return priority;
    }
    

    /**
     * Finds conditions in the element and fill the map (if exists)
     * @param e is element analyzed.
     * @param methodBooleanHashMap is map that represent conditions.
     * @throws NoSuchMethodException if method declared doesn't exist in this class declared.
     * @throws ClassNotFoundException if class declared doesn't exist.
     */

    private void findConditions(Element e, HashMap<Method,Boolean> methodBooleanHashMap) throws NoSuchMethodException, ClassNotFoundException {
        NodeList nodeList = e.getElementsByTagName(CONDITION);

        for(int n_cond = NUMBER_OF_TEXT_CONTENT; n_cond < nodeList.getLength(); n_cond++){
            Node node = nodeList.item(n_cond);
            addConditions(node, methodBooleanHashMap);
        }
    }

    /**
     * Adds conditions to map.
     * @param node is node analyzed.
     * @param methodBooleanHashMap is map containing conditions.
     * @throws NoSuchMethodException if method declared doesn't exist in this class declared.
     * @throws ClassNotFoundException if class declared doesn't exist.
     */

    private void addConditions(Node node, HashMap<Method,Boolean> methodBooleanHashMap) throws NoSuchMethodException, ClassNotFoundException {
        String methodName;
        boolean value;

        if(node.getNodeType() == Node.ELEMENT_NODE) {
            Element p = (Element) node;
            value = expectedValue(p);
            methodName = getFlag(p);
            String className = flagClass.getClass().getName();

            putMethodInMap(className, methodName, value, methodBooleanHashMap);
        }
    }

    /**
     * Finds expected value of method declared.
     * @param el is element analyzed.
     * @return this expected value of method.
     */

    private boolean expectedValue(Element el){
        String g = el.getElementsByTagName(EXPECTED_VALUE).item(NUMBER_OF_TEXT_CONTENT).getTextContent();

        return Boolean.parseBoolean(g);
    }

    /**
     * Gets flag to consider.
     * @param el is element analyzed.
     * @return name of method to control.
     */

    private String getFlag(Element el) {
        String nameMethod = null;

        switch (el.getElementsByTagName(FLAG).item(0).getTextContent()) {
            case "HasWon":
                nameMethod =  "isHasWon";
                break;
            case "HasLost":
                nameMethod = "isHasLost";
                break;
            case "ValidInput":
                nameMethod =  "isValidInput";
                break;
            case "IsEmpty":
                nameMethod =  "isEmpty";
                break;

        }

        return nameMethod;
    }

    /**
     * Finds method from name class and method name, then it puts method in hashmap with this expected value.
     * @param nameClass is name of class in which there's method.
     * @param nameMethod is name of method.
     * @param value is expected value of method.
     * @param methodBooleanHashMap is map containing conditions.
     * @throws ClassNotFoundException if class declared doesn't exist.
     * @throws NoSuchMethodException if method declared doesn't exist in this class declared.
     */

    private void putMethodInMap(String nameClass, String nameMethod, boolean value, HashMap<Method, Boolean> methodBooleanHashMap) throws ClassNotFoundException, NoSuchMethodException {
        if(nameClass!=null && nameMethod!=null) {
            Class cl = Class.forName(nameClass);
            Method m = cl.getDeclaredMethod(nameMethod);
            methodBooleanHashMap.put(m, value);
        }else throw new ClassNotFoundException();
    }
}

