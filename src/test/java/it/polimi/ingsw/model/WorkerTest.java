package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerTest {

    /*public void workerTest() {
        Tile testTile = new Tile(0,5);
        Player playerTest = new Player()
    }*/

    public void moveTest() {
        Worker workerTest = new Worker();
        Tile tileTest1 = new Tile(5,5);
        workerTest.setPositionOnBoard(tileTest1);
        Tile tileTest2 = new Tile(5,4);
        workerTest.move(tileTest2);
        assertNotEquals(tileTest1,workerTest.getPositionOnBoard());
        assertFalse(tileTest1.isOccupied());
        assertEquals(tileTest2.getWorker(),workerTest);
    }

}