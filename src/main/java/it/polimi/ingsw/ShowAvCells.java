package it.polimi.ingsw;

import java.util.HashMap;
import java.util.List;

import static it.polimi.ingsw.CommunicationSentences.printChoices;

public class ShowAvCells extends ReplyCommand {

    String specification;
    String playerName;
    String playerColor;
    String worker;
    List<HashMap<String, String>> avCellsList;

    public ShowAvCells(String specification, String playerName, String playerColor, String worker, List<HashMap<String, String>> avCellsList) {
        this.specification = specification;
        this.playerName = playerName;
        this.playerColor = playerColor;
        this.worker = worker;
        this.avCellsList = avCellsList;
    }


    @Override
    public void execute(GraphicInterface gui) {
        String key ="";
        String value = "";
        for(HashMap<String, String> hashMap: avCellsList){
            key = pickLastKey(hashMap);
            value = hashMap.get(key);
        }

        gui.cellLightenUp(Integer.parseInt(key), Integer.parseInt(value));
    }


    private String pickLastKey(HashMap<String, String> hashMap){
        String lastKey = "";
        for(String key: hashMap.keySet())
            lastKey = key;
        return lastKey;
    }


    @Override
    public void execute(Field field) {
        String sentence = printChoices(worker, avCellsList, specification, playerName, playerColor);
        field.printSentence(sentence);
    }
}
