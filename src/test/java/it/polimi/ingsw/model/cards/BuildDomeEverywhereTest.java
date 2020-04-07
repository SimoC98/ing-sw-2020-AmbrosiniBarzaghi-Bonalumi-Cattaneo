package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildDomeEverywhereTest {
    private static Match match;
    private static BuildDomeEverywhere div;

    @BeforeEach
    void setUp() {
        match = new Match();
        Game game = new Game(match);
        div = new BuildDomeEverywhere(new StandardDivinity());
    }


    @Test
    public void domeTest() {
        match.setUserAction(Action.BUILDDOME);

        Tile t1 = new Tile(1,1);
        Tile t2 = new Tile(1,2);
        Worker w1 = new Worker(t1, new Player("jack",Color.BLUE));

        div.build(w1,t2);

        assert(t2.isDome());
        assertFalse(t2.getLevel()==3);
    }

    @Test
    public void standardBuild() {
        match.setUserAction(Action.BUILD);

        Tile t1 = new Tile(1,1);
        Tile t2 = new Tile(1,2);
        Worker w1 = new Worker(t1, new Player("jack",Color.BLUE));

        div.build(w1,t2);

        assertFalse(t2.isDome());
    }
}