package it.polimi.ingsw;


import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
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

public class Parser {
    File inputFile;
    public Parser(File input){
        this.inputFile=input;
    }

    public HashMap<String, List<String>> createHashRepresentation(){
        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc =dBuilder.parse(inputFile);
            HashMap<String, List<String>> map=new HashMap<String, List<String>>();
            String key;
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("God");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    key=eElement.getElementsByTagName("Name").item(0).getTextContent();

                    Node n=eElement.getElementsByTagName("Powers").item(0);
                    NodeList nn=n.getChildNodes();
                    ArrayList<String> s=new ArrayList<String>();
                    for (int t = 0; t < nn.getLength(); t++) {
                        Node nodo = nn.item(t);
                        if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                            Element ee = (Element) nodo;
                            s.add( ee
                                    .getTextContent());
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
