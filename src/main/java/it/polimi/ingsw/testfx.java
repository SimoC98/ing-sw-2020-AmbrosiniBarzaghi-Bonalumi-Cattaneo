package it.polimi.ingsw;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
/**
 * JavaFX App
 */
public class testfx extends Application {

    @Override
    public void start(Stage stage) {

        Label label = new Label("JavaFX + MVC ");
        Scene scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}