package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.StandardDivinity;
import it.polimi.ingsw.model.exceptions.WorkerBadPlacementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatchTest {
    private static Board board;
    private static Match match;
    private static Player p1;
    private static Player p2;
    private static Player p3;

    @BeforeEach
    void setUp() {
        List<String> players = new ArrayList<>();
        players.add("paolo");
        players.add("giacomo");
        players.add("franco");
        match = new Match(players);
        board = match.getBoard();
        p1 = match.getPlayers().get(0);
        p2 = match.getPlayers().get(1);
        p3 = match.getPlayers().get(2);
    }

    @Test
    public void startNewTurnTest() {
        p1.setDivinity(new StandardDivinity());
        p2.setDivinity(new StandardDivinity());
        p3.setDivinity(new StandardDivinity());

        Color[] colors = Color.values();

        assertNull(match.getCurrentPlayer());

        match.startNextTurn();
        assert(match.getCurrentPlayer().getUsername().equals("paolo"));
        assert(match.getCurrentPlayer().getColor().equals(colors[0]));

        match.startNextTurn();
        assert(match.getCurrentPlayer().getUsername().equals("giacomo"));
        assert(match.getCurrentPlayer().getColor().equals(colors[1]));

        match.startNextTurn();
        assert(match.getCurrentPlayer().getUsername().equals("franco"));
        assert(match.getCurrentPlayer().getColor().equals(colors[2]));

        match.startNextTurn();
        assert(match.getCurrentPlayer().getUsername().equals("paolo"));
        assert(match.getCurrentPlayer().getColor().equals(colors[0]));

        assert(match.getCurrentPlayer().getPossibleActions().contains(Action.MOVE));
    }

    @Test
    public void checkLoserTest() throws WorkerBadPlacementException {
        p1.setDivinity(new StandardDivinity());
        p2.setDivinity(new StandardDivinity());
        p3.setDivinity(new StandardDivinity());
        match.startNextTurn();

        match.placeWorkers(0,0,4,0);

        assert(board.getTile(0,0).isOccupied());
        assert(board.getTile(4,0).isOccupied());

        board.getTile(0,1).setDome();
        board.getTile(1,0).setDome();
        board.getTile(1,1).setDome();

        board.getTile(3,0).setDome();
        board.getTile(3,1).setDome();
        board.getTile(4,1).setDome();

        assert(match.getAvailableMoveTiles(p1.getWorkers().get(0)).size()==0);
        assert(match.getAvailableMoveTiles(p1.getWorkers().get(1)).size()==0);
        assert(match.getAvailableBuildTiles(p1.getWorkers().get(0)).size()==0);
        assert(match.getAvailableBuildTiles(p1.getWorkers().get(1)).size()==0);

        assert(match.checkLoser());
        assert(match.getCurrentPlayer().getUsername().equals("giacomo"));
        assert(match.getPlayers().size()==2);
        assertFalse(board.getTile(0,0).isOccupied());
        assertFalse(board.getTile(4,0).isOccupied());

        match.placeWorkers(0,0,4,0);

        assert(match.getAvailableMoveTiles(p2.getWorkers().get(0)).size()==0);
        assert(match.getAvailableMoveTiles(p2.getWorkers().get(1)).size()==0);
        assert(match.getAvailableBuildTiles(p2.getWorkers().get(0)).size()==0);
        assert(match.getAvailableBuildTiles(p2.getWorkers().get(1)).size()==0);

        assert(match.checkLoser());
        assert(match.getCurrentPlayer().getUsername().equals("franco"));
        assert(match.getPlayers().size()==1);
        assert(match.findWinner().equals(p3));
    }


}