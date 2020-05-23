package it.polimi.ingsw;

import java.util.HashMap;

public class InputVisitor implements Visitor {

    String string;
    Worker worker;
    Space space;
    int value;

    @Override
    public void visit(ReadyForActionState readyForActionState) {
        HashMap<Worker, Space> hashMap = new HashMap<Worker, Space>();
        hashMap.put(worker, space);
        readyForActionState.set
    }

    @Override
    public void visit(ActionState actionState) {

    }

    @Override
    public void visit(EndTurnState endTurnState) {

    }
}
