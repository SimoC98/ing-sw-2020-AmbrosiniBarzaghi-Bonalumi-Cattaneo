package it.polimi.ingsw.model.cards;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.exceptions.InvalidWorkerSelectionException;
import it.polimi.ingsw.model.exceptions.WorkerBadPlacementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class BuildTwiceNotSameTileTest {

    private static Game game;
    private static Match match;
    private static Board board;
    private static BuildTwice div;

    @BeforeEach
    void setup() throws WorkerBadPlacementException, InvalidWorkerSelectionException {
        List<String> players = new ArrayList<>();
        players.add("paolo");
        players.add("francesco");
        match = new Match(players);
        game = new Game(match);
        board = match.getBoard();
        div = new BuildTwiceNotSameTile(new StandardDivinity());
        match.getPlayers().get(0).setDivinity(div);
        match.placeWorkers(1,1,4,4);
        match.selectWorker(1,1);
        match.getCurrentPlayer().startOfTurn();
    }

    @Test
    public void buildTwiceNotSameTileTest(){
        Worker worker = match.getSelectedWorker();
        Tile tile1 = board.getTile(1,1);
        Tile tile2 = board.getTile(2,2);
        Tile tile3 = board.getTile(3,3);
        Player p1 = match.getCurrentPlayer();

        assert(p1.move(worker,tile2));
        assert(p1.build(worker,tile1));

        assert(div.getBuildCount()==1);
        assertEquals(div.getFirstBuildTile(), tile1);
        assertFalse(p1.build(worker,tile1));
        assertTrue(p1.build(worker,tile3));
    }

    @Test
    public void updatePossibleActionTest() {
        Tile tile1 = board.getTile(1,1);
        Tile tile2 = board.getTile(2,2);
        Tile tile3 = board.getTile(3,3);
        Worker w = match.getCurrentPlayer().getWorkers().get(0);
        Player p1 = match.getCurrentPlayer();

        assert(p1.getPossibleActions().size()==1);
        assert(p1.getPossibleActions().contains(Action.MOVE));

        p1.move(w,tile2);

        assert(p1.getPossibleActions().size()==1);
        assert(p1.getPossibleActions().contains(Action.BUILD));

        p1.build(w,tile1);

        assert(p1.getPossibleActions().size()==2);
        assert(p1.getPossibleActions().contains(Action.END));
        assert(p1.getPossibleActions().contains(Action.BUILD));

        assertFalse(p1.build(w,tile1));

        p1.build(w,tile3);

        assert(p1.getPossibleActions().size()==0);
    }

    @Test
    public void updatePossibleAction2Test() {
        Tile tile1 = board.getTile(1,1);
        Tile tile2 = board.getTile(0,0);
        Tile tile3 = board.getTile(3,3);
        Worker w = match.getCurrentPlayer().getWorkers().get(0);
        Player p1 = match.getCurrentPlayer();

        board.getTile(0,1).setDome();
        board.getTile(1,0).setDome();

        assert(p1.getPossibleActions().size()==1);
        assert(p1.getPossibleActions().contains(Action.MOVE));

        p1.move(w,tile2);

        assert(p1.getPossibleActions().size()==1);
        assert(p1.getPossibleActions().contains(Action.BUILD));

        p1.build(w,tile1);

        assert(p1.getPossibleActions().size()==0);
    }
}