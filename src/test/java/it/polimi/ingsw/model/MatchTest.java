package it.polimi.ingsw.model;

import it.polimi.ingsw.Pair;
import it.polimi.ingsw.XMLparser.XMLParserUtility;
import it.polimi.ingsw.model.cards.MoveTwiceNotBack;
import it.polimi.ingsw.model.cards.OpponentCannotMoveUp;
import it.polimi.ingsw.model.cards.StandardDivinity;
import it.polimi.ingsw.model.cards.SwapWithOpponent;
import it.polimi.ingsw.model.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MatchTest {
    private Board board;
    private Match match;
    private Player p1;
    private Player p2;
    private Player p3;

    @BeforeEach
    void setUp() {
        List<String> players = new ArrayList<>();
        players.add("paolo");
        players.add("giacomo");
        players.add("franco");
        match = new Match(players);
        match.setStartPlayer("paolo");
        board = match.getBoard();
        p1 = match.getPlayers().get(0);
        p2 = match.getPlayers().get(1);
        p3 = match.getPlayers().get(2);
    }

    @Test
    public void matchInitializationTest() {
        assert(match.getPlayersUsernames().size()==3);
        assert(match.getPlayersUsernames().contains("paolo"));
        assert(match.getPlayersUsernames().contains("giacomo"));
        assert(match.getPlayersUsernames().contains("franco"));

        assert(match.getPlayersColors().size()==3);

    }

    @Test
    public void startNewTurnTest() {
        p1.setDivinity(new StandardDivinity());
        p2.setDivinity(new StandardDivinity());
        p3.setDivinity(new StandardDivinity());

        Color[] colors = Color.values();

        assert(match.getCurrentPlayer().equals(match.getPlayers().get(0)));

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


        match.placeWorkers(0,0,4,0);

        assert(board.getTile(0,0).isOccupied());
        assert(board.getTile(4,0).isOccupied());

        board.getTile(0,1).setDome();
        board.getTile(1,0).setDome();
        board.getTile(1,1).setDome();

        board.getTile(3,0).setDome();
        board.getTile(3,1).setDome();
        board.getTile(4,1).setDome();

        assert(board.getAvailableMoveTiles(p1.getWorkers().get(0)).size()==0);
        assert(board.getAvailableMoveTiles(p1.getWorkers().get(1)).size()==0);
        assert(board.getAvailableBuildTiles(p1.getWorkers().get(0)).size()==0);
        assert(board.getAvailableBuildTiles(p1.getWorkers().get(1)).size()==0);

        assert(match.checkLoser());
        assert(match.getCurrentPlayer().getUsername().equals("giacomo"));
        assert(match.getPlayers().size()==2);
        assertFalse(board.getTile(0,0).isOccupied());
        assertFalse(board.getTile(4,0).isOccupied());

        match.placeWorkers(0,0,4,0);

        assert(board.getAvailableMoveTiles(p2.getWorkers().get(0)).size()==0);
        assert(board.getAvailableMoveTiles(p2.getWorkers().get(1)).size()==0);
        assert(board.getAvailableBuildTiles(p2.getWorkers().get(0)).size()==0);
        assert(board.getAvailableBuildTiles(p2.getWorkers().get(1)).size()==0);

        assert(match.checkLoser());
        assert(match.getCurrentPlayer().getUsername().equals("franco"));
        assert(match.getPlayers().size()==1);
        assert(match.findWinner().equals(p3));
    }

    @Test
    public void placeWorkersTest() {
        p1.setDivinity(new StandardDivinity());
        p2.setDivinity(new StandardDivinity());
        p3.setDivinity(new StandardDivinity());

        assertThrows(WorkerBadPlacementException.class,()-> match.placeWorkers(0,0,0,0));
        try {
            match.placeWorkers(0,0,4,0);
        } catch (WorkerBadPlacementException e) {System.out.println("Error in placeWorkersTest");}
        assertEquals(p1.getWorkers().get(0).getPositionOnBoard(), board.getTile(0, 0));
        assertEquals(p1.getWorkers().get(1).getPositionOnBoard(), board.getTile(4, 0));

        match.startNextTurn();
        assertThrows(WorkerBadPlacementException.class,()-> match.placeWorkers(0,0,3,0));
        try {
            match.placeWorkers(2,0,3,0);
        } catch (WorkerBadPlacementException e) {System.out.println("Error in placeWorkersTest");}
        assertEquals(p2.getWorkers().get(0).getPositionOnBoard(), board.getTile(2, 0));
        assertEquals(p2.getWorkers().get(1).getPositionOnBoard(), board.getTile(3, 0));
    }

    @Test
    public void selectWorkerTest() throws WorkerBadPlacementException {
        p1.setDivinity(new StandardDivinity());
        p2.setDivinity(new StandardDivinity());
        p3.setDivinity(new StandardDivinity());
        match.placeWorkers(0,0,4,0);
        match.startNextTurn();
        match.placeWorkers(0,4,4,4);
        match.startNextTurn();
        match.startNextTurn();

        assertThrows(InvalidWorkerSelectionException.class,()->match.selectWorker(1,1));
        assertThrows(InvalidWorkerSelectionException.class,()->match.selectWorker(4,4));

        board.getTile(0,1).setDome();
        board.getTile(1,0).setDome();
        board.getTile(1,1).setDome();

        assertThrows(InvalidWorkerSelectionException.class,()->match.selectWorker(0,0));

        try {
            match.selectWorker(4,0);
        } catch (InvalidWorkerSelectionException e) { System.out.println("Error in selectWorkerTest");}

        assertEquals(match.getSelectedWorker(),p1.getWorkers().get(1));
    }

    @Test
    public void setActionTest() {
        p1.setDivinity(new StandardDivinity());
        p2.setDivinity(new StandardDivinity());
        p3.setDivinity(new StandardDivinity());
        match.startNextTurn();

        assertThrows(InvalidActionException.class,()-> match.setAction(Action.BUILD));
        try {
            match.setAction(Action.MOVE);
        } catch (InvalidActionException e) {System.out.println("Error in setActionTest");}

        assert(match.getUserAction().equals(Action.MOVE));
    }

    @Test
    public void moveTest() throws WorkerBadPlacementException, InvalidWorkerSelectionException {
        p1.setDivinity(new StandardDivinity());
        p2.setDivinity(new StandardDivinity());
        p3.setDivinity(new StandardDivinity());

        match.startNextTurn();
        match.placeWorkers(0,0,4,0);
        match.startNextTurn();
        match.placeWorkers(0,1,4,4);
        match.startNextTurn();
        match.startNextTurn();
        match.selectWorker(0,0);

        board.getTile(1,1).setDome();
        board.getTile(1,0).increaseLevel();
        board.getTile(1,0).increaseLevel();

        assertThrows(InvalidMoveException.class,()->match.move(0,1));
        assertThrows(InvalidMoveException.class,()->match.move(1,1));
        assertThrows(InvalidMoveException.class,()->match.move(5,1));
        assertThrows(InvalidMoveException.class,()->match.move(0,0));
        assertThrows(InvalidMoveException.class,()->match.move(1,0));
    }

    @Test
    public void buildTest() throws WorkerBadPlacementException, InvalidWorkerSelectionException {
        p1.setDivinity(new StandardDivinity());
        p2.setDivinity(new StandardDivinity());
        p3.setDivinity(new StandardDivinity());

        match.startNextTurn();
        match.placeWorkers(0,0,4,0);
        match.startNextTurn();
        match.placeWorkers(0,1,4,4);
        match.startNextTurn();
        match.startNextTurn();
        match.selectWorker(0,0);

        board.getTile(1,1).setDome();
        board.getTile(1,0).increaseLevel();
        board.getTile(1,0).increaseLevel();

        assertThrows(InvalidBuildException.class,()->match.build(0,1));
        assertThrows(InvalidBuildException.class,()->match.build(1,1));
        assertThrows(InvalidBuildException.class,()->match.build(5,1));
        assertThrows(InvalidBuildException.class,()->match.build(0,0));
    }

    @Test
    public void divinityMapInitializationTest() {
        match.setDivinityMap(XMLParserUtility.getDivinities());

        assert(match.getAllDivinities().size()==15);
        assert(match.getAllDivinitiesDescriptions().size()==15);
    }


    @Test
    public void getPossibleActionTest() throws WorkerBadPlacementException, InvalidWorkerSelectionException {
        p1.setDivinity(new StandardDivinity());
        p2.setDivinity(new StandardDivinity());
        p3.setDivinity(new StandardDivinity());

        match.workerPlacementInitialization(0,0,1,1);
        match.workerPlacementInitialization(2,3,4,4);
        match.workerPlacementInitialization(4,0,4,1);

        assertEquals(match.getCurrentPlayer().getUsername(),"paolo");
        match.selectWorker(0,0);

        Map<Action,List<Pair<Integer,Integer>>> possibleActions = match.getPossibleActions();

        assert(possibleActions.size()==1);
        assert(possibleActions.keySet().contains(Action.MOVE));

        List<Pair<Integer,Integer>> availableTiles = possibleActions.get(Action.MOVE);

        assert(availableTiles.size()==2);
        assert(availableTiles.get(0).getFirst()==0 && availableTiles.get(0).getSecond()==1);
        assert(availableTiles.get(1).getFirst()==1 && availableTiles.get(1).getSecond()==0);
    }

    @Test
    public void divinityInitializationTest() throws InvalidDivinitySelectionEvent {
        match.setDivinityMap(XMLParserUtility.getDivinities());

        match.divinityInitialization("Apollo");

        assertEquals(match.getCurrentPlayer().getUsername(),"giacomo");

        match.divinityInitialization("Minotaur");

        assertEquals(match.getCurrentPlayer().getUsername(),"franco");

        match.divinityInitialization("Pan");

        assertEquals(match.getCurrentPlayer().getUsername(),"paolo");


        assertEquals(p1.getDivinity().getName(),"Apollo");
        assertEquals(p2.getDivinity().getName(),"Minotaur");
        assertEquals(p3.getDivinity().getName(),"Pan");

        assert(match.getPlayersDivinities().contains(p1.getDivinity().getName()));
        assert(match.getPlayersDivinities().contains(p2.getDivinity().getName()));
        assert(match.getPlayersDivinities().contains(p3.getDivinity().getName()));


    }

}