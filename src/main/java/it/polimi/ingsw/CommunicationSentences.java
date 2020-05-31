package it.polimi.ingsw;

import java.util.*;

import static it.polimi.ingsw.Field.ANSI_RESET;

public class CommunicationSentences {

    private static final String preLobbySent = "You can play a 1 vs 1 game or a three-players crossed game.\nIf you wanna play first one " +
            "insert";
    private static final String welcomeToSantorini = "Welcome to Santorini.\nGods sanctioned you as chosen one.\n" +
            "Only one of you could dominate Santorini.\nI hope it will be you.";
    private static final String colorSent = "You have to choose color with which your worker impregnate themselves.\n" +
            "From your decision, your worker will have that color. Those are available: \n";
    private static final String endSent1 = ", good job! Your turn is finished. Look at your enemies to understand their strategy.";
    private static final String endSent2 = ", well done! Now you can rest.";
    private static final String endSent3 = ", your turn ended. Who knows if your enemies will fall in your trap";
    private static final String errorSent1 = "You wrote an invalid input. Try again";
    private static final String errorSent2 = "Be careful. A minimum error could ruin your strategy";
    private static final String errorSent3 = "Come on. You're the boss. You can't make a mistake like this";
    private static final String activateSent1 = "God gave you his power. You can use it!";
    private static final String activateSent2 = "Now, it's your choice. Do you want to use your God power?";
    private static final String activateSent3 = "Be careful. God power could be a double-edge sword.";
    private static final String formToActivatePower = "If you want to activate it insert";
    private static final String godChoiceSent1 = " Choose carefully your Gods. Decide between: ";
    private static final String godSetSent1 = " you're the challenger. You have the power to decide which Gods would be involved " +
            "in this battle. In change of this possibility, you will receive the not-chosen God.";
    public static final String nameSent = "You have to choose your name";
    private static final String otherwise = "otherwise";
    private static final String moved = " moved in space ";
    private static final String built = " built in space ";
    private static final String MOVE = "move";
    public static final String won = "Congratulations, you won! You conquered Santorini. God thanks you.";
    public static final String lost = "You lost! Your God lashes out in rage. Run away while you can";
    private static final String actionSent1_1 = " Choose carefully. Decide who would ";
    private static final String actionSent1_2 = ". Worker can do it in these places\n";
    private static final String actionSent2_1 = "Worker can ";
    private static final String actionSent2_2 = "in these places\n";
    private static List<String> actionSentences;
    private static int actionIndex;
    private static List<String> endTurnSentences;
    private static int endTurnIndex;
    private static List<String> activationPowerSentence;
    private static int activationIndex;
    private static List<String> errorSentence;
    private static int errorIndex;

    public static String getEndTurnPhrase(String playerName, String playerColor){
        String prefix = playerColor + playerName + ANSI_RESET;
        String phrase = endTurnSentences.get(endTurnIndex);
        if(endTurnIndex<endTurnSentences.size()-1)
            endTurnIndex++;
        else
            endTurnIndex=0;

        return prefix + " " + phrase;
    }

    public static String getErrorPhrase(){
        String phrase = errorSentence.get(errorIndex);
        if(errorIndex<errorSentence.size()-1)
            errorIndex++;
        else
            errorIndex=0;

        return phrase;
    }

    public static String getActivationPhrase(List<String> stringList, String playerName, String playerColor){
        String phrase = activationPowerSentence.get(activationIndex);
        activationIndex++;
        String choiceSent = formToActivatePower + buildChoiceSent(stringList, otherwise);
        String prefix = playerColor + playerName + ANSI_RESET;

        if(activationIndex<activationPowerSentence.size()-1)
            activationIndex++;
        else
            activationIndex=0;

        return prefix + ". " + phrase + choiceSent;
    }

    public static String getPreLobbyPhrase(List<String> stringList){
        return welcomeToSantorini + preLobbySent +  buildChoiceSent(stringList, otherwise);
    }

    public static String getColorPhrase(List<String> stringList, String playerName){
        return colorSent + buildChoiceSent(stringList, otherwise);
    }

