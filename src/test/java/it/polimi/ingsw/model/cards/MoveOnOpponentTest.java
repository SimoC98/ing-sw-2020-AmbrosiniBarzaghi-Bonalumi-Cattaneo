package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveOnOpponentTest {

    @Test
    public void moveOnMyOtherWorker(){
        Player p = new Player("Giova", Color.CREAM);

        Tile t1 = new Tile(2,2);
        Tile t2 = new Tile(3,2);

        Worker w1 = new Worker(t1, p);
        Worker w2 = new Worker(t2, p);

        StandardDivinity sd = new StandardDivinity();
        MoveOnOpponent d = new MoveOnOpponent(sd);

        assertFalse(d.legalMove(w1,t2));
    }

    @Test
    public void moveOnOpponent() {
        Player p1 = new Player("Giova", Color.CREAM);
        Player p2 = new Player("Franco", Color.BLUE);

        Tile t1 = new Tile(2,2);
        Tile t2 = new Tile(3,2);

        Worker w1 = new Worker(t1, p1);
        Worker w2 = new Worker(t2, p2);

        Divinity sd = new StandardDivinity();
        MoveOnOpponent d = new MoveOnOpponent(sd);

        assertTrue(d.legalMove(w1,t2));
    }

}