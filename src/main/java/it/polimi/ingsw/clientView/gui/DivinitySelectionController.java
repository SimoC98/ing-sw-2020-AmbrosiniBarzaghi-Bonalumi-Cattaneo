package it.polimi.ingsw.clientView.gui;

import it.polimi.ingsw.clientView.ClientView;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DivinitySelectionController {

    private static ClientView clientView;
    private static GUI gui;

    @FXML
    private BorderPane bPane;

    @FXML
    private VBox vBox;

    @FXML
    private VBox vBoxGod;

    @FXML
    private VBox godDescription;

    @FXML
    private GridPane godGrid;

    @FXML
    private VBox endVBox;

    @FXML
    private Label labelTxt;

    @FXML
    private StackPane sPane;

    @FXML
    private HBox titleHBox;

    @FXML
    private ProgressIndicator progressIndicator;

    private List<ImageView> godsImages = new ArrayList<>();

    private List<String> divinities;
    private List<String> descriptions;
    private List<String> players;
    private int playerNumber;

    private List<String> chosenGods = new ArrayList<>();
    private List<Node> chosenGodsNode = new ArrayList<>();

    private boolean nodeListenersOff = false;

    private DoubleProperty fontSizeTitle = new SimpleDoubleProperty(20);
    private DoubleProperty fontSizeDescriptions = new SimpleDoubleProperty(20);
    private DoubleProperty fontSizeNames = new SimpleDoubleProperty(20);
    Font santoriniFont = Font.loadFont(getClass().getResource("/font/LillyBelle.ttf").toExternalForm(),18);



    @FXML
    public void initialize() {
        fontSizeTitle.bind(bPane.widthProperty().add(bPane.heightProperty()).divide(80));
        fontSizeDescriptions.bind(bPane.widthProperty().add(bPane.heightProperty()).divide(150));
        fontSizeNames.bind(bPane.widthProperty().add(bPane.heightProperty()).divide(110));

        bPane.getStylesheets().add(getClass().getResource("/css/btn.css").toExternalForm());


        titleHBox.setAlignment(Pos.CENTER);
        Text title = new Text("CHOOSE GODS THAT WILL BE USED IN THIS MATCH:");
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

    public static void setGui(GUI gui) {
        DivinitySelectionController.gui = gui;
        DivinitySelectionController.clientView = gui.getClientView();
    }

    public void setDivinityOnGrid(List<String> divinities, List<String> descriptions, int playerNumber,List<String> players) {

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

    private void addCell(Node node, int x, int y,int count) {
        //String back = "/graphics/clp_bg.png";
        String back = "/graphics/fg_panel4.png";
        ImageView b = new ImageView(new Image(getClass().getResource(back).toExternalForm()));

        b.fitHeightProperty().bind(bPane.heightProperty().divide(4).add(20));
        b.fitWidthProperty().bind(bPane.widthProperty().divide(10).add(20));
        //b.fitWidthProperty().bind(b.fitHeightProperty().divide(1.5));

        b.maxHeight(240);
        b.maxWidth(160);

        //b.setFitHeight(240);
        //b.setFitWidth(160);

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
            //text1.setFont(new Font(18));
            text1.setFont(santoriniFont);
            text1.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSizeDescriptions.asString(), ";"));
            text1.setWrappingWidth(250);
            text1.setTextAlignment(TextAlignment.CENTER);

            //add god effect to the desc panel
            godDescription.getChildren().add(text1);

            //creations of buttons
            HBox buttons = new HBox();
            godDescription.getChildren().add(buttons);
            buttons.setAlignment(Pos.CENTER);
            buttons.setSpacing(20);

            Button close = new Button("close");
            close.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSizeDescriptions.asString(), ";"));
            //close.setPrefHeight(40);
            //close.setPrefWidth(100);
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
