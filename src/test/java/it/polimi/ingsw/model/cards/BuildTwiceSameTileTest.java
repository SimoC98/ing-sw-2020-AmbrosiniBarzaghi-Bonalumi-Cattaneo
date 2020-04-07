package it.polimi.ingsw.model.cards;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class BuildTwiceSameTileTest {

    private static Tile tile1;
    private static Tile tile2;
    private static Tile tile3;
    private static BuildTwice div;
    private static Worker worker;
    private static Set<Action> actions;

    @BeforeEach
      void setup(){
        tile1 = new Tile(1,1);
        tile2 = new Tile(2,2);
        tile3 = new Tile(3,3);
        div = new BuildTwiceSameTile(new StandardDivinity());
        actions = new HashSet<>();
    }

    @Test
    public void buildTwiceSameTileTest(){
        worker = new Worker(tile1, new Player("anakin", Color.BLUE) );
        div.setupDivinity(actions);
        div.move(worker,tile2);
        div.build(worker,tile1);
        assert(div.getBuildCount()==1);
        assertEquals(div.getFirstBuildTile(), tile1);
        assertFalse(div.legalBuild(worker, tile3));
        assertTrue(div.legalBuild(worker, tile1));
    }

    @Test
    public void listControlTest(){
        worker = new Worker(tile1, new Player("anakin", Color.BLUE) );
        div.setupDivinity(actions);
        div.build(worker,tile2);
        div.updatePossibleActions(actions);
        assert(actions.size()==2);
        assertTrue(actions.contains(Action.BUILD) && actions.contains(Action.END));
    }

    @Test
    public void buildNoDomesTest(){
        worker = new Worker(tile1, new Player("anakin", Color.BLUE) );
        tile2.increaseLevel();
        tile2.increaseLevel();
        div.build(worker,tile2);
        div.updatePossibleActions(actions);
        assertEquals(actions.size(),0);
    }
}