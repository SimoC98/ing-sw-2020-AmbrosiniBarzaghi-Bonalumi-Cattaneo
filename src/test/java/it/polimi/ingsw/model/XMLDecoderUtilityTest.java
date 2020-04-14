package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.Divinity;
import it.polimi.ingsw.model.cards.StandardDivinity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class XMLDecoderUtilityTest {

    @Test
    public void apolloTest(){
        String name = "Apollo";
        Divinity div = XMLDecoderUtility.loadDivinity(name);
        StandardDivinity stdDiv = (StandardDivinity) div.getDivinity();

        assertEquals(stdDiv.getName(), name);
    }

}