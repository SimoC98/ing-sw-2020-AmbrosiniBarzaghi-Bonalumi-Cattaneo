package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BuildBeforeAndAfterTest {
    private static Tile tile1;
    private static Tile tile2;
    private static Tile tile3;
    private static BuildBeforeAndAfter div;
    private static Worker worker;
    private static Player p;

    @BeforeAll
    static void setup(){
        tile1 = new Tile(1,1);
        tile2 = new Tile(2,2);
        tile3 = new Tile(1,2);
        div = new BuildBeforeAndAfter(new StandardDivinity());
    }

    @Test
    public void setupTest() {
        p = new Player("jack", Color.BLUE);
        p.setDivinity(div);
        p.startOfTurn();

        Set actions = p.getPossibleActions();

        assert(actions.size()==2);
        assert(actions.contains(Action.MOVE) && actions.contains(Action.BUILD));
    }

    public void buildBeforeMoveTest() {
        p = new Player("jack", Color.BLUE);
        p.setDivinity(div);
        p.startOfTurn();
        p.addWorker(tile1);
        worker = p.getWorkers().get(0);

        p.build(worker,tile2);

        assert(p.getPossibleActions().size()==1 && p.getPossibleActions().contains(Action.MOVE));

        assertFalse(p.move(worker,tile2));
        assertFalse(div.legalMove(worker,tile2));

        boolean res = p.move(worker,tile3);

        assert(res);
        assert((p.getPossibleActions().size()==1) && p.getPossibleActions().contains(Action.BUILD));

        p.build(worker,tile1);
        assert(p.getPossibleActions().size()==0);
    }

    @Test
    public void CannotBuildIfCannotMoveTest() {
        List<String> l = new ArrayList<>();
        l.add("simone");
        l.add("marco");
        Match match = new Match(l);
        Game g = new Game(match);
        Board b = match.getBoard();

        Player p = match.getPlayers().get(0);
        p.setDivinity(div);
        p.startOfTurn();
        p.addWorker(b.getTile(0,0));

        assert(p.getWorkers().get(0).getPositionOnBoard().getX()==0 && p.getWorkers().get(0).getPositionOnBoard().getY()==0);

        b.getTile(0,1).setDome();
        b.getTile(1,0).setDome();
        assertFalse(div.legalBuild(p.getWorkers().get(0),b.getTile(1,1)));
        assert(div.legalMove(p.getWorkers().get(0),b.getTile(1,1)));

        p.move(p.getWorkers().get(0),b.getTile(1,1));
        p.move(p.getWorkers().get(0),b.getTile(0,0));

        assert(div.legalBuild(p.getWorkers().get(0),b.getTile(1,1)));

    }


}