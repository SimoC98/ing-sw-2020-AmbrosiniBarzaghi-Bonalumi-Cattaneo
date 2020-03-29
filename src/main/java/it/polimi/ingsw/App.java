package it.polimi.ingsw;

import it.polimi.ingsw.model.cards.Divinity;
import it.polimi.ingsw.model.cards.StandardDivinity;
import it.polimi.ingsw.model.cards.SwapWithOpponent;

import java.io.File;
import java.beans.XMLEncoder;
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
        Divinity d = new StandardDivinity("Apollo", "può swappare with opponent");
        Divinity apollo = new SwapWithOpponent(d);

        try{
            FileOutputStream fos = new FileOutputStream(new File("./apolloStd.xml"));
            XMLEncoder encoder = new XMLEncoder(fos);
            encoder.writeObject(d);
            encoder.close();
            fos.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }

    }
}
