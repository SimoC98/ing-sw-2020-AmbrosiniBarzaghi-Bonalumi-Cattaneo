package it.polimi.ingsw;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Divinity;
import it.polimi.ingsw.model.DivinityDecoratorWithEffects;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.exceptions.WorkerBadPlacementException;
import it.polimi.ingsw.view.View;


import java.beans.XMLDecoder;
import java.io.File;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{


    public static void main(String[] args) {
        List<String> players = new ArrayList<>();
        players.add("simone");
        players.add("riccardo");

        Match match = new Match(players);
        Game game = new Game(match);
        View view = new View();
        Controller controller = new Controller(match,view);

        view.addObserver(controller);

        try {
            match.playerInitialization(1,1,2,2,"Pan");
            match.playerInitialization(3,3,4,4,"Demeter");
        } catch (WorkerBadPlacementException e) {
            System.out.println("error");
        }
        view.startTurn("simone");

    }

 /*   public static void main( String[] args )
    {
        String name, description, heading;

        int number = 0; //MODIFY!
        name = "Human";   //MODIFY!
        heading = "Normal Human";    //MODIFY!
        description = "Has no additional power or side-effect";   //MODIFY!

        StandardDivinity stdDiv = new StandardDivinity(name, heading, description, number);
        DivinityDecoratorWithEffects decDiv = new BuildBeforeAndAfter(stdDiv); //MODIFY!

        encoder(decDiv, name);
//        encoder(stdDiv, name);
        decoder(name);
    }

    public static void encoder(Divinity divinity, String name){
        System.out.println("ENCODING...");
        System.out.println("FILE:\t\"" + name + ".xml\"");

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
//            StandardDivinity stddiv = (StandardDivinity) decoder.readObject();
            decoder.close();
            fis.close();

            //tests printing name and description
            System.out.println("NAME:\t_" + stddiv.getName());
            System.out.println("DESCRIPTION:\t_" + stddiv.getDescription());
            System.out.println("HEADING:\t_" + stddiv.getHeading());
            System.out.println("NUMBER:\t_" + stddiv.getNumber());
        }
        catch(IOException ex){
            ex.printStackTrace();
        }

        System.out.println("\nDONE.\n");
    }*/
}
