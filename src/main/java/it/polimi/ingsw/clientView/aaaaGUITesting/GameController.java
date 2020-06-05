package it.polimi.ingsw.clientView.aaaaGUITesting;

import it.polimi.ingsw.Pair;
import it.polimi.ingsw.model.Action;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.DoubleExpression;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Thread.sleep;

public class GameController {

    @FXML
    private VBox possibleActionsBox;
    @FXML
    private StackPane leftStack, rightStack;
    @FXML
    private Label graphicAction;
    @FXML
    private Label graphicSelectedWorker;
    @FXML
    private Label message;
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
    private Pair<Integer, Integer> selectedWorker;

    @FXML
    public void initialize() {
        createBoard();
        board.prefHeightProperty().bind(hBox.heightProperty().subtract(50));
        board.prefWidthProperty().bind(board.heightProperty());
        board.setMinHeight(300);
        hBox.setMinHeight(200);
        hBox.setMinWidth(400);

        //Testing
        placeWorkers(2,2,3,4);
//        for(int i=0; i<5; i++)
//            toggleSelectable(i,i);
        actualAction = "build";

        selectedWorker = null;

        leftStack.prefHeightProperty().bind(board.heightProperty());
        rightStack.prefHeightProperty().bind(board.heightProperty());
    }

    public void createBoard() {

        DoubleBinding tileHeight = board.heightProperty().divide(board.getRowCount()).subtract(12);

        for(int i=0; i<board.getColumnCount(); i++) {
            for(int j=0; j<board.getRowCount(); j++) {
                StackPane s = new StackPane();
                s.prefHeightProperty().bind(tileHeight);
                s.prefWidthProperty().bind(s.heightProperty());

                for(int k=0; k<3; k++) {
                    ImageView lvl = new ImageView();
                    lvl.fitHeightProperty().bind(tileHeight.multiply(1-(0.175*k)));
                    lvl.fitWidthProperty().bind(lvl.fitHeightProperty());
                    s.getChildren().add(lvl);
                }
                ImageView dome = new ImageView();
                dome.fitHeightProperty().bind(tileHeight.multiply(0.5));
                dome.fitWidthProperty().bind(dome.fitHeightProperty());
                s.getChildren().add(dome);

                ImageView worker = new ImageView();
                worker.fitHeightProperty().bind(tileHeight.multiply(0.95));
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
        if(selectedWorker == null)
            return;

        System.out.println(GridPane.getRowIndex(s) + "_" + GridPane.getColumnIndex(s));

        int x = selectedWorker.getFirst();
        int y = selectedWorker.getSecond();
        if(x == GridPane.getRowIndex(s) && y == GridPane.getColumnIndex(s))
            return;

        StackPane workerS = (StackPane) board.getChildren().get((board.getRowCount()*y) + x);

        ImageView workerOld = (ImageView) workerS.getChildren().get(WORKER);
        Image workerImg = workerOld.getImage();
        if(workerImg == null) {
            System.out.println("awwww, shit");
            return;
        }
        workerOld.setImage(null);

        ImageView workerNew = (ImageView) s.getChildren().get(WORKER);
        workerNew.setImage(workerImg);

        selectedWorker = null;
        graphicSelectedWorker.setText("?-?");
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
                    image = new Image(new FileInputStream("src/main/java/it/polimi/ingsw/clientView/aaaaGUITesting/pics/lvl2bis.png"));
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
        int x = GridPane.getRowIndex(s);
        int y = GridPane.getColumnIndex(s);

        ImageView worker = (ImageView) s.getChildren().get(WORKER);
        if(worker.getImage() != null) {
            System.out.println("Worker Selected: " + x + "-" + y);
            selectedWorker = new Pair(x, y);
            graphicSelectedWorker.setText(x + "-" + y);
        }
    }

    public void placeWorkers(int x1, int y1, int x2, int y2) {

        StackPane s1 = (StackPane) board.getChildren().get(board.getRowCount()*y1 + x1);
        ImageView w1 = (ImageView) s1.getChildren().get(WORKER);

        StackPane s2 = (StackPane) board.getChildren().get(board.getRowCount()*y2 + x2);
        ImageView w2 = (ImageView) s2.getChildren().get(WORKER);

        try {
            Image image = new Image(new FileInputStream("src/main/java/it/polimi/ingsw/clientView/aaaaGUITesting/pics/MaleBuilder_Blue.png"));
            w1.setImage(image);
            w2.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void handlePossibleActions(List<Action> possibleActions) {
        message.setText("Choose the action\nto perform: ");

        List<Button> actionButtons = new ArrayList<>();
        for(Action action : possibleActions) {
            System.out.println(action.toString());

            Button actionBtn = new Button(action.toString());
            actionBtn.setOnMouseClicked((event) -> {
                actualAction = action.toString().toLowerCase();
                graphicAction.setText(action.toString());
                emptyPossibleActions();
                if(action != Action.END)
                    message.setText("Choose tile to\n" + action.toString());
                else
                    message.setText("Your turn is over");
            });
            actionButtons.add(actionBtn);
        }

        possibleActionsBox.getChildren().addAll(actionButtons);
    }

    private void emptyPossibleActions() {
        possibleActionsBox.getChildren().clear();
    }

    //TEST - REMOVEME
    public void testPossibleActions() {
        handlePossibleActions(Arrays.asList(Action.MOVE, Action.BUILD, Action.BUILDDOME, Action.MOVE));
    }


    public void setSelectable(int x, int y, boolean val) {
        StackPane s = (StackPane) board.getChildren().get(board.getRowCount()*y + x);
        StackPane shadow = (StackPane) s.getChildren().get(SHADOW);

        shadow.setVisible(val);
    }

    public void toggleSelectable(int x, int y) {
        StackPane s = (StackPane) board.getChildren().get(board.getRowCount()*y + x);
        StackPane shadow = (StackPane) s.getChildren().get(SHADOW);

        if(shadow.isVisible())
            shadow.setVisible(false);
        else
            shadow.setVisible(true);
    }

    public void setActionMove(){
        focusWorkers(false);
        System.out.println("Now you MOVE");
        graphicAction.setText("MOVE");
        actualAction = "move";
    }

    public void setActionBuild(){
        focusWorkers(false);
        System.out.println("Now you BUILD");
        graphicAction.setText("BUILD");
        actualAction = "build";
    }

    public void setActionBuildDome(){
        focusWorkers(false);
        System.out.println("Now you BUILDDOME");
        graphicAction.setText("BUILDDOME");
        actualAction = "builddome";
    }

    public void setActionSelectWorker(){
        focusWorkers(true);
        System.out.println("Now SELECTWORKER");
        graphicAction.setText("SELECTWORKER");
        actualAction = "selectworker";
    }

    public void focusWorkers(boolean value) {
        StackPane x;
        ImageView v;
        for(int i=0; i<25; i++) {
            Label l = new Label(i%5 + "-" + i/5);
            x = (StackPane) board.getChildren().get(i);
            x.getChildren().add(l);
            v = (ImageView) x.getChildren().get(WORKER);
            if(v.getImage() != null)
                setSelectable(i%5, i/5, value);
        }
    }
}
