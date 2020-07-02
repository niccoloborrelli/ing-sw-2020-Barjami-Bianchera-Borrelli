package it.polimi.ingsw;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class Line {

    /**
     * Represents a line of a table.
     */


    private final int MINIMUM_LEVEL_OF_PRIORITY = 0;
    private State finishState;
    private HashMap<Method, Boolean> conditions;
    private int priority;


    public Line(State finishState){
        this.finishState = finishState;
        conditions = new HashMap<>();
        priority = MINIMUM_LEVEL_OF_PRIORITY;
    }

    public void setConditions(HashMap<Method, Boolean> conditions) {
        this.conditions = conditions;
    }

    public void setFinishState(State finishState) {
        this.finishState = finishState;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public State getFinishState() {
        return finishState;
    }

    public int getPriority() {
        return priority;
    }

    public HashMap<Method, Boolean> getConditions() {
        return conditions;
    }

    /**
     * Controls if all conditions are verified.
     * @param classID indicates the class on which method are invoked.
     * @return true if all conditions are followed, otherwise returns false;
     * @throws IllegalAccessException if method invoked are private
     * @throws InvocationTargetException if method generate a Runtime Exception
     */

    public boolean controlCondition(Object classID) throws InvocationTargetException, IllegalAccessException, IllegalArgumentException {
        boolean result = true;
        boolean expectedValue;
        Object o = null;
        for(Method method: conditions.keySet()) {
            o = method.invoke(classID);
            expectedValue = conditions.get(method);
            if (!o.equals(expectedValue)) {
                result = false;
                break;
            }
        }
        return result;
    }
}
