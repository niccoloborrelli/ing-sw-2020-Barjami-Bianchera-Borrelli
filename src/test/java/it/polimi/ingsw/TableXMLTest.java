package it.polimi.ingsw;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TableXMLTest {

    @Test
    void readXML() {
        File file = new File("C:\\Users\\Yoshi\\Desktop\\testXML.txt");
        HashMap<State, List<Line>> hash = null;
        Player player = new Player();
        TableXML tableXML = new TableXML(file, player);
        State readyForAction = new ReadyForActionState(player);
        State endTurn = new EndTurnState(player);
        State restriction = new ReadyForActionState(player);

        HashMap<String, State> hashMap = new HashMap<>();
        hashMap.put(restriction.toString(), restriction);
        hashMap.put(readyForAction.toString(), readyForAction);
        hashMap.put(endTurn.toString(), endTurn);

        try {
            hash =  tableXML.readXML(hashMap);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

        for (State start: hash.keySet()) {
            System.out.println("Lo stato di partenza è: " + start);
            for (Line line : hash.get(start)) {
                System.out.println("Lo stato di arrivo è: " + line.getFinishState());
                System.out.println("Il livello di priorità è: " + line.getPriority());
                for (Method m : line.getConditions().keySet()) {
                    System.out.println("Il metodo da usare è: " + m);
                    System.out.println("Il valore atteso è: " + line.getConditions().get(m));
                }
            }
        }


    }
}