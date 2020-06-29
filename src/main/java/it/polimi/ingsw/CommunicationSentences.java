package it.polimi.ingsw;

import java.util.*;

import static it.polimi.ingsw.Field.ANSI_RESET;
import static it.polimi.ingsw.FinalCommunication.*;

public class CommunicationSentences {
    /**
     * Represents the creation center of sentences to print.
     */

    private static final String preLobbySent = "You can play a 1 vs 1 game or a three-players crossed game.\nIf you wanna play first one " +
            "insert ";
    private static final String welcomeToSantorini = "Welcome to Santorini.\nGods sanctioned you as chosen one.\n" +
            "Only one of you could dominate Santorini.\nI hope it will be you.";
    private static final String colorSent = "You have to choose color with which your workers paint themselves.\n" +
            "From your decision, your worker will have that color. Those are available: \n";
    private static final String setup = "Choose positions for your worker. Write w_+_-_ where first _ is the number of worker you want to place." +
            "Second _ is the row of space and third _ is the column\n";
    public static final String disconnection = "Somebody crashed. You're gonna be disconnected. Press any button.";
    public static final String endGame = "Game finished. Press any button";
    private static final String endSent1 = ", good job! Your turn is finished. Look at your enemies to understand their strategy.";
    private static final String endSent2 = ", well done! Now you can rest.";
    private static final String endSent3 = ", your turn ended. Who knows if your enemies will fall in your trap.";
    private static final String errorSent1 = "You wrote an invalid input. Try again";
    private static final String errorSent2 = "Be careful. A minimum error could ruin your strategy";
    private static final String errorSent3 = "Come on. You're the boss. You can't make a mistake like this";
    private static final String activateSent1 = "God gave you his power. You can use it!";
    private static final String activateSent2 = "Now, it's your choice. Do you want to use your God power?";
    private static final String activateSent3 = "Be careful. God power could be a double-edge sword. ";
    private static final String formToActivatePower = "If you want to activate it insert ";
    private static final String godChoiceSent1 = " choose carefully your Gods. Decide between: ";
    private static final String godSetSent1 = " you're the challenger. You have the power to decide which Gods would be involved " +
            "in this battle. In change of this possibility, you will receive the not-chosen God.\n" +
            "Pick them from those:";
    private static final String godsPowers = ", these are god powers:\n";
    private static final String singlePower = ", this is god power:\n";
    public static final String nameSent = "You have to choose your name";
    private static final String otherwise = " otherwise ";
    private static final String moved = " moved in space ";
    private static final String built = " built in space ";
    private static final String MOVE = "move";
    private static final String BUILD = "build";
    public static final String won = "Congratulations, you won! You conquered Santorini. God thanks you.";
    public static final String lose = "You lost! Your God lashes out in rage. Run away while you can.\n" +
            "Now you can watch others winning.";
    private static final String lost = " lost! One less enemy.";
    private static final String actionSent1_1 = " Choose carefully. Decide who would ";
    private static final String actionSent1_2 = ", worker can do it in these places:";
    private static final String actionSent2_1 = ", worker can ";
    private static final String actionSent2_2 = " in these places:";
    private static final String godChosen1 = " chose you. ";
    private static final String removed = " removed his worker due to his defeat";
    private static List<String> actionSentences;
    private static int actionIndex;
    private static List<String> endTurnSentences;
    private static int endTurnIndex;
    private static List<String> activationPowerSentence;
    private static int activationIndex;
    private static List<String> errorSentence;
    private static int errorIndex;


    /**
     * @return a phrase of enemy losing.
     */
    public static String getLostPhrase(){
        return lost;
    }

    /**
     * Chooses a sentence that represents the end of turn.
     * @return a sentence that represents the end of turn.
     */

    public static String getEndTurnPhrase(){
        String phrase = endTurnSentences.get(endTurnIndex);

        endTurnIndex = addIndexOfList(endTurnSentences, endTurnIndex);

        return phrase;
    }

    /**
     * Chooses a sentence that represents an error.
     * @return a sentence that represents an error.
     */

    public static String getErrorPhrase(){
        String phrase = errorSentence.get(errorIndex);

        errorIndex = addIndexOfList(errorSentence,errorIndex);
        return phrase;
    }

    /**
     * Chooses a sentence that represents a request of power activation.
     * @return a sentence that represents a request of power activation.
     */

    public static String getActivationPhrase(List<String> stringList){
        String phrase = activationPowerSentence.get(activationIndex);
        activationIndex++;
        String choiceSent = formToActivatePower + buildChoiceSent(stringList, otherwise, ",");

        activationIndex = addIndexOfList(activationPowerSentence, activationIndex);

        return ". " + phrase + choiceSent;
    }

    /**
     * Chooses a sentence that represents the beginning of game.
     * @return a sentence that representsthe beginning of game.
     */

