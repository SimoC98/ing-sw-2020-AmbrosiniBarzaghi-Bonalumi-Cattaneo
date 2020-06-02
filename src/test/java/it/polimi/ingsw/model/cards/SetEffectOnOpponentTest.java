package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.InvalidMoveException;
import it.polimi.ingsw.model.exceptions.InvalidWorkerSelectionException;
import it.polimi.ingsw.model.exceptions.WorkerBadPlacementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SetEffectOnOpponentTest {

    private Match match;
    private Board board;
    private SetEffectOnOpponent div1;

    @BeforeEach
    void setUp() throws WorkerBadPlacementException, InvalidWorkerSelectionException {
        List<String> users = new ArrayList<String>();
        users.add("Hello");
        users.add("World");
        match = new Match(users);
        match.setStartPlayer("Hello");
        board = match.getBoard();
        div1 = new SetEffectOnOpponent(new StandardDivinity());
        match.getPlayers().get(0).setDivinity(div1);
        match.placeWorkers(0,0,1,1);
        match.selectWorker(0,0);
        match.getPlayers().get(1).setDivinity(new StandardDivinity());
        match.getPlayers().get(1).addWorker(board.getTile(2,2));
        match.getPlayers().get(1).addWorker(board.getTile(3,3));
    }

    @Test
    public void test() {
        Worker w1 = match.getSelectedWorker();
        Worker w2 = board.getTile(2,2).getWorker();
        Player p1 = match.getCurrentPlayer();
        Tile t1 = board.getTile(0,1);
        Tile t2 = board.getTile(2,3);
        Tile t3 = board.getTile(2,2);
        Tile t4 = board.getTile(2,1);

        t1.increaseLevel();
        p1.move(board,w1,t1);
        p1.build(board,w1,board.getTile(0,0));
        match.startNextTurn();

        t2.increaseLevel();
        Player p2 = match.getCurrentPlayer();
        assertFalse(p2.getDivinity().legalMove(board,w2,t2));
        assertTrue(p2.getDivinity().legalMove(board,w2,t4));
        p2.move(board,w2,t4);
        p2.build(board,w2,t3);
        match.startNextTurn();

        p1.move(board,w1,board.getTile(0,0));
        p1.build(board,w1,t1);
        match.startNextTurn();

        assertTrue(p2.getDivinity().legalMove(board,w2,t3));
    }
}