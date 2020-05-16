package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

public class CheckingUtility {

    private static final int CODE_MOVE = 0;
    private static final int CODE_BUILD = 1;
    private static final int CODE_OTHERS = 2;
    private static final int LOWEST_ROW = 0;
    private static final int BIGGEST_ROW = 5;
    private static final int LOWEST_COLUMN = 0;
    private static final int BIGGEST_COLUMN = 5;
    private static final int RANGE_ROW_ALLOWED_ACTION = 1;
    private static final int RANGE_COLUMN_ALLOWED_ACTION = 1;
    private static final int FIRST_INDEX_OF_WORKER = 0;
    private static final int MAX_DIFFERENCE = 1;
    private static final int MIN_DIFFERENCE = -3;

    /**
     * Copies determined action list of both workers.
     * @param player is worker's owner.
     * @param action is the specific action controlled.
     * @return an array containg (specific) action list of every worker.
     */

    public static List<ArrayList<Space>> getLists(Player player, String action){
        List<ArrayList<Space>> validSpaces = new ArrayList<ArrayList<Space>>();

        int code = traduceAction(action);

        for(Worker worker: player.getWorkers()) {
            if (code==CODE_MOVE) {
                ArrayList<Space> result = new ArrayList<>(worker.getPossibleMovements());
                validSpaces.add(result);
            }else if(code==CODE_BUILD) {
                ArrayList<Space> result = new ArrayList<>(worker.getPossibleBuilding());
                validSpaces.add(result);
            }
        }

        return validSpaces;
    }

    /**
     * Sets lists of valid spaces in every worker's specific (in base of action) attribute
     * @param player is workers' owner.
     * @param islandBoard is game field in which workers are controlled.
     * @param action indicates if method has to control possible movements or buildings
     */

    public static void calculateValidSpace(Player player, IslandBoard islandBoard, String action){
        int needMovement = traduceAction(action);
        for(Worker worker: player.getWorkers()){
            if(needMovement == CODE_MOVE){
                worker.getPossibleMovements().clear();
                worker.getPossibleMovements().addAll((restriction(worker,islandBoard,needMovement)));
            }
            else if(needMovement == CODE_BUILD)
                worker.getPossibleBuilding().addAll(restriction(worker,islandBoard,needMovement));
        }
    }

    /**
     * Sets which worker can operate on which space.
     *
     * @param worker is the worker checked.
     * @param islandBoard is the game field that contains the list of Space.
     * @param code indicates if this control has to add extra condition, due to moving/building rules
     * @return list of valid spaces
     */

    private static List<Space> restriction(Worker worker, IslandBoard islandBoard, int code) {
        List<Space> possibleSpace;
        List<Space> leftPossibleSpace;

        possibleSpace = findAroundSpace(worker, islandBoard);
        leftPossibleSpace = setValidSpace(worker, possibleSpace, code);

        return leftPossibleSpace;
    }

    /**
     * Finds spaces in game field around this worker.
     *
     * @param worker This is the worker whose around spaces are controlled.
     * @param islandBoard This is the game field that contains the list of Space.
     * @return minimum three-spaces' list that contains positions around this worker.
     */
    private static List<Space> findAroundSpace(Worker worker, IslandBoard islandBoard) {
        List<Space> spaceList = new ArrayList<>();
        for (int i = worker.getWorkerSpace().getRow() - RANGE_ROW_ALLOWED_ACTION; i <= worker.getWorkerSpace().getRow() + RANGE_ROW_ALLOWED_ACTION; i++)
            for (int j = worker.getWorkerSpace().getColumn() - RANGE_COLUMN_ALLOWED_ACTION; j <= worker.getWorkerSpace().getColumn() + RANGE_COLUMN_ALLOWED_ACTION; j++) {
                if ((LOWEST_ROW <= i && i < BIGGEST_ROW) && (LOWEST_COLUMN <= j && j < BIGGEST_COLUMN) && ((i!=worker.getWorkerSpace().getRow()) || (j!=worker.getWorkerSpace().getColumn())))
                    spaceList.add(islandBoard.getSpace(i, j));
            }
        return spaceList;
    }

    /**
     * Controls this spaceList and remove invalid spaces, according to rules of game.
     * If a space has a dome or another worker on its, this space are considered invalid.
     * If <move>true</move> it also remove spaces with a level's difference major than MAXDIFFERENCE.
     * @param worker  is the worker investigated.
     * @param spaceList contains valid spaces until now
     * @param code indicates what kind of more restrictive check are needed.
     * @return list containing valid spaces.
     */

    private static List<Space> setValidSpace(Worker worker, List<Space> spaceList, int code) {
        List<Space> totalSpace = new ArrayList<>(spaceList);

        for (Space space : totalSpace) {
            removeInvalidSpace(spaceList, space);
            if (code == CODE_MOVE) {
                removeSpecifiedMove(spaceList, worker.getWorkerSpace().getLevel(), space);
                removeDueToMoveFlag(spaceList, worker, space);
            }else if(code == CODE_BUILD) {
                removeDueToBuildFlag(spaceList, worker, space);
            }
        }

        if(code==CODE_BUILD)
            addSpecifiedBuild(spaceList,worker);

        return spaceList;
    }

    /**
     * Removes spaces that are already occupied or has a dome.
     * @param spaceList contains valid spaces until now.
     * @param space is the space in which presence of dome or another worker will be controlled.
     */

