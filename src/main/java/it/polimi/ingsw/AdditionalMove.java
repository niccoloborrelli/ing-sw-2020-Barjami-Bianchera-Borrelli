package it.polimi.ingsw;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdditionalMove extends  PowerMovementDecoratorAB {

    /*
    Artemis' power
     */

    /*
    Your worker may move one additional time, but not in the initial space
     */
    /**
     * this is a constructor for the decorator pattern
     * @param movementAB is the decorated object
     */
    private final int MINIMUMSIZE = 0;
    private final int FIRSTPOSSIBLEVALUE = 0;
    private final int SECONDPOSSIBLEVALUE = 1;
    private final int FIRSTINDEX = 0;


    public AdditionalMove(MovementAB movementAB){
        this.movement = movementAB;
    }
    /**
     * This method implements the Artemis's movement power
     * @param worker is the Worker who is going to move
     * @param finishSpace is the position in which worker is going to move
     * @param islandBoard is the IslandBoard containing worker and finishSpace
     * @throws IOException is an exception invoked by the usage of socket
     */
    public void move(Worker worker, Space finishSpace,IslandBoard islandBoard) throws IOException {
        int choice = FIRSTPOSSIBLEVALUE;
        int startLevel = worker.getWorkerSpace().getLevel();
        Space startSpace = worker.getWorkerSpace();
        movement.move(worker, finishSpace, islandBoard);
        worker.getWorkerPlayer().getWinCondition().checkHasWon(worker, startLevel, islandBoard);

        if (worker.getWorkerPlayer().getWinCondition().gethasWon()){
            return; //se ha vinto esco
        }


        List<Integer> list = new ArrayList<>();
        list.add(FIRSTPOSSIBLEVALUE);
        list.add(SECONDPOSSIBLEVALUE);

        List<Space> possibleMovements = checkPossibleMovements(worker, startSpace, islandBoard);

        if (possibleMovements.size() > MINIMUMSIZE) {
            choice = islandBoard.requiredInt(worker.getWorkerPlayer().getSocket(), "<message>Do you want to use your God power? Insert 1 if you want, 0 otherwise</message>", list);
            if (choice == SECONDPOSSIBLEVALUE) {
                    String message = generateMessage(possibleMovements);
                    Space selectedPos = islandBoard.requiredSpace(worker.getWorkerPlayer().getSocket(),message, possibleMovements );
                    startLevel = worker.getWorkerSpace().getLevel();
                    movement.move(worker, selectedPos, islandBoard);
                    worker.getWorkerPlayer().getWinCondition().checkHasWon(worker, startLevel, islandBoard);

            }
        }
    }

    private List<Space> checkPossibleMovements(Worker worker, Space startSpace, IslandBoard islandBoard){
        List<Space>[] spaces;
        int indexOfWorker = worker.getWorkerPlayer().getWorkers().indexOf(worker);

        worker.getWorkerPlayer().getRestriction().restrictionEffectMovement(worker.getWorkerPlayer(), islandBoard);
        spaces = islandBoard.checkAvailableMovement(worker.getWorkerPlayer());
        spaces[indexOfWorker].remove(startSpace);

        return spaces[indexOfWorker];
    }

    /**
     * this method permits the player to chose in which position to move the worker in the second movement permitted by Artemis's power
     * @param availableSpaces is a List of spaces which contains all the possible movements that a worker can do
     * @return is the Space in which the worker is going to do his second movement
     */

    private String generateMessage(List<Space> availableSpaces){
        String message = "<message>Choose the index of position you want to build on </message>";
        int i = FIRSTINDEX;
        for(Space s: availableSpaces){
            message = message + "<Space" + i + "><Row>" + s.getRow() + "</Row><Column>" + s.getColumn() + "</Column><Index>" + i + "</Index></Space" + i + ">";
        }
        return message;
    }
}
