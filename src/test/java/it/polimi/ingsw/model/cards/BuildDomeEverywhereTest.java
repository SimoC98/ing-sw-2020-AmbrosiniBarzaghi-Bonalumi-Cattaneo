package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.InvalidActionException;
import it.polimi.ingsw.model.exceptions.InvalidWorkerSelectionException;
import it.polimi.ingsw.model.exceptions.WorkerBadPlacementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BuildDomeEverywhereTest {

    private Match match;
    private Board board;
    private BuildDomeEverywhere div;

    @BeforeEach
    void setUp() throws WorkerBadPlacementException, InvalidWorkerSelectionException {
        List<String> players = new ArrayList<>();
        players.add("paolo");
        match = new Match(players);
        match.setStartPlayer("paolo");
        board = match.getBoard();
        div = new BuildDomeEverywhere(new StandardDivinity());
        match.getPlayers().get(0).setDivinity(div);
        match.placeWorkers(1,1,4,4);
        match.selectWorker(1,1);
        match.getCurrentPlayer().startOfTurn();
    }


    @Test
    public void buildNotDomeTest() throws InvalidActionException {
        Tile tile1 = board.getTile(1,1);
        Tile tile2 = board.getTile(2,2);
        Worker w = match.getCurrentPlayer().getWorkers().get(0);
        Player p1 = match.getCurrentPlayer();

        p1.move(board,w,tile2);
        match.setAction(Action.BUILD);
        assert(p1.getPossibleActions().size()==2);
        assert(p1.getPossibleActions().contains(Action.BUILD));
        assert(p1.getPossibleActions().contains(Action.BUILDDOME));

        p1.build(board,w,tile1);


        assertFalse(tile1.isDome());
        assert(tile1.getLevel()==1);
    }

    @Test
    public void buildDomeTest() throws InvalidActionException {
        Tile tile1 = board.getTile(1,1);
        Tile tile2 = board.getTile(2,2);
        Worker w = match.getCurrentPlayer().getWorkers().get(0);
        Player p1 = match.getCurrentPlayer();


        p1.move(board,w,tile2);
        match.setAction(Action.BUILDDOME);

        assert(p1.getPossibleActions().size()==2);
        assert(p1.getPossibleActions().contains(Action.BUILD));
        assert(p1.getPossibleActions().contains(Action.BUILDDOME));

        p1.build(board,w,tile1);

        assert(tile1.isDome());
        assertNotEquals(3, tile1.getLevel());
    }

}