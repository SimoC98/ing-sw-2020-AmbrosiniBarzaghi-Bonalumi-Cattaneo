package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.BuildTwiceSameTile;
import it.polimi.ingsw.model.cards.StandardDivinity;
import it.polimi.ingsw.model.exceptions.InvalidWorkerSelectionException;
import it.polimi.ingsw.model.exceptions.WorkerBadPlacementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoveManagerTest {

    @Test
    public void test() throws InvalidWorkerSelectionException, WorkerBadPlacementException {
        assertNull(ActionManager.getMatch());

        List<String> players = new ArrayList<>();
        players.add("paolo");
        Match match = new Match(players);
        Divinity div = new BuildTwiceSameTile(new StandardDivinity());
        match.getPlayers().get(0).setDivinity(div);
        match.placeWorkers(1,1,4,4);
        match.selectWorker(1,1);
        match.getCurrentPlayer().startOfTurn();

        assertEquals(match.getCurrentPlayer().getUsername(),"paolo");

        MoveManager m = new MoveManager(match,1,1);

        assertNotNull(ActionManager.getMatch());
        assertEquals(ActionManager.getMatch(),match);

        assertFalse(m.start());

       // assert(match.getBoard().getTile(2,2).isOccupied());
        assert(match.getBoard().getTile(1,1).isOccupied());

        assertNull(ActionManager.getMatch());
    }
}