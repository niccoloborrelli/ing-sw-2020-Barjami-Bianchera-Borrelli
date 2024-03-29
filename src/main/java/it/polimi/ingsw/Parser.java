package it.polimi.ingsw;


import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.prefs.NodeChangeEvent;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import static it.polimi.ingsw.DefinedValues.MINSIZE;
import static it.polimi.ingsw.FinalCommunication.DESCRIPTION;

public class Parser {

    /**
     * Permits to search into a inputstream (formed as XML).
     */

    private static final String GOD="God";
    private static final String NAME="Name";
    private InputStream inputFile;
    public Parser(InputStream input){
        this.inputFile=input;
    }

    /**
     * Searches information (determined by target) and adds them to an hash map.
     * @param target is information to search.
     * @return the hash map with the information.
     */
    public HashMap<String, List<String>> createHashRepresentation(String target){
        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc =dBuilder.parse(inputFile);
            HashMap<String, List<String>> map=new HashMap<String, List<String>>();
            String key;
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName(GOD);

            for (int temp = MINSIZE; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    key=eElement.getElementsByTagName(NAME).item(MINSIZE).getTextContent();

                    Node n=eElement.getElementsByTagName(target).item(MINSIZE);
                    NodeList nn=n.getChildNodes();
                    ArrayList<String> s=new ArrayList<String>();
                    for (int t = MINSIZE; t < nn.getLength(); t++) {
                        Node nodo = nn.item(t);
                        if(!target.equals(DESCRIPTION)) {
                            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                                Element ee = (Element) nodo;
                                s.add(ee.getTextContent());
                            }
                        }else {
                            s.add(nodo.getTextContent());
                        }
                    }
                    map.put(key,s);
                }
            }
            return map;
        }
        catch (Exception e){
            System.out.println("errore");
            return null;
        }
    }

}
