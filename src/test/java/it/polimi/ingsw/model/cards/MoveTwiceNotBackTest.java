package it.polimi.ingsw.model.cards;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Worker;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class MoveTwiceNotBackTest {

    private static Tile tile1;
    private static Tile tile2;
    private static Tile tile3;
    private static MoveTwice div;
    private static Worker worker;
    private static List<Phase> actions;

    @BeforeAll
    static void setup(){
        tile1 = new Tile(1,1);
        tile2 = new Tile(2,2);
        tile3 = new Tile(3,3);
        div = new MoveTwiceNotBack(new StandardDivinity());
        actions = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        tile1.free();
        tile2.free();
        tile3.free();
    }

    @Test
    public void moveTwiceNotBackTest(){
        worker = new Worker(tile1, new Player("jack", Color.BLUE) );
        div.setupDivinity(actions);
        div.move(worker,tile2);
        assert(div.getMoveCount()==1);
        assertEquals(div.getFirstMovedTile(), tile1);
        assertFalse(div.legalMove(worker, tile1));
        assertTrue(div.legalMove(worker, tile3));
    }


    @Test
    public void listControlTest(){
        worker = new Worker(tile1, new Player("jack", Color.BLUE) );
        div.setupDivinity(actions);
        div.move(worker,tile2);
        div.updatePossibleActions(actions);
        assert(actions.size()==1);
        assertTrue(actions.contains(Phase.MOVE));
    }
}