package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.InvalidWorkerSelectionException;
import it.polimi.ingsw.model.exceptions.WorkerBadPlacementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BuildUnderYourselfTest {

    private Match match;
    private Board board;
    private BuildUnderYourself div;

    @BeforeEach
    void setUp() throws WorkerBadPlacementException, InvalidWorkerSelectionException {
        List<String> players = new ArrayList<>();
        players.add("paolo");
        match = new Match(players);
        match.setStartPlayer("paolo");
        board = match.getBoard();
        div = new BuildUnderYourself(new StandardDivinity());
        match.getPlayers().get(0).setDivinity(div);
        match.placeWorkers(1,1,4,4);
        match.selectWorker(1,1);
        match.getCurrentPlayer().startOfTurn();

    }

    @Test
    public void buildUnderItselfTest() {
        Tile tile1 = board.getTile(1,1);
        Tile tile2 = board.getTile(2,2);
        Tile tile3 = board.getTile(3,3);
        Worker w = match.getCurrentPlayer().getWorkers().get(0);
        Player p1 = match.getCurrentPlayer();

        assert(p1.getPossibleActions().size()==1 && p1.getPossibleActions().contains(Action.MOVE));

        assert(p1.move(board,w,tile2));
        assert(p1.getPossibleActions().size()==1 && p1.getPossibleActions().contains(Action.BUILD));

        assert(tile2.getLevel()==0);
        assert(tile2.isOccupied());
        assertEquals(tile2.getWorker(),w);

        assert(p1.build(board,w,tile2));
        assert(tile2.getLevel()==1);
        assert(tile2.isOccupied());
        assertEquals(tile2.getWorker(),w);
    }

    @Test
    public void cannotBuildIfLevel3() {
        Tile tile1 = board.getTile(1,1);
        Tile tile2 = board.getTile(2,2);
        Tile tile3 = board.getTile(3,3);
        Worker w = match.getCurrentPlayer().getWorkers().get(0);
        Player p1 = match.getCurrentPlayer();

        tile1.increaseLevel();
        tile1.increaseLevel();
        tile2.increaseLevel();
        tile2.increaseLevel();
        tile2.increaseLevel();

        assert(tile1.getLevel()==2 && tile2.getLevel()==3);
        assert(tile1.isOccupied());
        assertEquals(tile1.getWorker(),w);

        assert(p1.move(board,w,tile2));

        assert(tile2.isOccupied());
        assertEquals(tile2.getWorker(),w);

        assertFalse(p1.build(board,w,tile2));
        assert(p1.build(board,w,tile3));

        assert(tile3.getLevel()==1);



    }
}