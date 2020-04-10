package it.polimi.ingsw;

import it.polimi.ingsw.model.cards.Divinity;
import it.polimi.ingsw.model.cards.DivinityDecoratorWithEffects;
import it.polimi.ingsw.model.cards.StandardDivinity;
import it.polimi.ingsw.model.cards.SwapWithOpponent;

import java.beans.XMLDecoder;
import java.io.File;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
//        StandardDivinity divinity = new StandardDivinity("Artemis", "Goddess of the Hunt", "Your Move: " +
//                "Your Worker may move one additional time, but not back to its initial space.", 3);
//        DivinityDecoratorWithEffects decDiv = new SwapWithOpponent(divinity);
//
//        try{
//            FileOutputStream fos = new FileOutputStream(new File("src/main/java/it/polimi/ingsw/divinitiesxml/Artemis.xml"));
//            XMLEncoder encoder = new XMLEncoder(fos);
//            encoder.writeObject(decDiv);
//            encoder.close();
//            fos.close();
//        }
//        catch(IOException ex){
//            ex.printStackTrace();
//        }


        String divinityName = "Artemis";

        try{
            FileInputStream fis = new FileInputStream(new File("src/main/java/it/polimi/ingsw/divinitiesxml/" + divinityName + ".xml"));
//            FileInputStream fis = new FileInputStream(new File("src/main/java/it/polimi/ingsw/divinitiesxml/Apollo.xml"));
            XMLDecoder decoder = new XMLDecoder(fis);

            Divinity div = (DivinityDecoratorWithEffects) decoder.readObject();
            StandardDivinity stddiv = (StandardDivinity) div.getDivinity();
            decoder.close();
            fis.close();

            System.out.println(stddiv.getName());
            System.out.println(stddiv.getDescription());
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