    private static void removeInvalidSpace(List<Space> spaceList, Space space) {
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

    private static void removeSpecifiedMove(List<Space> spaceList, int workerLevel, Space space) {
        int differenceInHeight = space.getLevel() - workerLevel;
        if (differenceInHeight > MAX_DIFFERENCE || differenceInHeight < MIN_DIFFERENCE)
            spaceList.remove(space);
    }

    /**
     * Removes all of spaces if worker can't move.
     * Removes spaces with a level higher than level of worker's spaces if this worker can't move up.
     * Removes spaces due to worker flag. It may not go up, down or in the initial space.
     * @param spaceList contains valid spaces until now.
     * @param worker is class that cointains flags.
     * @param space is the space, in which level is controlled.
     */

    private static void removeDueToMoveFlag(List<Space> spaceList, Worker worker, Space space){
        if(worker.isCantMove()){
            spaceList.clear();
        }else {
            if (worker.isCantMoveUp())
                cantMoveUP(spaceList, worker.getWorkerSpace().getLevel(), space);
            if (worker.isCantMoveDown())
                cantMoveDOWN(spaceList, worker.getWorkerSpace().getLevel(), space);
            if (worker.isCantMoveFirstSpace())
                cantMoveFirstSpace(spaceList,space,worker.getLastSpaceOccupied());
        }
    }


    /**
     * Removes spaces with a level higher than level of worker's spaces.
     * @param spaceList contains valid spaces until now.
     * @param workerLevel ndicates space level's of worker controlled.
     * @param space is the space, in which level is controlled.
     */
    private static void cantMoveUP(List<Space> spaceList, int workerLevel, Space space){
        if(space.getLevel()>workerLevel)
            spaceList.remove(space);
    }

    /**
     * Removes spaces with a level lower than level of worker's spaces.
     * @param spaceList contains valid spaces until now.
     * @param workerLevel ndicates space level's of worker controlled.
     * @param space is the space, in which level is controlled.
     */

    private static void cantMoveDOWN(List<Space> spaceList, int workerLevel, Space space){
        if(space.getLevel()<workerLevel)
            spaceList.remove(space);
    }

    /**
     * Removes worker's start position of turn if there's in list of valid space.
     * @param spaceList is list of valid space.
     * @param actualSpace is this space controlled.
     * @param initialSpace is worker's start position.
     */

    private static void cantMoveFirstSpace(List<Space> spaceList, Space actualSpace, Space initialSpace){
        if(actualSpace.equals(initialSpace))
            spaceList.remove(actualSpace);
    }

    /**
     * Traduces string passed in a int code.
     * @param action is string controlled that represents an action.
     * @return 0 if action represents moving, 1 if building, 2 others.
     */

    private static int traduceAction(String action){
        String move = "move";
        String build = "build";

        if(move.equals(action))
            return CODE_MOVE;
        else if(build.equals(action))
            return CODE_BUILD;
        else
            return CODE_OTHERS;
    }

    /**
     * Controls specified worker flag that could permit its to build on space not considered yet.
     * @param spaceList is list of valid spaces.
     * @param worker is worker controlled.
     */

    private static void addSpecifiedBuild(List<Space> spaceList, Worker worker){
        if(!worker.isCantBuildUnder())
            canBuildUNDER(spaceList, worker);
    }

    /**
     * Adds worker space to the list of valid spaces.
     * @param spaceList is list of valid spaces.
     * @param worker is worker that can build under itself.
     */

    private static void canBuildUNDER(List<Space> spaceList, Worker worker){
        spaceList.add(worker.getWorkerSpace());
    }

    /**
     * Removes spaces in which worker can't build due to its flag.
     * @param spaceList is list of valid spaces.
     * @param worker is worker controlled.
     * @param space is space checked.
     */

    private static void removeDueToBuildFlag(List<Space> spaceList, Worker worker, Space space){
        if(worker.isCantBuild())
            spaceList.clear();
        else{
            if(worker.isCantBuildDome())
                cantBuildDOME(spaceList,space);
            if(worker.isCantBuildFirstSpace())
                cantBuildFirstSpace(spaceList,worker,space);
            if(worker.isCantBuildPerimeter())
                cantBuildPerimeter(spaceList,space);
        }
    }

    /**
     * Removes space if the next build step is "building a dome".
     * @param spaceList is list of valid spaces.
     * @param space is space checked.
     */

    private static void cantBuildDOME(List<Space> spaceList, Space space){
        if(space.getLevel()==3) {
            spaceList.remove(space);
        }
    }

    /**
     * Removes space from list if it's worker first building space in this turn.
     * @param spaceList is list of valid spaces.
     * @param worker is worker controlled.
     * @param space is space checked
     */

    private static void cantBuildFirstSpace(List<Space> spaceList, Worker worker, Space space){
        if(worker.getLastSpaceBuilt().equals(space)) {
            spaceList.remove(space);
        }
    }

    /**
     * Removes space from list if it's in the perimeter.
     * @param spaceList is list of valid space.
     * @param space is space checked.
     */
    private static void cantBuildPerimeter(List<Space> spaceList, Space space){
        if(space.getRow()==LOWEST_ROW || space.getColumn()==LOWEST_COLUMN || space.getRow() == BIGGEST_ROW || space.getColumn()==BIGGEST_COLUMN) {
            spaceList.remove(space);
        }
    }

}


