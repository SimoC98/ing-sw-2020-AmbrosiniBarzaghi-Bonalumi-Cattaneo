package it.polimi.ingsw.model.cards;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.exceptions.InvalidWorkerSelectionException;
import it.polimi.ingsw.model.exceptions.WorkerBadPlacementException;
import org.junit.jupiter.api.*;


import java.util.ArrayList;
import java.util.List;

class MoveTwiceNotBackTest {

    private Match match;
    private Board board;
    private MoveTwice div;

    @BeforeEach
    void setup() throws WorkerBadPlacementException, InvalidWorkerSelectionException {
        List<String> players = new ArrayList<>();
        players.add("paolo");
        players.add("francesco");
        match = new Match(players);
        match.setStartPlayer("paolo");
        board = match.getBoard();
        div = new MoveTwiceNotBack(new StandardDivinity());
        match.getPlayers().get(0).setDivinity(div);
        match.placeWorkers(1,1,4,4);
        match.selectWorker(1,1);
        match.getCurrentPlayer().startOfTurn();
    }


    @Test
    public void moveTwiceNotBackTest(){
        Tile tile1 = board.getTile(1,1);
        Tile tile2 = board.getTile(2,2);
        Tile tile3 = board.getTile(3,3);
        Worker w = match.getSelectedWorker();
        Player p1 = match.getCurrentPlayer();

        assert(p1.move(board,w,tile2));

        assert(div.getMoveCount()==1);
        assertEquals(div.getFirstMovedTile(), tile1);
        assertFalse(p1.move(board,w,tile1));
        assertTrue(p1.move(board,w,tile3));
    }

    @Test
    public void updatePossibleActionsTest() {
        Tile tile2 = board.getTile(2,2);
        Tile tile3 = board.getTile(3,3);
        Worker w = match.getSelectedWorker();
        Player p1 = match.getCurrentPlayer();

        assert(p1.getPossibleActions().size()==1);
        assert(p1.getPossibleActions().contains(Action.MOVE));

        p1.move(board,w,tile2);

        assert(p1.getPossibleActions().size()==2);
        assert(p1.getPossibleActions().contains(Action.MOVE));
        assert(p1.getPossibleActions().contains(Action.BUILD));

        p1.move(board,w,tile3);

        assert(p1.getPossibleActions().size()==1);
        assert(p1.getPossibleActions().contains(Action.BUILD));

        p1.build(board,w,tile2);

        assert(p1.getPossibleActions().size()==0);

    }

    @Test
    public void updatePossibleActionsTest2() {
        Tile tile1 = board.getTile(1,1);
        Tile tile2 = board.getTile(0,0);
        Tile tile3 = board.getTile(3,3);
        Worker w = match.getCurrentPlayer().getWorkers().get(0);
        Player p1 = match.getCurrentPlayer();

        board.getTile(0,1).setDome();
        board.getTile(1,0).increaseLevel();
        board.getTile(1,0).increaseLevel();

        p1.move(board,w,tile2);

        assert(p1.getPossibleActions().size()==1);
        assert(p1.getPossibleActions().contains(Action.BUILD));

        p1.build(board,w,tile1);
        assert(p1.getPossibleActions().size()==0);
    }

}