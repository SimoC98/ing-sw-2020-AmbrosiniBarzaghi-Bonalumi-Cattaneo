package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WinByDropTwoLevelTest {

    @Test
    public void dropFrom2To0(){
        Tile t1 = new Tile(2,2);
        Tile t2 = new Tile(3,2);
        t1.increaseLevel();
        t1.increaseLevel();

        Worker w = new Worker(t1, new Player("Marconi", Color.WHITE));

        Divinity sd = new StandardDivinity();
        WinByDropTwoLevel d = new WinByDropTwoLevel(sd);

        d.move(w,t2);

        assertTrue(w.getPlayer().isWinner());
    }

    @Test
    public void dropFrom3To0(){
        Tile t1 = new Tile(2,2);
        Tile t2 = new Tile(3,2);
        t1.increaseLevel();
        t1.increaseLevel();
        t1.increaseLevel();

        Worker w = new Worker(t1, new Player("Marconi", Color.WHITE));

        Divinity sd = new StandardDivinity();
        WinByDropTwoLevel d = new WinByDropTwoLevel(sd);

        d.move(w,t2);

        assertTrue(w.getPlayer().isWinner());
    }
}