    public static String getGodChoicePhrase(List<String> stringList, String playerName, String playerColor){
        String prefix = playerColor + playerName + ANSI_RESET;
        return prefix + "," + godChoiceSent1 + "\n" + buildChoiceSent(stringList, "\n");
    }

    public static String getGodSetPhrase(List<String> stringList, String playerName, String playerColor){
        String prefix = playerColor + playerName + ANSI_RESET;
        return prefix + "," + godSetSent1 + "\n" + buildChoiceSent(stringList, "\n");
    }

    private static String buildChoiceSent(List<String> stringList, String conjunction){
        if(stringList.size()<=2)
            return " " + stringList.get(0) + ", " + conjunction + " " + stringList.get(1);
        else{
            StringBuilder stringBuilder = new StringBuilder();
            for (String s : stringList)
                stringBuilder.append(s).append("\n");
            return stringBuilder.toString();
        }
    }

    public static String updateField(int newRow, int newColumn, String playerName, String playerColor, String specification){
        String sentence;
        String prefix = playerColor + playerName + ANSI_RESET;
        if(specification.equals(MOVE)){
            sentence = moved + "[" + newRow + "]"+"["+newColumn+"]";
        }else
            sentence = built + "[" + newRow + "]"+"["+newColumn+"]";

        return prefix + sentence;
    }

    public static String getActionPhrase(String worker, List<HashMap<String, String>> hashMapList, String specification, String playerName, String playerColor) {
        String phrase = actionSentences.get(actionIndex);
        String prefix = playerColor + playerName + ANSI_RESET;

        if(actionIndex<actionSentences.size()-1)
            actionIndex++;
        else
            actionIndex=0;

        return buildAvailablePhrase(prefix, phrase, specification, worker, hashMapList);
    }

    private static String buildAvailablePhrase(String prefix, String phrase, String specification, String worker, List<HashMap<String, String>> hashMapList){
        String beginOfPhrase = prefix +  insertWorker(phrase + specification + actionSentences.get(actionIndex), worker);
        String endOfPhrase = findSpaces(hashMapList);
        if(actionIndex<actionSentences.size()-1)
            actionIndex++;
        else
            actionIndex=0;
        return beginOfPhrase + "\n" + endOfPhrase;
    }

    private static String insertWorker(String content, String number){
        int index = content.lastIndexOf("Worker");
        return content.substring(0, index) + " " + number + " " + content.substring(index+1);
    }

    private static String findSpaces(List<HashMap<String, String>> hashMapList){
        Comparator<HashMap<String, String>> comparator = createComparator();
        StringBuilder s = new StringBuilder();
        hashMapList.sort(comparator);
        for(HashMap<String, String> hashMap: hashMapList){
            s.append("[").append(lastString(hashMap, true)).append("]").append("[").append(lastString(hashMap, false)).append("] ");
        }
        return s.toString();
    }

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

    public static void createAll(){
        createBaseActionSentence();
        createBaseActivationPowerSentence();
        createBaseEndTurnSentence();
        createBaseErrorEndTurnSentence();
    }

    private static void createBaseEndTurnSentence(){
        endTurnIndex = 0;
        endTurnSentences = new ArrayList<>();
        endTurnSentences.add(endSent1);
        endTurnSentences.add(endSent2);
        endTurnSentences.add(endSent3);
    }

    private static void createBaseErrorEndTurnSentence(){
        errorIndex = 0;
        errorSentence = new ArrayList<>();
        errorSentence.add(errorSent1);
        errorSentence.add(errorSent2);
        errorSentence.add(errorSent3);

    }

    private static void createBaseActivationPowerSentence(){
        activationIndex = 0;
        activationPowerSentence = new ArrayList<>();
        activationPowerSentence.add(activateSent1);
        activationPowerSentence.add(activateSent2);
        activationPowerSentence.add(activateSent3);
    }

    private static void createBaseActionSentence(){
        actionIndex = 0;
        actionSentences = new ArrayList<>();
        actionSentences.add(actionSent1_1);
        actionSentences.add(actionSent1_2);
        actionSentences.add(actionSent2_1);
        actionSentences.add(actionSent2_2);
    }

}
