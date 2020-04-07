package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SetEffectOnOpponentTest {
    private static Game game;
    private static Match match;
    private static SetEffectOnOpponent div1;
    private static StandardDivinity div2;

    @BeforeEach
    void setUp() {
        List<String> users = new ArrayList<String>();
        List<Color> colors = new ArrayList<Color>();
        users.add("simone");
        users.add("marco");
        colors.add(Color.BLUE);
        colors.add(Color.WHITE);
        match = new Match(new Board(),users,colors);
        game = new Game(match);
        div1 = new SetEffectOnOpponent(new StandardDivinity());
        div1.setupDivinity(new ArrayList<Phase>());
        match.getPlayers().get(0).setDivinity(div1);
        div2 = new StandardDivinity();
        match.getPlayers().get(1).setDivinity(div2);
    }

    @Test
    public void test() {
        Worker w1 = new Worker(match.getBoard().getTile(0,0), match.getPlayers().get(0));
        Worker w2 = new Worker(match.getBoard().getTile(4,4),match.getPlayers().get(1));

        div1.setupDivinity(new ArrayList<>());
        assertFalse(div1.isHasMovedUp());


        match.getBoard().getTile(0,1).increaseLevel();
        match.getBoard().getTile(4,3).increaseLevel();
        div1.move(w1,match.getBoard().getTile(0,1));


        assertFalse(match.getPlayers().get(1).getPlayerDivinity().legalMove(w2,match.getBoard().getTile(4,3)));
        assert(match.getPlayers().get(1).getPlayerDivinity().legalMove(w2,match.getBoard().getTile(3,4)));

        match.getBoard().getTile(3,4).increaseLevel();

        assert( match.getBoard().getTile(3,4).getLevel()==1);
        assertFalse(match.getPlayers().get(1).getPlayerDivinity().legalMove(w2,match.getBoard().getTile(3,4)));
        


        div1.setupDivinity(new ArrayList<>());
        assertTrue(match.getPlayers().get(1).getPlayerDivinity().legalMove(w2,match.getBoard().getTile(3,4)));      //dopo setup può muoversi su liv più alto

    }
}