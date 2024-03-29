package it.polimi.ingsw;

public class DefinedValues {

    protected static final String actionType1 = "move";
    protected static final String actionType2 = "build";
    protected static final int DIM = 5;
    protected static final int MINROW = 0;
    protected static final int MAXROW = 4;
    protected static final int MINCOLUMN = 0;
    protected static final int MAXCOLUMN = 4;
    protected static final int MAXIMUMLEVEL = 3;
    protected static final int ROW_CLI = (MAXROW + 1) * 2 + 2;
    protected static final int COLUMN_CLI = (MAXCOLUMN + 1) * 4 + 2;
    protected static final int FIRST_ROW_CLI = 1;
    protected static final int FIRST_COLUMN_CLI = 1;
    protected static final int SECOND_COLUMN_CLI = 5;
    protected static final int COMPLETE_TOWER_TO_WIN = 5;
    protected static final int DOME_LEVEL = 3;
    protected static final int JUMP_LEVELS_TO_WIN = 2;
    protected static final int MINSIZE=0;
    protected static final int firstWorker=0;
    protected static final int secondWorker=1;
    protected static final int MINLEVEL=0;

    protected static final String actionState = "ActionState";
    protected static final String readyForActionState = "ReadyForActionState";
    protected static final String colorSettingState = "ColorSettingState";
    protected static final String nameSettingState = "NameSettingState";
    protected static final String powerActivationState = "PowerActivationState";
    protected static final String endGameState = "EndGameState";
    protected static final String workerSettingState = "WorkerSettingState";
    protected static final String godSetState = "GodSetState";
    protected static final String godChoice = "GodChoiceState";
    protected static final String endTurn = "EndTurnState";
}
