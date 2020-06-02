package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.InvalidWorkerSelectionException;
import it.polimi.ingsw.model.exceptions.WorkerBadPlacementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BuildTwiceNotOnPerimeterTest {

    private Match match;
    private Board board;
    private BuildTwiceNotOnPerimeter div;

    @BeforeEach
    void setUp() throws WorkerBadPlacementException, InvalidWorkerSelectionException {
        List<String> players = new ArrayList<>();
        players.add("paolo");
        match = new Match(players);
        match.setStartPlayer("paolo");
        board = match.getBoard();
        div = new BuildTwiceNotOnPerimeter(new StandardDivinity());
        match.getPlayers().get(0).setDivinity(div);
        match.placeWorkers(1,1,4,4);
        match.selectWorker(1,1);
        match.getCurrentPlayer().startOfTurn();
    }

    @Test
    public void buildTwiceNotOnPerimeterTest() {
        Tile tile1 = board.getTile(1,1);
        Tile tile2 = board.getTile(0,0);
        Tile tile3 = board.getTile(0,1);
        Tile tile4 = board.getTile(2,2);
        Worker w = match.getCurrentPlayer().getWorkers().get(0);
        Player p1 = match.getCurrentPlayer();

        assert(p1.getPossibleActions().size()==1 && p1.getPossibleActions().contains(Action.MOVE));

        assert(p1.move(board,w,tile4));
        assert(p1.getPossibleActions().size()==1 && p1.getPossibleActions().contains(Action.BUILD));

        assert(p1.build(board,w,tile1));
        assert(p1.getPossibleActions().size()==2);
        assert(p1.getPossibleActions().contains(Action.BUILD));
        assert(p1.getPossibleActions().contains(Action.END));

        assert(p1.build(board,w,tile1));
        assert(p1.getPossibleActions().size()==0);
    }

    @Test
    public void cannotBuildTwiceOnPerimeter() {
        Tile tile1 = board.getTile(1,1);
        Tile tile2 = board.getTile(0,0);
        Tile tile3 = board.getTile(0,1);
        Tile tile4 = board.getTile(2,2);
        Worker w = match.getCurrentPlayer().getWorkers().get(0);
        Player p1 = match.getCurrentPlayer();

        assert(p1.getPossibleActions().size()==1 && p1.getPossibleActions().contains(Action.MOVE));
        assert(p1.move(board,w,tile2));
        assert(p1.getPossibleActions().size()==1 && p1.getPossibleActions().contains(Action.BUILD));

        assertFalse(p1.build(board,w,tile4));
        assert(p1.build(board,w,tile3));
        assert(p1.getPossibleActions().size()==2);
        assert(p1.getPossibleActions().contains(Action.BUILD));
        assert(p1.getPossibleActions().contains(Action.END));

        assertFalse(p1.build(board,w,tile3));
    }

    @Test
    public void test() {
        Tile tile1 = board.getTile(1,1);
        Tile tile2 = board.getTile(0,0);
        Tile tile3 = board.getTile(0,1);
        Tile tile4 = board.getTile(2,2);
        Tile tile5 = board.getTile(1,0);
        Worker w = match.getCurrentPlayer().getWorkers().get(0);
        Player p1 = match.getCurrentPlayer();

        tile1.increaseLevel();
        tile1.increaseLevel();
        tile1.increaseLevel();


        assert(tile1.getLevel()==3 && tile2.getLevel()==0);

        assert(p1.move(board,w,tile2));
        assert(p1.build(board,w,tile1));

        assert(tile1.isDome());

        assert(p1.getPossibleActions().size()==0);




    }
}