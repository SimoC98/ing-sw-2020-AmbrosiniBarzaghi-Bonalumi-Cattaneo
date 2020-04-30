package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.InvalidWorkerSelectionException;
import it.polimi.ingsw.model.exceptions.WorkerBadPlacementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BuildBeforeAndAfterTest {
    private Match match;
    private Board board;
    private BuildBeforeAndAfter div;

    @BeforeEach
    void setUp() {
        List<String> players = new ArrayList<>();
        players.add("paolo");
        match = new Match(players);
        board = match.getBoard();
        div = new BuildBeforeAndAfter(new StandardDivinity());
        match.getPlayers().get(0).setDivinity(div);
    }

    @Test
    public void setupTest() throws WorkerBadPlacementException, InvalidWorkerSelectionException {
        match.placeWorkers(0,0,4,4);
        match.selectWorker(0,0);
        match.getCurrentPlayer().startOfTurn();
        Player p = match.getPlayers().get(0);

        List actions = p.getPossibleActions();

        assert(actions.size()==2);
        assert(actions.contains(Action.MOVE) && actions.contains(Action.BUILD));
    }

    @Test
    public void buildBeforeMoveTest() throws WorkerBadPlacementException, InvalidWorkerSelectionException {
        match.placeWorkers(0,0,4,4);
        match.selectWorker(0,0);
        match.getCurrentPlayer().startOfTurn();
        Player p = match.getPlayers().get(0);
        Tile tile1 = board.getTile(1,1);
        Tile tile2 = board.getTile(0,1);
        Worker worker = match.getSelectedWorker();

        p.build(board,worker,tile1);

        assert(p.getPossibleActions().size()==1 && p.getPossibleActions().contains(Action.MOVE));

        assertFalse(p.move(board,worker,tile1)); //can't move here because is on level 1

        assert(p.move(board,worker,tile2));
        assert((p.getPossibleActions().size()==1) && p.getPossibleActions().contains(Action.BUILD));

        p.build(board,worker,tile1);
        assert(p.getPossibleActions().size()==0);
    }

    @Test
    public void CannotBuildIfCannotMoveTest() throws WorkerBadPlacementException, InvalidWorkerSelectionException {
        match.placeWorkers(0,0,4,4);
        match.selectWorker(0,0);
        match.getCurrentPlayer().startOfTurn();
        Player p = match.getPlayers().get(0);
        Tile tile1 = board.getTile(0,1);
        Tile tile2 = board.getTile(1,0);
        Tile tile3 = board.getTile(1,1);
        Tile tile4 = board.getTile(0,0);
        Worker worker = match.getSelectedWorker();

        assert(p.getWorkers().get(0).getPositionOnBoard().getX()==0 && p.getWorkers().get(0).getPositionOnBoard().getY()==0);

        tile1.setDome();
        tile2.setDome();
        assertFalse(div.legalBuild(board,worker,tile3));
        assert(div.legalMove(board,worker,tile3));

        p.move(board,worker,tile3);
        p.move(board,worker,tile4);

        assert(div.legalBuild(board,worker,tile3));
    }

    @Test
    public void updatePossibleActionTest() throws WorkerBadPlacementException, InvalidWorkerSelectionException {
        Player p = match.getPlayers().get(0);
        Tile tile1 = board.getTile(0,1);
        Tile tile2 = board.getTile(1,0);
        Tile tile3 = board.getTile(1,1);
        Tile tile4 = board.getTile(0,0);

        tile4.increaseLevel();
        tile1.increaseLevel();
        tile1.increaseLevel();
        tile2.increaseLevel();
        tile2.increaseLevel();

        match.placeWorkers(0,0,4,4);
        match.selectWorker(0,0);
        Worker worker = match.getSelectedWorker();
        match.getCurrentPlayer().startOfTurn();

        //(0,0) level 1, (0,1) level 2, (1,0) level 2, (1,1) level 0

        assert(worker.getPositionOnBoard().getX()==0 && worker.getPositionOnBoard().getY()==0 && tile4.getLevel()==1);
        assert(tile2.getLevel()==2 && tile1.getLevel()==2);

        assert(div.legalBuild(board,worker,tile3));  //if at lower level, he can build
        tile3.increaseLevel();
        assertFalse(div.legalBuild(board,worker,tile3)); //if on the same level he can't build, because therefore he couldn't move
        assert(div.legalBuild(board,worker,tile1));

        assert(p.getPossibleActions().size()==2);
        assert(p.getPossibleActions().contains(Action.MOVE));
        assert(p.getPossibleActions().contains(Action.BUILD));

        assertFalse(p.build(board,worker,tile3));
        assert(p.build(board,worker,tile2));
        assert(p.getPossibleActions().size()==1 && p.getPossibleActions().contains(Action.MOVE));
        //assert(b.getTile(0,1).getLevel()==2);
        assertFalse(p.move(board,worker,tile1)); //after building he can't move up
        assertFalse(p.move(board,worker,tile2)); // same
        assert(p.move(board,worker,tile3));
        assert(p.getPossibleActions().size()==1 && p.getPossibleActions().contains(Action.BUILD));

        p.build(board,worker,tile4);
        assert(p.getPossibleActions().size()==0);
    }


}