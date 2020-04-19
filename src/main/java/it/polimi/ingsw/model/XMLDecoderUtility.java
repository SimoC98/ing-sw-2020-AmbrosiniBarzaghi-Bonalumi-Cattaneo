package it.polimi.ingsw.model;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class XMLDecoderUtility {

    public static Divinity loadDivinity(String name){
        try {
            FileInputStream fis = new FileInputStream(new File("src/main/java/it/polimi/ingsw/divinitiesxml/" + name + ".xml"));
            XMLDecoder decoder = new XMLDecoder(fis);

            Divinity divinity = (DivinityDecoratorWithEffects) decoder.readObject();

            decoder.close();
            fis.close();

            return divinity;
            //normal behaviour
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
