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


class SwapWithOpponentTest {

    private Match match;
    private Board board;
    private SwapWithOpponent div;

    @BeforeEach
    void setUp() throws WorkerBadPlacementException, InvalidWorkerSelectionException {
        List<String> players = new ArrayList<>();
        players.add("Paolo");
        players.add("Felix");
        match = new Match(players);
        match.setStartPlayer("Paolo");
        board = match.getBoard();
        div = new SwapWithOpponent(new StandardDivinity());
        match.getPlayers().get(0).setDivinity(div);
        match.placeWorkers(1,1,4,4);
        match.selectWorker(1,1);
        match.getPlayers().get(1).addWorker(board.getTile(2,2));
        match.getPlayers().get(1).addWorker(board.getTile(0,0));
    }

    @Test
    public void swapTest() {
        Worker worker = match.getSelectedWorker();
        Tile tile1 = board.getTile(1,1);
        Tile tile2 = board.getTile(2,2);
        Player p = match.getCurrentPlayer();
        Worker opponentWorker = board.getTile(2,2).getWorker();

        assertTrue(p.move(board,worker,tile2));

        assert(p.getModelUpdates().size()==2);

        assertEquals(tile2.getWorker(),worker);
        assertEquals(tile1.getWorker(),opponentWorker);
    }

    @Test
    public void swapDeniedTest() {
        Worker worker = match.getSelectedWorker();
        Tile t = board.getTile(0,0);
        Player p1 = match.getCurrentPlayer();

        board.getTile(0,1).setDome();
        board.getTile(1,0).setDome();

        assertFalse(p1.move(board,worker,t));
    }

    @Test
    public void moveOn() throws InvalidMoveException {
        Worker worker = match.getSelectedWorker();
        Tile tile2 = board.getTile(0,1);
        Player p1 = match.getCurrentPlayer();

        tile2.increaseLevel();
        board.getTile(0,0).getWorker().move(tile2);

        assert(p1.move(board,worker,tile2));
    }

    @Test
    public void NoWinBySwapping()  {
        Tile t1 = board.getTile(0,1);
        t1.increaseLevel();
        t1.increaseLevel();
        t1.increaseLevel();
        match.getCurrentPlayer().addWorker(t1);

        match.getCurrentPlayer().move(board,t1.getWorker(),board.getTile(0,0)); //third worker to simplify test

        assertFalse(t1.getWorker().getPlayer().isWinner());
    }


}