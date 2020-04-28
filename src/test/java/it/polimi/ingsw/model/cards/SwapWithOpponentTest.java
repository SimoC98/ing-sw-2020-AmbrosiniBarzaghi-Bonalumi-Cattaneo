package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.InvalidWorkerSelectionException;
import it.polimi.ingsw.model.exceptions.WorkerBadPlacementException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class SwapWithOpponentTest {

 /*   private static Game game;
    private static Match match;
    private static Board board;
    private static MoveOnOpponent div;
    private static ActionManager a;

    @BeforeEach
    void setUp() throws WorkerBadPlacementException, InvalidWorkerSelectionException {
        List<String> players = new ArrayList<>();
        players.add("paolo");
        match = new Match(players);
        a = new ActionManager(match);
        game = new Game(match);
        board = match.getBoard();
        div = new SwapWithOpponent(new StandardDivinity());
        match.getPlayers().get(0).setDivinity(div);
        match.startNextTurn();
        match.placeWorkers(1,1,4,4);
        match.selectWorker(1,1);
    }

    @Test
    public void swapTest() {
        Worker worker = match.getSelectedWorker();
        Tile tile1 = board.getTile(1,1);
        Tile tile2 = board.getTile(2,2);
        Tile tile3 = board.getTile(3,3);
        Player p1 = match.getCurrentPlayer();

        Worker opponentWorker = new Worker(tile2,new Player("Marco",Color.BLUE));

        assert(match.getCurrentPlayer().getUsername().equals("paolo"));
        assert(p1.move(worker,tile2));

        assertEquals(tile2.getWorker(),worker);
        assertEquals(tile1.getWorker(),opponentWorker);
    }

    @Test
    public void swapdeniedTest() {
        Worker worker = match.getSelectedWorker();
        Tile tile1 = board.getTile(1,1);
        Tile tile2 = board.getTile(2,2);
        Tile tile3 = board.getTile(0,0);
        Player p1 = match.getCurrentPlayer();

        Worker opponentWorker1 = new Worker(tile2,new Player("Marco",Color.BLUE));
        Worker opponentWorker2 = new Worker(tile3,new Player("jack",Color.CREAM));

        board.getTile(0,1).setDome();
        board.getTile(1,0).setDome();

        assertFalse(p1.move(worker,tile3));
        assert(p1.move(worker,tile2));
    }

    @Test
    public void moveOn() {
        Worker worker = match.getSelectedWorker();
        Tile tile1 = board.getTile(1,1);
        Tile tile2 = board.getTile(2,2);
        Tile tile3 = board.getTile(3,3);
        Player p1 = match.getCurrentPlayer();

        tile2.increaseLevel();
        Worker opponentWorker1 = new Worker(tile2,new Player("Marco",Color.BLUE));

        assert(tile2.getLevel()==1);
        assert(p1.move(worker,tile2));
    }

    /*@Test
    public void test() {
        board.getTile(0,0).increaseLevel();
        board.getTile(0,0).increaseLevel();
        board.getTile(0,0).increaseLevel();
        board.getTile(0,1).increaseLevel();
        board.getTile(0,1).increaseLevel();

        assert(board.getTile(0,0).getLevel()==3 && board.getTile(0,1).getLevel()==2);

        Worker opponentWorker1 = new Worker(board.getTile(0,0),new Player("Marco",Color.BLUE));
        Worker opponentWorker2 = new Worker(board.getTile(0,1),new Player("jack",Color.CREAM));

        div.move(opponentWorker1,board.getTile(0,1));

        assertFalse(opponentWorker2.getPlayer().isWinner());
    }


    @AfterAll
    static void afterAll() {
        a.clearMatch();
    }*/
}