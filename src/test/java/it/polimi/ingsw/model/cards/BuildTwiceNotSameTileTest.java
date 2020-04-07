package it.polimi.ingsw.model.cards;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Worker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class BuildTwiceNotSameTileTest {

    private static Tile tile1;
    private static Tile tile2;
    private static Tile tile3;
    private static BuildTwice div;
    private static Worker worker;
    private static Set<Action> actions;

    @BeforeAll
    static void setup(){
        tile1 = new Tile(1,1);
        tile2 = new Tile(2,2);
        tile3 = new Tile(3,3);
        div = new BuildTwiceNotSameTile(new StandardDivinity());
        actions = new HashSet<>();
    }

    @AfterEach
    void tearDown() {
        tile1.free();
        tile2.free();
        tile3.free();
    }

    @Test
    public void buildTwiceNotSameTileTest(){
        worker = new Worker(tile1, new Player("jack", Color.BLUE) );
        div.setupDivinity(actions);
        div.move(worker,tile2);
        div.build(worker,tile1);
        assert(div.getBuildCount()==1);
        assertEquals(div.getFirstBuildTile(), tile1);
        assertFalse(div.legalBuild(worker, tile1));
        assertTrue(div.legalBuild(worker, tile3));
    }

    @Test
    public void listControlTest(){
        worker = new Worker(tile1, new Player("jack", Color.BLUE) );
        div.setupDivinity(actions);
        div.build(worker,tile2);
        div.updatePossibleActions(actions);
        assert(actions.size()==2);
        assertTrue(actions.contains(Action.BUILD) && actions.contains(Action.END));
    }
}