    public static String getPreLobbyPhrase(List<String> stringList){
        return welcomeToSantorini + preLobbySent +  buildChoiceSent(stringList, otherwise, ",");
    }

    /**
     * Chooses a sentence that represents a list of color.
     * @return a sentence that represents a list of color.
     */

    public static String getColorPhrase(List<String> stringList){
        return colorSent + buildChoiceSent(stringList, otherwise, ",");
    }

    /**
     * Chooses a sentence that represents a list of available god.
     * @return a sentence that represents a list of available god.
     */

    public static String getGodChoicePhrase(List<String> stringList){
        if(stringList.size()>1)
            return  "," + godChoiceSent1 + "\n" + buildChoiceSent(stringList, "\n", "");
        else return ", "  + stringList.get(0) + godChosen1;
    }

    /**
     * Chooses a sentence that represents the entire god set.
     * @return a sentence that represents th entire god set.
     */

    public static String getGodSetPhrase(List<String> stringList, String playerName, String playerColor){
        return "," + godSetSent1 + "\n" + buildChoiceSent(stringList, "\n", "");
    }

    /**
     * Builds a sentence that represents a choice from determined strings.
     * @return a sentence that represents a choice from determined strings .
     */

    private static String buildChoiceSent(List<String> stringList, String conjunction, String punctuation){
        if(stringList.size()<=2)
            return stringList.get(0) + punctuation + conjunction + stringList.get(1);
        else{
            StringBuilder stringBuilder = new StringBuilder();
            for (String s : stringList)
                stringBuilder.append(s).append("\n");
            return stringBuilder.toString();
        }
    }

    /**
     * Creates a sentence that represents an update of game field.
     * @param newRow is row in which update are done.
     * @param newColumn is column in which update are done.
     * @param playerName is player name.
     * @param playerColor is player color.
     * @param specification specified event.
     * @return a sentence that represents an update of game field.
     */

    public static String updateField(int newRow, int newColumn, String playerName, String playerColor, String specification){
        String sentence;
        String prefix = playerColor + playerName + ANSI_RESET;
        if(specification.equals(MOVE)){
            sentence = moved + "[" + newRow +","+newColumn+"]";
        }else if(specification.equals(BUILD))
            sentence = built + "[" + newRow + ","+newColumn+"]";
        else
            sentence = removed;

        return prefix + sentence;
    }

    /**
     * Creates a sentence that represents an action.
     * @param worker is worker that could do the action.
     * @param hashMapList contains the available spaces in which do an action.
     * @param specification specified the event
     * @param playerName is player name.
     * @param playerColor is player color.
     * @return a sentence that represents an action.
     */

    public static String getActionPhrase(String worker, List<HashMap<String, String>> hashMapList, String specification, String playerName, String playerColor) {
        String phrase = actionSentences.get(actionIndex);
        String prefix = setup + playerColor + playerName + ANSI_RESET;

        actionIndex = addIndexOfList(actionSentences, actionIndex);

        return buildAvailablePhrase(prefix, phrase, specification, worker, hashMapList);
    }

    /**
     * Creates a sentence that represents available spaces in which do an action.
     * @param prefix is the prefix of sentence.
     * @param phrase is phrase that represents the action.
     * @param specification specified the event.
     * @param worker is worker that could do the action..
     * @param hashMapList contains the available spaces in which make an action.
     * @return a sentence that represents available spaces in which do an action.
     */

    private static String buildAvailablePhrase(String prefix, String phrase, String specification, String worker, List<HashMap<String, String>> hashMapList){
        String beginOfPhrase = prefix +  insertWorker(phrase + specification + actionSentences.get(actionIndex), worker);
        String endOfPhrase = findSpaces(hashMapList);

        actionIndex = addIndexOfList(actionSentences, actionIndex);

        return beginOfPhrase + "\n" + endOfPhrase;
    }

    /**
     * Insert worker in a sentence.
     * @param content is content of sentence.
     * @param number is number of worker.
     * @return sentence with worker inserted.
     */
    private static String insertWorker(String content, String number){
        int index = content.lastIndexOf("worker");
        return content.substring(0, index+6) + " " + number  + content.substring(index+6);
    }

    /**
     * Builds a sentence that contains spaces.
     * @param hashMapList contains spaces to put in sentence.
     * @return a sentence that contains spaces
     */

    private static String findSpaces(List<HashMap<String, String>> hashMapList){
        Comparator<HashMap<String, String>> comparator = createComparator();
        StringBuilder s = new StringBuilder();
        hashMapList.sort(comparator);
        for(HashMap<String, String> hashMap: hashMapList){
            s.append("[").append(lastString(hashMap, true)).append(",").append(lastString(hashMap, false)).append("] ");
        }
        return s.toString();
    }

    /**
     * Picks the last string in hash map.
     * @param hashMap contains strings.
     * @param key is a boolean. If true it must search keys, otherwise values.
     * @return last string searched.
     */

