package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConcreteMinotaurMovementTest {

    @Test
    public void moveTest1() {
        IslandBoard islandBoard = new IslandBoard();
        ConcreteMinotaurMove concreteMinotaurMove = new ConcreteMinotaurMove(new BaseMovement());
        ConcreteMinotaurRestriction concreteMinotaurRestriction = new ConcreteMinotaurRestriction(new BaseRestriction());

        Player player1 = new Player("yes");
        Player player2 = new Player("no");

        player1.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(0, 0));
        islandBoard.getSpace(0, 0).setOccupator(player1.getWorkers().get(0));

        player1.getWorkers().get(1).setWorkerSpace(islandBoard.getSpace(4, 4));
        islandBoard.getSpace(4, 4).setOccupator(player1.getWorkers().get(1));

        player2.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(1, 1));
        islandBoard.getSpace(1, 1).setOccupator(player1.getWorkers().get(0));

        concreteMinotaurRestriction.restrictionEffectMovement(player1, islandBoard);

        concreteMinotaurMove.move(player1.getWorkers().get(0), islandBoard.getSpace(1, 1), islandBoard);

        assertTrue(islandBoard.getSpace(1, 1).getOccupator().equals(player1.getWorkers().get(0))
                && (islandBoard.getSpace(2, 2).getOccupator().equals(player2.getWorkers().get(0))));

    }

    @Test
    public void moveTest2() {
        IslandBoard islandBoard = new IslandBoard();
        ConcreteMinotaurMove concreteMinotaurMove = new ConcreteMinotaurMove(new BaseMovement());
        ConcreteMinotaurRestriction concreteMinotaurRestriction = new ConcreteMinotaurRestriction(new BaseRestriction());

        Player player1 = new Player("yes");
        Player player2 = new Player("no");

        player1.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(0, 0));
        islandBoard.getSpace(0, 0).setOccupator(player1.getWorkers().get(0));

        player1.getWorkers().get(1).setWorkerSpace(islandBoard.getSpace(4, 4));
        islandBoard.getSpace(4, 4).setOccupator(player1.getWorkers().get(1));

        player2.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(0, 1));
        islandBoard.getSpace(0, 1).setOccupator(player1.getWorkers().get(0));

        concreteMinotaurRestriction.restrictionEffectMovement(player1, islandBoard);

        concreteMinotaurMove.move(player1.getWorkers().get(0), islandBoard.getSpace(0, 1), islandBoard);

        assertTrue(islandBoard.getSpace(0, 1).getOccupator().equals(player1.getWorkers().get(0))
                && (islandBoard.getSpace(0, 2).getOccupator().equals(player2.getWorkers().get(0))));

    }

    @Test
    public void moveTest3() {
        IslandBoard islandBoard = new IslandBoard();
        ConcreteMinotaurMove concreteMinotaurMove = new ConcreteMinotaurMove(new BaseMovement());
        ConcreteMinotaurRestriction concreteMinotaurRestriction = new ConcreteMinotaurRestriction(new BaseRestriction());

        Player player1 = new Player("yes");
        Player player2 = new Player("no");

        player1.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(0, 0));
        islandBoard.getSpace(0, 0).setOccupator(player1.getWorkers().get(0));

        player1.getWorkers().get(1).setWorkerSpace(islandBoard.getSpace(4, 4));
        islandBoard.getSpace(4, 4).setOccupator(player1.getWorkers().get(1));

        player2.getWorkers().get(0).setWorkerSpace(islandBoard.getSpace(1, 0));
        islandBoard.getSpace(1, 0).setOccupator(player1.getWorkers().get(0));

        concreteMinotaurRestriction.restrictionEffectMovement(player1, islandBoard);

        concreteMinotaurMove.move(player1.getWorkers().get(0), islandBoard.getSpace(1, 0), islandBoard);

        assertTrue(islandBoard.getSpace(1, 0).getOccupator().equals(player1.getWorkers().get(0))
                && (islandBoard.getSpace(2, 0).getOccupator().equals(player1.getWorkers().get(1))));

    }
}