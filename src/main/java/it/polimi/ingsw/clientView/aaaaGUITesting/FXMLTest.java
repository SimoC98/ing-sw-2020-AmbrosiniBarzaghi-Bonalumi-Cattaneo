package it.polimi.ingsw.clientView.aaaaGUITesting;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;


public class FXMLTest extends Application {

   private GameController gameController;

    @Override
    public void start(Stage stage) throws Exception {
        URL testFXML = new File("src/main/java/it/polimi/ingsw/clientView/aaaaGUITesting/test.fxml").toURI().toURL();
        FXMLLoader testLoader = new FXMLLoader(testFXML);
        Parent testPane = testLoader.load();
        this.gameController = testLoader.getController();

        Scene game = new Scene(testPane, 1500,700);

        stage.setScene(game);
        stage.setResizable(true);
        stage.minHeightProperty().setValue(400);
        stage.minWidthProperty().bind(stage.minHeightProperty().multiply(2));

        stage.show();


        //fundamental date
//        SimpleDoubleProperty stageWidthProperty = new SimpleDoubleProperty(stage.getWidth());
//        double windowBorder = stage.getHeight() - game.getHeight();

        //read scene width
//        game.widthProperty().addListener(new ChangeListener<Number>() {
//            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
//                stage.setHeight((newSceneWidth.doubleValue() / 2) + (stage.getHeight() - game.getHeight())); //stageHeight - sceneHeight means tha height of the window top bar
//            }
//        });

    }
}
