package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    @Test
    public void increaseLevelTest() {
        Tile tileTest = new Tile(0,0);
        assertEquals(0,tileTest.getLevel());
        for(int i=0; i<3; i++) {
            assertEquals(i,tileTest.getLevel());
            tileTest.increaseLevel();
            assertFalse(tileTest.isDome());
        }
        tileTest.increaseLevel();
        assertEquals(3,tileTest.getLevel());
        assert(tileTest.isDome());
        tileTest.increaseLevel();
        assertEquals(3,tileTest.getLevel());
        assert(tileTest.isDome());
    }

   @Test
   public void occupiedTileTest() {
        Tile tileTest = new Tile(5,5);
        assertNull(tileTest.getWorker());

        Worker workerTest = new Worker();
        tileTest.setWorker(workerTest);
        assertNotNull(tileTest.getWorker());
        assert(tileTest.isOccupied());
        assertEquals(workerTest,tileTest.getWorker());

        tileTest.free();
        assertNull(tileTest.getWorker());
   }





}