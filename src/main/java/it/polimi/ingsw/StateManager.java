package it.polimi.ingsw;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class StateManager {

    /*
    Idee: ordinare subito la tabella in base alla priorità, in modo tale che il calcolo del nuovo stato sia veloce
     */

    /**
     * Permits a run-time management of state flow.
     * stateHashMap contains correspondences between state name and effective class.
     * table contains possible state changes with conditions or not. It has also a level of priority for every change.
     * Two changes can't have the same level of priority. In case of this, the first that realizes conditions are chosen.
     * If it would like to implement "and" between conditions, it needs add conditions in hash map in the same line.
     * If it would like to implement "or" between conditions, it needs have separate line for each condition.
     * A condition is realized when method return has the same value of "expected value".
     * Only method "is" + "nameVariable" are valid and they always return a variable. (It could be expanded evaluating int with a new hash map in Line).
     * Until now, every flag, that has to be controlled, is in a single class.
     */

    private State current_state;
    private HashMap<String, State> stateHashMap;
    private HashMap<State, List<Line>> table;
    private TurnManager turnManager;

    /*
    NON è POSSIBILE AVERE LO STESSO STATO DI PARTENZA PER STATI DI ARRIVO CON UGUALE PRIORITà
    SE IL METODO NON DEVE CONTROLLARE NESSUNA CONDIZIONE, SI USERà IL METODO QUI SOTTO ALWAYSTRUE E EXPECTED VALUE = TRUE
     */

    public StateManager() {
        stateHashMap = new HashMap<>();
        table = new HashMap<>();
    }

    /*
        AREA METODI --ADD--
     */

    /**
     * Adds a new state if it doesn't exist yet, it will be matched with his name and will be inserted in
     * table without arrival's state.
     * @param newState is the new state added.
     */
    public void addNewState(State newState) {
        if (!stateHashMap.containsValue(newState)) {
            stateHashMap.put(newState.toString(), newState);
            table.put(newState, new ArrayList<>());
        }
    }

    /*
    Se non ha una condizione method e expectedvalue saranno a null
     */

    /**
     * Adds a new arrival's state for departure's state (already existing) adding a priority related to this.
     * Sets also the condition about state's changing. If there's no condition, method and expected value could be null.
     * @param startState is the state's departure.
     * @param finishState is the state's arrival.
     * @param method contains the way to control determined flag.
     * @param expectedValue is the result expected from method.
     * @param priority sets the priority in terms of need/requirement.
     */

    public void addNewFinishSpace(State startState, State finishState, Method method, boolean expectedValue, int priority) {
        Line newLine = new Line(finishState);
        newLine.getConditions().put(method, expectedValue);
        newLine.setPriority(priority);
        if(table.get(startState)!=null)
            table.get(startState).add(newLine);
    }

    /**
     * Adds a new condition to the changing from this start state and this finish state, both already existing.
     * Sets also the level of priority of this condition.
     * If line exists, it has to be unique, due to class' specifications.
     * @param startState is the state's departure.
     * @param finishState is the state's arrival.
     * @param method contains the way to control determined flag.
     * @param expectedValue is the result expected from method.
     * @param priority sets the priority in terms of need/requirement.
     */

    public void addNewConditions(State startState, State finishState, Method method, boolean expectedValue, int priority) {
        if(method!=null) {
            List<Line> lineList = table.get(startState);
            if (lineList != null) {
                List<Line> linesWithFinishState = searchFinishState(lineList, finishState);
                if (linesWithFinishState != null) {
                    lineList = searchForPriority(linesWithFinishState, priority);
                    Line validLine;
                    if (lineList != null) {
                        validLine = lineList.get(0);
                        validLine.getConditions().put(method,expectedValue);
                    }
                }
            }
        }
    }

    /**
     * Adds a new state with its state's arrival, condition to change and priority level.
     * If it exists, this method adds only the condition as a new one of existing specific line.
     * @param startState is this new state.
     * @param finishState is startState arrival's
     * @param method is the way to control the condition.
     * @param expectedValue is the value that validate change.
     * @param priority is the level of priority.
     */

    public void addAllInOneTime(State startState, State finishState, Method method, boolean expectedValue, int priority) {
        addNewState(startState);
        addNewFinishSpace(startState, finishState, method, expectedValue, priority);
    }

    /*
        AREA METODI --SEARCH--
     */

    /**
     * Finds which lines have this state as arrival.
     * @param lines is the list controlled.
     * @param finishState is the state of arrival.
     * @return a list of lines with this finish state. If there's no one it returns null.
     */

    public List<Line> searchFinishState(List<Line> lines, State finishState) {
        List<Line> resultOfSearch = new ArrayList<>();

        if(lines!=null && finishState!=null) {
            for (Line line : lines) {
                if (line.getFinishState().equals(finishState))
                    resultOfSearch.add(line);
            }
        }
        return sizeControl(resultOfSearch);
    }

    /**
     * Finds which lines have this value of priority.
     * @param lines is the list controlled.
     * @param priority is the value on which research are based.
     * @return a list containing lines found with this level of priority. If there's no one it returns null.
     */

    public List<Line> searchForPriority(List<Line> lines, int priority) {
        List<Line> resultOfSearch = new ArrayList<>();

        for (Line line : lines) {
            if (line.getPriority() == priority)
                resultOfSearch.add(line);
        }

        return sizeControl(resultOfSearch);
    }

    /**
     * Finds which lines have this method.
     * @param lines is the list controlled.
     * @param condition is the value on which research are based.
     * @return a list containing lines found with this method. If there's no one it returns null.
     */

    public List<Line> searchForCondition(List<Line> lines, HashMap<Method,Boolean> condition) {
        List<Line> resultOfSearch = new ArrayList<>();

        if(lines!=null && condition!=null) {
            for (Line line : lines) {
                if (line.getConditions().equals(condition))
                    resultOfSearch.add(line);
            }
        }

        return sizeControl(resultOfSearch);
    }

    /**
     * Controls size of given list.
     * @param lineList is the list controlled.
     * @return early list if has at least one element in it, otherwise null.
     */

    private List<Line> sizeControl(List<Line> lineList) {
        if(lineList!=null && lineList.size()>0)
            return lineList;
        else
            return null;
    }

    /*
        AREA METODI --REMOVE--
     */

    /**
     * Removes from state's list this state.
     * Removes also from table this state, both as start state and lines (of every other state) which has it as arrival state.
     * @param startState is the state that has to be deleted.
     */
    public void removeStartState(State startState){
        if(startState!=null){
            stateHashMap.remove(startState.toString());
            removeFromOtherFinishSpace(startState);
            table.remove(startState);
        }
    }

    /**
     * Removes every line that has this state as arrival.
     * After removal, if start has no left lines, it's removed.
     * @param deleted is state that has to be deleted as arrivals
     */
    public void removeFromOtherFinishSpace(State deleted){
        Collection<State> stateSet = stateHashMap.values();
        List<Line> lineListOfState;
        for(State state: stateSet){
            lineListOfState = table.get(state);
            if(lineListOfState!=null) {
                removeFromArrival(lineListOfState, deleted);
                removeEmptyState(state,lineListOfState);
            }
        }
    }

    private void removeEmptyState(State empty, List<Line> left){
        if(left.size()==0)
            table.remove(empty);
    }
    /**
     * Removes line that has this state as arrival.
     * @param lineList is the list controlled.
     * @param deleted is the state that has to be deleted as arrival.
     */

    public void removeFromArrival(List<Line> lineList, State deleted){
        if(lineList!=null && deleted!=null)
            lineList.removeIf(line -> line.getFinishState().equals(deleted));
    }

    /**
     * Removes line that has this specific condition (determined by arrival's state method)
     * @param startState is departure state's condition.
     * @param finishState is arrival's state in condition.
     * @param conditions are conditions that have to be deleted.
     */

    public void removeSpecifiedCondition(State startState, State finishState, HashMap<Method,Boolean> conditions){
           List<Line> lineList = table.get(startState);
           List<Line> finishList = searchFinishState(lineList, finishState);
           if(finishList!=null){
                List<Line> methodList = searchForCondition(finishList, conditions);
                if(methodList!=null)
                    lineList.removeIf(methodList::contains);
                    removeEmptyState(startState, lineList);
        }
    }


    /**
     * Permits possibilities of no-condition in table.
     * @return always true
     */

    public boolean alwaysTrue() {
        return true;
    }


    /*
    AREA METODI --CHANGE--
     */

    /**
     * Change priority in one specific line.
     * @param startState is start state.
     * @param finishState is arrival state.
     * @param conditions is conditions in which change priority
     * @param newPriority is the new value of priority.
     */

    public void changePriority(State startState, State finishState, HashMap<Method,Boolean> conditions, int newPriority){

        searchUnique(startState,finishState,conditions).setPriority(newPriority);
    }

    /**
     * Change conditions in one specific line.
     * @param startState is start state.
     * @param finishState is arrival state.
     * @param oldConditions is conditions to change.
     * @param newConditions is new conditions.
     */

    public void changeConditions(State startState, State finishState, HashMap<Method,Boolean> oldConditions , HashMap<Method,Boolean> newConditions){

        searchUnique(startState,finishState,oldConditions).setConditions(newConditions);
    }

    /**
     * Change arrival state in one specific line.
     * @param startState is start state.
     * @param oldFinishState is old arrival state.
     * @param conditions is conditions of line.
     * @param newFinishState is new arrival state.
     */

    public void changeFinishState(State startState, State oldFinishState, HashMap<Method,Boolean> conditions, State newFinishState){

        searchUnique(startState,oldFinishState,conditions).setFinishState(newFinishState);
    }

    /**
     * Permits to find only one specific line determined by: start state, arrival state and conditions.
     * @param startState is start state.
     * @param finishState is arrival state.
     * @param conditions is condition of state change.
     * @return line searched.
     */

    private Line searchUnique(State startState, State finishState, HashMap<Method,Boolean> conditions){
        List<Line> lineList = table.get(startState);
        List<Line> sameFinishState = searchFinishState(lineList, finishState);
        List<Line> unique = searchForCondition(sameFinishState,conditions);

        return unique.get(0);
    }

    /*
    AREA METODO --SET NEXT STATE
     */

    /**
     * Sets next state depending by conditions.
     * If control generates exception, condition is considered wrong and it will be removed.
     * @param obRef is class in which are invoked method to control conditions
     */

    public void setNextState(Object obRef){
        List<Line> stateLines = table.get(current_state);

        for(Line line: stateLines){
            try {
                if (line.controlCondition(obRef))
                    current_state = line.getFinishState();
            } catch (InvocationTargetException | IllegalAccessException e) {
                removeSpecifiedCondition(current_state,line.getFinishState(),line.getConditions());
            }
        }
        current_state.onStateTransition();
    }


    /*
    AREA METODO -- SORT FOR PRIORITY
     */

    /**
     * Sorts for priority this given list.
     * @param listToOrder is list to order.
     */
    public void sortForPriority(List<Line> listToOrder){
        Comparator<Line> comparator = comparatorLine();

        listToOrder.sort(comparator);
    }

    /**
     * Creates a priority comparator between Line
     * @return a priority comparator of lines.
     */
    private Comparator<Line> comparatorLine(){
        Comparator<Line> comparator = new Comparator<Line>() {
            @Override
            public int compare(Line o1, Line o2) {
                int firstPriority = o1.getPriority();
                int secondPriority = o2.getPriority();

                if(firstPriority>secondPriority)
                    return 1;
                else if(firstPriority==secondPriority)
                    return 0;
                else
                    return -1;
            }
        };

        return comparator;
    }

    public TurnManager getTurnManager() {
        return turnManager;
    }

    public void setTurnManager(TurnManager turnManager) {
        this.turnManager = turnManager;
    }
}


