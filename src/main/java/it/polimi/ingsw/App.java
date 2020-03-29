package it.polimi.ingsw;

import it.polimi.ingsw.model.Person;
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
        StandardDivinity divinity = new StandardDivinity("Apollo", "può swappare with opponent");
        DivinityDecoratorWithEffects apollo = new SwapWithOpponent(divinity);
        Person person = new Person("Marco", "Bonalumi", 21);

        try{
            FileOutputStream fos = new FileOutputStream(new File("src/main/java/it/polimi/ingsw/divinitiesxml/apollo.xml"));
//            FileOutputStream fos = new FileOutputStream(new File("src/main/java/it/polimi/ingsw/divinitiesxml/person.xml"));
            XMLEncoder encoder = new XMLEncoder(fos);
            encoder.writeObject(apollo);
            encoder.close();
            fos.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }

        try{
            FileInputStream fis = new FileInputStream(new File("src/main/java/it/polimi/ingsw/divinitiesxml/apollo.xml"));
//            FileInputStream fis = new FileInputStream(new File("src/main/java/it/polimi/ingsw/divinitiesxml/person.xml"));
            XMLDecoder decoder = new XMLDecoder(fis);

//            Person person2 = (Person) decoder.readObject();
            Divinity div = (DivinityDecoratorWithEffects) decoder.readObject();
            decoder.close();
            fis.close();
//            System.out.println(div.getName());
//            System.out.println(div.getDescription());
//            System.out.println(person2.getFirstName());
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
