package it.polimi.ingsw.model.manager;

import it.polimi.ingsw.model.Divinity;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.cards.BuildTwiceSameTile;
import it.polimi.ingsw.model.cards.StandardDivinity;
import it.polimi.ingsw.model.exceptions.InvalidWorkerSelectionException;
import it.polimi.ingsw.model.exceptions.WorkerBadPlacementException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActionManagerTest {

    @Test
    public void test() throws WorkerBadPlacementException, InvalidWorkerSelectionException {
        List<String> players = new ArrayList<>();
        players.add("paolo");
        players.add("francesco");
        Match match = new Match(players);
        Game game = new Game(match);
        Divinity div = new BuildTwiceSameTile(new StandardDivinity());
        match.getPlayers().get(0).setDivinity(div);
        match.placeWorkers(1,1,4,4);
        match.selectWorker(1,1);
        match.getCurrentPlayer().startOfTurn();

        assertNull(ActionManager.getMatch());

        MoveManager m = new MoveManager(match,2,2);

        assertNotNull(ActionManager.getMatch());

        assert(m.start());
        assertNull(ActionManager.getMatch());
        assert(match.getBoard().getTile(2,2).isOccupied());
        assertFalse(match.getBoard().getTile(1,1).isOccupied());






    }

}