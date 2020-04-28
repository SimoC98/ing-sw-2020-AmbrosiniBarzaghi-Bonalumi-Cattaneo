package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.StandardDivinity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

  /*  Player p;
    Worker w;
    Board b;

    @BeforeEach
    void setUp() {
        b = new Board();
        p = new Player("Gigetto", Color.CREAM);
        p.setDivinity(new StandardDivinity());
        p.startOfTurn();
    }

    @Test
    public void moveTest(){
        Tile t1 = b.getTile(1, 1);
        Tile t2 = b.getTile(1, 2);

        p.addWorker(t1);
        w = p.getWorkers().get(0);

        assertFalse(p.getWorkers().isEmpty());
        assertTrue(p.getPossibleActions().contains(Action.MOVE));

        boolean success = p.move(w, t2);

        assertTrue(success);
        assertTrue(p.getPossibleActions().contains(Action.BUILD));
    }

    @Test
    public void hasWon(){
        Tile t1 = b.getTile(1, 1);
        Tile t2 = b.getTile(1, 2);

        t1.increaseLevel();
        t1.increaseLevel();

        t2.increaseLevel();
        t2.increaseLevel();
        t2.increaseLevel();

        p.addWorker(t1);
        w = p.getWorkers().get(0);

        boolean success = p.move(w, t2);

        assertTrue(p.isWinner());
    }


   /* @Test
    public void wrongMoves(){
        Tile t1 = b.getTile(1, 1);
        Tile t2 = b.getTile(1, 2);
        Tile t3 = b.getTile(2, 3);

        t2.increaseLevel();
        t2.increaseLevel();

        p.addWorker(t1);
        w = p.getWorkers().get(0);

        boolean success = p.move(w, t2);

        assertFalse(success);
        assertTrue(p.getPossibleActions().contains(Action.MOVE));   //no moves no changes on possibleMoves

        success = p.move(w, t3);

        assertFalse(success);
        assertTrue(p.getPossibleActions().contains(Action.MOVE));
    }*/

   /* @Test
    public void buildTest(){
        Tile t1 = b.getTile(1, 1);
        Tile t2 = b.getTile(1, 2);

        t2.increaseLevel();
        t2.increaseLevel();

        p.addWorker(t1);
        w = p.getWorkers().get(0);

        boolean success = p.build(w, t2);

        assertTrue(success);
    }*/
}
