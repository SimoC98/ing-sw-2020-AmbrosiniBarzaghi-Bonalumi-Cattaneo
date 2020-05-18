package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.BuildDomeEverywhere;
import it.polimi.ingsw.model.cards.StandardDivinity;
import it.polimi.ingsw.model.exceptions.InvalidBuildException;
import it.polimi.ingsw.model.exceptions.InvalidMoveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorkerTest {
    private Worker w;
    private Board board;
    private Match match;

    @BeforeEach
    void setup(){
        board = new Board();
        match = new Match(board);
        w = new Worker(new Player("Kirk",Color.CREAM));
    }

     @Test
    public void moveTest() {
        Tile tileTest1 = board.getTile(4,4);
        w.setPositionOnBoard(tileTest1);
        Tile tileTest2 = new Tile(4,3);

        try {
            w.move(tileTest2);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }

        assertNotEquals(tileTest1,w.getPositionOnBoard());
        assertFalse(tileTest1.isOccupied());
        assertEquals(tileTest2.getWorker(),w);
    }

    @Test
    public void moveOnOpponent() {
        w.setPositionOnBoard(board.getTile(3,3));

        Tile t = board.getTile(3,2);
        t.setWorker(new Worker());

        assertThrows(InvalidMoveException.class,()->w.move(t));
        assertNotEquals(t.getWorker(),w);
    }

    @Test
    public void moveOnDome() {
        Tile tileTest1 = board.getTile(2,2);
        w.setPositionOnBoard(tileTest1);
        tileTest1.setWorker(w);
        Tile tileTest2 = board.getTile(2,3);
        tileTest2.setDome();

        assertThrows(InvalidMoveException.class,()->w.move(tileTest2));
    }

    @Test
    public void moveOnDifferentLevels() {
        Tile tileTest1 =board.getTile(3,3);
        w.setPositionOnBoard(tileTest1);
        tileTest1.setWorker(w);
        Tile tileTest2 = board.getTile(4,4);
        tileTest2.increaseLevel();
        tileTest2.increaseLevel();

        assertThrows(InvalidMoveException.class,()->w.move(tileTest2));
    }

    @Test
    public void winCondition() {
        Tile tileTest1 = board.getTile(3,3);
        tileTest1.increaseLevel();
        tileTest1.increaseLevel();

        w.setPositionOnBoard(tileTest1);
        tileTest1.setWorker(w);

        Tile tileTest2 = board.getTile(4,4);
        tileTest2.increaseLevel();
        tileTest2.increaseLevel();
        tileTest2.increaseLevel();

        try {
            w.move(tileTest2);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }

        assert(w.getPlayer().isWinner());

    }

    @Test
    public void NotWinIfAlreadyLevel3() {
        Tile tileTest1 = board.getTile(3,3);

        tileTest1.increaseLevel();
        tileTest1.increaseLevel();
        tileTest1.increaseLevel();
        w.setPositionOnBoard(tileTest1);
        tileTest1.setWorker(w);


        Tile tileTest2 = board.getTile(4,4);
        tileTest2.increaseLevel();
        tileTest2.increaseLevel();
        tileTest2.increaseLevel();

        try {
            w.move(tileTest2);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }

        assertFalse(w.getPlayer().isWinner());

    }

    @Test
    public void buildTest()  {
        Tile tileTest1 = board.getTile(4,4);
        w.setPositionOnBoard(tileTest1);
        tileTest1.setWorker(w);
        Tile tileTest2 = board.getTile(3,4);

        for(int i=0;i<=3;i++) {
            assertEquals(i,tileTest2.getLevel());
            w.build(tileTest2);
        }

        assert(tileTest2.isDome());
        //assertThrows(InvalidBuildException.class,()->w.build(tileTest2));
    }


}