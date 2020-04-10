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
        String name, description, heading;

        int number = 3;
        name = "";
        heading = "";
        description = "";

        StandardDivinity stdDiv = new StandardDivinity(name, heading, description, number);
        DivinityDecoratorWithEffects decDiv = new SwapWithOpponent(stdDiv);

//        encoder(decDiv, name);
        decoder(name);
    }

    public static void encoder(DivinityDecoratorWithEffects divinity, String name){
        System.out.println("ENCODING...\n");
        System.out.println("\t\t\t" + name + ".xml");
        try{
            FileOutputStream fos = new FileOutputStream(new File("src/main/java/it/polimi/ingsw/divinitiesxml/" + name + ".xml"));
            XMLEncoder encoder = new XMLEncoder(fos);
            encoder.writeObject(divinity);
            encoder.close();
            fos.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }

        System.out.println("\nDONE.\n");
    }

    public static void decoder(String divinityName){
        System.out.println("DECODING...\n");

        try{
            FileInputStream fis = new FileInputStream(new File("src/main/java/it/polimi/ingsw/divinitiesxml/" + divinityName + ".xml"));
            XMLDecoder decoder = new XMLDecoder(fis);

            Divinity div = (DivinityDecoratorWithEffects) decoder.readObject();
            StandardDivinity stddiv = (StandardDivinity) div.getDivinity();
            decoder.close();
            fis.close();

            //tests printing name and description
            System.out.println("NAME:\t" + stddiv.getName());
            System.out.println("DESCRIPTION:\t" + stddiv.getDescription());
            System.out.println("HEADING:\t" + stddiv.getHeading());
            System.out.println("NUMBER:\t" + stddiv.getNumber());
        }
        catch(IOException ex){
            ex.printStackTrace();
        }

        System.out.println("\nDONE.\n");
    }
}
