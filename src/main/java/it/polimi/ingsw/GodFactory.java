package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class GodFactory {

    public List<God> godList(HashMap<String, List<String>> mappa){
        List<God> godList=new ArrayList<God>();
        Set<String> keys=mappa.keySet();
        for (String tempS:keys) {
            God tempGod=new God(tempS);
            List<String> effects=mappa.get(tempS);
            //GROSSO if; if; if.....if;
            //il decorato verra' sostituito mettendo quello del player
            if(effects.contains("SwapWorkers")){
                tempGod.addMoveEffect(new SwapWorkers(new BaseMovement()));
                tempGod.addOwnRestrictionEffect(new SwapWorkersRestriction(new BaseRestriction()));
            }
            if(effects.contains("AdditionalMove"))
                tempGod.addMoveEffect(new AdditionalMove(new BaseMovement()));
            if(effects.contains("DenyUpperMove")) {
                tempGod.addEnemyRestrictionEffect(new DenyUpperMove(new BaseRestriction()));
                tempGod.addMoveEffect(new ActivateOthersNotMoveUpIfNotMoveUp(new BaseMovement()));
            }
            if(effects.contains("DomeEverywhere"))
                tempGod.addBuildEffect(new CanBuildADomeAtAnyLevel(new BaseBuild()));
            if(effects.contains("AdditionalBuildNoInitial"))
                tempGod.addBuildEffect(new AdditionalBuildNoInitial(new BaseBuild()));
            if(effects.contains("CanBuildTwiceNotDome"))
                tempGod.addBuildEffect(new CanBuildTwiceNotDome(new BaseBuild()));
            if(effects.contains("PushBack")){
                tempGod.addMoveEffect(new PushWorker(new BaseMovement()));
                tempGod.addOwnRestrictionEffect(new PushWorkerRestriction(new BaseRestriction()));
            }
            if(effects.contains("JumpMoreLevelsWin"))
                tempGod.addWinConditionEffect(new JumpMoreLevelsWin(new BaseWinCondition()));
            if(effects.contains("BuildAlsoBeforeIfNotMoveUp"))
                tempGod.addMoveEffect(new BuildAlsoBeforeIfNotMoveUp(new BaseMovement()));
            if(effects.contains("CompleteTowerWin"))
                tempGod.addWinConditionEffect(new CompleteTowerWin(new BaseWinCondition()));
            if(effects.contains("AdditionalBuildNotPerimeter"))
                tempGod.addBuildEffect(new AdditionalBuildNotPerimeter(new BaseBuild()));
            if(effects.contains("IfHigherNoMove"))
                tempGod.addEnemyRestrictionEffect(new IfHigherNoMoveRestriction(new BaseRestriction()));
            if(effects.contains("MustMoveUp"))
                tempGod.addEnemyRestrictionEffect(new MustMoveUpRestriction(new BaseRestriction()));
            if(effects.contains("BuildUnderYou"))
                tempGod.addOwnRestrictionEffect(new BuildUnderYouRestriction(new BaseRestriction()));


            godList.add(tempGod);
        }
        return godList;
    }
}
