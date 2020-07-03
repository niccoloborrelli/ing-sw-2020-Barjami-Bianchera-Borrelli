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
    private static final String WORKERPLACED = "isWorkerPlaced";
    private static final String SWAPWORKERS="SwapWorkers";
    private static final String ADDMOVE="AdditionalMove";
    private static final String DENYMOVE="DenyUpperMove";
    private static final String DOME="DomeEverywhere";
    private static final String ABNOINITIAL="AdditionalBuildNoInitial";
    private static final String BUILDTWICE="CanBuildTwiceNotDome";
    private static final String PUSHBACK="PushBack";
    private static final String WINJUMP="JumpMoreLevelsWin";
    private static final String BUILDBEFORE="BuildAlsoBeforeIfNotMoveUp";
    private static final String TOWERWIN="CompleteTowerWin";
    private static final String ADDBUILDNOPERIMETER="AdditionalBuildNotPerimeter";
    private static final String HIGHERNOMOVE="IfHigherNoMove";
    private static final String MOVEUP="MustMoveUp";
    private static final String BUILDUNDER="BuildUnderYou";

    public void decoratePlayer(HashMap<String, List<String>> godMap,Player player) throws NoSuchMethodException, ClassNotFoundException {
        StateManager stateManager=player.getStateManager();
        List<String> effects=godMap.get(player.getPlayerGod());
        if(effects!=null) {
            if (effects.contains(SWAPWORKERS)) {
                swapWorkers(player, stateManager);
            }

            if (effects.contains(ADDMOVE)) {
                additionalMove(player, stateManager);
            }

            if (effects.contains(DENYMOVE)) {
                denyUpperMove(player, stateManager);
            }

            if (effects.contains(DOME)) {
                domeEveryWhere(player);
            }

            if (effects.contains(ABNOINITIAL)) {
                additionalBuildNoInitial(player, stateManager);
            }
            if (effects.contains(BUILDTWICE)) {
                CanBuildTwiceNotDome(player, stateManager);
            }
            if (effects.contains(PUSHBACK)) {
                pushBack(player, stateManager);
            }
            if (effects.contains(WINJUMP)) {
                jumpMoreLevelsWin(player);
            }

            if (effects.contains(BUILDBEFORE)) {
                buildAlsoBefore(player, stateManager);
            }

            if (effects.contains(TOWERWIN)) {
                completeTowerWin(player);
            }

            if (effects.contains(ADDBUILDNOPERIMETER)) {
                additionalBuildsNoPerimeter(player, stateManager);
            }

            if (effects.contains(HIGHERNOMOVE)) {
                player.getIslandBoard().setHigherNoMove(true);
                player.setNotHigherNoMove(true);
            }

            if (effects.contains(MOVEUP)) {
                player.getIslandBoard().setMustMoveUp(true);
                player.setNotMustMoveUp(true);
            }

            if (effects.contains(BUILDUNDER)) {
                buildUnderYou(player);
            }
        }

    }


    /**
     * adds the power to move twice to a player
     * @param player the player to be decorated
     * @param stateManager the state manager of the player
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    private void additionalMove(Player player,StateManager stateManager) throws ClassNotFoundException, NoSuchMethodException {
        PowerActivationState activationState=new PowerActivationState(player,new AdditionalMoveFlow());
        afterActionActivation(stateManager, activationState, ANOTHERMOVE);

    }

    /**
     * permits to create a power activated after an action
     * @param stateManager state manager of the player receiving the power
     * @param activationState the state for activating the power
     * @param hasToDo the string name of the power
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
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

    /**
     * decorates a player move with the swap power
     * @param player player to be decorated
     * @param stateManager player's state manager
     */
    private void swapWorkers(Player player,StateManager stateManager){
        AbstractActionState actionState;
        actionState=(AbstractActionState) stateManager.getState(ACTION);
        MoveOnOccupiedDecorator decorator=new MoveOnOccupiedDecorator(actionState,SWAPEFFECT);
        player.setCantSwap(false);
        stateManager.changeStates(decorator,actionState); //metodo che cambia tutte le occurrenze di actionState in decorator
    }

    /**
     * gives a player the uppermovePower
     * @param player player to be given the power
     * @param stateManager stateManager of the player
     */
    private void denyUpperMove(Player player,StateManager stateManager){
        AbstractActionState actionState;
        actionState=(AbstractActionState) stateManager.getState(ACTION);
        OnMoveUpDecorator decorator=new OnMoveUpDecorator(actionState,DENYUPPERMOVE);
        stateManager.changeStates(decorator,actionState);
    }

    /**
     * gives a player the power to build everywhere
     * @param player player receiving the power
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    private void domeEveryWhere(Player player) throws ClassNotFoundException, NoSuchMethodException {
        PowerActivationState activationState=new PowerActivationState(player,new DomeOnEveryLevel());
        StateManager stateManager=player.getStateManager();
        stateManager.addNewState(activationState);
        State readyForActionState=stateManager.getState(INPUTFORACTION);
        State endTurnState=stateManager.getState(ENDTURN);
        State actionState=stateManager.getState(ACTION);
        Class cl = Class.forName(CLASSNAME1);
        Method m2 = cl.getDeclaredMethod("hasBuildsToDo");
        Method m4=cl.getDeclaredMethod(ANOTHERACTION);
        stateManager.addNewFinishSpace(actionState,activationState,m2,true,3);
        stateManager.addNewFinishSpace(activationState,readyForActionState,cl.getDeclaredMethod("isInGame"),true,3);
        stateManager.addNewFinishSpace(activationState,endTurnState,m4,false,3);
    }

    /**
     * gives a player the power to build multiple times, not in the initial space
     * @param player player to be given the power
     * @param stateManager stateManager of the player
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    private void additionalBuildNoInitial(Player player,StateManager stateManager) throws ClassNotFoundException, NoSuchMethodException {
        PowerActivationState activationState=new PowerActivationState(player,new AdditionalBuildFlow(NOINITIAL));
        additionalBuilds(player,stateManager,activationState);
    }

    /**
     * gives a player the power to build twice but not a dome
     * @param player player to be given the power
     * @param stateManager stateManager of the player
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    private void CanBuildTwiceNotDome(Player player,StateManager stateManager) throws ClassNotFoundException, NoSuchMethodException {
        PowerActivationState activationState=new PowerActivationState(player,new AdditionalBuildFlow(NODOME));
        additionalBuilds(player,stateManager,activationState);
    }

    /**
     * gives a player a power for multiple times
     * @param player the player receiving the power
     * @param stateManager the player's state manager
     * @param activationState the activation state of the player
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    private void additionalBuilds(Player player,StateManager stateManager, PowerActivationState activationState) throws ClassNotFoundException, NoSuchMethodException {
        afterActionActivation(stateManager, activationState, ANOTHERBUILD);
    }

    /**
     * gives a player the power to pushBack an other enemy worker
     * @param player player to be given the power
     * @param stateManager stateManager of the player
     */
    private void pushBack(Player player,StateManager stateManager){
        AbstractActionState actionState;
        actionState=(AbstractActionState) stateManager.getState(ACTION);
        MoveOnOccupiedDecorator decorator=new MoveOnOccupiedDecorator(actionState,PUSHEFFECT);
        player.setCantPush(false);
        stateManager.changeStates(decorator,actionState); //metodo che cambia tutte le occurrenze di actionState in decorator
    }

    /**
     * gives a player the power to win by jumping 2 levels down
     * @param player player to be given the power
     */
    private void jumpMoreLevelsWin(Player player){
        JumpMoreLevelsWin jmw=new JumpMoreLevelsWin(player.getWinCondition());
        player.setWinCondition(jmw);
    }

    /**
     * gives a player the power to win by having 5 complete towers in the board
     * @param player player to be given the power
     */
    private void completeTowerWin(Player player){
        CompleteTowerWin ctw=new CompleteTowerWin(player.getWinCondition());
        player.setWinCondition(ctw);
    }

    /**
     * gives a player the ability to build multiple times but not in the perimeter
     * @param player  player to be given the power
     * @param stateManager the stat manager of the player
     * @throws NoSuchMethodException
     * @throws ClassNotFoundException
     */
    private void additionalBuildsNoPerimeter(Player player,StateManager stateManager) throws NoSuchMethodException, ClassNotFoundException {
        PowerActivationState activationState=new PowerActivationState(player,new AdditionalBuildFlow(NOPERIMETER));
        additionalBuilds(player,stateManager,activationState);
    }

    /**
     * gives a player the power to build under himself
     * @param player player to be given the power
     */
    private void buildUnderYou(Player player){
        for(Worker tempWorker:player.getWorkers()){
            tempWorker.setCantBuildUnder(false);
        }
    }

    /**
     * gives a player the power to build before moving
     * @param player player receiving the power
     * @param stateManager state manager of the player
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    private void buildAlsoBefore(Player player,StateManager stateManager) throws ClassNotFoundException, NoSuchMethodException {
        PowerActivationState activationState=new PowerActivationState(player,new AdditionalBuildFlow(NOMOVEUP));
        stateManager.addNewState(activationState);
        State readyForActionState=stateManager.getState(INPUTFORACTION);
        State endTurnState=stateManager.getState(ENDTURN);
        Class cl = Class.forName(CLASSNAME1);
        Method m = cl.getDeclaredMethod(INGAME);
        Method m1 = cl.getDeclaredMethod(WORKERPLACED);
        stateManager.addNewFinishSpace(endTurnState,activationState,m,true,2);
        stateManager.addNewFinishSpace(activationState,readyForActionState,m,true,3);
        stateManager.addNewConditions(endTurnState, activationState, m1, true, 2);
        stateManager.sortAllTable();
    }
}