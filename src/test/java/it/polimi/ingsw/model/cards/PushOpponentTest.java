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
    void pushTest() {
        Board board = new Board();
        Game g = new Game(new Match(board));

        Worker w = new Worker(board.getTile(2,1),new Player("simone", Color.BLUE));
        board.getTile(2,1).setWorker(w);

        Divinity d = new StandardDivinity();
        PushOpponent pushD = new PushOpponent(d);

        pushD.move(w,board.getTile(2,2));
        assertEquals(board.getTile(2,2).getWorker(),w);

        Worker w1 = new Worker(board.getTile(2,1),new Player("marco", Color.CREAM));
        board.getTile(2,1).setWorker(w1);

        pushD.move(w,board.getTile(2,1));
        assertEquals(board.getTile(2,1).getWorker(),w);
        assertEquals(board.getTile(2,0).getWorker(),w1);

        assert(!pushD.legalMove(w,board.getTile(2,0)));

        board.getTile(2,0).free();

        assert(pushD.legalMove(w,board.getTile(2,0)));


        board.getTile(2,2).setWorker(w1);
        w1.setPositionOnBoard(board.getTile(2,2));
        assert(pushD.legalMove(w,board.getTile(2,2)));
        assertEquals(w,board.getTile(2,1).getWorker());

        board.getTile(2,3).increaseLevel();
        board.getTile(2,3).increaseLevel();
        board.getTile(2,3).increaseLevel();

        assert(pushD.legalMove(w,board.getTile(2,2)));

        pushD.move(w,board.getTile(2,2));
        assertEquals(w,board.getTile(2,2).getWorker());
        assertEquals(w1,board.getTile(2,3).getWorker());

        assertEquals(board.getTile(2,3).getLevel(),3);
        assert(w1.getPositionOnBoard().getLevel()==3);
        assert(!w1.getPlayer().isWinner());
    }
}