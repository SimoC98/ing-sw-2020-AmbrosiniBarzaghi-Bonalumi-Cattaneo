package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidBuildException;
import it.polimi.ingsw.model.exceptions.InvalidMoveException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerTest {



    @Test
    public void moveTest() {
        Worker workerTest = new Worker();
        Tile tileTest1 = new Tile(5,5);
        workerTest.setPositionOnBoard(tileTest1);
        Tile tileTest2 = new Tile(5,4);

        try {
            workerTest.move(tileTest2);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }

        assertNotEquals(tileTest1,workerTest.getPositionOnBoard());
        assertFalse(tileTest1.isOccupied());
        assertEquals(tileTest2.getWorker(),workerTest);
    }

    @Test
    public void moveOnOpponent() {
        Worker w = new Worker();
        w.setPositionOnBoard(new Tile(5,5));

        Tile t = new Tile(5,4);
        t.setWorker(new Worker());

        assertThrows(InvalidMoveException.class,()->w.move(t));
        assertNotEquals(t.getWorker(),w);
    }

    @Test
    public void moveOnDome() {
        Worker workerTest = new Worker();
        Tile tileTest1 = new Tile(5,5);
        workerTest.setPositionOnBoard(tileTest1);
        tileTest1.setWorker(workerTest);
        Tile tileTest2 = new Tile(5,4);
        tileTest2.setDome();

        assertThrows(InvalidMoveException.class,()->workerTest.move(tileTest2));


    }

    @Test
    public void moveOnDifferentLevels() {
        Worker workerTest = new Worker();
        Tile tileTest1 = new Tile(5,5);
        workerTest.setPositionOnBoard(tileTest1);
        tileTest1.setWorker(workerTest);
        Tile tileTest2 = new Tile(5,4);
        tileTest2.increaseLevel();
        tileTest2.increaseLevel();

        assertThrows(InvalidMoveException.class,()->workerTest.move(tileTest2));
    }

    @Test
    public void winCondition() {
        Tile tileTest1 = new Tile(5,5);
        Worker workerTest = new Worker(tileTest1,new Player("username",Color.CREAM));

        tileTest1.increaseLevel();
        tileTest1.increaseLevel();
        tileTest1.setWorker(workerTest);


        Tile tileTest2 = new Tile(5,4);
        tileTest2.increaseLevel();
        tileTest2.increaseLevel();
        tileTest2.increaseLevel();

        try {
            workerTest.move(tileTest2);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }

        assert(workerTest.getPlayer().isWinner());

    }

    @Test
    public void NotWinIfAlreadyLevel3() {
        Tile tileTest1 = new Tile(5,5);
        Worker workerTest = new Worker(tileTest1,new Player("username",Color.CREAM));

        tileTest1.increaseLevel();
        tileTest1.increaseLevel();
        tileTest1.increaseLevel();
        tileTest1.setWorker(workerTest);


        Tile tileTest2 = new Tile(5,4);
        tileTest2.increaseLevel();
        tileTest2.increaseLevel();
        tileTest2.increaseLevel();

        try {
            workerTest.move(tileTest2);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }

        assert(!workerTest.getPlayer().isWinner());

    }

    @Test
    public void buildTest()  {
        Worker workerTest = new Worker();
        Tile tileTest1 = new Tile(5,5);
        workerTest.setPositionOnBoard(tileTest1);
        tileTest1.setWorker(workerTest);
        Tile tileTest2 = new Tile(5,4);

        for(int i=0;i<=3;i++) {
            assertEquals(i,tileTest2.getLevel());
            try {
                workerTest.build(tileTest2);
            } catch (InvalidBuildException e) {
                e.printStackTrace();
            }
        }

        assert(tileTest2.isDome());
        assertThrows(InvalidBuildException.class,()->workerTest.build(tileTest2));
    }


}