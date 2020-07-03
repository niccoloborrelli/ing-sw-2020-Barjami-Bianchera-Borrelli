package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void playerClass() {
        Player player = new Player();
        player.setPlayerName("Player");
        String string = player.getPlayerName();

        player.setPlayerColor("Color");
        string = player.getPlayerColor();

        player.setPlayerGod("God");
        string = player.getPlayerGod();

        player.setPowerActivated(true);
        Boolean bool = player.isPowerActivated();

        player.setPowerNotUsable(true);
        bool = player.isPowerNotUsable();

        player.setInGame(true);
        bool = player.isInGame();

        player.setCantSwap(true);
        bool = player.isCantSwap();

        player.setCantPush(true);
        bool = player.isCantPush();

        player.setPowerUsed(true);
        bool = player.isPowerUsed();

        player.setDomeEveryWhere(true);
        bool = player.isDomeEveryWhere();

        player.setHasWon(true);
        bool = player.isHasWon();

        player.setNotHigherNoMove(true);
        bool = player.isNotHigherNoMove();

        player.setNotMustMoveUp(true);
        bool = player.isNotMustMoveUp();

        player.setLastChange(new LastChange());
        LastChange lastChange = player.getLastChange();

        player.addAnAction("Action");
        string = player.removeAction();

        player.setStateManager(new StateManager());
        StateManager stateManager = player.getStateManager();

        player.setActionsToPerform(new ArrayList<>());
        bool = player.hasActionsToPerform();
        bool = player.hasAction();
        bool = player.hasBuildsToDo();
        bool = player.hasMovesToDo();
        bool = player.isValidGod();

        player.setController(new Controller());
        Controller controller = player.getController();

        player.setWinCondition(new BaseWinCondition());
        WinConditionAB winConditionAB = player.getWinCondition();

        player.setIslandBoard(new IslandBoard());
        IslandBoard islandBoard = player.getIslandBoard();

        player.setActionsToPerform(new ArrayList<>());
        List<String> list= player.getActionsToPerform();
        bool = player.isEmpty();
        player.setEmpty(true);

        player.setLastReceivedInput(new WorkerSpaceCouple());
        WorkerSpaceCouple workerSpaceCouple = player.getLastReceivedInput();

        player.setWorkers(new ArrayList<>());
        List<Worker> workers = player.getWorkers();
        bool = player.isWorkerPlaced();

    }
}