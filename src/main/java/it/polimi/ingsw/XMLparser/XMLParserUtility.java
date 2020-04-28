package it.polimi.ingsw.XMLparser;

import it.polimi.ingsw.model.Divinity;
import it.polimi.ingsw.model.DivinityDecoratorWithEffects;
import it.polimi.ingsw.model.cards.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class XMLParserUtility {

    private static final HashMap<String, Callable<Divinity>> effectToDivinity = new HashMap<String, Callable<Divinity>>() {
        {
            //0 - Human
            put("none", DivinityDecoratorWithEffects::new);

            //1 - Apollo
            put("CanSwapWorkers", SwapWithOpponent::new);

            //2 - Artemis
            put("CanMoveTwiceNotBack", MoveTwiceNotBack::new);

            //3 - Athena
            put("OpponentCantGoUp", SetEffectOnOpponent::new);

            //4 - Atlas
            put("CanBuildDomeEverywhere", BuildDomeEverywhere::new);

            //5 - Demeter
            put("CanBuildTwiceNotSame", BuildTwiceNotSameTile::new);

            //6 - Hephaestus
            put("CanBuildTwiceSame", BuildTwiceSameTile::new);

            //8 - Minotaur
            put("CanPushWorker", PushOpponent::new);

            //9 - Pan
            put("CanWinByGettingDown", WinByDropTwoLevel::new);

            //10 - Prometheus
            put("CanBuildBeforeMove", BuildBeforeAndAfter::new);
        }
    };

    public static Map<String, Divinity> getDivinities(){
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
            if (builder != null) {
                doc = builder.parse(xmlFile);
            }
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }

        if (doc != null) {
            doc.getDocumentElement().normalize();
        }

        NodeList divinities = null;
        if (doc != null) {
            divinities = doc.getElementsByTagName("divinity");
        }
        Map<String, Divinity> divinitiesMap = new HashMap<>();

        StandardDivinity stdDiv;
        String name, heading, description, effect;
        int number;
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
            DivinityDecoratorWithEffects parsedDiv = null;
            try {
                parsedDiv = (DivinityDecoratorWithEffects) effectToDivinity.get(effect).call();
            } catch (Exception e) {
                e.printStackTrace();
            }
            parsedDiv.setDivinity(stdDiv);

            divinitiesMap.put(name, parsedDiv);
        }

        return divinitiesMap;
    }


}
