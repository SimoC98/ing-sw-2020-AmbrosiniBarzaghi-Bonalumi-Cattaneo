package it.polimi.ingsw.Client.gui;

import it.polimi.ingsw.Client.ClientView;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to display and menage the GUI inherent to the selection of the divinities to be selected by the last player.
 * After displaying the divinities and collecting the choice of the user, a list of players is shown and the last player
 * will have to chose the starting player. At the end {@link ClientView#playableDivinitiesSelection(List, String)} is called.
 */
public class DivinitySelectionController {

    private static ClientView clientView;
    private static GUI gui;

    @FXML
    private BorderPane bPane;

    @FXML
    private VBox godDescription;

    @FXML
    private GridPane godGrid;

    @FXML
    private VBox endVBox;

    @FXML
    private StackPane sPane;

    @FXML
    private HBox titleHBox;

    private List<ImageView> godsImages = new ArrayList<>();

    private List<String> divinities;
    private List<String> descriptions;
    private List<String> players;
    private int playerNumber;

    private Text title;

    private List<String> chosenGods = new ArrayList<>();
    private List<Node> chosenGodsNode = new ArrayList<>();

    private boolean nodeListenersOff = false;

    private DoubleProperty fontSizeTitle = new SimpleDoubleProperty(20);
    private DoubleProperty fontSizeDescriptions = new SimpleDoubleProperty(20);
    private DoubleProperty fontSizeNames = new SimpleDoubleProperty(20);
    Font santoriniFont = Font.loadFont(getClass().getResource("/font/LillyBelle.ttf").toExternalForm(),18);

    /**
     * Graphical properties to show the divinities.
     */
    @FXML
    public void initialize() {
        fontSizeTitle.bind(bPane.widthProperty().add(bPane.heightProperty()).divide(80));
        fontSizeDescriptions.bind(bPane.widthProperty().add(bPane.heightProperty()).divide(150));
        fontSizeNames.bind(bPane.widthProperty().add(bPane.heightProperty()).divide(110));

        bPane.getStylesheets().add(getClass().getResource("/css/btn.css").toExternalForm());


        titleHBox.setAlignment(Pos.CENTER);
        this.title = new Text();
        title.setFont(santoriniFont);
        title.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSizeTitle.asString(), ";"));
        titleHBox.getChildren().add(title);


        godGrid.setAlignment(Pos.CENTER);

        godGrid.setHgap(10);
        godGrid.setVgap(50);

        godGrid.prefHeightProperty().bind(bPane.heightProperty().divide(1.34));
        godGrid.prefWidthProperty().bind(bPane.widthProperty());

        godDescription.prefHeightProperty().bind(bPane.heightProperty().divide(1.37));
        godDescription.prefWidthProperty().bind(bPane.widthProperty().divide(2.73));

        endVBox.prefHeightProperty().bind(bPane.heightProperty().divide(5));
        endVBox.prefWidthProperty().bind(bPane.widthProperty());

        endVBox.setVisible(false);
    }

    public DivinitySelectionController() {
    }

    public static void setClientView(ClientView clientView) {
        DivinitySelectionController.clientView = clientView;
    }

    /**
     * Assigns the gui and clientView
     * @param gui {@link GUI}
     */
    public static void setGui(GUI gui) {
        DivinitySelectionController.gui = gui;
        DivinitySelectionController.clientView = gui.getClientView();
    }

    /**
     * It uses the information passed by the server to create a grid of divinities with their name, description and image.
     * @param divinities List of divinities' names
     * @param descriptions List of divinities' description
     * @param playerNumber number of player in game. used in methods below
     * @param players List of username . used in methods below
     */
    public void setDivinityOnGrid(List<String> divinities, List<String> descriptions, int playerNumber,List<String> players) {
        title.setText("CHOOSE " + playerNumber + " GODS THAT WILL BE USED IN THIS MATCH:");

        this.players = players;
        this.divinities = new ArrayList<>(divinities);
        this.descriptions = new ArrayList<>(descriptions);
        this.playerNumber = playerNumber;

        int count=0;


        for(int c=0;c<godGrid.getRowCount();c++) {
            for(int r=0;r<godGrid.getColumnCount();r++) {

                if(count<divinities.size()) {
                    ImageView god = new ImageView();

                    //Image godImage = new Image("/graphics/" + divinities.get(count).toLowerCase() + ".png");
                    Image godImage = new Image(getClass().getResource("/graphics/" + divinities.get(count).toLowerCase() + ".png").toExternalForm());


                    god.setImage(godImage);

                    //height: 4.55; width 12
                    god.fitHeightProperty().bind(bPane.heightProperty().divide(4));
                    //god.fitWidthProperty().bind(god.fitHeightProperty().divide(1.5));
                    god.fitWidthProperty().bind(bPane.widthProperty().divide(11));

                    god.isPreserveRatio();

                    addCell(god,r,c,count);

                    count++;
                }

            }
        }


    }

    /**
     * The aim is to manage the graphical part of the divinities' selection. Divinities are added to a grid where buttons
     * are created to observe if they are clicked and in that case another window opens to let the user see the divinity's
     * description alongside with two buttons to select (or deselect) and to close the window. Once a user chooses the
     * third divinity, {@link #askStarter()} is called.
     * @param node Reference node to perform actions on a divinity's graphical elements.
     * @param x x position in the grid
     * @param y y position in the grid
     * @param count number of the divinity in the grid
     */
    private void addCell(Node node, int x, int y,int count) {
        String back = "/graphics/fg_panel4.png";
        ImageView b = new ImageView(new Image(getClass().getResource(back).toExternalForm()));

        b.fitHeightProperty().bind(bPane.heightProperty().divide(4).add(20));
        b.fitWidthProperty().bind(bPane.widthProperty().divide(10).add(20));

        b.maxHeight(240);
        b.maxWidth(160);

        godGrid.add(new StackPane(node,b),x,y);

        GridPane.setHgrow(node, Priority.ALWAYS);
        GridPane.setVgrow(node,Priority.ALWAYS);

        godsImages.add((ImageView)node);



        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135.0);

        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(4.0);


        GridPane.setHalignment(node, HPos.CENTER);
        GridPane.setValignment(node, VPos.CENTER);

        node.setOnMouseEntered((e)->{
            node.setEffect(lighting);
        });

        node.setOnMouseExited((e)->{
            if(!chosenGodsNode.contains(node)) node.setEffect(null);
        });

        node.setOnMouseClicked((e1)-> {
            //if already chosen return without show desc
            if(nodeListenersOff) return;

            nodeListenersOff = true;

            ImageView god = new ImageView();
            Image godImg = new Image(getClass().getResource("/graphics/" + divinities.get(count).toLowerCase() + ".png").toExternalForm());
            god.setImage(godImg);

            god.fitHeightProperty().bind(godDescription.heightProperty().divide(1.8));
            god.fitWidthProperty().bind(godDescription.widthProperty().divide(2.2));
            god.isPreserveRatio();

            String clip = "/graphics/clp_bg.png";
            ImageView c = new ImageView(new Image(getClass().getResource(clip).toExternalForm()));
            c.fitHeightProperty().bind(godDescription.heightProperty().divide(1.8).add(60));
            c.fitWidthProperty().bind(godDescription.widthProperty().divide(2.2).add(40));

            sPane.setAlignment(Pos.CENTER);

            //add image of divinity selected to the desc panel
            godDescription.getChildren().add(new StackPane(c,god));

            Text text1=new Text(descriptions.get(count));
            text1.setStyle("-fx-font-style: italic");
            text1.setFont(santoriniFont);
            text1.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSizeDescriptions.asString(), ";"));
            text1.setWrappingWidth(250);
            text1.setTextAlignment(TextAlignment.CENTER);

            //add god effect to the desc panel
            godDescription.getChildren().add(text1);

            //creation of buttons
            HBox buttons = new HBox();
            godDescription.getChildren().add(buttons);
            buttons.setAlignment(Pos.CENTER);
            buttons.setSpacing(20);

            Button close = new Button("close");
            close.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSizeDescriptions.asString(), ";"));
            close.prefWidthProperty().bind(godDescription.widthProperty().divide(5.5));
            close.prefHeightProperty().bind(godDescription.heightProperty().divide(18.25));
            close.getStyleClass().add("coral");
            close.getStyleClass().add("whiteTxt");
            close.setFont(santoriniFont);

            close.setOnMouseEntered((e)->{
                close.setEffect(lighting);
            });

            close.setOnMouseExited((e)->{
                close.setEffect(null);
            });

            close.setOnMouseClicked((e)->{
                godDescription.getChildren().clear();
                godDescription.setVisible(false);
                nodeListenersOff=false;
            });
            buttons.getChildren().add(close);

            //if this god was already selected --> add deselect button
            if(chosenGodsNode.contains(node)) {
                Button deselect = new Button("deselect");
                deselect.setFont(santoriniFont);

                deselect.setOnMouseEntered((e)->{
                    deselect.setEffect(lighting);
                });

                deselect.setOnMouseExited((e)->{
                    deselect.setEffect(null);
                });

                deselect.getStyleClass().add("blue");
                deselect.getStyleClass().add("whiteTxt");
                deselect.setFont(santoriniFont);
                deselect.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSizeDescriptions.asString(), ";"));
                deselect.prefWidthProperty().bind(godDescription.widthProperty().divide(5.5));
                deselect.prefHeightProperty().bind(godDescription.heightProperty().divide(18.25));

                deselect.setOnMouseClicked((e)->{
                    chosenGods.remove(divinities.get(count));
                    chosenGodsNode.remove(node);
                    godDescription.getChildren().clear();
                    godDescription.setVisible(false);
                    node.setEffect(null);
                    nodeListenersOff=false;
                });
                buttons.getChildren().add(deselect);
            }
            //else --> add select button
            else{
                Button select = new Button("select");
                select.setFont(santoriniFont);
                //select.setPrefHeight(40);
                //select.setPrefWidth(100);
                select.prefWidthProperty().bind(godDescription.widthProperty().divide(5.5));
                select.prefHeightProperty().bind(godDescription.heightProperty().divide(18.25));
                select.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSizeDescriptions.asString(), ";"));

                select.setOnMouseEntered((e)->{
                    select.setEffect(lighting);
                });

                select.setOnMouseExited((e)->{
                    select.setEffect(null);
                });

                select.getStyleClass().add("blue");
                select.getStyleClass().add("whiteTxt");
                select.setFont(santoriniFont);
                //select.getStylesheets().add(getClass().getResource("/css/btn.css").toExternalForm());

                select.setOnMouseClicked((e)->{
                    chosenGods.add(divinities.get(count));
                    chosenGodsNode.add(node);
                    godDescription.getChildren().clear();
                    godDescription.setVisible(false);
                    node.setEffect(lighting);
                    nodeListenersOff=false;

                    if(chosenGods.size()==playerNumber) {
                        askStarter();
                    }


                });
                buttons.getChildren().add(select);
            }

            godDescription.setVisible(true);

        });
    }

    /**
     * Called after the selection of the divinities. A window containing all the players' names is created as to display
     * them and make them clickable so that the last player can click on the player they want to make the starter.
     * At the end {@link ClientView#playableDivinitiesSelection(List, String)} is invoked and the information is sent to the server.
     */
    public void askStarter() {
        godDescription.getChildren().clear();
        godDescription.setVisible(true);

        godDescription.setSpacing(30);

        Label label = new Label("SELECT THE STARTER:");
        label.setStyle("-fx-font-weight: bold");
        label.setFont(Font.loadFont(getClass().getResource("/font/LillyBelle.ttf").toExternalForm(),30));

        VBox v = new VBox();
        v.setSpacing(20);
        v.setAlignment(Pos.CENTER);

        godDescription.getChildren().add(label);
        godDescription.getChildren().add(v);

        nodeListenersOff = true;

        for(int i=0; i<players.size(); i++) {
            String player = players.get(i);
            Label p = new Label(players.get(i));
            p.setFont(Font.loadFont(getClass().getResource("/font/LillyBelle.ttf").toExternalForm(),24));


            p.setOnMouseEntered((e) -> {
                p.getStyleClass().clear();
                p.getStyleClass().add("mouse_in");
            });

            p.setOnMouseExited((e) -> {
                p.getStyleClass().clear();
                p.getStyleClass().add("mouse_out");
            });
            p.setOnMouseClicked((e) -> {
                godDescription.setVisible(false);


                StringBuilder s = new StringBuilder();
                s.append("WAIT OTHER PLAYERS...");
                Text t = new Text("WAIT OTHER PLAYERS...");
                t.setFont(santoriniFont);
                t.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSizeDescriptions.asString(), ";"));

                ProgressIndicator progress = new ProgressIndicator();
                progress.setMaxSize(40,40);

                endVBox.getChildren().addAll(t,progress);


                endVBox.setVisible(true);
                endVBox.setSpacing(5);

                clientView.playableDivinitiesSelection(chosenGods,player);
            });

            v.getChildren().add(p);
        }
    }
}
