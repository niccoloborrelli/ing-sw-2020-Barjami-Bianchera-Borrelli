package it.polimi.ingsw;

import java.util.List;
import java.util.Scanner;

public class ConcreteArtemisMove extends  PowerMovementDecoratorAB {

    public ConcreteArtemisMove(MovementAB movementAB){
        this.movement=movementAB;
    }

    public  boolean move(Worker worker, Space finishSpace, IslandBoard islanBoard){
        Scanner sc = new Scanner(System.in);
        int startLevel = worker.getWorkerSpace().getLevel();
        Space startSpace = worker.getWorkerSpace();
        movement.move(worker, finishSpace, islanBoard);
        worker.getWorkerPlayer().getWinCondition().checkHasWon(worker, startLevel, islanBoard);

        if(worker.getWorkerPlayer().getWinCondition().gethasWon()) //se ha vinto esco
            return true;

        System.out.println("Press 1 to use power or 0 to not use");
        int scelta = sc.nextInt();

        if(scelta == 1){
            worker.getWorkerPlayer().getRestriction().restrictionEffectMovement(worker.getWorkerPlayer(), islanBoard);
            List<Space> possibleMovements = islanBoard.checkAvailableMovement(worker.getWorkerPlayer())[worker.getWorkerPlayer().getWorkers().indexOf(worker)];
            possibleMovements.remove(startSpace);// tolgo dalla lista cosi' non puo' tornare dove si e' mossa col primo movimento
            if (possibleMovements.size() > 0) {
                Space selectedPos = selectPos(possibleMovements);
                startLevel = worker.getWorkerSpace().getLevel();
                movement.move(worker, selectedPos, islanBoard);
                worker.getWorkerPlayer().getWinCondition().checkHasWon(worker, startLevel, islanBoard);
            }
            else
                System.out.println("You can't move");
        }
        return true;
    }

    private Space selectPos(List <Space> possibleSpace){
        int indexOfSelected;
        int i = 0;
        Scanner sc = new Scanner(System.in);
        for(Space pos: possibleSpace){
            System.out.println("Insert number " + i + " to execute the operation in position: " + pos.getRow() + "-" + pos.getColumn());
            i++;
        }
        indexOfSelected = sc.nextInt();
        while(indexOfSelected < 0 || indexOfSelected > i){
            System.out.println("The position is not valid, please insert a valid position");
            indexOfSelected = sc.nextInt();
        }
        return possibleSpace.get(indexOfSelected);
    }
    
}
