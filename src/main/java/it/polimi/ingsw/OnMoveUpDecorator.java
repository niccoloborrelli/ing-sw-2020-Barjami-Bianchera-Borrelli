package it.polimi.ingsw;

import java.io.IOException;
import static it.polimi.ingsw.DefinedValues.*;

public class OnMoveUpDecorator extends ActionStateDecorator {
    private final static String DENYUPPERMOVE="denyUpperMove";
    private static final String ACTIONTYPE1="move";
    private AbstractActionState decorated;

    OnMoveUpDecorator(AbstractActionState decorated,String effect) {
        super(decorated,effect);
    }

    @Override
    public void onInput(Visitor visitor) throws IOException {
        decorated.onInput(visitor);
    }

    @Override
    public void onStateTransition() throws IOException {
        decorated.onStateTransition();
        String action=player.getActionsToPerform().get(0);
        if(action.equals(ACTIONTYPE1)&&decorated.getSpaceToAct().getLevel()>decorated.getStartingSpace().getLevel()) //attivo gli effetti di oneMoveUp se il livello in cui si agisce e' superiore
            ability();
    }

    private void ability(){
        if(effect.equals(DENYUPPERMOVE))
            denyUppermoves();
    }

    public void denyUppermoves(){
        Worker tempWorker;
        for(int i = 0; i < DIM; i++)
            for(int j = 0; j < DIM; j++) {
                tempWorker = player.getIslandBoard().getSpace(i, j).getOccupator();
                if(tempWorker!=null&&tempWorker.getWorkerPlayer()!=player)
                    tempWorker.setCantMoveUp(true);
            }
    }
}
