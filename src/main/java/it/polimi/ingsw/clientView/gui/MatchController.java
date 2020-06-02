package it.polimi.ingsw.clientView.gui;

import it.polimi.ingsw.clientView.ClientView;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class MatchController {
    private static ClientView clientView;

    @FXML
    private GridPane board;
    @FXML
    private HBox hBox;
    @FXML
    private VBox vBoxLeft;
    @FXML
    private VBox vBoxRight;

    private int [][]boardInt = new int[5][5];
    private final int LVL1=0, LVL2=1, LVL3=2, DOME=3, WORKER=4, SHADOW=5;
    private String actualAction;

    @FXML
    public void initialize() {
        createBoard();
        board.prefHeightProperty().bind(hBox.heightProperty().subtract(25));
        board.prefWidthProperty().bind(board.heightProperty());
        hBox.setMinHeight(200);
        hBox.setMinWidth(400);

        //Testing
        placeWorkers(2,2,3,4);
//        for(int i=0; i<5; i++)
//            toggleSelectable(i,i);
        actualAction = "build";

        vBoxLeft.setSpacing(40);
        vBoxLeft.setAlignment(Pos.CENTER);

    }

    public static void setClientView(ClientView clientView) {
        MatchController.clientView = clientView;
    }

    public void createBoard() {
        for(int i=0; i<board.getColumnCount(); i++) {
            for(int j=0; j<board.getRowCount(); j++) {
                StackPane s = new StackPane();
                s.prefHeightProperty().bind(board.heightProperty().divide(board.getRowCount()).subtract(10));
                s.prefWidthProperty().bind(s.heightProperty());

                for(int k=0; k<3; k++) {
                    ImageView lvl = new ImageView();
                    lvl.fitHeightProperty().bind(board.heightProperty().divide(board.getRowCount()).subtract(10).multiply(1-(0.175*k)));
                    lvl.fitWidthProperty().bind(lvl.fitHeightProperty());
                    s.getChildren().add(lvl);
                }
                ImageView dome = new ImageView();
                dome.fitHeightProperty().bind(board.heightProperty().divide(board.getRowCount()).subtract(10).multiply(0.5));
                dome.fitWidthProperty().bind(dome.fitHeightProperty());
                s.getChildren().add(dome);

                ImageView worker = new ImageView();
                worker.fitHeightProperty().bind(board.heightProperty().divide(board.getRowCount()).subtract(10).multiply(0.95));
                worker.fitWidthProperty().bind(worker.fitHeightProperty());
                s.getChildren().add(worker);

                StackPane selectableShade = new StackPane();
                selectableShade.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
                selectableShade.setOpacity(0.4);
                selectableShade.setVisible(false);
                s.getChildren().add(selectableShade);



                board.add(s, i, j);

                s.setOnMouseClicked((e) -> action(s));
            }
        }
    }

    public void action(StackPane s) {

        //TODO: add worker selection

        switch(actualAction) {
            case "move":
                move(s);
                break;

            case "build":
                build(s);
                break;

            case "builddome":
                buildDome(s);
                break;

            case "end":
                //nothing
                break;

            case "selectworker":
                selectWorker(s);
                break;
        }

    }

    public void move(StackPane s) {
        //TODO
    }

    public void build(StackPane s) {
        int x = GridPane.getRowIndex(s);
        int y = GridPane.getColumnIndex(s);

        System.out.println(x + "-" + y);
        boardInt[x][y] ++;
        System.out.println(boardInt[x][y]);


        Image image;
        ImageView imageView;
        try {
            switch(boardInt[x][y]) {
                case 1:
                    image = new Image(new FileInputStream("src/main/java/it/polimi/ingsw/clientView/aaaaGUITesting/pics/lvl1.png"));
                    imageView = (ImageView) s.getChildren().get(LVL1);
                    imageView.setImage(image);
                    break;

                case 2:
                    image = new Image(new FileInputStream("src/main/java/it/polimi/ingsw/clientView/aaaaGUITesting/pics/lvl2.png"));
                    imageView = (ImageView) s.getChildren().get(LVL2);
                    imageView.setImage(image);
                    break;

                case 3:
                    image = new Image(new FileInputStream("src/main/java/it/polimi/ingsw/clientView/aaaaGUITesting/pics/lvl3.png"));
                    imageView = (ImageView) s.getChildren().get(LVL3);
                    imageView.setImage(image);
                    break;

                case 4:
                    buildDome(s);
                    break;

                default:
                    System.out.println("boh");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void buildDome(StackPane s) {
        Image image = null;
        try {
            image = new Image(new FileInputStream("src/main/java/it/polimi/ingsw/clientView/aaaaGUITesting/pics/dome.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView imageView = (ImageView) s.getChildren().get(DOME);
        imageView.setImage(image);
    }

    public void selectWorker(StackPane s) {
        ImageView v = (ImageView) s.getChildren().get(WORKER);
        if(v.getImage() != null)
            System.out.println("Worker Selected");

    }

    public void placeWorkers(int x1, int y1, int x2, int y2) {

        StackPane s1 = (StackPane) board.getChildren().get(board.getRowCount()*x1 + y1);
        ImageView w1 = (ImageView) s1.getChildren().get(WORKER);

        StackPane s2 = (StackPane) board.getChildren().get(board.getRowCount()*x2 + y2);
        ImageView w2 = (ImageView) s2.getChildren().get(WORKER);

        try {
            Image image = new Image(new FileInputStream("src/main/java/it/polimi/ingsw/clientView/aaaaGUITesting/pics/MaleBuilder_Blue.png"));
            w1.setImage(image);
            w2.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void toggleSelectable(int x, int y) {
        StackPane s = (StackPane) board.getChildren().get(board.getRowCount()*x + y);
        StackPane shadow = (StackPane) s.getChildren().get(SHADOW);

        if(shadow.isVisible())
            shadow.setVisible(false);
        else
            shadow.setVisible(true);
    }

    public void setActionMove(){
        System.out.println("Now you MOVE");
        actualAction = "move";
    }

    public void setActionBuild(){
        System.out.println("Now you BUILD");
        actualAction = "build";
    }

    public void setActionBuildDome(){
        System.out.println("Now you BUILDDOME");
        actualAction = "builddome";
    }

    public void setActionSelectWorker(){
        StackPane x;
        ImageView v;
        for(int i=0; i<25; i++) {
            x = (StackPane) board.getChildren().get(i);
            v = (ImageView) x.getChildren().get(WORKER);
            if(v.getImage() != null)
                toggleSelectable(i/5, i%5);
        }
        System.out.println("Now SELECTWORKER");
        actualAction = "selectworker";
    }

    public void setPlayers() {
        List<String> players = clientView.getBoard().getPlayersNames();
        List<String> divinities = new ArrayList<>();

        clientView.getBoard().getDivinities().keySet().forEach(x -> divinities.add(x));



        for(int i=0;i<divinities.size();i++) {
            ImageView god = new ImageView();

            String url = "/graphics/" + divinities.get(i).toLowerCase() + ".png";
            Image godImage = new Image(url);

            god.setImage(godImage);
            god.setFitHeight(150);
            god.setFitWidth(100);

            VBox box = new VBox();
            box.setSpacing(5);
            box.setAlignment(Pos.CENTER);

            box.getChildren().addAll(god,new Label(players.get(i)));

            vBoxLeft.getChildren().add(box);
        }






    }

}