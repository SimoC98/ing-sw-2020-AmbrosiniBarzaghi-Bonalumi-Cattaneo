package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.InvalidWorkerSelectionException;
import it.polimi.ingsw.model.exceptions.WorkerBadPlacementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PushOpponentTest {

    private Match match;
    private Board board;
    private PushOpponent div;

    @BeforeEach
    void setup() throws WorkerBadPlacementException, InvalidWorkerSelectionException {
        List<String> players = new ArrayList<>();
        players.add("Mike");
        players.add("Oscar");
        match = new Match(players);
        board = match.getBoard();
        div = new PushOpponent(new StandardDivinity());
        match.getPlayers().get(0).setDivinity(div);
        match.placeWorkers(2,3,3,2);
        match.getPlayers().get(1).setDivinity(new StandardDivinity());
        match.getPlayers().get(1).addWorker(board.getTile(3,3));
        match.getPlayers().get(1).addWorker(board.getTile(4,3));
    }
        @Test
        public void pushOpponentSimple(){
        Player p = match.getCurrentPlayer();
        Worker w = p.getWorkers().get(1);
        Tile t1 = board.getTile(3,2);
        Tile t2 = board.getTile(3,3);
        Worker w2 = t2.getWorker();


        assertTrue(div.legalMove(board,w, t2));

        p.move(board,w, t2);

        assertEquals(w.getPositionOnBoard(), t2);
        assertEquals(w2.getPositionOnBoard(), board.getTile(t2.getX(), t2.getY()+1));
        assertFalse(t1.isOccupied());

    }

    @Test
    public void pushOpponentOnHigherTile(){

        Player p = match.getCurrentPlayer();
        Worker w = p.getWorkers().get(1);
        Tile t1 = board.getTile(3,2);
        Tile t2 = board.getTile(3,3);
        Tile t3 = board.getTile(3,4);
        Worker w2 = t2.getWorker();

        t3.increaseLevel();
        t3.increaseLevel();
        t3.increaseLevel();

        assertTrue(div.legalMove(board,w, t2));

        p.move(board,w, t2);

        assertEquals(w.getPositionOnBoard(), t2);
        assertEquals(w2.getPositionOnBoard(), board.getTile(t2.getX(), t2.getY()+1));
        assertFalse(t1.isOccupied());
        assertFalse(w2.getPlayer().isWinner());
    }

    @Test
    public void pushOpponentOffEdge(){
        Player p = match.getCurrentPlayer();
        Worker w = p.getWorkers().get(1);
        Tile t = board.getTile(4,3);

        assertFalse(div.legalMove(board,w, t));
    }

    @Test
    public void pushOpponentOnOccupiedTile(){

        Player p = match.getCurrentPlayer();
        Worker w = p.getWorkers().get(0);
        Tile t = board.getTile(3,3);

        assertFalse(div.legalMove(board,w, t));
    }

    @Test
    public void pushOpponentOnDome(){

        Player p = match.getCurrentPlayer();
        Worker w = p.getWorkers().get(1);
        Tile t = board.getTile(3,2);
        Tile t1 = board.getTile(3,4);
        t1.setDome();

        assertFalse(div.legalMove(board,w,t));
    }
}