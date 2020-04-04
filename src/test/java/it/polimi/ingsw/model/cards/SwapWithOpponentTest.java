package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class SwapWithOpponentTest {

    @Test
    public void swapTest() {
        Tile t = new Tile(5,5);
        Worker w = new Worker(t,new Player("simone", Color.CREAM));

        Tile t1 = new Tile(5,4);
        Worker w1 = new Worker(t1,new Player("Marco",Color.BLUE));

        Divinity d = new StandardDivinity();
        SwapWithOpponent swapD = new SwapWithOpponent(d);

        assert(swapD.legalMove(w,t1));

        swapD.move(w,t1);
        assertEquals(t1.getWorker(),w);
        assertEquals(t.getWorker(),w1);
    }

    @Test
    public void moveOnFreeTile() {
        Tile t1 = new Tile(2,2);
        Worker w = new Worker(t1, new Player("Marco", Color.BLUE));
        t1.setWorker(w);

        Tile t2 = new Tile(1,2);
        t2.free();

        Divinity d = new StandardDivinity();
        SwapWithOpponent swapD = new SwapWithOpponent(d);

        assert(swapD.legalMove(w,t2));

        swapD.move(w,t2);
        assertEquals(t2.getWorker(), w);
        assertNull(t1.getWorker());
    }

    @Test
    public void moveOn() {
        Tile t1 = new Tile(2,2);
        t1.increaseLevel();
        t1.increaseLevel();
        t1.increaseLevel();
        Worker w = new Worker(t1, new Player("Marco", Color.BLUE));
        t1.setWorker(w);

        Tile t2 = new Tile(1,2);
        t2.free();

        Divinity d = new StandardDivinity();
        SwapWithOpponent swapD = new SwapWithOpponent(d);

        assert(swapD.legalMove(w,t2));

        swapD.move(w,t2);
        assertEquals(t2.getWorker(), w);
        assertNull(t1.getWorker());
    }

}