package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PushOpponentTest {
    private static Board boarda;

    @BeforeAll
    static void setup() {
        Board boarda = new Board();
    }

    @Test
    public void pushOpponentSimple(){
        Board board = new Board();
        Game g = new Game(new Match(board));

        Tile t1 = board.getTile(2,1);
        Tile t2 = board.getTile(2,2);

        Worker w1 = new Worker(t1, new Player("simone", Color.BLUE));
        Worker w2 = new Worker(t2 ,new Player("Giova", Color.BLUE));

        Divinity d = new StandardDivinity();
        PushOpponent pushD = new PushOpponent(d);

        assertTrue(pushD.legalMove(w1, t2));

        pushD.move(w1, t2);

        assertEquals(w1.getPositionOnBoard(), t2);
        assertEquals(w2.getPositionOnBoard(), board.getTile(t2.getX(), t2.getY()+1));
        assertTrue(!t1.isOccupied());

    }

    @Test
    public void pushOpponentOnHigherTile(){
        Board board = new Board();
        Game g = new Game(new Match(board));

        Tile t1 = board.getTile(2,1);
        Tile t2 = board.getTile(2,2);
        Tile t3 = board.getTile(2,3);

        t3.increaseLevel();
        t3.increaseLevel();
        t3.increaseLevel();

        Worker w1 = new Worker(t1, new Player("simone", Color.BLUE));
        Worker w2 = new Worker(t2 ,new Player("Giova", Color.BLUE));

        Divinity d = new StandardDivinity();
        PushOpponent pushD = new PushOpponent(d);

        assertTrue(pushD.legalMove(w1, t2));

        pushD.move(w1, t2);

        assertEquals(w1.getPositionOnBoard(), t2);
        assertEquals(w2.getPositionOnBoard(), board.getTile(t2.getX(), t2.getY()+1));
        assertTrue(!t1.isOccupied());
        assertFalse(w2.getPlayer().isWinner());
    }

    @Test
    public void pushOpponentOffEdge(){
        Board board = new Board();
        Game g = new Game(new Match(board));

        Tile t1 = board.getTile(2,1);
        Tile t2 = board.getTile(2,0);

        Worker w1 = new Worker(t1, new Player("simone", Color.BLUE));
        Worker w2 = new Worker(t2 ,new Player("Giova", Color.BLUE));

        Divinity d = new StandardDivinity();
        PushOpponent pushD = new PushOpponent(d);

        assertFalse(pushD.legalMove(w1, t2));
    }

    @Test
    public void pushOpponentOnOccupiedTile(){
        Board board = new Board();
        Game g = new Game(new Match(board));

        Tile t1 = board.getTile(1,1);
        Tile t2 = board.getTile(2,2);
        Tile t3 = board.getTile(3,3);

        Worker w1 = new Worker(t1, new Player("simone", Color.BLUE));
        Worker w2 = new Worker(t2 ,new Player("Giova", Color.BLUE));
        Worker w3 = new Worker(t3 ,new Player("franco", Color.BLUE));

        Divinity d = new StandardDivinity();
        PushOpponent pushD = new PushOpponent(d);

        assertFalse(pushD.legalMove(w1, t2));
    }

    @Test
    public void pushOpponentOnDome(){
        Board board = new Board();
        Game g = new Game(new Match(board));

        Tile t1 = board.getTile(1,1);
        Tile t2 = board.getTile(2,2);
        Tile t3 = board.getTile(3,3);
        t3.setDome();

        Worker w1 = new Worker(t1, new Player("simone", Color.BLUE));
        Worker w2 = new Worker(t2 ,new Player("Giova", Color.BLUE));

        Divinity d = new StandardDivinity();
        PushOpponent pushD = new PushOpponent(d);

        assertFalse(pushD.legalMove(w1, t2));
    }
}