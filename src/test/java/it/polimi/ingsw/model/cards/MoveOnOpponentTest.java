package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.InvalidWorkerSelectionException;
import it.polimi.ingsw.model.exceptions.WorkerBadPlacementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoveOnOpponentTest {

    private Match match;
    private Board board;
    private MoveOnOpponent div;

    @BeforeEach
    void setup() throws WorkerBadPlacementException, InvalidWorkerSelectionException {
        List<String> players = new ArrayList<>();
        players.add("Jackie");
        players.add("Rose");
        match = new Match(players);
        board = match.getBoard();
        div = new MoveOnOpponent(new StandardDivinity());
        match.getPlayers().get(0).setDivinity(div);
        match.placeWorkers(3,3,4,4);
        match.selectWorker(3,3);
        match.getPlayers().get(1).addWorker(board.getTile(2,2));
    }

    @Test
    public void moveOnMyOtherWorker(){

        Tile t = board.getTile(4,4);
        Worker worker = match.getSelectedWorker();

        assertFalse(div.legalMove(board,worker,t));
    }

    @Test
    public void moveOnOpponent() {

        Tile t = board.getTile(2,2);
        Worker worker = match.getSelectedWorker();

        assertTrue(div.legalMove(board,worker,t));
    }

}