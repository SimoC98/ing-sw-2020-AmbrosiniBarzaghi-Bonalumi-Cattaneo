package it.polimi.ingsw.XMLparser;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.StandardDivinity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class XMLParserUtilityTest {

    List<Divinity> divinities;

    @BeforeEach
    void setup() {
        divinities = XMLParserUtility.getDivinityList();
//        divinities = XMLParserUtility.getDivinitiesSimple();
    }

    @Test
    public void parsingTest(){
        StandardDivinity stdDiv = (StandardDivinity) divinities.get(1).getDivinity();
        assertEquals(1, stdDiv.getNumber());
        assertEquals("God of Music", stdDiv.getHeading());

        stdDiv = (StandardDivinity) divinities.get(4).getDivinity();
        assertEquals("Atlas", stdDiv.getName());
        assertEquals(4, stdDiv.getNumber());

        stdDiv = (StandardDivinity) divinities.get(7).getDivinity();    //there's no 6!!
        assertEquals(8, stdDiv.getNumber());
    }

    @Test
    public void apolloMoveTest(){
        List<String> pList = new ArrayList<>();
        pList.add("Marco");
        pList.add("Gino");
        Match match = new Match(pList);
        Game game = new Game(match);
        Board board = match.getBoard();

        Player p = match.getPlayers().get(0);
        Player p2 = match.getPlayers().get(1);
        Tile t1 = board.getTile(1, 2);
        Tile t2 = board.getTile(2, 2);

        //THE JUICE
        DivinityDecoratorWithEffects div = (DivinityDecoratorWithEffects) divinities.get(1);
        p.setDivinity(div);

        p.addWorker(t1);
        Worker w1 = p.getWorkers().get(0);
        Worker w2 = new Worker(t2, p2);

        assertTrue(div.legalMove(w1, t2));

        p.move(w1, t2);

        assertEquals(w1.getPositionOnBoard(), t2);
        assertEquals(w2.getPositionOnBoard(), t1);
    }

}