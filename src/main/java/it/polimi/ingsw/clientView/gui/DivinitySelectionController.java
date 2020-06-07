package it.polimi.ingsw.clientView.gui;

import it.polimi.ingsw.clientView.ClientView;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DivinitySelectionController {

    private static ClientView clientView;

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
    private ProgressIndicator progressIndicator;

    private List<ImageView> godsImages = new ArrayList<>();

    private List<String> divinities;
    private List<String> descriptions;
    private List<String> players;
    private int playerNumber;

    private List<String> chosenGods = new ArrayList<>();
    private List<Node> chosenGodsNode = new ArrayList<>();

    private boolean end = false;


    @FXML
    public void initialize() {
        godGrid.setAlignment(Pos.CENTER);
        godGrid.setHgap(10);
        godGrid.setVgap(50);

        godGrid.setPadding(new Insets(10,10,10,10));

    }

    public static void setClientView(ClientView clientView) {
        DivinitySelectionController.clientView = clientView;
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

                    Image godImage = new Image("/graphics/" + divinities.get(count).toLowerCase() + ".png");


                    god.setImage(godImage);

                    god.setFitHeight(220);
                    god.setFitWidth(150);


                   // god.setFitHeight(mainPane.getHeight()/20);
                   // god.setFitWidth(mainPane.getWidth()/20);

                    addCell(god,r,c,count);

                    count++;
                }

            }
        }


    }

    private void addCell(Node node, int x, int y,int count) {
        String back = "/graphics/clp_bg.png";
        ImageView b = new ImageView(new Image(back));

        b.setFitHeight(240);
        b.setFitWidth(160);

        godGrid.add(new StackPane(b,node),x,y);

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
            if(end) return;

            ImageView god = new ImageView();
            Image godImg = new Image("/graphics/" + divinities.get(count).toLowerCase() + ".png");
            god.setImage(godImg);
            god.setFitHeight(350);
            god.setFitWidth(250);
            god.isPreserveRatio();

            String clip = "/graphics/clp_bg.png";
            ImageView c = new ImageView(new Image(back));
            c.setFitHeight(390);
            c.setFitWidth(280);

            //add image of divinity selected to the desc panel
            godDescription.getChildren().add(new StackPane(c,god));

            Label desc = new Label();

            Text text1=new Text(descriptions.get(count));
            text1.setStyle("-fx-font-style: italic");
            text1.setFont(new Font(18));
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
            close.setPrefHeight(40);
            close.setPrefWidth(100);
            close.setOnMouseClicked((e)->{
                godDescription.getChildren().clear();
                godDescription.setVisible(false);
            });
            buttons.getChildren().add(close);

            //if this god was already selected --> add deselect button
            if(chosenGodsNode.contains(node)) {
                Button deselect = new Button("deselect");
                deselect.setPrefHeight(40);
                deselect.setPrefWidth(100);
                deselect.setOnMouseClicked((e)->{
                    chosenGods.remove(divinities.get(count));
                    chosenGodsNode.remove(node);
                    godDescription.getChildren().clear();
                    godDescription.setVisible(false);
                    node.setEffect(null);
                });
                buttons.getChildren().add(deselect);
            }
            //else --> add select button
            else{
                Button select = new Button("select");
                select.setPrefHeight(40);
                select.setPrefWidth(100);
                select.setOnMouseClicked((e)->{
                    chosenGods.add(divinities.get(count));
                    chosenGodsNode.add(node);
                    godDescription.getChildren().clear();
                    godDescription.setVisible(false);
                    node.setEffect(lighting);

                    if(chosenGods.size()==playerNumber) {
                        StringBuilder s = new StringBuilder();
                        s.append("You chose: ");
                        chosenGods.stream().forEach(z -> s.append(z + " "));
                        s.append("\n");
                        s.append("WAIT OTHER PLAYERS...");
                        labelTxt.setText(s.toString());
                        labelTxt.setFont(new Font(18));

                        endVBox.setVisible(true);

                        end = true;

                        clientView.playableDivinitiesSelection(chosenGods,players.get(0));
                    }


                });
                buttons.getChildren().add(select);
            }


            //godDescription.getChildren().add(close);

            godDescription.setVisible(true);



             /*labelTxt.setText(divinities.get(count).toUpperCase() + ": " + descriptions.get(count));
             labelTxt.setWrapText(true);

             confirmBtn.setOnMouseClicked((e2)->{

                 if(chosenGods.contains(divinities.get(count))) {
                     ImageView img = (ImageView) e1.getTarget();
                     img.setEffect(null);
                     chosenGods.remove(divinities.get(count));
                     chosenGodsNode.remove(node);
                     labelTxt.setText("God deselected ");
                 }
                 else {
                     chosenGods.add(divinities.get(count));
                     chosenGodsNode.add(node);

                     ImageView img = (ImageView) e1.getTarget();
                     img.setEffect(lighting);
                 }

                 if(chosenGods.size()==playerNumber) {
                     confirmBtn.setOnAction(null);

                     labelTxt.setText("WAIT YOUR TURN...");

                     confirmBtn.setVisible(false);

                     StringBuilder s = new StringBuilder();
                     s.append("You chose: ");
                     chosenGods.stream().forEach(z -> s.append(z + " "));
                     s.append("\n");
                     s.append("WAIT OTHER PLAYERS...");

                     //progressIndicator.setVisible(true);

                     labelTxt.setText(s.toString());


                     //prova
                     vBoxGod.getChildren().remove(confirmBtn);
                     ProgressIndicator p = new ProgressIndicator();
                     p.setMaxSize(50,50);

                     vBoxGod.setSpacing(0);

                     vBoxGod.getChildren().add(p);


                     godsImages.stream().forEach(i -> i.setOnMouseClicked(null));

                     clientView.playableDivinitiesSelection(chosenGods,players.get(0));
                 }
             });*/

        });

        //add event listener
    }



}
