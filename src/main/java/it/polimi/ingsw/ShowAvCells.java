package it.polimi.ingsw;

import java.util.HashMap;
import java.util.List;

import static it.polimi.ingsw.CommunicationSentences.printChoices;

public class ShowAvCells extends ReplyCommand {

    /**
     * Permits to show which cells are available to do an action (moving or building in this case).
     */
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


    /**
     * Paints of a determined color every cells available in which player could do an action.
     * @param gui contains cells.
     */
    @Override
    public void execute(GraphicInterface gui) {
        String key ="";
        String value = "";
        System.out.println("Sto eseguendo avCellList");
        for(HashMap<String, String> hashMap: avCellsList){
            key = pickLastKey(hashMap);
            value = hashMap.get(key);
            gui.cellLightenUp(Integer.parseInt(key), Integer.parseInt(value));
        }

    }

    /**
     * Picks the last key of hash map.
     * @param hashMap is hash map in which a key will be picked.
     * @return last key of hash map.
     */

    private String pickLastKey(HashMap<String, String> hashMap){
        String lastKey = "";
        for(String key: hashMap.keySet())
            lastKey = key;
        return lastKey;
    }

    /**
     * Prints the list of cell available in CLI.
     * @param field permits to print this list.
     */


    @Override
    public void execute(Field field) {
        String sentence = printChoices(worker, avCellsList, specification, playerName, playerColor);
        field.printSentence(sentence);
    }
}
