package it.polimi.ingsw;

import sun.jvm.hotspot.debugger.win32.coff.DebugVC50SSSrcLnSeg;

import java.util.ArrayList;
import java.util.List;

public class BaseRestriction extends RestrictionAB {


    /**
     * Represents a pre-control of every operations could be done on game field.
     * Sets in every space which player's worker can operate on.
     * MAXDIFFERENCE sets the maximum difference in height between a space's level and
     * worker space's level (in this order).
     * MINDIFFERENCE sets the minimum difference in height between two previous level.
     * MOVE and BUIILD defines if operation's control need more restrictive rules.
     */

    public final int MINDIFFERENCE = -3;
    public final int MAXDIFFERENCE = 1;
    public final boolean MOVE = true;
    public final boolean BUILD = false;

    /**
     * Sets for every worker which space can move in.
     *
     * @param player This is the worker's owner checked.
     * @param islandBoard This is the game field that contains the list of Space.
     */
    @Override
    public void restrictionEffectMovement(Player player, IslandBoard islandBoard) {
        restriction(player, islandBoard, MOVE);
    }

    /**
     * Verifies for every player's worker which space can build on.
     *
     * @param player This is the worker's owner checked.
     * @param islandBoard This is the game field that contains the list of Space.
     */
    @Override
    public void restrictionEffectBuilding(Player player, IslandBoard islandBoard) {
        restriction(player, islandBoard, BUILD);
    }

    /**
     * Sets which worker can operate on which space.
     *
     * @param player This is the worker's owner checked.
     * @param islandboard This is the game field that contains the list of Space.
     * @param needMovement This boolean indicates if this control has to add extra condition, due to moving rules
     */
    private void restriction(Player player, IslandBoard islandboard, boolean needMovement) {
        List<Space> possibleSpace;
        islandboard.resetBoard();
        for (Worker worker : player.getWorkers()) {
            possibleSpace = findAroundSpace(worker, islandboard);
            setValidSpace(worker, possibleSpace, needMovement);
        }
    }

    /**
     * Finds spaces in game field around this worker.
     *
     * @param worker This is the worker whose around spaces are controlled.
     * @param islandBoard This is the game field that contains the list of Space.
     * @return minimum three-spaces' list that contains positions around this worker.
     */
    private List<Space> findAroundSpace(Worker worker, IslandBoard islandBoard) {
        List<Space> spaceList = new ArrayList<>();
        for (int i = worker.getWorkerSpace().getRow() - 1; i <= worker.getWorkerSpace().getRow() + 1; i++)
            for (int j = worker.getWorkerSpace().getColumn() - 1; j <= worker.getWorkerSpace().getColumn() + 1; j++) {
                if ((0 <= i && i < 5) && (0 <= j && j < 5))
                    spaceList.add(islandBoard.getSpace(i, j));
            }
        return spaceList;
    }

    /**
     * Controls this spaceList and remove invalid spaces, according to rules of game.
     * If a space has a dome or another worker on its, this space are considered invalid.
     * If <move>true</move> it also remove spaces with a level's difference major than MAXDIFFERENCE.
     * @param worker This is the worker investigated.
     * @param spaceList This contains valid spaces until now
     * @param move This indicates if it's necessary a more restrictive check.
     */

    private void setValidSpace(Worker worker, List<Space> spaceList, boolean move) {
        for (Space space : spaceList) {
            removeInvalidSpace(spaceList, space);
            if (move) {
                removeSpecifiedMove(spaceList, worker.getWorkerSpace().getLevel(), space);
                removeDueToMoveFlag(spaceList, worker, space);
                space.addAvailableMovement(worker);
            }else
                space.addAvailableBuilding(worker);

        }

    }

    /**
     * Removes spaces that are already occupied or has a dome.
     * @param spaceList contains valid spaces until now.
     * @param space is the space in which presence of dome or another worker will be controlled.
     */

    private void removeInvalidSpace(List<Space> spaceList, Space space) {
        if (space.getOccupator() != null || space.HasDome())
            spaceList.remove(space);
    }

    /**
     * Removes spaces from list, if this level's difference is too high.
     * The level's difference is always calculated between in this way: level of investigated space as minuend and
     * worker space's level as subtrahend.
     * @param spaceList contains valid spaces until now.
     * @param workerLevel indicates space level's of worker controlled.
     * @param space is the space, in which level is controlled.
     */

    private void removeSpecifiedMove(List<Space> spaceList, int workerLevel, Space space) {
        int differenceInHeight = space.getLevel() - workerLevel;
        if (differenceInHeight > MAXDIFFERENCE || differenceInHeight < MINDIFFERENCE)
            spaceList.remove(space);
    }

    /**
     * Removes all of spaces if worker can't move.
     * Removes spaces with a level higher than level of worker's spaces if this worker can't move up.
     * Removes spaces lower with a level lower than level of worker's spaces if this worker can't move up.
     * @param spaceList contains valid spaces until now.
     * @param worker is class that cointains flags.
     * @param space is the space, in which level is controlled.
     */

    private void removeDueToMoveFlag(List<Space> spaceList, Worker worker, Space space){
        if(worker.isCantMove()){
            spaceList.clear();
        }else {
            if (worker.isCantMoveUp())
                cantMoveUP(spaceList, worker.getWorkerSpace().getLevel(), space);
            if (worker.isCantMoveDown())
                cantMoveDOWN(spaceList, worker.getWorkerSpace().getLevel(), space);
        }
    }


    /**
     * Removes spaces with a level higher than level of worker's spaces.
     * @param spaceList contains valid spaces until now.
     * @param workerLevel ndicates space level's of worker controlled.
     * @param space is the space, in which level is controlled.
     */
    private void cantMoveUP(List<Space> spaceList, int workerLevel, Space space){
        if(space.getLevel()>workerLevel)
            spaceList.remove(space);
    }

    /**
     * Removes spaces with a level lower than level of worker's spaces.
     * @param spaceList contains valid spaces until now.
     * @param workerLevel ndicates space level's of worker controlled.
     * @param space is the space, in which level is controlled.
     */

    private void cantMoveDOWN(List<Space> spaceList, int workerLevel, Space space){
        if(space.getLevel()<workerLevel)
            spaceList.remove(space);
    }

}



