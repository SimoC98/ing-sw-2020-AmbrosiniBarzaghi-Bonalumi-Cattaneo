package it.polimi.ingsw;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class testfx extends Application {

    @Override
    public void start(Stage stage) throws IOException {


        stage.setTitle("HelloDevoxx");

        FXMLLoader playerDivinityLoader = new FXMLLoader(getClass().getResource("/prove/Prova.fxml"));
        //Parent playableDivinitiesPane = playableDivinitiesLoader.load();
        Parent root = playerDivinityLoader.load();
        // Scene loginScene = new Scene(loginPane, 750, 500);

        Scene scene=new Scene(root, 400, 250,Color.ALICEBLUE);








        //root.getChildren().add(b);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}