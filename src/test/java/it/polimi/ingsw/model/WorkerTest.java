package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidMoveException;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;

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
    public void prova() {
        Worker w = new Worker();
        w.setPositionOnBoard(new Tile(5,5));

        Tile t = new Tile(5,4);
        t.setWorker(new Worker());

        assertThrows(InvalidMoveException.class,()->w.move(t));



    }



}