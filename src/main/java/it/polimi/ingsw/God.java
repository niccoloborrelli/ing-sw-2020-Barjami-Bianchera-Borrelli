package it.polimi.ingsw;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class God {
    private String godName;
    private WinConditionAB winCondition;
    private AbstractActionState actionState;
    private PowerActivationState powerActivationState;

    public God(String name){
        this.godName=name;
        winCondition=new BaseWinCondition();
        actionState=new ActionState();
    }
}
