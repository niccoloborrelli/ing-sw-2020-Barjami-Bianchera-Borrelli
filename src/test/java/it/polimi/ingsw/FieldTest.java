package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Thread.sleep;

class FieldTest {

    @Test
    void CLITest1() throws InterruptedException {
        Field field = new Field();
        field.viewSetup(1,3, Color.ANSI_RED.escape());
        sleep(1000);
        field.viewMove("A", 1,3,2,3, Color.ANSI_RED.escape());
        sleep(1000);
        field.viewSetup(2,2, Color.ANSI_WHITE.escape());
        sleep(1000);
        field.viewSetup(2,1, Color.ANSI_CYAN.escape());
        sleep(1000);
        field.viewSetup(3,2, Color.ANSI_PURPLE.escape());
        sleep(1000);
        field.viewBuild(3,2,1,0);
        sleep(1000);
        field.viewBuild(0,0,2,1);
        sleep(1000);
        field.viewRemoveWorker(2,1);
        field.plot();
    }

    @Test
    void printParticularSentences(){
        Field field = new Field();
        field.printParticularSentence("endTurn", "carmelo", Color.ANSI_RED.escape());
        field.printParticularSentence("endTurn", "carmelo", Color.ANSI_RED.escape());
        field.printParticularSentence("endTurn", "carmelo", Color.ANSI_RED.escape());
        field.printParticularSentence("endTurn", "carmelo", Color.ANSI_RED.escape());
        field.printParticularSentence("error", "carmelo", Color.ANSI_RED.escape());
        field.printParticularSentence("win", "carmelo", Color.ANSI_RED.escape());
        field.printParticularSentence("lose", "carmelo", Color.ANSI_RED.escape());
        field.printParticularSentence("lost", "carmelo", Color.ANSI_RED.escape());
        field.printParticularSentence("name", "carmelo", Color.ANSI_RED.escape());
        field.printParticularSentence("workerSetting", "carmelo", Color.ANSI_RED.escape());
        field.printParticularSentence("endGame", "carmelo", Color.ANSI_RED.escape());

    }

    @Test
    void printChoicesTest1(){
        Field field = new Field();
        List<String> stringList = new ArrayList<>();
        stringList.add("9");
        stringList.add("1");

        field.printChoices(stringList, "power", "carmelo", Color.ANSI_RED.escape());
        field.printChoices(stringList, "preLobby", "carmelo", Color.ANSI_RED.escape());
        field.printChoices(stringList, "color", "carmelo", Color.ANSI_RED.escape());
        field.printChoices(stringList, "godChoice", "carmelo", Color.ANSI_RED.escape());
        field.printChoices(stringList, "godSet", "carmelo", Color.ANSI_RED.escape());
    }
    @Test
    void printChoicesTest2() {
        Field field = new Field();
        List<HashMap<String, String>> hashMaps = new ArrayList<>();
        HashMap<String, String> space1 = new HashMap<>();
        HashMap<String, String> space2 = new HashMap<>();
        space1.put("1","1");
        space2.put("2", "2");
        hashMaps.add(space1);
        hashMaps.add(space2);

        field.printChoices("A", hashMaps, "move", "carmelo", Color.ANSI_RED.escape());
        field.printChoices("A", hashMaps, "build", "carmelo", Color.ANSI_RED.escape());
    }

    @Test
    void printChoicesTest3(){
        Field field = new Field();
        List<String> strings = new ArrayList<>();
        strings.add("-help : It gives you a list of possible operations you can always require");
        strings.add("-whatToDo : It gives you the last significant message, which contains indication for what you have to do");
        strings.add("-god : It gives you every God with associated power");
        strings.add("-god__ : Instead of \"__\", you have to put a God name. It gives you the power of chosen God");

        field.printChoices(strings, "help", "carmelo", "");

        strings.clear();
        strings.add("Atlas : [aaaaaaaaaa]");
        strings.add("Persephone : [bbbbbb]");

        field.printChoices(strings, "god", "carmelo", "");

        strings.remove(0);

        field.printChoices(strings, "god", "carmelo", "");;
    }

}