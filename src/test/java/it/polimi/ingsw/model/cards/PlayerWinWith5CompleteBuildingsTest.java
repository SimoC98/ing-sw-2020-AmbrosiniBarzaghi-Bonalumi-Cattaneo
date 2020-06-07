package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.InvalidWorkerSelectionException;
import it.polimi.ingsw.model.exceptions.WorkerBadPlacementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerWinWith5CompleteBuildingsTest {

    private Match match;
    private Board board;
    private PlayerWinWith5CompleteBuildings div;

    @BeforeEach
    void setup() throws WorkerBadPlacementException, InvalidWorkerSelectionException {
        List<String> players = new ArrayList<>();
        players.add("paolo");
        players.add("francesco");
        match = new Match(players);
        match.setStartPlayer("paolo");
        board = match.getBoard();
        div = new PlayerWinWith5CompleteBuildings(new StandardDivinity());
        match.getPlayers().get(0).setDivinity(div);
        match.placeWorkers(1,1,4,4);
        match.selectWorker(1,1);
        match.getPlayers().get(1).setDivinity(new StandardDivinity());
        match.getPlayers().get(1).addWorker(board.getTile(2,2));
        match.getPlayers().get(1).addWorker(board.getTile(3,3));

        match.getCurrentPlayer().startOfTurn();
    }

    @Test
    public void test() {
        Player p1 = match.getPlayers().get(1);
        Player p2 = match.getPlayers().get(0);

        Worker w = p1.getWorkers().get(0);
        Tile t = board.getTile(2,3);

        for(int i=0;i<4;i++) {
           for(int j=0;j<4;j++) {
               board.getTile(i,0).increaseLevel();
           }
        }

        for(int i=0;i<4;i++) {
            assert(board.getTile(i,0).isDome());
        }

        p2.move(board,match.getSelectedWorker(),board.getTile(1,2));

        match.startNextTurn();

        assertEquals(match.getCurrentPlayer().getUsername(),"francesco");

        assert(p1.build(board,w,t));
        assertFalse(p2.isWinner());

        t.increaseLevel();
        t.increaseLevel();
        

        assert(p1.build(board,w,t));
        assert(p2.isWinner());

    }

}