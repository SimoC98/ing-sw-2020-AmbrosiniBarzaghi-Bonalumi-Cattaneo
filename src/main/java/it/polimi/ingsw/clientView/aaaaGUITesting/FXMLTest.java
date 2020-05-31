package it.polimi.ingsw.clientView.aaaaGUITesting;

import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;


public class FXMLTest extends Application {

    @FXML
    private GridPane board;

    @FXML
    private Button mybutt;

    @FXML
    private Label label;

    @FXML
    public void initialize() {
        int numRows = 5;
        for (int row = 0 ; row < numRows ; row++ ){
            RowConstraints rc = new RowConstraints();
            ColumnConstraints cc = new ColumnConstraints();
            rc.setFillHeight(true);
            cc.setFillWidth(true);
            rc.setVgrow(Priority.ALWAYS);
            cc.setHgrow(Priority.ALWAYS);
            board.getRowConstraints().add(rc);
            board.getColumnConstraints().add(cc);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL testFXML = new File("src/main/java/it/polimi/ingsw/clientView/aaaaGUITesting/test.fxml").toURI().toURL();

        FXMLLoader testLoader = new FXMLLoader(testFXML);

        Parent testPane = testLoader.load();




//        Button myButt = new Button("Butt");
//        mybutt.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
//        board.add(myButt, 0, 0);
//        GridPane.setFillWidth(mybutt, true);
//        GridPane.setFillHeight(mybutt, true);

        Scene game = new Scene(testPane, 800,400);



        stage.setScene(game);

//        stage.setFullScreen(true);
//        DoubleBinding x = stage.heightProperty().subtract(28).multiply(2);
//        DoubleBinding y = stage.widthProperty().add(28).divide(2);

//        stage.minHeightProperty().bind(game.widthProperty().divide(2).add(40));
//        stage.maxHeightProperty().bind(game.widthProperty().divide(2).add(stage.getHeight() - game.getHeight()));

        stage.show();


        //fundamental date
        SimpleDoubleProperty stageWidthProperty = new SimpleDoubleProperty(stage.getWidth());
        double windowBorder = stage.getHeight() - game.getHeight();


        //read scene width
        game.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
//                System.out.println("Scene width = " + newSceneWidth);
                stage.setHeight((newSceneWidth.doubleValue() / 2) + (stage.getHeight() - game.getHeight())); //stageHeight - sceneHeight means tha height of the window top bar
            }
        });

//        game.heightProperty().addListener(new ChangeListener<Number>() {
//            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
//                System.out.println("Scene width = " + newSceneHeight);
//                stage.setHeight(oldSceneHeight.doubleValue()); //stageHeight - sceneHeight means tha height of the window top bar
//            }
//        });






    }

    public void resizeEverything() {
        label.setText("Guantanamera");
        label.setVisible(false);
    }
}
