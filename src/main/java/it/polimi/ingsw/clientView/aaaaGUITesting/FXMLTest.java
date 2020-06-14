package it.polimi.ingsw.clientView.aaaaGUITesting;

import it.polimi.ingsw.clientView.gui.DivinitySelectionController;
import it.polimi.ingsw.clientView.gui.EndGameWinnerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class FXMLTest extends Application {

   private GameController gameController;

   private EndGameWinnerController endGameController;

   private DivinitySelectionController divinitySelectionController;

    @Override
    public void start(Stage stage) throws Exception {
        /*URL testFXML = new File("/fxml/EndGameWinner.fxml").toURI().toURL();
        FXMLLoader testLoader = new FXMLLoader(testFXML);
        Parent testPane = testLoader.load();
        this.gameController = testLoader.getController();*/

        FXMLLoader endLoader = new FXMLLoader(getClass().getResource("/fxml/DivinitySelection.fxml"));
        Parent endPane = endLoader.load();
        //this.endGameController = endLoader.getController();
        this.divinitySelectionController = endLoader.getController();

        Scene game = new Scene(endPane, 1500,1000);

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
