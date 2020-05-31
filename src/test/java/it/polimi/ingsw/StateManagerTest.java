package it.polimi.ingsw;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.print.attribute.HashAttributeSet;
import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StateManagerTest {

    /*
    Tests if a new state is added to both hash map.
     */

    @Test
    void addNewStateTest1() throws ParserConfigurationException, SAXException, IOException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);

        controller.createFluxTable();
        State state = new StateTest(player);
        stateManager.addNewState(state);

        assertTrue(stateManager.getStateHashMap().containsKey(state.toString()) && stateManager.getTable().containsKey(state) &&
                stateManager.getTable().get(state).size()==0);
    }

    /*
    Tests if a state is already present, hash maps don't change.
     */

    @Test
    void addNewStateTest2() throws ParserConfigurationException, SAXException, IOException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        controller.createFluxTable();

        int sizeKeys = stateManager.getStateHashMap().keySet().size();
        int sizeValues = stateManager.getTable().get(stateManager.getStateHashMap().get("ReadyForActionState")).size();
        State state = new ReadyForActionState(player);
        stateManager.addNewState(state);

        assertTrue(stateManager.getStateHashMap().containsKey(state.toString()) && stateManager.getTable().get(stateManager.getStateHashMap().get(state.toString())).size()==sizeValues
                    && stateManager.getStateHashMap().keySet().size()==sizeKeys);
    }

    /*
    Tests if a arrival state is correctly added with no condition.
     */

    @Test
    void addNewFinishSpaceTest1() throws ParserConfigurationException, SAXException, IOException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        controller.createFluxTable();

        State stateTest = new StateTest(player);
        stateManager.addNewState(stateTest);
        State action = stateManager.getStateHashMap().get("ActionState");
        int sizeAction = stateManager.getTable().get(action).size();

        stateManager.addNewFinishSpace(action,stateTest,null, false, 1);
        List<Line> lineList =  stateManager.searchFinishState(stateManager.getTable().get(action), stateTest);

        assertTrue(stateManager.getTable().get(action).size()==sizeAction+1 && lineList.size()==1 &&
                lineList.get(0).getConditions().size()==0 && lineList.get(0).getPriority()==1);
    }

    /*
    Tests if a state with an invalid value of priority is correctly not added.
     */

    @Test
    void addNewFinishSpaceTest2() throws ParserConfigurationException, SAXException, IOException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        controller.createFluxTable();

        State stateTest = new StateTest(player);
        stateManager.addNewState(stateTest);
        State action = stateManager.getStateHashMap().get("ActionState");
        int sizeAction = stateManager.getTable().get(action).size();

        stateManager.addNewFinishSpace(action,stateTest,null, false, -1);
        List<Line> lineList =  stateManager.searchFinishState(stateManager.getTable().get(action), stateTest);

        assertTrue(stateManager.getTable().get(action).size()==sizeAction && lineList==null);
    }

    /*
    Tests if an arrival state with condition is correctly added.
     */

    @Test
    void addNewFinishSpaceTest3() throws ParserConfigurationException, SAXException, IOException, NoSuchMethodException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        controller.createFluxTable();

        State stateTest = new StateTest(player);
        stateManager.addNewState(stateTest);
        State action = stateManager.getStateHashMap().get("ActionState");
        int sizeAction = stateManager.getTable().get(action).size();
        Method method = player.getClass().getDeclaredMethod("isLastGod");

        stateManager.addNewFinishSpace(action,stateTest,method, true, 1);
        List<Line> lineList =  stateManager.searchFinishState(stateManager.getTable().get(action), stateTest);

        assertTrue(stateManager.getTable().get(action).size()==sizeAction+1 && lineList.size()==1 &&
                lineList.get(0).getConditions().containsKey(method) && lineList.get(0).getConditions().containsValue(true) );
    }

    /*
    Tests if a non-existing arrival state isn't added.
     */
    @Test
    void addNewFinishSpaceTest4() throws ParserConfigurationException, SAXException, IOException, NoSuchMethodException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        controller.createFluxTable();

        State stateTest = new StateTest(player);
        State action = stateManager.getStateHashMap().get("ActionState");
        int sizeAction = stateManager.getTable().get(action).size();
        Method method = player.getClass().getDeclaredMethod("isLastGod");

        stateManager.addNewFinishSpace(action, stateTest, method, true, 1);
        List<Line> lineList = stateManager.searchFinishState(stateManager.getTable().get(action), stateTest);

        assertTrue(stateManager.getTable().get(action).size() == sizeAction  && lineList == null);
    }

    @Test
    void addNewFinishSpaceTest5() throws ParserConfigurationException, SAXException, IOException, NoSuchMethodException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        controller.createFluxTable();

        State stateTest = new StateTest(player);
        State action = stateManager.getStateHashMap().get("ActionState");
        Method method = player.getClass().getDeclaredMethod("isLastGod");
        int sizeTable = stateManager.getTable().size();

        stateManager.addNewFinishSpace(stateTest, action, method, true, 1);
        List<Line> lineList = stateManager.searchFinishState(stateManager.getTable().get(action), stateTest);

        assertTrue(stateManager.getTable().size()==sizeTable  && lineList == null);
    }

    /*
    Tests if a new condition is added to existing line.
     */

    @Test
    void addNewConditionsTest1() throws ParserConfigurationException, SAXException, IOException, NoSuchMethodException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        controller.createFluxTable();

        State action = stateManager.getStateHashMap().get("ActionState");
        State endTurn = stateManager.getStateHashMap().get("EndTurnState");
        Method m = player.getClass().getDeclaredMethod("hasAction");
        int oldSize = stateManager.getTable().get(action).get(1).getConditions().size();

        stateManager.addNewConditions(action,endTurn,m,true,0);
        List<Line> lineList = stateManager.getTable().get(action);
        List<Line> endTurnArrival = stateManager.searchFinishState(lineList, endTurn);
        List<Line> found = stateManager.searchForPriority(endTurnArrival,0);

        assertTrue(found.get(0).getConditions().size()==oldSize+1 && found.get(0).getConditions().containsKey(m));
    }

    /*
    Tests that a new condition isn't added to an existing finish state if it's related to a different level of priority.

     */

    @Test
    void addNewConditionsTest2() throws ParserConfigurationException, SAXException, IOException, NoSuchMethodException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        controller.createFluxTable();

        State action = stateManager.getStateHashMap().get("ActionState");
        State endTurn = stateManager.getStateHashMap().get("EndTurnState");
        Method m = player.getClass().getDeclaredMethod("hasAction");
        int oldSize = stateManager.getTable().get(action).get(1).getConditions().size();

        stateManager.addNewConditions(action,endTurn,m,true,1);
        List<Line> lineList = stateManager.getTable().get(action);
        List<Line> endTurnArrival = stateManager.searchFinishState(lineList, endTurn);
        List<Line> found = stateManager.searchForPriority(endTurnArrival,0);

        assertTrue(found.get(0).getConditions().size()==oldSize && found.get(0).getConditions().size()==0);
    }

    /*
    Tests that a new condition couldn't be added if finish state doesn't exist.
     */

    @Test
    void addNewConditionsTest3() throws ParserConfigurationException, SAXException, IOException, NoSuchMethodException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        controller.createFluxTable();

        State action = stateManager.getStateHashMap().get("ActionState");
        State stateTest = new StateTest(player);
        stateManager.addNewState(stateTest);
        Method m = player.getClass().getDeclaredMethod("hasAction");
        int oldSize = stateManager.getTable().get(action).size();

        stateManager.addNewConditions(action,stateTest,m,true,0);
        List<Line> lineList = stateManager.getTable().get(action);
        List<Line> found= stateManager.searchFinishState(lineList, stateTest);

        assertEquals(stateManager.getTable().get(action).size(), oldSize);
    }
    /*
    Tests if it's added a new start state with an existing finish state and with a new  condition.
     */

    @Test
    void addAllInOneTime() throws ParserConfigurationException, SAXException, IOException, NoSuchMethodException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        controller.createFluxTable();

        State action = stateManager.getStateHashMap().get("ActionState");
        State testState = new StateTest(player);
        Method m = player.getClass().getDeclaredMethod("hasAction");
        int oldSizeTable = stateManager.getTable().size();

        stateManager.addAllInOneTime(testState,action,m,true,5);

        assertTrue(stateManager.getTable().size()==oldSizeTable+1 && stateManager.getTable().get(testState).get(0).getPriority()==5 &&
                stateManager.getTable().get(testState).get(0).getConditions().containsKey(m) && stateManager.getTable().get(testState).get(0).getConditions().containsValue(true));
    }

    /*
    Tests if correctly return a list of line with the existing finish state passed.
     */

    @Test
    void searchFinishStateTest1() throws ParserConfigurationException, SAXException, IOException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        controller.createFluxTable();

        State workerSettingState = stateManager.getState("WorkerSettingState");
        State endTurn = stateManager.getState("EndTurnState");

        List<Line> lineList = stateManager.searchFinishState(stateManager.getTable().get(endTurn), workerSettingState);

        assertTrue(lineList.size()==2 && lineList.get(0).getFinishState().equals(workerSettingState) &&
                lineList.get(1).getFinishState().equals(workerSettingState));
    }

    /*
    Tests if return null if finish state isn't present.
     */

    @Test
    void searchFinishStateTest2() throws ParserConfigurationException, SAXException, IOException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        controller.createFluxTable();

        State endTurn = stateManager.getState("EndTurnState");

        List<Line> lineList = stateManager.searchFinishState(stateManager.getTable().get(endTurn), endTurn);

        assertNull(lineList);
    }

    /*
    Tests that if finish state doesn't exist it returns null.
     */


    @Test
    void searchFinishStateTest3() throws ParserConfigurationException, SAXException, IOException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        controller.createFluxTable();

        State stateTest = new StateTest(player);

        List<Line> lineList = stateManager.searchFinishState(stateManager.getTable().get(stateTest), stateTest);

        assertNull(lineList);
    }


    /*
    Tests if it finds all of line with a determined priority.
     */
    @Test
    void searchForPriorityTest1() throws ParserConfigurationException, SAXException, IOException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        controller.createFluxTable();

        State endTurn = stateManager.getState("EndTurnState");
        List<Line> lineList = stateManager.searchForPriority(stateManager.getTable().get(endTurn), 2);

        assertTrue(lineList.size()==2 && lineList.get(0).getPriority()==2 && lineList.get(1).getPriority()==2);
    }

    /*
    Tests if there's no line with this level of priority, it returns null.
     */

    @Test
    void searchForPriorityTest2() throws ParserConfigurationException, SAXException, IOException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        controller.createFluxTable();

        State endTurn = stateManager.getState("EndTurnState");
        List<Line> lineList = stateManager.searchForPriority(stateManager.getTable().get(endTurn), -1);

        assertNull(lineList);
    }

    /*
    Tests if it founds the correct lines with those conditions.
     */

    @Test
    void searchForConditionTest1() throws ParserConfigurationException, SAXException, IOException, NoSuchMethodException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        controller.createFluxTable();

        State endTurn = stateManager.getState("EndTurnState");
        State godSetState = stateManager.getState("GodSetState");
        Method m = player.getClass().getDeclaredMethod("isGodSetFormed");
        Method m1 = player.getClass().getDeclaredMethod("isChallenger");
        HashMap<Method, Boolean> hashMap = new HashMap<>();

        hashMap.put(m,false);
        hashMap.put(m1,true);

        List<Line> lineList = stateManager.searchForCondition(stateManager.getTable().get(endTurn), hashMap);

        assertEquals(lineList.get(0).getFinishState(), godSetState);

    }

    /*
    Tests if it returns null, if there's no line with those conditions.
     */

    @Test
    void searchForConditionTest2() throws ParserConfigurationException, SAXException, IOException, NoSuchMethodException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        controller.createFluxTable();

        State endTurn = stateManager.getState("EndTurnState");
        Method m = player.getClass().getDeclaredMethod("isGodSetFormed");
        Method m1 = player.getClass().getDeclaredMethod("isChallenger");
        HashMap<Method, Boolean> hashMap = new HashMap<>();

        hashMap.put(m,true);
        hashMap.put(m1,true);

        List<Line> lineList = stateManager.searchForCondition(stateManager.getTable().get(endTurn), hashMap);

        assertNull(lineList);

    }

    /*
    Tests if the state is remove from anywhere.
     */
    @Test
    void removeStartStateTest1() throws ParserConfigurationException, SAXException, IOException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        controller.createFluxTable();

        State endTurn = stateManager.getState("EndTurnState");
        int sizeMap = stateManager.getStateHashMap().size();

        stateManager.removeStartState(endTurn);

        assertTrue(3==stateManager.getTable().keySet().size() && sizeMap-1==stateManager.getStateHashMap().size());
    }

    /*
    Tests if table doesn't change if state to remove isn't in there.
     */

    @Test
    void removeStartStateTest2() throws ParserConfigurationException, SAXException, IOException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        controller.createFluxTable();

        State stateTest = new StateTest(player);
        int sizeMap = stateManager.getStateHashMap().size();
        int sizeTable = stateManager.getTable().keySet().size();

        stateManager.removeStartState(stateTest);

        assertTrue(sizeTable==stateManager.getTable().keySet().size() && sizeMap==stateManager.getStateHashMap().size());
    }

    @Test
    void removeFromOtherFinishSpaceTest1() throws ParserConfigurationException, SAXException, IOException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        controller.createFluxTable();

        State endTurn = stateManager.getState("EndTurnState");

        stateManager.removeFromOtherFinishSpace(endTurn);

        for(State state: stateManager.getTable().keySet())
            System.out.println(state);

        assertEquals(4, stateManager.getTable().keySet().size());
    }

    /*
    Tests if there's no changes if the state (to remove) isn't in the table.
     */
    @Test
    void removeFromOtherFinishSpaceTest2() throws ParserConfigurationException, SAXException, IOException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        controller.createFluxTable();

        State testState = new StateTest(player);
        int size = stateManager.getTable().keySet().size();

        stateManager.removeFromOtherFinishSpace(testState);

        for(State state: stateManager.getTable().keySet())
            System.out.println(state);

        assertEquals(size, stateManager.getTable().keySet().size());
    }

    /*
    Tests if it removes all lines with specified condition.
     */

    @Test
    void removeSpecifiedConditionTest1() throws ParserConfigurationException, SAXException, IOException, NoSuchMethodException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        controller.createFluxTable();

        State endTurn = stateManager.getState("EndTurnState");
        State godSetState = stateManager.getState("GodSetState");
        int oldSize = stateManager.getTable().get(endTurn).size();
        Method m = player.getClass().getDeclaredMethod("isGodSetFormed");
        Method m1 = player.getClass().getDeclaredMethod("isChallenger");
        HashMap<Method, Boolean> hashMap = new HashMap<>();

        hashMap.put(m,false);
        hashMap.put(m1,true);

        stateManager.removeSpecifiedCondition(endTurn,godSetState,hashMap);

        assertEquals(stateManager.getTable().get(endTurn).size(), oldSize - 1);
    }

    /*
    Tests if it doesn't remove anything if conditions don't exist.
     */
    @Test
    void removeSpecifiedConditionTest2() throws ParserConfigurationException, SAXException, IOException, NoSuchMethodException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        controller.createFluxTable();

        State endTurn = stateManager.getState("EndTurnState");
        State godSetState = stateManager.getState("GodSetState");
        int oldSize = stateManager.getTable().get(endTurn).size();
        Method m = player.getClass().getDeclaredMethod("isGodSetFormed");
        Method m1 = player.getClass().getDeclaredMethod("isChallenger");
        HashMap<Method, Boolean> hashMap = new HashMap<>();

        hashMap.put(m,true);
        hashMap.put(m1,true);

        stateManager.removeSpecifiedCondition(endTurn,godSetState,hashMap);

        assertEquals(stateManager.getTable().get(endTurn).size(), oldSize);
    }

    /*
    Tests if line changes priority.
     */

    @Test
    void changePriority() throws ParserConfigurationException, SAXException, IOException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        controller.createFluxTable();

        State endTurn = stateManager.getState("EndTurnState");
        State workerSettingState = stateManager.getState("WorkerSettingState");
        List<Line> lineList = stateManager.getTable().get(workerSettingState);
        List<Line> lines = stateManager.searchFinishState(lineList, endTurn);
        HashMap<Method, Boolean> hashMap = new HashMap<>();
        int oldPriority = lines.get(0).getPriority();

        stateManager.changePriority(workerSettingState,endTurn, hashMap, 5);

        assertNotEquals(oldPriority, lineList.get(0).getPriority());
    }

    @Test
    void changeConditionsTest1() throws ParserConfigurationException, SAXException, IOException, NoSuchMethodException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        controller.createFluxTable();

        State endTurn = stateManager.getState("EndTurnState");
        State readyForAction = stateManager.getState("ReadyForActionState");
        Method method1 = player.getClass().getDeclaredMethod("isInGame");
        Method method2 = player.getClass().getDeclaredMethod("isWorkerPlaced");
        Method newMethod = player.getClass().getDeclaredMethod("isChallenger");

        HashMap<Method, Boolean> oldMap = new HashMap<>();
        HashMap<Method, Boolean> newMap = new HashMap<>();

        oldMap.put(method1,true);
        oldMap.put(method2, true);
        newMap.put(newMethod, false);

        stateManager.changeConditions(endTurn,readyForAction, oldMap, newMap);
        List<Line> lineList = stateManager.searchFinishState(stateManager.getTable().get(endTurn), readyForAction);

        assertEquals(lineList.get(0).getConditions(), newMap);
    }

    @Test
    void changeConditionsTest2() throws ParserConfigurationException, SAXException, IOException, NoSuchMethodException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        controller.createFluxTable();

        State endTurn = stateManager.getState("EndTurnState");
        State stateTest = new StateTest(player);
        Method method1 = player.getClass().getDeclaredMethod("isInGame");
        Method method2 = player.getClass().getDeclaredMethod("isWorkerPlaced");
        Method newMethod = player.getClass().getDeclaredMethod("isChallenger");

        HashMap<Method, Boolean> oldMap = new HashMap<>();
        HashMap<Method, Boolean> newMap = new HashMap<>();

        oldMap.put(method1, true);
        oldMap.put(method2, true);
        newMap.put(newMethod, false);

        stateManager.changeConditions(endTurn, stateTest, oldMap, newMap);

        for (Line line : stateManager.getTable().get(endTurn))
            System.out.println(line.getFinishState());
    }

    @Test
    void changeFinishState() throws ParserConfigurationException, SAXException, IOException, NoSuchMethodException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        controller.createFluxTable();

        State endTurn = stateManager.getState("EndTurnState");
        State action = stateManager.getState("ActionState");
        State readyForAction = stateManager.getState("ReadyForActionState");

        Method method1 = player.getClass().getDeclaredMethod("isInGame");
        Method method2 = player.getClass().getDeclaredMethod("isWorkerPlaced");

        HashMap<Method, Boolean> hashMap = new HashMap<>();

        hashMap.put(method1, true);
        hashMap.put(method2, true);

        for(Line line: stateManager.getTable().get(endTurn))
            System.out.println(line.getFinishState());

        stateManager.changeFinishState(endTurn, readyForAction, hashMap, action);

        for(Line lin: stateManager.getTable().get(endTurn))
            System.out.println(lin.getFinishState());

    }

    @Test
    void changeStates() throws ParserConfigurationException, SAXException, IOException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        controller.createFluxTable();

        State stateTest = new StateTest(player);
        State endTurn = stateManager.getState("EndTurnState");

        for (State inState : stateManager.getTable().keySet()) {
            System.out.println("Lo stato di partenza è: " + inState);
            for (Line line : stateManager.getTable().get(inState))
                System.out.println("Lo stato di arrivo è: " + line.getFinishState());
            System.out.println("\n");
        }

        stateManager.changeStates(stateTest, endTurn);
        System.out.println("Cambiati gli stati\n");

        for (State state : stateManager.getTable().keySet()) {
            System.out.println("Lo stato di partenza è: " + state);
            for (Line line : stateManager.getTable().get(state))
                System.out.println("Lo stato di arrivo è: " + line.getFinishState());
            System.out.println("\n");
        }
    }


    @Test
    void setNextStateTest1() throws ParserConfigurationException, SAXException, IOException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        controller.createFluxTable();

        State stateTest = new StateTest(player);
        State endTurn = stateManager.getState("EndTurnState");
        stateManager.addNewState(stateTest);
        stateManager.addNewFinishSpace(endTurn, stateTest, null, false, 20);

        stateManager.setCurrent_state(endTurn);
        stateManager.setNextState(player);

        assertEquals(stateManager.getCurrent_state(), stateTest);
    }

    @Test
    void setNextStateTest2() throws ParserConfigurationException, SAXException, IOException, NoSuchMethodException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        controller.createFluxTable();

        State stateTest1 = new StateTest(player);
        State stateTest2 = new StateTest(player);
        State endTurn = stateManager.getState("EndTurnState");
        stateManager.addNewState(stateTest1);
        Method method = player.getWorkers().get(0).getClass().getDeclaredMethod("isCantMove");
        stateManager.addNewFinishSpace(endTurn, stateTest1, null, false, 10);
        stateManager.addNewFinishSpace(endTurn, stateTest2, method, false, 20);
        stateManager.sortAllTable();

        for (Line line : stateManager.getTable().get(endTurn))
            System.out.println("Lo stato di arrivo è: " + line.getFinishState());
        System.out.println("\n");


        stateManager.setCurrent_state(endTurn);
        stateManager.setNextState(player);

        for (Line line : stateManager.getTable().get(endTurn))
            System.out.println("Lo stato di arrivo è: " + line.getFinishState());
        System.out.println("\n");

        assertEquals(stateManager.getCurrent_state(), stateTest1);
    }

    @Test
    void sortForPriority() throws ParserConfigurationException, SAXException, IOException {
        Controller controller = new Controller();
        Player player = new Player();
        controller.setPlayer(player);
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        stateManager.createBaseStates(player);
        controller.createFluxTable();

        List<Line> lineList = stateManager.getTable().get(stateManager.getState("EndTurnState"));
        List<Line> newLineList = new ArrayList<>();

        newLineList.add(lineList.get(1));
        newLineList.add(lineList.get(0));
        newLineList.add(lineList.get(5));
        newLineList.add(lineList.get(3));
        newLineList.add(lineList.get(4));

        int i = 0;
        for(Line line: newLineList){
            System.out.println("Posizione numero " + i + " Prorità: " + line.getPriority());
            i++;
        }

        System.out.println("\n");

        stateManager.sortForPriority(newLineList);

        int j = 0;
        for(Line line: newLineList){
            System.out.println("Posizione numero " + j + " Prorità: " + line.getPriority());
            j++;
        }
    }


}