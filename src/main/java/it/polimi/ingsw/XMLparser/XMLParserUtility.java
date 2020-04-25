package it.polimi.ingsw.XMLparser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import it.polimi.ingsw.model.Divinity;
import it.polimi.ingsw.model.DivinityDecoratorWithEffects;
import it.polimi.ingsw.model.cards.StandardDivinity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

//TODO: use DOM
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
    private static Map<String, Divinity> XMLParse() {
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

        Element root = doc.getDocumentElement();
        NodeList divinities = root.getElementsByTagName("divinity");
        Map<String, Divinity> divinitiesList = new HashMap<>();

        StandardDivinity stdDiv;
        String name=null, heading=null, description=null, divClassStr;
        int number = 0;
        //TODO: guarda questi for di singola riga annidati e vergognati, sistemare!!
        for(int i=0; i < divinities.getLength(); i++) {
            int j;

            NodeList divNodeChildren = divinities.item(i).getChildNodes();
            for(j=0; divNodeChildren.item(j).getNodeType() != Node.ELEMENT_NODE; j++);
            divClassStr = divNodeChildren.item(j).getTextContent();
            for(j++; divNodeChildren.item(j).getNodeType() != Node.ELEMENT_NODE; j++);
            NodeList stdDivNodeChildren = divNodeChildren.item(j).getChildNodes();

            for(j=0; stdDivNodeChildren.item(j).getNodeType() != Node.ELEMENT_NODE; j++);
            number = Integer.parseInt(stdDivNodeChildren.item(j).getTextContent());
            for(j++; stdDivNodeChildren.item(j).getNodeType() != Node.ELEMENT_NODE; j++);
            name = stdDivNodeChildren.item(j).getTextContent();
            for(j++; stdDivNodeChildren.item(j).getNodeType() != Node.ELEMENT_NODE; j++);
            heading = stdDivNodeChildren.item(j).getTextContent();
            for(j++; stdDivNodeChildren.item(j).getNodeType() != Node.ELEMENT_NODE; j++);
            description = stdDivNodeChildren.item(j).getTextContent();

            stdDiv = new StandardDivinity(name, heading, description, number);

            DivinityDecoratorWithEffects parsedDiv = parseDivinity(divClassStr);
            parsedDiv.setDivinity(stdDiv);
            divinitiesList.put(name, parsedDiv);
        }

        return divinitiesList;
    }

    //this method is meant for giving a meaningful name to the method user will call, and leave an explicative one to XMLParse()
    public static Map<String, Divinity> getDivinityMap() {
        return XMLParse();
    }

}
