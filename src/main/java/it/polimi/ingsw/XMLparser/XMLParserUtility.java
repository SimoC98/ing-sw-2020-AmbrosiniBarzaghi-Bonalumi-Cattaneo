package it.polimi.ingsw.XMLparser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import it.polimi.ingsw.model.Divinity;
import it.polimi.ingsw.model.DivinityDecoratorWithEffects;
import it.polimi.ingsw.model.cards.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.xml.sax.*;

public class XMLParserUtility {

    //given a StandardDivinity and the String containing the path to the DivinityDecoratorWithEffects's son class, instantiates a Divinity
    private static DivinityDecoratorWithEffects parseDivinity(String divClassPath) {
        /*
            code suggested in:
            https://stackoverflow.com/questions/6094575/creating-an-instance-using-the-class-name-and-calling-constructor/6094602
         */
        Class<?> c = null;
        try {
            c = Class.forName(divClassPath);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Constructor<?> cons = null;
        try {
            cons = c.getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        Object obj = null;
        try {
            obj = cons.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return (DivinityDecoratorWithEffects) obj;
    }

    //opens resources/divinities.xml and parses the divinities
    private static List<Divinity> XMLParse() {
        File xmlFile = new File("resources/divinities.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = builder.parse(xmlFile);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        NodeList divinities = doc.getDocumentElement().getElementsByTagName("divinity");
        List<Divinity> divinitiesList = new ArrayList<>();

        StandardDivinity stdDiv;
        String name=null, heading=null, description=null, divClassStr=null;
        int number = 0;
        for(int i=0; i < divinities.getLength(); i++) {
            Node divNode = divinities.item(i);
            Element divElem = (Element) divNode;

            Node divClassNode = divElem.getElementsByTagName("divClassPath").item(0);
            divClassStr = divClassNode.getTextContent();

            Node stdDivNode = divElem.getElementsByTagName("stdDivProperties").item(0);
            Element stdDivElem = (Element) stdDivNode;

            Node stdDivPropNode = stdDivElem.getElementsByTagName("name").item(0);
            name = stdDivPropNode.getTextContent();
            stdDivPropNode = stdDivElem.getElementsByTagName("number").item(0);
            number = Integer.parseInt(stdDivPropNode.getTextContent());
            stdDivPropNode = stdDivElem.getElementsByTagName("heading").item(0);
            heading = stdDivPropNode.getTextContent();
            stdDivPropNode = stdDivElem.getElementsByTagName("description").item(0);
            description = stdDivPropNode.getTextContent();

            stdDiv = new StandardDivinity(name, heading, description, number);

            DivinityDecoratorWithEffects parsedDiv = parseDivinity(divClassStr);
            parsedDiv.setDivinity(stdDiv);
            divinitiesList.add(parsedDiv);
        }

        return divinitiesList;
    }

    //this method is meant for giving a meaningful name to the method user will call, and leave an explicative one to XMLParse()
    public static List<Divinity> getDivinityList() {
        return XMLParse();
    }



    public static List<Divinity> getDivinitiesSimple(){
        File xmlFile = new File("resources/divinities2.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = builder.parse(xmlFile);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        doc.getDocumentElement().normalize();

        NodeList divinities = doc.getElementsByTagName("divinity");
        List<Divinity> divinitiesList = new ArrayList<>();

        StandardDivinity stdDiv;
        String name=null, heading=null, description=null, effect=null;
        int number = 0;
        for(int i=0; i < divinities.getLength(); i++) {
            Node divNode = divinities.item(i);
            Element elem = (Element) divNode;

            Node node = elem.getElementsByTagName("name").item(0);
            name = node.getTextContent();

            node = elem.getElementsByTagName("number").item(0);
            number = Integer.parseInt(node.getTextContent());

            node = elem.getElementsByTagName("heading").item(0);
            heading = node.getTextContent();

            node = elem.getElementsByTagName("description").item(0);
            description = node.getTextContent();

            node = elem.getElementsByTagName("effect").item(0);
            effect = node.getTextContent();

            stdDiv = new StandardDivinity(name, heading, description, number);
            Divinity parsedDiv = divinitySwitchCase(effect, stdDiv);
            if (parsedDiv != null)
                divinitiesList.add(parsedDiv);
        }

        return divinitiesList;
    }

    //for now xml not changed, as effect description I use the class-path
    private static Divinity divinitySwitchCase(String effectName, StandardDivinity stdDiv){
        switch (effectName){
            case "CanSwapWorkers":
                return new SwapWithOpponent(stdDiv);

            case "CanMoveTwiceNotBack":
                return new MoveTwiceNotBack(stdDiv);

            case "OpponentCantGoUp":
                return new SetEffectOnOpponent(stdDiv);

            case "CanBuildDomeEverywhere":
                return new BuildDomeEverywhere(stdDiv);

            case "CanBuildTwiceNotSame":
                return new BuildTwiceNotSameTile(stdDiv);

            case "CanBuildTwiceSame":
                return new BuildTwiceSameTile(stdDiv);

            case "CanPushWorker":
                return new PushOpponent(stdDiv);

            case "CanWinByGettingDown":
                return new WinByDropTwoLevel(stdDiv);

            case "CanBuildBeforeMove":
                return new BuildBeforeAndAfter(stdDiv);

            case "none":
                return new DivinityDecoratorWithEffects(stdDiv);

            default:
                return null;
        }
    }

}