    private static String lastString(HashMap<String, String> hashMap, boolean key){
        String last = null;
        if(key){
            for(String s: hashMap.keySet())
                last = s;
        }else{
            for(String s: hashMap.values())
                last = s;
        }

        return last;

    }

    /**
     * Compares strings.
     * @return value of comparison.
     */

    private static Comparator<HashMap<String, String>> createComparator(){
        return new Comparator<HashMap<String, String>>() {
            @Override
            public int compare(HashMap<String, String> o1, HashMap<String, String> o2) {
                String realKey1 = null;
                String realKey2 = null;
                for(String key: o1.keySet())
                    realKey1=key;
                for(String key: o1.keySet())
                    realKey2=key;

                if(Integer.parseInt(realKey1)<Integer.parseInt(realKey2))
                    return -1;
                else if(Integer.parseInt(realKey1)>Integer.parseInt(realKey2))
                    return 1;
                else
                    return 0;
            }
        };
    }

    /**
     * Builds a sentence with list of string that represents god powers.
     * @param stringList is list of powers.
     * @return a sentence with list of string that represents god powers.
     */

    public static String getGodPowers(List<String> stringList){
        String built;
        if(stringList.size()>1)
            built = godsPowers + buildChoiceSent(stringList, "", "");
        else
            built = singlePower + stringList.get(0);

        return built;
    }

    /**
     * Creates a sentence that contains possible client actions.
     * @param stringList contains possible client actions.
     * @return a sentence that contains possible client actions.
     */

    public static String getHelp(List<String> stringList){
        return buildChoiceSent(stringList, "", "");
    }

    /**
     * Creates all sentences pre-formed.
     */

    public static void createAll(){
        createBaseActionSentence();
        createBaseActivationPowerSentence();
        createBaseEndTurnSentence();
        createBaseErrorSentence();
    }

    /**
     * Creates base end turn sentences.
     */

    private static void createBaseEndTurnSentence(){
        endTurnIndex = 0;
        endTurnSentences = new ArrayList<>();
        endTurnSentences.add(endSent1);
        endTurnSentences.add(endSent2);
        endTurnSentences.add(endSent3);
    }

    /**
     * Creates base error sentences.
     */

    private static void createBaseErrorSentence(){
        errorIndex = 0;
        errorSentence = new ArrayList<>();
        errorSentence.add(errorSent1);
        errorSentence.add(errorSent2);
        errorSentence.add(errorSent3);

    }

    /**
     * Creates base activation powers sentences.
     */

    private static void createBaseActivationPowerSentence(){
        activationIndex = 0;
        activationPowerSentence = new ArrayList<>();
        activationPowerSentence.add(activateSent1);
        activationPowerSentence.add(activateSent2);
        activationPowerSentence.add(activateSent3);
    }

    /**
     * Creates base action sentences.
     */

    private static void createBaseActionSentence(){
        actionIndex = 0;
        actionSentences = new ArrayList<>();
        actionSentences.add(actionSent1_1);
        actionSentences.add(actionSent1_2);
        actionSentences.add(actionSent2_1);
        actionSentences.add(actionSent2_2);
    }

    /**
     * Calculates next index of a list.
     * @param stringList is list controlled.
     * @param index is initial index.
     * @return next index of a list.
     */

    private static int addIndexOfList(List<String> stringList, int index){
        if(index<stringList.size()-1)
            index++;
        else
            index=0;

        return index;
    }

    /**
     * Builds a particular sentence depending of event.
     * @param specification specified event.
     * @param playerName is player name.
     * @param playerColor is player color.
     * @return a particular sentence depending of event.
     */

    public static String printParticularSentence(String specification, String playerName, String playerColor) {
        String data = null;
        String prefix = playerColor + playerName + ANSI_RESET;
        switch (specification) {
            case ENDTURN:
                data = prefix + getEndTurnPhrase();
                break;
            case ERROR:
                data = getErrorPhrase();
                break;
            case WIN:
                data = won;
                break;
            case LOSE:
                data = lose;
                break;
            case LOST:
                data = prefix  + getLostPhrase();
                break;
            case NAME:
                data = nameSent;
                break;
            case SET_UP:
                data = setup;
                break;
            case DISCONNECTION:
                data = disconnection;
                break;
            case ENDGAME:
                data = endGame;
                break;
        }

        return data;
    }

    /**
     * Builds a sentence that represents a list of choices.
     * @param worker is worker that could do a choice.
     * @param hashMap contains possible choices.
     * @param specification specified event.
     * @param playerName is player name
     * @param playerColor is player color.
     * @return a sentence that represents a list of choices.
     */

    public static String printChoices(String worker, List<HashMap<String, String>> hashMap, String specification, String playerName, String playerColor) {
        String data = null;
        switch (specification) {
            case MOVE:
            case BUILD:
                data = getActionPhrase(worker, hashMap, specification, playerName, playerColor);
                break;
        }

        return data;
    }


}
