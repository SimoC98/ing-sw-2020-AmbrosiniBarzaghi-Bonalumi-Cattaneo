package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.InvalidWorkerSelectionException;
import it.polimi.ingsw.model.exceptions.WorkerBadPlacementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WinByDropTwoLevelTest {

    private static Game game;
    private static Match match;
    private static Board board;
    private static WinByDropTwoLevel div;

    @BeforeEach
    void setUp() throws WorkerBadPlacementException, InvalidWorkerSelectionException {
        List<String> players = new ArrayList<>();
        players.add("paolo");
        players.add("francesco");
        match = new Match(players);
        game = new Game(match);
        board = match.getBoard();
        div = new WinByDropTwoLevel(new StandardDivinity());
        match.getPlayers().get(0).setDivinity(div);
        match.getCurrentPlayer().startOfTurn();
    }

    @Test
    public void dropFrom2To0(){
        Tile t1 = board.getTile(1,1);
        Tile t2 = board.getTile(2,2);
        Player p1 = match.getCurrentPlayer();

        t1.increaseLevel();
        t1.increaseLevel();
        p1.addWorker(t1);
        Worker w = p1.getWorkers().get(0);

        assert(p1.move(w,t2));
        assertTrue(p1.isWinner());
    }

    @Test
    public void dropFrom3To0(){
        Tile t1 = board.getTile(1,1);
        Tile t2 = board.getTile(2,2);
        Player p1 = match.getCurrentPlayer();

        t1.increaseLevel();
        t1.increaseLevel();
        t1.increaseLevel();
        p1.addWorker(t1);
        Worker w = p1.getWorkers().get(0);

        assert(p1.move(w,t2));
        assertTrue(p1.isWinner());
    }
}