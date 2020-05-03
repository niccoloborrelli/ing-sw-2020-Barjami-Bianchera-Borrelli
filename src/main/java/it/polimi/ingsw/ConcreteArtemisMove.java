package it.polimi.ingsw;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ConcreteArtemisMove extends  PowerMovementDecoratorAB {

    /*
    Your worker may move one additional time,
    but not back to its initial space
     */

    /**
     * this is a constructor for the decorator pattern
     * @param movementAB is the decorated object
     */
    public ConcreteArtemisMove(MovementAB movementAB){
        this.movement = movementAB;
    }


    /**
     * This method implements the Artemis's movement power
     * @param worker is the Worker who is going to move
     * @param finishSpace is the position in which worker is going to move
     * @param islanBoard is the IslandBoard containing worker and finishSpace
     * @throws IOException is an exception invoked by the usage of socket
     */
    public void move(Worker worker, Space finishSpace,IslandBoard islanBoard) throws IOException {

        int startLevel = worker.getWorkerSpace().getLevel();
        Space startSpace=worker.getWorkerSpace();
        movement.move(worker,finishSpace,islanBoard);
        worker.getWorkerPlayer().getWinCondition().checkHasWon(worker, startLevel, islanBoard);

        if(worker.getWorkerPlayer().getWinCondition().gethasWon()) //se ha vinto esco
            return ;

        ControllerUtility.communicate(worker.getWorkerPlayer().getSocket(),"Do you want to use your power? 1 if you want, 0 otherwise",1);
        int scelta = ControllerUtility.getInt(worker.getWorkerPlayer().getSocket());

        if(scelta == 1){
            worker.getWorkerPlayer().getRestriction().restrictionEffectMovement(worker.getWorkerPlayer(),islanBoard);
            List<Space> possibleMovements= islanBoard.checkAvailableMovement(worker.getWorkerPlayer())[worker.getWorkerPlayer().getWorkers().indexOf(worker)];
            possibleMovements.remove(startSpace);// tolgo dalla lista cosi' non puo' tornare dove si e' mossa col primo movimento
            if (possibleMovements.size() > 0) {
                Space selectedPos = ControllerUtility.selectPos(possibleMovements, worker.getWorkerPlayer());
                startLevel = worker.getWorkerSpace().getLevel();
                movement.move(worker, selectedPos, islanBoard);
                worker.getWorkerPlayer().getWinCondition().checkHasWon(worker, startLevel, islanBoard);
            }
            else
                ControllerUtility.communicate(worker.getWorkerPlayer().getSocket(),"You can go nowhere",0);
        }
    }

    /**
     * this method permits the player to chose in which position to move the worker in the second movement permitted by Artemis's power
     * @param possibleSpace is a List of spaces which contains all the possible movements that a worker can do
     * @return is the Space in which the worker is going to do his second movement
     */
    private Space selectPos(List <Space> possibleSpace, Socket socket) throws IOException {
        int indexOfSelected;
        int i=0;
        String s = "";
        Scanner sc = new Scanner(System.in);
        for(Space pos: possibleSpace){
            s=s+"inserire il numero "+i+" per compiere l'azione nella posizione: "+pos.getRow()+"-"+pos.getColumn()+"\n";
            i++;
        }
        ControllerUtility.communicate(socket,s,1);
        indexOfSelected=ControllerUtility.getInt(socket);

        while(indexOfSelected<0||indexOfSelected>=i){
            //comunica errore nella posizione
            ControllerUtility.communicate(socket,"e' stata inserita una pos errata",4);
            //richiede posizione
            ControllerUtility.communicate(socket,s,1);
            indexOfSelected=ControllerUtility.getInt(socket);
        }
        return possibleSpace.get(indexOfSelected);
    }
}
