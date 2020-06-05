package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.InvalidMoveException;
import it.polimi.ingsw.model.exceptions.InvalidWorkerSelectionException;
import it.polimi.ingsw.model.exceptions.WorkerBadPlacementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CannotWinOnPerimeterTest {

    private Match match;
    private Board board;
    private CannotWinOnPerimeter div1;

    @BeforeEach
    void setUp() throws WorkerBadPlacementException, InvalidWorkerSelectionException {
        List<String> users = new ArrayList<String>();
        users.add("Jim");
        users.add("Kim");
        match = new Match(users);
        match.setStartPlayer("Jim");
        board = match.getBoard();
        div1 = new CannotWinOnPerimeter(new StandardDivinity());
        match.getPlayers().get(0).setDivinity(div1);
        match.placeWorkers(0,0,1,1);
        match.selectWorker(0,0);
        match.getPlayers().get(1).setDivinity(new WinByDropTwoLevel(new StandardDivinity()));
    }

    //player2 can not build moving on a perimetral tile
    @Test
    public void BlockStandardWin() {
        Worker w1 = match.getSelectedWorker();
        Player p1 = match.getCurrentPlayer();
        Player p2 = match.getPlayers().get(1);
        Tile t1 = board.getTile(0,1);
        Tile t2 = board.getTile(3,0);
        Tile t3 = board.getTile(4,0);
        Tile t4 = board.getTile(3,1);

        p1.move(board,w1,t1);
        p1.build(board,w1,board.getTile(0,0));
        match.startNextTurn();

        t2.increaseLevel();
        t2.increaseLevel();
        t3.increaseLevel();
        t3.increaseLevel();
        t3.increaseLevel();
        t4.increaseLevel();
        t4.increaseLevel();
        t4.increaseLevel();
        p2.addWorker(t2);

        p2.move(board,p2.getWorkers().get(0),t3);
        assertFalse(p2.isWinner());
    }

    //player2 can win moving on a inside tile even if they are on the edge
    @Test
    public void TestStandardWin() {
        Worker w1 = match.getSelectedWorker();
        Player p1 = match.getCurrentPlayer();
        Player p2 = match.getPlayers().get(1);
        Tile t1 = board.getTile(0,1);
        Tile t2 = board.getTile(3,0);
        Tile t3 = board.getTile(4,0);
        Tile t4 = board.getTile(3,1);

        p1.move(board,w1,t1);
        p1.build(board,w1,board.getTile(0,0));
        match.startNextTurn();

        t2.increaseLevel();
        t2.increaseLevel();
        t3.increaseLevel();
        t3.increaseLevel();
        t3.increaseLevel();
        t4.increaseLevel();
        t4.increaseLevel();
        t4.increaseLevel();
        p2.addWorker(t2);

        p2.move(board,p2.getWorkers().get(0),t4);
        assertTrue(p2.isWinner());
    }

    //tests if a the win condition deriving from a divinity is not allowed
    @Test
    public void BlockSpecialWin() {
        Worker w1 = match.getSelectedWorker();
        Player p1 = match.getCurrentPlayer();
        Player p2 = match.getPlayers().get(1);
        Tile t1 = board.getTile(0,1);
        Tile t2 = board.getTile(3,0);
        Tile t3 = board.getTile(4,0);

        p1.move(board,w1,t1);
        p1.build(board,w1,board.getTile(0,0));
        match.startNextTurn();

        t2.increaseLevel();
        t2.increaseLevel();
        t2.increaseLevel();

        p2.addWorker(t2);

        p2.move(board,p2.getWorkers().get(0),t3);
        assertFalse(p2.isWinner());
    }

    //tests the normal flow a divinity with a win condition
    @Test
    public void SpecialWin() {
        Worker w1 = match.getSelectedWorker();
        Player p1 = match.getCurrentPlayer();
        Player p2 = match.getPlayers().get(1);
        Tile t1 = board.getTile(0,1);
        Tile t2 = board.getTile(3,0);
        Tile t3 = board.getTile(3,1);

        p1.move(board,w1,t1);
        p1.build(board,w1,board.getTile(0,0));
        match.startNextTurn();

        t2.increaseLevel();
        t2.increaseLevel();
        t2.increaseLevel();

        p2.addWorker(t2);

        p2.move(board,p2.getWorkers().get(0),t3);
        assertTrue(p2.isWinner());
    }
}