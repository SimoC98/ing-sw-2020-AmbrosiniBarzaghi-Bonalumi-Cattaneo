package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.InvalidWorkerSelectionException;
import it.polimi.ingsw.model.exceptions.WorkerBadPlacementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CannotSelectIfHigherTest {

    private Match match;
    private Board board;
    private CannotSelectIfHigher div;

    @BeforeEach
    void setUp() throws WorkerBadPlacementException, InvalidWorkerSelectionException {
        List<String> users = new ArrayList<String>();
        users.add("Hypno");
        users.add("Rospo");
        match = new Match(users);
        match.setStartPlayer("Hypno");
        board = match.getBoard();
        div = new CannotSelectIfHigher(new StandardDivinity());
        match.getPlayers().get(0).setDivinity(div);
        match.placeWorkers(0,0,1,1);
        match.selectWorker(0,0);
        match.getPlayers().get(1).setDivinity(new MoveAgainIfOnPerimeterSpace(new StandardDivinity()));
    }

    @Test
    public void CanGoUpAfterFirstMove(){
        Worker w1 = match.getSelectedWorker();
        Player p1 = match.getCurrentPlayer();
        Player p2 = match.getPlayers().get(1);
        Tile t1 = board.getTile(0,1);
        Tile t2 = board.getTile(4,0);
        Tile t3 = board.getTile(4,1);
        Tile t4 = board.getTile(4,2);
        Tile t5 = board.getTile(2,2);

        p1.move(board,w1,t1);
        p1.build(board,w1,board.getTile(0,0));
        match.startNextTurn();

        t5.increaseLevel();
        p2.addWorker(t5);
        t3.increaseLevel();
        t4.increaseLevel();
        t4.increaseLevel();
        p2.addWorker(t2);

        assertFalse(p2.getDivinity().legalMove(board,p2.getWorkers().get(1),board.getTile(2,3)));

        p2.move(board,p2.getWorkers().get(1),t3);
        assertTrue(p2.getDivinity().legalMove(board,p2.getWorkers().get(1),t4));

    }

    @Test
    public void CannotSelectWorker(){
        Worker w1 = match.getSelectedWorker();
        Player p1 = match.getCurrentPlayer();
        Player p2 = match.getPlayers().get(1);
        Tile t1 = board.getTile(0,1);
        Tile t2 = board.getTile(1,0);
        Tile t3 = board.getTile(4,0);

        p1.move(board,w1,t1);
        p1.build(board,w1,board.getTile(0,0));
        match.startNextTurn();

        t1.increaseLevel();
        p2.addWorker(t2);
        t3.increaseLevel();
        t3.increaseLevel();
        p2.addWorker(t3);

        assertThrows(InvalidWorkerSelectionException.class,()-> match.selectWorker(4,0)); //
    }
}