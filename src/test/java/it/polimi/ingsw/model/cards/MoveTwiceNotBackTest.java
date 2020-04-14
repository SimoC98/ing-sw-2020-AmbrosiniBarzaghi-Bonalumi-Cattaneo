package it.polimi.ingsw.model.cards;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.exceptions.InvalidWorkerSelectionException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class MoveTwiceNotBackTest {

    private static Tile tile1;
    private static Tile tile2;
    private static Tile tile3;
    private static Tile tile4;
    private static MoveTwice div;
    private static Worker worker;
    private static Set<Action> actions;

    @BeforeAll
    static void setup(){
        tile1 = new Tile(1,1);
        tile2 = new Tile(2,2);
        tile3 = new Tile(3,3);
        div = new MoveTwiceNotBack(new StandardDivinity());
        actions = new HashSet<>();
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
        assertTrue(actions.contains(Action.MOVE));
    }

   /* @Test
    public void test() throws InvalidWorkerSelectionException {
        List<String> l = new ArrayList<>();
        l.add("jack");
        Match m = new Match(l);
        Game g = new Game(m);
        Board b = m.getBoard();
        Player p = m.getPlayers().get(0);
        Divinity div = new MoveTwiceNotBack(new StandardDivinity());
        p.setDivinity(div);

        p.addWorker(b.getTile(1,1));

        m.setCurrentPlayer(p);

        assert(b.getTile(1, 1).isOccupied());
        assert(b.getTile(1, 1).getWorker().getPlayer().equals(m.getCurrentPlayer()));

        m.selectWorker(1,1);

        b.getTile(0,1).setDome();
        b.getTile(1,0).setDome();

        p.startOfTurn();

        assertFalse(p.move(p.getWorkers().get(0),b.getTile(1,0)));
        assert(p.getPossibleActions().size()==1 && p.getPossibleActions().contains(Action.MOVE));
        assert(div.legalMove(p.getWorkers().get(0),b.getTile(2,2)));
        //assert(p.move(p.getWorkers().get(0),b.getTile(2,2)));
        assert(p.move(p.getWorkers().get(0),b.getTile(0,0)));
        assert(p.getPossibleActions().size()==1);
        assert(p.getPossibleActions().contains(Action.BUILD));
        assertFalse(p.build(p.getWorkers().get(0),b.getTile(2,2)));
        p.build(p.getWorkers().get(0),b.getTile(1,1));
        assert(p.getPossibleActions().size()==0);



    }*/
}