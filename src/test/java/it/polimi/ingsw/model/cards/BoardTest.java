package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class BoardTest {

    private Board b;
    private Player player;
    private List<Tile> list;

    @BeforeEach
    public void setup(){
        b = new Board();
        player = new Player("Mark", Color.BLUE);
        player.setDivinity(new StandardDivinity());
    }

    @Test
    public void tilesTest(){
        list = b.getAdjacentTiles(b.getTile(1,0));

        assertNull(b.getTile(-1,0));
        assertTrue(list.contains(b.getTile(0,0)));
        assertTrue(list.contains(b.getTile(0,1)));
        assertTrue(list.contains(b.getTile(1,1)));
        assertTrue(list.contains(b.getTile(2,1)));
        assertTrue(list.contains(b.getTile(2,0)));
        assertFalse(list.contains(b.getTile(1,0)));
        assertFalse(list.contains(b.getTile(2,2)));
        assertFalse(list.contains(b.getTile(3,3)));
    }

    @Test
    public void availableMoveTilesTest(){
        b.getTile(0,1).setDome();
        b.getTile(1,0).increaseLevel();
        b.getTile(1,0).increaseLevel();
        Worker worker = new Worker(b.getTile(0,0),player);
        list = b.getAvailableMoveTiles(worker);

        assertTrue(list.contains(b.getTile(1,1)));
        assertFalse(list.contains(b.getTile(0,1)));
        //assertFalse(list.contains(b.getTile(1,0)));
        assertFalse(list.contains(b.getTile(0,0)));
        assertFalse(list.contains(b.getTile(2,2)));
    }

    @Test
    public void availableBuildTilesTest(){
        b.getTile(0,1).setDome();
        b.getTile(1,0).increaseLevel();
        b.getTile(1,0).increaseLevel();
        b.getTile(1,1).setWorker(new Worker());
        Worker worker = new Worker(b.getTile(0,0),player);
        list = b.getAvailableBuildTiles(worker);

        assertTrue(list.contains(b.getTile(1,0)));
        assertFalse(list.contains(b.getTile(0,1)));
        assertFalse(list.contains(b.getTile(1,1)));
        assertFalse(list.contains(b.getTile(0,0)));
        assertFalse(list.contains(b.getTile(2,2)));
    }

    @Test
    public void removePlayerTest(){
        player.addWorker(b.getTile(0,0));
        player.addWorker(b.getTile(0,1));

        assertTrue(b.getTile(0,0).isOccupied());
        assertTrue(b.getTile(0,1).isOccupied());

        b.removePlayerWorkers(player);

        assertFalse(b.getTile(0,0).isOccupied());
        assertFalse(b.getTile(0,1).isOccupied());
    }
}
