package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GodFactory {
    private static final String CLASSNAME1="it.polimi.ingsw.Player";
    private static final String INGAME="isInGame";
    private static final String DENYUPPERMOVE="denyUpperMove";
    private static final String SWAPEFFECT="swap";
    private static final String PUSHEFFECT="push";
    private static final String NOPERIMETER="perimeterSpace";
    private static final String NODOME="dome";
    private static final String NOINITIAL="initialSpace";
    private static final String NOMOVEUP="moveUp";
    private static final String ENDTURN="EndTurnState";
    private static final String ACTION="ActionState";
    private static final String ANOTHERBUILD="hasBuildsToDo";
    private static final String ANOTHERMOVE="hasMovesToDo";
    private static final String ANOTHERACTION="hasActionsToPerform";
    private static final String INPUTFORACTION="ReadyForActionState";

    public void decoratePlayer(HashMap<String, List<String>> godMap,Player player) throws NoSuchMethodException, ClassNotFoundException {
        StateManager stateManager=player.getStateManager();
        List<String> effects=godMap.get(player.getPlayerGod());
        if(effects.contains("SwapWorkers")){
            swapWorkers(player,stateManager);
        }

        if(effects.contains("AdditionalMove")){
            additionalMove(player,stateManager);
        }

        if(effects.contains("DenyUpperMove")) {
            denyUpperMove(player,stateManager);
        }

        if(effects.contains("DomeEverywhere")){
            domeEveryWhere(player);
        }

        if(effects.contains("AdditionalBuildNoInitial")){
            additionalBuildNoInitial(player,stateManager);
        }
        if(effects.contains("CanBuildTwiceNotDome")) {
            CanBuildTwiceNotDome(player,stateManager);
        }
        if(effects.contains("PushBack")){
            pushBack(player,stateManager);
        }
        if(effects.contains("JumpMoreLevelsWin")){
            jumpMoreLevelsWin(player);
        }

        if(effects.contains("BuildAlsoBeforeIfNotMoveUp")){
            buildAlsoBefore(player,stateManager);
        }

        if(effects.contains("CompleteTowerWin")){
            completeTowerWin(player);
        }

        if(effects.contains("AdditionalBuildNotPerimeter")){
            additionalBuildsNoPerimeter(player,stateManager);
        }

        if(effects.contains("IfHigherNoMove")){

        }

        if(effects.contains("MustMoveUp")) {

        }

        if(effects.contains("BuildUnderYou")){
            buildUnderYou(player);
        }

    }


    private void additionalMove(Player player,StateManager stateManager) throws ClassNotFoundException, NoSuchMethodException {
        PowerActivationState activationState=new PowerActivationState(player,new AdditionalMoveFlow());
        afterActionActivation(stateManager, activationState, ANOTHERMOVE);

    }

    private void afterActionActivation(StateManager stateManager, PowerActivationState activationState, String hasToDo) throws ClassNotFoundException, NoSuchMethodException {
        stateManager.addNewState(activationState);
        State readyForActionState=stateManager.getState(INPUTFORACTION);
        State endTurnState=stateManager.getState(ENDTURN);
        State actionState=stateManager.getState(ACTION);
        Class cl = Class.forName(CLASSNAME1);
        Method m1=cl.getDeclaredMethod("isPowerUsed");
        Method m2 = cl.getDeclaredMethod(hasToDo);
        Method m4=cl.getDeclaredMethod(ANOTHERACTION);
        stateManager.addNewFinishSpace(actionState,activationState,m1,false,3);
        stateManager.addNewConditions(actionState,activationState,m2,false,3);
        stateManager.addNewFinishSpace(activationState,readyForActionState,m4,true,3);
        stateManager.addNewFinishSpace(activationState,endTurnState,m4,false,3);
    }

    private void swapWorkers(Player player,StateManager stateManager){
        AbstractActionState actionState;
        actionState=(AbstractActionState) stateManager.getState(ACTION);
        MoveOnOccupiedDecorator decorator=new MoveOnOccupiedDecorator(actionState,SWAPEFFECT);
        player.setCantSwap(false);
        stateManager.changeStates(decorator,actionState); //metodo che cambia tutte le occurrenze di actionState in decorator
    }

    private void denyUpperMove(Player player,StateManager stateManager){
        AbstractActionState actionState;
        actionState=(AbstractActionState) stateManager.getState(ACTION);
        System.out.println(actionState.toString());
        OnMoveUpDecorator decorator=new OnMoveUpDecorator(actionState,DENYUPPERMOVE);
        stateManager.changeStates(decorator,actionState);
    }

    private void domeEveryWhere(Player player){
        player.setDomeEveryWhere(true);
    }

    private void additionalBuildNoInitial(Player player,StateManager stateManager) throws ClassNotFoundException, NoSuchMethodException {
        PowerActivationState activationState=new PowerActivationState(player,new AdditionalBuildFlow(NOINITIAL));
        additionalBuilds(player,stateManager,activationState);
    }

    private void CanBuildTwiceNotDome(Player player,StateManager stateManager) throws ClassNotFoundException, NoSuchMethodException {
        PowerActivationState activationState=new PowerActivationState(player,new AdditionalBuildFlow(NODOME));
        additionalBuilds(player,stateManager,activationState);
    }

    private void additionalBuilds(Player player,StateManager stateManager, PowerActivationState activationState) throws ClassNotFoundException, NoSuchMethodException {
        afterActionActivation(stateManager, activationState, ANOTHERBUILD);
    }

    private void pushBack(Player player,StateManager stateManager){
        AbstractActionState actionState;
        actionState=(AbstractActionState) stateManager.getState(ACTION);
        MoveOnOccupiedDecorator decorator=new MoveOnOccupiedDecorator(actionState,PUSHEFFECT);
        player.setCantPush(false);
        stateManager.changeStates(decorator,actionState); //metodo che cambia tutte le occurrenze di actionState in decorator
    }

    private void jumpMoreLevelsWin(Player player){
        JumpMoreLevelsWin jmw=new JumpMoreLevelsWin(player.getWinCondition());
        player.setWinCondition(jmw);
    }

    private void completeTowerWin(Player player){
        CompleteTowerWin ctw=new CompleteTowerWin(player.getWinCondition());
        player.setWinCondition(ctw);
    }

    private void additionalBuildsNoPerimeter(Player player,StateManager stateManager) throws NoSuchMethodException, ClassNotFoundException {
        PowerActivationState activationState=new PowerActivationState(player,new AdditionalBuildFlow(NOPERIMETER));
        additionalBuilds(player,stateManager,activationState);
    }

    private void buildUnderYou(Player player){
        for(Worker tempWorker:player.getWorkers()){
            tempWorker.setCantBuildUnder(false);
        }
    }

    private void buildAlsoBefore(Player player,StateManager stateManager) throws ClassNotFoundException, NoSuchMethodException {
        PowerActivationState activationState=new PowerActivationState(player,new AdditionalBuildFlow(NOMOVEUP));
        stateManager.addNewState(activationState);
        State readyForActionState=stateManager.getState(INPUTFORACTION);
        State endTurnState=stateManager.getState(ENDTURN);
        Class cl = Class.forName(CLASSNAME1);
        Method m = cl.getDeclaredMethod(INGAME);
        System.out.println("IL NOME DEL METODO " + m.getName());
        stateManager.addNewFinishSpace(endTurnState,activationState,m,true,2);
        stateManager.addNewFinishSpace(activationState,readyForActionState,m,true,3);
        stateManager.sortAllTable();
    }
}
